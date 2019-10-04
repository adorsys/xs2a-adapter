package de.adorsys.xs2a.adapter.registry;

import de.adorsys.xs2a.adapter.registry.exception.RegistryIOException;
import de.adorsys.xs2a.adapter.service.AspspCsvService;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class LuceneAspspRepositoryFactory {
    private static final String DEFAULT_LUCENE_DIR_PATH = "lucene";

    public LuceneAspspRepository newLuceneAspspRepository() {
        try {
            return newLuceneAspspRepositoryInternal();
        } catch (IOException e) {
            throw new RegistryIOException(e);
        }
    }

    private LuceneAspspRepository newLuceneAspspRepositoryInternal() throws IOException {
        Directory directory = FSDirectory.open(Paths.get(DEFAULT_LUCENE_DIR_PATH, "index"));
        LuceneAspspRepository luceneAspspRepository = new LuceneAspspRepository(directory);
        AspspCsvService aspspCsvService = new AspspCsvServiceImpl(luceneAspspRepository);
        byte[] csv = aspspCsvService.getCsvFileAsByteArray();

        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        String computedDigest = new BigInteger(1, messageDigest.digest(csv)).toString(16);

        Path digestPath = Paths.get(DEFAULT_LUCENE_DIR_PATH, "digest.md5");
        boolean changed;
        if (Files.exists(digestPath)) {
            String storedDigest = new String(Files.readAllBytes(digestPath));
            changed = !computedDigest.equals(storedDigest);
        } else {
            changed = true;
        }

        if (changed) {
            for (String f : directory.listAll()) {
                directory.deleteFile(f);
            }
            List<Aspsp> aspsps = aspspCsvService.readAllRecords(csv);
            luceneAspspRepository.saveAll(aspsps);
            Files.write(digestPath, computedDigest.getBytes());
        }
        return luceneAspspRepository;
    }
}
