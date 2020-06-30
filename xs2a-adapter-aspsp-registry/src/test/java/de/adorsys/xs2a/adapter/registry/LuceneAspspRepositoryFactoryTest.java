package de.adorsys.xs2a.adapter.registry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class LuceneAspspRepositoryFactoryTest {


    @Test
    void newLuceneAspspRepository() {
        LuceneAspspRepositoryFactory factory = new LuceneAspspRepositoryFactory();

        LuceneAspspRepository repository = factory.newLuceneAspspRepository();

        assertNotNull(repository);
    }
}
