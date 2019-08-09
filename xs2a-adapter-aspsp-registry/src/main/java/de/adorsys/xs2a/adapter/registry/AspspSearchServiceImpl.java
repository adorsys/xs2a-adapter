package de.adorsys.xs2a.adapter.registry;


import de.adorsys.xs2a.adapter.service.AspspRepository;
import de.adorsys.xs2a.adapter.service.AspspSearchService;
import de.adorsys.xs2a.adapter.service.exception.IbanException;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.iban4j.Iban;
import org.iban4j.Iban4jException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class AspspSearchServiceImpl implements AspspSearchService {

    private final Logger log = LoggerFactory.getLogger(AspspSearchServiceImpl.class);
    private final AspspRepository luceneAspspRepository;

    public AspspSearchServiceImpl(AspspRepository luceneAspspRepository) {
        this.luceneAspspRepository = luceneAspspRepository;
    }

    @Override
    public List<Aspsp> findByIban(String iban, String after, int size) {
        String bankCode;
        try {
            bankCode = Iban.valueOf(iban).getBankCode();
        } catch (Iban4jException e) {
            log.error(iban, e);
            throw new IbanException(e.getMessage());
        }
        if (bankCode == null) {
            throw new IbanException("Failed to extract the bank code from the iban");
        }
        return findByBankCode(bankCode, after, size);
    }

    @Override
    public Optional<Aspsp> findById(String id) {
        return luceneAspspRepository.findById(id);
    }

    @Override
    public List<Aspsp> findByBic(String bic, String after, int size) {
        return luceneAspspRepository.findByBic(bic, after, size);
    }

    @Override
    public List<Aspsp> findByBankCode(String bankCode, String after, int size) {
        return luceneAspspRepository.findByBankCode(bankCode, after, size);
    }

    @Override
    public List<Aspsp> findByName(String name, String after, int size) {
        return luceneAspspRepository.findByName(name, after, size);
    }

    @Override
    public List<Aspsp> findAll(String after, int size) {
        return luceneAspspRepository.findAll(after, size);
    }

    @Override
    public List<Aspsp> findLike(Aspsp aspsp, String after, int size) {
        return luceneAspspRepository.findLike(aspsp, after, size);
    }
}
