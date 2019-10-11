package de.adorsys.xs2a.adapter.registry;

import de.adorsys.xs2a.adapter.service.AspspCsvService;
import de.adorsys.xs2a.adapter.service.AspspRepository;
import de.adorsys.xs2a.adapter.service.AspspService;
import de.adorsys.xs2a.adapter.service.PropertyUtil;
import de.adorsys.xs2a.adapter.service.model.Aspsp;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static de.adorsys.xs2a.adapter.registry.AspspCsvServiceImpl.CSV_ASPSP_ADAPTER_CONFIG_FILE_PATH;

public class AspspServiceImpl implements AspspService {
    private final AspspRepository aspspRepository;
    private final AspspCsvService aspspCsvService;

    public AspspServiceImpl(AspspRepository aspspRepository, AspspCsvService aspspCsvService) {
        this.aspspRepository = aspspRepository;
        this.aspspCsvService = aspspCsvService;
    }

    @Override
    public Aspsp create(Aspsp aspsp) {
        return aspspRepository.save(aspsp);
    }

    @Override
    public Aspsp update(Aspsp aspsp) {
        return aspspRepository.save(aspsp);
    }

    @Override
    public void deleteById(String aspspId) {
        aspspRepository.deleteById(aspspId);
    }

    @Override
    public List<Aspsp> readAll() {
        return aspspRepository.findAll();
    }

    @Override
    public void importAspsps(List<Aspsp> aspsps) {
        String pathToCsvFile = PropertyUtil.readProperty(CSV_ASPSP_ADAPTER_CONFIG_FILE_PATH);

        if (pathToCsvFile.isEmpty()) {
            throw new RuntimeException(
                String.format(
                    "Import operation could be proceeded on custom CSV file only. " +
                        "Path to custom file should be provided through [%s] property.",
                    CSV_ASPSP_ADAPTER_CONFIG_FILE_PATH
                )
            );
        }

        if (!Files.isWritable(Paths.get(pathToCsvFile))) {
            throw new RuntimeException(String.format("File [%s] does not exist or is not writable", pathToCsvFile));
        }

        aspspRepository.deleteAll();
        aspspRepository.saveAll(aspsps);
        aspspCsvService.saveCsv();
    }
}
