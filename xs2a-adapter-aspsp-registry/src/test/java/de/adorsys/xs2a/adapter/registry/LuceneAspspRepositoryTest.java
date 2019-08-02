package de.adorsys.xs2a.adapter.registry;

import de.adorsys.xs2a.adapter.registry.exception.RegistryIOException;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class LuceneAspspRepositoryTest {

    LuceneAspspRepository luceneAspspRepository = new LuceneAspspRepository(new ByteBuffersDirectory());

    @Test
    public void saveCanHandleNullProperties() {
        luceneAspspRepository.save(new Aspsp());
        // expect no exceptions
    }

    @Test(expected = RegistryIOException.class)
    public void findByIdThrowsIOExceptionWhenIndexDoesntExist() {
        luceneAspspRepository.findById("id");
    }

    @Test
    public void findById_NotFound() {
        luceneAspspRepository.save(new Aspsp());
        Optional<Aspsp> found = luceneAspspRepository.findById("id");
        assertThat(found).isEmpty();
    }

    @Test
    public void findById_Found() {
        Aspsp aspsp = new Aspsp();
        aspsp.setId("id");
        luceneAspspRepository.save(aspsp);
        Optional<Aspsp> found = luceneAspspRepository.findById("id");
        assertThat(found).get().hasFieldOrPropertyWithValue("id", "id");
    }

    @Test
    public void findByBic_NotFound() {
        luceneAspspRepository.save(new Aspsp());
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
        assertThat(found).hasSize(3);
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

        List<Aspsp> firstPage = luceneAspspRepository.findAll(2);
        assertThat(firstPage).hasSize(2);

        List<Aspsp> secondPage = luceneAspspRepository.findAll(firstPage.get(1).getPaginationId(), 2);
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

        Aspsp aspsp = new Aspsp();
        aspsp.setBic("ABC");
        aspsp.setBankCode("123");
        List<Aspsp> found = luceneAspspRepository.findLike(aspsp);
        assertThat(found).hasSize(2);
    }
}
