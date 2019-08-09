package de.adorsys.xs2a.adapter.registry;

import de.adorsys.xs2a.adapter.registry.exception.RegistryIOException;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class LuceneAspspRepositoryTest {
    private static final String ASPSP_ID = "1";

    private LuceneAspspRepository luceneAspspRepository = new LuceneAspspRepository(new ByteBuffersDirectory());
    private Aspsp aspsp;

    @Before
    public void setUp() {
        aspsp = new Aspsp();
        aspsp.setId(ASPSP_ID);
        luceneAspspRepository.writeToIndex(index -> {
            luceneAspspRepository.save(index, aspsp);
            return aspsp;
        });
    }

    @Test
    public void deleteById() {
        luceneAspspRepository.deleteById(ASPSP_ID);
    }

    @Test
    public void update() {
        luceneAspspRepository.save(aspsp);
    }

    @Test
    public void saveCanHandleNullProperties() {
        luceneAspspRepository.save(new Aspsp());
        // expect no exceptions
    }

    @Test(expected = RegistryIOException.class)
    public void findByIdThrowsIOExceptionWhenIndexDoesntExist() {
        new LuceneAspspRepository(new ByteBuffersDirectory()).findById("id");
    }

    @Test
    public void findById_NotFound() {
        Optional<Aspsp> found = luceneAspspRepository.findById("id");
        assertThat(found).isEmpty();
    }

    @Test
    public void findById_Found() {
        Optional<Aspsp> found = luceneAspspRepository.findById(ASPSP_ID);
        assertThat(found).get().hasFieldOrPropertyWithValue("id", ASPSP_ID);
    }

    @Test
    public void findByBic_NotFound() {
        List<Aspsp> found = luceneAspspRepository.findByBic("bic");
        assertThat(found).isEmpty();
    }

    @Test
    public void findByBic_Found() {
        Aspsp aspsp1 = new Aspsp();
        aspsp1.setBic("BIC1");
        luceneAspspRepository.save(aspsp1);

        Aspsp aspsp2 = new Aspsp();
        aspsp2.setBic("BIC2");
        luceneAspspRepository.save(aspsp2);

        List<Aspsp> found = luceneAspspRepository.findByBic("BIC1");
        assertThat(found).hasSize(1);
        assertThat(found.get(0)).hasFieldOrPropertyWithValue("bic", "BIC1");
    }

    @Test
    public void findByBicUsesPrefixSearch() {
        Aspsp aspsp1 = new Aspsp();
        aspsp1.setBic("BIC1");
        luceneAspspRepository.save(aspsp1);

        Aspsp aspsp2 = new Aspsp();
        aspsp2.setBic("BIC2");
        luceneAspspRepository.save(aspsp2);

        List<Aspsp> found = luceneAspspRepository.findByBic("BIC");
        assertThat(found).hasSize(2);
    }

    @Test
    public void findByNameUsesPrefixSearch() {
        Aspsp aspsp1 = new Aspsp();
        aspsp1.setName("Sparkasse Nürnberg - Geschäftsstelle");
        luceneAspspRepository.save(aspsp1);

        Aspsp aspsp2 = new Aspsp();
        aspsp2.setName("VR Bank Nürnberg");
        luceneAspspRepository.save(aspsp2);

        Aspsp aspsp3 = new Aspsp();
        aspsp3.setName("Commerzbank");
        luceneAspspRepository.save(aspsp3);

        Iterable<Aspsp> found = luceneAspspRepository.findByName("nurnberg");
        assertThat(found).hasSize(2);
    }

    @Test
    public void findAll() {
        Aspsp aspsp1 = new Aspsp();
        luceneAspspRepository.save(aspsp1);

        Aspsp aspsp2 = new Aspsp();
        luceneAspspRepository.save(aspsp2);

        Aspsp aspsp3 = new Aspsp();
        luceneAspspRepository.save(aspsp3);

        Iterable<Aspsp> found = luceneAspspRepository.findAll();
        assertThat(found).hasSize(4);
    }

    @Test
    public void findAllWithPageSize() {
        Aspsp aspsp1 = new Aspsp();
        luceneAspspRepository.save(aspsp1);

        Aspsp aspsp2 = new Aspsp();
        luceneAspspRepository.save(aspsp2);

        Aspsp aspsp3 = new Aspsp();
        luceneAspspRepository.save(aspsp3);

        Iterable<Aspsp> found = luceneAspspRepository.findAll(2);
        assertThat(found).hasSize(2);
    }

    @Test
    public void findAllWithPagination() {
        Aspsp aspsp1 = new Aspsp();
        luceneAspspRepository.save(aspsp1);

        Aspsp aspsp2 = new Aspsp();
        luceneAspspRepository.save(aspsp2);

        Aspsp aspsp3 = new Aspsp();
        luceneAspspRepository.save(aspsp3);

        List<Aspsp> firstPage = luceneAspspRepository.findAll(3);
        assertThat(firstPage).hasSize(3);

        List<Aspsp> secondPage = luceneAspspRepository.findAll(firstPage.get(2).getPaginationId(), 2);
        assertThat(secondPage).hasSize(1);
    }

    @Test
    public void findLike() {
        Aspsp aspsp1 = new Aspsp();
        aspsp1.setBic("ABCDEF");
        luceneAspspRepository.save(aspsp1);

        Aspsp aspsp2 = new Aspsp();
        aspsp2.setBankCode("123446");
        luceneAspspRepository.save(aspsp2);

        Aspsp aspsp3 = new Aspsp();
        aspsp3.setBic("ABC");
        aspsp3.setBankCode("123");
        List<Aspsp> found = luceneAspspRepository.findLike(aspsp3);
        assertThat(found).hasSize(2);
    }
}
