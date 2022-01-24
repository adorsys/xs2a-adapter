/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

package de.adorsys.xs2a.adapter.registry;

import de.adorsys.xs2a.adapter.api.AspspRepository;
import de.adorsys.xs2a.adapter.api.exception.IbanException;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.AspspScaApproach;
import de.adorsys.xs2a.adapter.registry.exception.RegistryIOException;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.iban4j.Iban;
import org.iban4j.Iban4jException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class LuceneAspspRepository implements AspspRepository {
    private static final Logger logger = LoggerFactory.getLogger(LuceneAspspRepository.class);

    private static final String SEMICOLON_SEPARATOR = ";";
    private static final String ZERO_OR_MORE_OF_ANY_CHARS_REGEX = ".*";
    private static final float HIGH_PRIORITY = 1.5f;
    private static final float MEDIUM_PRIORITY = 1;
    private static final float LOW_PRIORITY = 0.5f;

    private static final String ID_FIELD_NAME = "id";
    private static final String NAME_FIELD_NAME = "name";
    private static final String URL_FIELD_NAME = "url";
    private static final String BIC_FIELD_NAME = "bic";
    private static final String BANK_CODE_FIELD_NAME = "bankCode";
    private static final String ADAPTER_ID_FIELD_NAME = "adapterId";
    private static final String IDP_URL_FIELD_NAME = "idpUrl";
    private static final String SCA_APPROACHES_FIELD_NAME = "scaApproaches";

    private Directory directory;

    public LuceneAspspRepository(Directory directory) {
        this.directory = directory;
    }

    public Aspsp save(Aspsp aspsp) {
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig();
        try (IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig)) {
            return save(indexWriter, aspsp);
        } catch (IOException e) {
            throw new RegistryIOException(e);
        }
    }

    @Override
    public void deleteById(String aspspId) {
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig();
        try (IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig)) {
            indexWriter.deleteDocuments(new Term(ID_FIELD_NAME, aspspId));
        } catch (IOException e) {
            throw new RegistryIOException(e);
        }
    }

    @Override
    public void deleteAll() {
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig();
        try (IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig)) {
            indexWriter.deleteAll();
        } catch (IOException e) {
            throw new RegistryIOException(e);
        }
    }

    private Aspsp save(IndexWriter indexWriter, Aspsp aspsp) throws IOException {
        Optional<Aspsp> storedAspsp = Optional.empty();

        if (aspsp.getId() == null) {
            aspsp.setId(UUID.randomUUID().toString());
        } else {
            storedAspsp = findById(aspsp.getId());
        }

        Document document = new Document();
        document.add(new StringField(ID_FIELD_NAME, serialize(aspsp.getId()), Field.Store.YES));
        document.add(new TextField(NAME_FIELD_NAME, serialize(aspsp.getName()), Field.Store.YES));
        document.add(new StringField(URL_FIELD_NAME, serialize(aspsp.getUrl()), Field.Store.YES));
        document.add(new StringField(BIC_FIELD_NAME, serialize(aspsp.getBic()), Field.Store.YES));
        document.add(new StringField(BANK_CODE_FIELD_NAME, serialize(aspsp.getBankCode()), Field.Store.YES));
        document.add(new StringField(ADAPTER_ID_FIELD_NAME, serialize(aspsp.getAdapterId()), Field.Store.YES));
        document.add(new StringField(IDP_URL_FIELD_NAME, serialize(aspsp.getIdpUrl()), Field.Store.YES));
        document.add(new StringField(SCA_APPROACHES_FIELD_NAME, serialize(aspsp.getScaApproaches()), Field.Store.YES));

        if (storedAspsp.isPresent()) {
            indexWriter.updateDocument(new Term(ID_FIELD_NAME, aspsp.getId()), document);
        } else {
            indexWriter.addDocument(document);
        }

        return aspsp;
    }

    private String serialize(String s) {
        return String.valueOf(s); // null -> "null"
    }

    private <T extends Enum<T>> String serialize(List<T> data) {
        if (data == null) {
            return "null";
        }

        return data.stream()
                   .map(Enum::toString)
                   .collect(Collectors.joining(SEMICOLON_SEPARATOR));
    }

    @Override
    public void saveAll(List<Aspsp> aspsps) {
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig();
        try (IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig)) {
            for (Aspsp aspsp : aspsps) {
                save(indexWriter, aspsp);
            }
        } catch (IOException e) {
            throw new RegistryIOException(e);
        }
    }

    @Override
    public Optional<Aspsp> findById(String id) {
        logger.debug("Searching for ASPSPs: by ID [{}]", id);
        List<Aspsp> aspsps = find(new TermQuery(new Term(ID_FIELD_NAME, id)), null, 1);
        if (aspsps.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(aspsps.get(0));
    }

    private Optional<Document> getDocument(int docId) {
        try (IndexReader indexReader = DirectoryReader.open(directory)) {
            if (docId < 0 || docId >= indexReader.maxDoc()) {
                return Optional.empty();  // index reader throws IllegalArgumentException if docID is out of bounds
            }
            Document document = indexReader.document(docId);
            return Optional.of(document);

        } catch (IOException e) {
            throw new RegistryIOException(e);
        }
    }

    private String deserialize(String s) {
        if ("null".equals(s)) {
            return null;
        }
        return s;
    }

    /*
     * Possible incoming strings are:
     * - "null"
     * - ""
     * - "DECOUPLED,REDIRECT"
     * - "DECOUPLED, REDIRECT" (with space)
     * - "decoupled,redirect"
     * - "decoupled, redirect" (with space)
     * - "decoupled,blablabla" (with unknown enum value)
     */
    private <T extends Enum<T>> List<T> deserialize(String s, Class<T> klass) {
        if ("null".equals(s)) {
            return null;
        }

        if (s == null || s.isEmpty()) {
            return Collections.emptyList();
        }

        String[] values = s.split(SEMICOLON_SEPARATOR);

        return Arrays.stream(values)
                   // .trim() to handle strings with spaces (like "DECOUPLED, REDIRECT")
                   // .toUpperCase() to handle lowercase strings (like "redirect")
                   .map(value -> Enum.valueOf(klass, value.trim().toUpperCase()))
                   .collect(Collectors.toList());
    }

    @Override
    public List<Aspsp> findByBic(String bic, String after, int size) {
        logger.debug("Searching for ASPSPs: by BIC [{}]", bic);
        Query query = getBicQuery(bic);
        return find(query, after, size);
    }

    private Query getBicQuery(String bic) {
        return new PrefixQuery(new Term(BIC_FIELD_NAME, bic));
    }

    private List<Aspsp> find(Query query, String after, int size) {
        try (IndexReader indexReader = DirectoryReader.open(directory)) {
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);
            ScoreDoc afterDoc = parseScoreDoc(after);
            TopDocs topDocs = indexSearcher.searchAfter(afterDoc, query, size);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            List<Aspsp> aspsps = Arrays.stream(scoreDocs)
                                      .map(this::getDocumentAsAspsp)
                                      .filter(Optional::isPresent)
                                      .map(Optional::get)
                                      .collect(toList());
            logger.debug("Searching for ASPSPs: {} record(s) have been found", aspsps.size());
            return aspsps;
        } catch (IndexNotFoundException e) {
            logger.debug("Searching for ASPSPs: no records have been found");
            return Collections.emptyList();
        } catch (IOException e) {
            throw new RegistryIOException(e);
        }
    }

    private Optional<Aspsp> getDocumentAsAspsp(ScoreDoc scoreDoc) {
        return getDocument(scoreDoc.doc)
                   .map(document -> {
                       Aspsp aspsp = new Aspsp();
                       aspsp.setId(deserialize(document.get(ID_FIELD_NAME)));
                       aspsp.setName(deserialize(document.get(NAME_FIELD_NAME)));
                       aspsp.setUrl(deserialize(document.get(URL_FIELD_NAME)));
                       aspsp.setBic(deserialize(document.get(BIC_FIELD_NAME)));
                       aspsp.setBankCode(deserialize(document.get(BANK_CODE_FIELD_NAME)));
                       aspsp.setAdapterId(deserialize(document.get(ADAPTER_ID_FIELD_NAME)));
                       aspsp.setIdpUrl(deserialize(document.get(IDP_URL_FIELD_NAME)));
                       aspsp.setScaApproaches(deserialize(document.get(SCA_APPROACHES_FIELD_NAME), AspspScaApproach.class));
                       aspsp.setPaginationId(scoreDoc.doc + ":" + scoreDoc.score);
                       return aspsp;
                   });
    }

    private ScoreDoc parseScoreDoc(String id) {
        if (id == null) {
            return null;
        }
        // id should consist of a document id and search score separated by a colon
        // both components are required for org.apache.lucene.search.IndexSearcher.searchAfter to work properly
        String[] components = id.split(":");
        if (components.length != 2) {
            return null;
        }
        try {
            int docId = Integer.parseInt(components[0]);
            float score = Float.parseFloat(components[1]);
            return new ScoreDoc(docId, score);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public List<Aspsp> findByBankCode(String bankCode, String after, int size) {
        logger.debug("Searching for ASPSPs: by bank code [{}}", bankCode);
        Query query = getBankCodeQuery(bankCode);
        return find(query, after, size);
    }

    private Query getBankCodeQuery(String bankCode) {
        return new PrefixQuery(new Term(BANK_CODE_FIELD_NAME, bankCode));
    }

    @Override
    public List<Aspsp> findByName(String name, String after, int size) {
        logger.debug("Searching for ASPSPs: by name [{}]", name);
        BooleanQuery.Builder queryBuilder = new BooleanQuery.Builder();
        queryBuilder.add(getNameFuzzyQuery(name), BooleanClause.Occur.SHOULD);
        queryBuilder.add(getNameRegexpQuery(name), BooleanClause.Occur.SHOULD);
        return find(queryBuilder.build(), after, size);
    }

    private Query getNameFuzzyQuery(String name) {
        return new FuzzyQuery(new Term(NAME_FIELD_NAME, name));
    }

    private Query getNameRegexpQuery(String name) {
        return new RegexpQuery(
            new Term(
                NAME_FIELD_NAME,
                name + ZERO_OR_MORE_OF_ANY_CHARS_REGEX
            )
        );
    }

    @Override
    public List<Aspsp> findAll(String after, int size) {
        logger.debug("Searching for ASPSPs: any {} records", size);
        Query query = new MatchAllDocsQuery();
        return find(query, after, size);
    }

    @Override
    public List<Aspsp> findLike(Aspsp aspsp, String after, int size) {
        if (logger.isDebugEnabled()) {
            logger.debug(buildFindLikeLoggingMessage(aspsp));
        }
        BooleanQuery.Builder queryBuilder = new BooleanQuery.Builder();
        if (aspsp.getName() != null) {
            queryBuilder.add(buildQueryWithPriority(getNameFuzzyQuery(aspsp.getName()), LOW_PRIORITY), BooleanClause.Occur.SHOULD);
            queryBuilder.add(buildQueryWithPriority(getNameRegexpQuery(aspsp.getName()), LOW_PRIORITY), BooleanClause.Occur.SHOULD);
        }
        if (aspsp.getBic() != null && aspsp.getBankCode() != null) {
            String bic = aspsp.getBic();
            String bankCode = aspsp.getBankCode();

            queryBuilder.add(buildQueryWithPriority(getBicAndBankCodeQuery(bic, bankCode), HIGH_PRIORITY), BooleanClause.Occur.SHOULD);
            queryBuilder.add(buildQueryWithPriority(getBicAndEmptyBankCodeQuery(bic), MEDIUM_PRIORITY), BooleanClause.Occur.SHOULD);
            queryBuilder.add(buildQueryWithPriority(getEmptyBicAndBankCodeQuery(bankCode), LOW_PRIORITY), BooleanClause.Occur.SHOULD);
        } else {
            if (aspsp.getBic() != null) {
                queryBuilder.add(getBicQuery(aspsp.getBic()), BooleanClause.Occur.SHOULD);
            }
            if (aspsp.getBankCode() != null) {
                queryBuilder.add(getBankCodeQuery(aspsp.getBankCode()), BooleanClause.Occur.SHOULD);
            }
        }

        return find(queryBuilder.build(), after, size);
    }

    private String buildFindLikeLoggingMessage(Aspsp aspsp) {
        StringBuilder messageBuilder = new StringBuilder("Searching for ASPSPs: by");

        if (aspsp.getName() != null) {
            messageBuilder.append(" name [")
                .append(aspsp.getName())
                .append("] ,");
        }

        if (aspsp.getBic() != null) {
            messageBuilder.append(" BIC [")
                .append(aspsp.getBic())
                .append("] ,");
        }

        if (aspsp.getBankCode() != null) {
            messageBuilder.append(" bank code [")
                .append(aspsp.getBankCode())
                .append("] ,");
        }

        // .substring(0, messageBuilder.length() - 1) to remove the comma
        return messageBuilder.toString().substring(0, messageBuilder.length() - 1);
    }

    @Override
    public List<Aspsp> findByIban(String iban, String after, int size) {
        String bankCode;
        try {
            bankCode = Iban.valueOf(iban).getBankCode();
        } catch (Iban4jException e) {
            // iban4j exception not used as the cause because the message might contain account number
            throw new IbanException("Invalid iban");
        }
        if (bankCode == null) {
            throw new IbanException("Failed to extract the bank code from the iban");
        }
        return findByBankCode(bankCode, after, size);
    }

    private BoostQuery buildQueryWithPriority(Query query, float priority) {
        return new BoostQuery(query, priority);
    }

    private Query getBicAndBankCodeQuery(String bic, String bankCode) {
        return new BooleanQuery.Builder()
                   .add(getBicQuery(bic), BooleanClause.Occur.MUST)
                   .add(getBankCodeQuery(bankCode), BooleanClause.Occur.MUST)
                   .build();
    }

    private Query getBicAndEmptyBankCodeQuery(String bic) {
        return new BooleanQuery.Builder()
                   .add(getBicQuery(bic), BooleanClause.Occur.MUST)
                   .add(getBankCodeQuery("null"), BooleanClause.Occur.MUST)
                   .build();
    }

    private Query getEmptyBicAndBankCodeQuery(String bankCode) {
        return new BooleanQuery.Builder()
                   .add(getBicQuery("null"), BooleanClause.Occur.MUST)
                   .add(getBankCodeQuery(bankCode), BooleanClause.Occur.MUST)
                   .build();
    }
}
