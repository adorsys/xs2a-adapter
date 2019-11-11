package de.adorsys.xs2a.adapter.registry;

import de.adorsys.xs2a.adapter.service.AspspReadOnlyRepository;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class LuceneAspspRepositoryTest {
    private static final String ASPSP_ID = "1";

    private LuceneAspspRepository luceneAspspRepository = new LuceneAspspRepository(new ByteBuffersDirectory());

    @Test
    public void deleteById() {
        Aspsp aspsp = new Aspsp();
        aspsp.setId(ASPSP_ID);

        luceneAspspRepository.save(aspsp);
        List<Aspsp> all = luceneAspspRepository.findAll(AspspReadOnlyRepository.DEFAULT_SIZE);

        assertThat(all).hasSize(1);

        luceneAspspRepository.deleteById(ASPSP_ID);

        Optional<Aspsp> aspsp1 = luceneAspspRepository.findById(ASPSP_ID);
        assertThat(aspsp1.isPresent()).isFalse();
    }

    @Test
    public void deleteAll() {
        Aspsp aspsp1 = new Aspsp();
        luceneAspspRepository.save(aspsp1);

        Aspsp aspsp2 = new Aspsp();
        luceneAspspRepository.save(aspsp2);

        List<Aspsp> found = luceneAspspRepository.findAll();
        assertThat(found).hasSize(2);

        luceneAspspRepository.deleteAll();

        found = luceneAspspRepository.findAll();

        assertThat(found).isEmpty();
    }

    @Test
    public void saveCanHandleNullProperties() {
        luceneAspspRepository.save(new Aspsp());
        // expect no exceptions
    }

    @Test
    public void findByIdReturnsEmptyWhenIndexDoesntExist() {
        Optional<Aspsp> found = new LuceneAspspRepository(new ByteBuffersDirectory()).findById("id");
        assertThat(found).isEmpty();
    }

    @Test
    public void findById_NotFound() {
        Optional<Aspsp> found = luceneAspspRepository.findById("id");
        assertThat(found).isEmpty();
    }

    @Test
    public void findById_Found() {
        Aspsp aspsp = new Aspsp();
        aspsp.setId(ASPSP_ID);

        luceneAspspRepository.save(aspsp);
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

        Aspsp aspsp3 = new Aspsp();
        aspsp3.setBic("ABC");
        aspsp3.setBankCode("123");
        List<Aspsp> found = luceneAspspRepository.findLike(aspsp3);
        assertThat(found).hasSize(2);
    }

    @Test
    public void findLikeShouldBeOrderedByPriorities_BicBankCodeName_BicBankCode_Bic_BankCode_Name() {
        List<Aspsp> expected = Arrays.asList(
            buildAspsp("TESTBICA", "111111", "SomeBank"),
            buildAspsp("TESTBICA", "111111"),
            buildAspsp("TESTBICA", "111111"),
            buildAspsp("TESTBICA", "111111"),
            buildAspsp("TESTBICA", "111111"),
            buildAspsp("TESTBICA", "111111"),
            buildAspsp("TESTBICA", null),
            buildAspsp("TESTBICA", null),
            buildAspsp(null, "111111"),
            buildAspsp(null, "111111")
        );

        luceneAspspRepository.save(buildAspsp("TESTBICA", "111111"));
        luceneAspspRepository.save(buildAspsp("TESTBICA", "111111"));
        luceneAspspRepository.save(buildAspsp("TESTBICA", "111111"));
        luceneAspspRepository.save(buildAspsp("TESTBICA", "111111"));
        luceneAspspRepository.save(buildAspsp("TESTBICA", "111111"));
        luceneAspspRepository.save(buildAspsp("TESTBICB", "222222"));
        luceneAspspRepository.save(buildAspsp("TESTBICC", "333333"));
        luceneAspspRepository.save(buildAspsp("TESTBICD", "444444"));
        luceneAspspRepository.save(buildAspsp("TESTBICE", "555555"));
        luceneAspspRepository.save(buildAspsp(null, "111111"));
        luceneAspspRepository.save(buildAspsp(null, "111111"));
        luceneAspspRepository.save(buildAspsp(null, "111111"));
        luceneAspspRepository.save(buildAspsp(null, "111111"));
        luceneAspspRepository.save(buildAspsp("TESTBICA", null));
        luceneAspspRepository.save(buildAspsp("TESTBICA", null));
        luceneAspspRepository.save(buildAspsp("TESTBICA", "111111", "SomeBank"));

        List<Aspsp> actual = luceneAspspRepository.findLike(buildAspsp("TESTBICA", "111111", "SomeBank"));

        assertThat(actual).hasSize(10);
        assertThat(actual).isEqualTo(expected);
    }

    private Aspsp buildAspsp(String bic, String bankCode) {
        Aspsp aspsp = new Aspsp();
        aspsp.setBic(bic);
        aspsp.setBankCode(bankCode);
        return aspsp;
    }

    private Aspsp buildAspsp(String bic, String bankCode, String name) {
        Aspsp aspsp = new Aspsp();
        aspsp.setBic(bic);
        aspsp.setBankCode(bankCode);
        aspsp.setName(name);
        return aspsp;
    }

    @Test
    public void saveAllTreatsEmptyFieldsAsNull() {
        Aspsp aspsp = new Aspsp();
        aspsp.setId(ASPSP_ID);
        aspsp.setIdpUrl("");

        luceneAspspRepository.saveAll(singletonList(aspsp));

        Optional<Aspsp> found = luceneAspspRepository.findById(ASPSP_ID);
        assertThat(found).get().extracting(Aspsp::getIdpUrl).isNull();
    }

    @Test
    public void saveTreatsEmptyIdAsNull() {
        Aspsp aspsp = new Aspsp();
        aspsp.setId("");

        Aspsp saved = luceneAspspRepository.save(aspsp);

        assertThat(saved.getId()).isNotEmpty();
    }
}
