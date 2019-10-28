package de.adorsys.xs2a.adapter.registry;

import de.adorsys.xs2a.adapter.registry.exception.RegistryIOException;
import de.adorsys.xs2a.adapter.service.AspspRepository;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.AspspScaApproach;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class LuceneAspspRepository implements AspspRepository {
    private static final String SEMICOLON_SEPARATOR = ";";
    private static final String ZERO_OR_MORE_OF_ANY_CHARS_REGEX = ".*";

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
            return Arrays.stream(scoreDocs)
                .map(this::getDocumentAsAspsp)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());
        } catch (IndexNotFoundException e) {
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
        Query query = getBankCodeQuery(bankCode);
        return find(query, after, size);
    }

    private Query getBankCodeQuery(String bankCode) {
        return new PrefixQuery(new Term(BANK_CODE_FIELD_NAME, bankCode));
    }

    @Override
    public List<Aspsp> findByName(String name, String after, int size) {
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
        Query query = new MatchAllDocsQuery();
        return find(query, after, size);
    }

    @Override
    public List<Aspsp> findLike(Aspsp aspsp, String after, int size) {
        BooleanQuery.Builder queryBuilder = new BooleanQuery.Builder();
        if (aspsp.getName() != null) {
            queryBuilder.add(getNameFuzzyQuery(aspsp.getName()), BooleanClause.Occur.SHOULD);
            queryBuilder.add(getNameRegexpQuery(aspsp.getName()), BooleanClause.Occur.SHOULD);
        }
        if (aspsp.getBic() != null) {
            queryBuilder.add(getBicQuery(aspsp.getBic()), BooleanClause.Occur.SHOULD);
        }
        if (aspsp.getBankCode() != null) {
            queryBuilder.add(getBankCodeQuery(aspsp.getBankCode()), BooleanClause.Occur.SHOULD);
        }
        return find(queryBuilder.build(), after, size);
    }
}
