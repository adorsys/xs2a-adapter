package de.adorsys.xs2a.adapter.registry;

import de.adorsys.xs2a.adapter.service.AspspReadOnlyRepository;
import de.adorsys.xs2a.adapter.service.exception.IbanException;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.AspspScaApproach;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.*;

class LuceneAspspRepositoryTest {
    private static final String ASPSP_ID = "1";
    private static final Integer SIZE = Integer.MAX_VALUE;
    private static final String AFTER = "0";

    private LuceneAspspRepository luceneAspspRepository = new LuceneAspspRepository(new ByteBuffersDirectory());

    @Test
    void deleteById() {
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
    void deleteAll() {
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
    void saveCanHandleNullProperties() {
        try {
            luceneAspspRepository.save(new Aspsp());
        } catch (Throwable e) {
            fail("expect no exceptions");
        }
    }

    @Test
    void findByIdReturnsEmptyWhenIndexDoesntExist() {
        Optional<Aspsp> found = new LuceneAspspRepository(new ByteBuffersDirectory()).findById("id");
        assertThat(found).isEmpty();
    }

    @Test
    void findById_NotFound() {
        Optional<Aspsp> found = luceneAspspRepository.findById("id");
        assertThat(found).isEmpty();
    }

    @Test
    void findById_Found() {
        Aspsp aspsp = new Aspsp();
        aspsp.setId(ASPSP_ID);

        luceneAspspRepository.save(aspsp);
        Optional<Aspsp> found = luceneAspspRepository.findById(ASPSP_ID);
        assertThat(found).get().hasFieldOrPropertyWithValue("id", ASPSP_ID);
    }

    @Test
    void findByBic_NotFound() {
        List<Aspsp> found = luceneAspspRepository.findByBic("bic");
        assertThat(found).isEmpty();
    }

    @Test
    void findByBic_Found() {
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
    void findByBicUsesPrefixSearch() {
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
    void findByNameUsesPrefixSearch() {
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
    void findAll() {
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
    void findAllWithPageSize() {
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
    void findAllWithPagination() {
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
    void findLike() {
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
    void findLikeShouldBeOrderedByPriorities_BicBankCodeName_BicBankCode_Bic_BankCode_Name() {
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
    void saveAllTreatsEmptyFieldsAsNull() {
        Aspsp aspsp = new Aspsp();
        aspsp.setId(ASPSP_ID);
        aspsp.setIdpUrl("");

        luceneAspspRepository.saveAll(singletonList(aspsp));

        Optional<Aspsp> found = luceneAspspRepository.findById(ASPSP_ID);
        assertThat(found).get().extracting(Aspsp::getIdpUrl).isNull();
    }

    @Test
    void saveTreatsEmptyIdAsNull() {
        Aspsp aspsp = new Aspsp();
        aspsp.setId("");

        Aspsp saved = luceneAspspRepository.save(aspsp);

        assertThat(saved.getId()).isNotEmpty();
    }

    @Test
    void findByBankCode() {
        String bankCode = "00000000";
        Aspsp aspsp = buildAspsp(null, bankCode);
        aspsp.setScaApproaches(singletonList(AspspScaApproach.EMBEDDED));

        luceneAspspRepository.save(aspsp);

        List<Aspsp> actual = luceneAspspRepository.findByBankCode(bankCode, AFTER, SIZE);

        assertThat(actual.isEmpty()).isFalse();
        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual.get(0).getBankCode()).isEqualTo(bankCode);
    }

    @Test
    void findByIban() {
        String iban = "DE86999999990000001000";
        String bankCode = "99999999";
        Aspsp aspsp = buildAspsp("", bankCode);

        luceneAspspRepository.save(aspsp);

        List<Aspsp> actual = luceneAspspRepository.findByIban(iban, AFTER, SIZE);

        assertThat(actual.isEmpty()).isFalse();
        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual.get(0).getBankCode()).isEqualTo(bankCode);
    }

    @Test
    void findByIban_invalidIban() {
        String iban = "DE123123123123123123123";
        Aspsp aspsp = new Aspsp();

        luceneAspspRepository.save(aspsp);

        Throwable actual = catchThrowable(() -> {
            luceneAspspRepository.findByIban(iban, AFTER, SIZE);
        });

        assertThat(actual).isInstanceOf(IbanException.class);
    }

    @Test
    void findLike_bicOnlyAndBankCodeOnly() {
        String bic = "ABABAB";
        String bankCode = "00000000";
        Aspsp bicOnly = buildAspsp(bic, null);
        Aspsp bankCodeOnly = buildAspsp(null, bankCode);

        luceneAspspRepository.saveAll(Arrays.asList(bicOnly, bankCodeOnly));

        List<Aspsp> bicOnlyActual = luceneAspspRepository.findLike(bicOnly, AFTER, SIZE);
        List<Aspsp> bankCodeOnlyActual = luceneAspspRepository.findLike(bankCodeOnly, AFTER, SIZE);

        assertThat(bicOnlyActual.isEmpty()).isFalse();
        assertThat(bicOnlyActual.size()).isEqualTo(1);
        assertThat(bicOnlyActual.get(0).getBic()).isEqualTo(bic);
        assertThat(bankCodeOnlyActual.isEmpty()).isFalse();
        assertThat(bankCodeOnlyActual.size()).isEqualTo(1);
        assertThat(bankCodeOnlyActual.get(0).getBankCode()).isEqualTo(bankCode);
    }
}
