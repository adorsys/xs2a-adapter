package de.adorsys.xs2a.adapter.registry;

import de.adorsys.xs2a.adapter.service.AspspCsvService;
import de.adorsys.xs2a.adapter.service.AspspRepository;
import de.adorsys.xs2a.adapter.service.AspspService;
import de.adorsys.xs2a.adapter.service.model.Aspsp;

import java.util.List;

public class AspspServiceImpl implements AspspService {
    private final AspspRepository aspspRepository;
    private final AspspCsvService aspspCsvService;

    public AspspServiceImpl(AspspRepository aspspRepository, AspspCsvService aspspCsvService) {
        this.aspspRepository = aspspRepository;
        this.aspspCsvService = aspspCsvService;
    }

    @Override
    public void importAspsps(List<Aspsp> aspsps) {
        aspspRepository.deleteAll();
        aspspRepository.saveAll(aspsps);
        aspspCsvService.saveCsv();
    }
}
