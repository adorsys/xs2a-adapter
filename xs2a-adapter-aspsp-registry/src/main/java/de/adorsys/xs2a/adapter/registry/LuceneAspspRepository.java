package de.adorsys.xs2a.adapter.registry;

import de.adorsys.xs2a.adapter.registry.exception.RegistryIOException;
import de.adorsys.xs2a.adapter.service.AspspModifyRepository;
import de.adorsys.xs2a.adapter.service.AspspRepository;
import de.adorsys.xs2a.adapter.service.exception.AspspRegistrationException;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

public class LuceneAspspRepository implements AspspRepository, AspspModifyRepository {

    private static final String ID_FIELD_NAME = "id";
    private static final String NAME_FIELD_NAME = "name";
    private static final String URL_FIELD_NAME = "url";
    private static final String BIC_FIELD_NAME = "bic";
    private static final String BANK_CODE_FIELD_NAME = "bankCode";
    private static final String ADAPTER_ID_FIELD_NAME = "adapterId";

    private Directory directory;

    public LuceneAspspRepository(Directory directory) {
        this.directory = directory;
    }

    public Aspsp save(Aspsp aspsp) {
        if (aspsp == null) {
            throw new AspspRegistrationException("Aspsp must be not null");
        }
        if (aspsp.getId() == null) {
            aspsp.setId(UUID.randomUUID().toString());
        }
        Optional<Aspsp> optional = findById(aspsp.getId());

        writeToIndex(index -> {
            if (optional.isPresent()) {
                update(index, aspsp);
            } else {
                save(index, aspsp);
            }
            return optional;
        });
        return aspsp;
    }

    @Override
    public void deleteById(String aspspId) {
        Optional<Aspsp> aspsp = findById(aspspId);
        aspsp.ifPresent(value -> writeToIndex(writer -> writer.deleteDocuments(new Term(ID_FIELD_NAME, aspspId))));
    }

    void save(IndexWriter indexWriter, Aspsp aspsp) throws IOException {
        Document document = buildDocument(aspsp);
        indexWriter.addDocument(document);
    }

    private void update(IndexWriter indexWriter, Aspsp aspsp) throws IOException {
        Document document = buildDocument(aspsp);
        indexWriter.updateDocument(new Term(ID_FIELD_NAME, aspsp.getId()), document);
    }

    private Document buildDocument(Aspsp aspsp) {
        Document document = new Document();
        document.add(new StringField(ID_FIELD_NAME, serialize(aspsp.getId()), Field.Store.YES));
        document.add(new TextField(NAME_FIELD_NAME, serialize(aspsp.getName()), Field.Store.YES));
        document.add(new StringField(URL_FIELD_NAME, serialize(aspsp.getUrl()), Field.Store.YES));
        document.add(new StringField(BIC_FIELD_NAME, serialize(aspsp.getBic()), Field.Store.YES));
        document.add(new StringField(BANK_CODE_FIELD_NAME, serialize(aspsp.getBankCode()), Field.Store.YES));
        document.add(new StringField(ADAPTER_ID_FIELD_NAME, serialize(aspsp.getAdapterId()), Field.Store.YES));
        return document;
    }

    private String serialize(String s) {
        return String.valueOf(s); // null -> "null"
    }

    public void saveAll(List<Aspsp> aspsps) {
        writeToIndex(writer -> {
            for (Aspsp aspsp : aspsps) {
                save(writer, aspsp);
            }
            return aspsps;
        });
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
        return readFromIndex(index -> {
            if (docId < 0 || docId >= index.maxDoc()) {
                return Optional.empty();  // index reader throws IllegalArgumentException if docID is out of bounds
            }
            Document document = index.document(docId);
            return Optional.of(document);
        });
    }

    private String deserialize(String s) {
        if ("null".equals(s)) {
            return null;
        }
        return s;
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
        return readFromIndex(index -> {
            IndexSearcher indexSearcher = new IndexSearcher(index);
            ScoreDoc afterDoc = parseScoreDoc(after);
            TopDocs topDocs = indexSearcher.searchAfter(afterDoc, query, size);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            return Arrays.stream(scoreDocs)
                       .map(this::getDocumentAsAspsp)
                       .filter(Optional::isPresent)
                       .map(Optional::get)
                       .collect(toList());

        });
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
        Query query = getNameQuery(name);
        return find(query, after, size);
    }

    private Query getNameQuery(String name) {
        return new FuzzyQuery(new Term(NAME_FIELD_NAME, name));
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
            queryBuilder.add(getNameQuery(aspsp.getName()), BooleanClause.Occur.SHOULD);
        }
        if (aspsp.getBic() != null) {
            queryBuilder.add(getBicQuery(aspsp.getBic()), BooleanClause.Occur.SHOULD);
        }
        if (aspsp.getBankCode() != null) {
            queryBuilder.add(getBankCodeQuery(aspsp.getBankCode()), BooleanClause.Occur.SHOULD);
        }
        return find(queryBuilder.build(), after, size);
    }

    private <T> T readFromIndex(IndexExecutor<IndexReader, T> indexExecutor) {
        try (IndexReader indexReader = DirectoryReader.open(directory)) {
            return indexExecutor.execute(indexReader);
        } catch (IOException e) {
            throw new RegistryIOException(e);
        }
    }

    <T> void writeToIndex(IndexExecutor<IndexWriter, T> indexExecutor) {
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig();
        try (IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig)) {
            indexExecutor.execute(indexWriter);
        } catch (IOException e) {
            throw new RegistryIOException(e);
        }
    }

    interface IndexExecutor<T, R> {
        R execute(T index) throws IOException;
    }
}
