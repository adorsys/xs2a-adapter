package de.adorsys.xs2a.adapter.registry;

import de.adorsys.xs2a.adapter.service.AspspCsvService;
import de.adorsys.xs2a.adapter.service.AspspRepository;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.AspspScaApproach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static de.adorsys.xs2a.adapter.registry.AspspServiceImpl.CSV_ASPSP_ADAPTER_CONFIG_FILE_PATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AspspServiceImplTest {
    private static final String ID = "81cecc67-6d1b-4169-b67c-2de52b99a0cc";
    private static final String ASPSP_NAME = "BNP Paribas Germany, Consorsbank";
    private static final String BIC = "CSDBDE71XXX";
    private static final String URL = "https://xs2a-sndbx.consorsbank.de";
    private static final String ADAPTER_ID = "consors-bank-adapter";
    private static final String BANK_CODE = "76030080";
    private static final String IDP_URL = "https://example.com";
    private static final String PATH_TO_NOT_EXISTING_FILE = "/path/to/not/existing/file";
    private static final String PATH_TO_EXISTING_FILE = "./src/test/resources/aspsp-adapter-config-test.csv";
    private static final List<AspspScaApproach> SCA_APPROACHES = Arrays.asList(AspspScaApproach.EMBEDDED, AspspScaApproach.REDIRECT);
    private static final Aspsp ASPSP = buildAspsp();
    private static final List<Aspsp> ASPSPS = Collections.singletonList(ASPSP);

    @Mock
    private AspspRepository aspspRepository;
    @Mock
    private AspspCsvService aspspCsvService;

    @InjectMocks
    private AspspServiceImpl service;

    @Test
    public void create() {
        when(aspspRepository.save(ASPSP)).thenReturn(ASPSP);

        Aspsp result = service.create(ASPSP);

        verify(aspspRepository, times(1)).save(ASPSP);
        assertThat(result).isEqualTo(ASPSP);
    }

    @Test
    public void update() {
        when(aspspRepository.save(ASPSP)).thenReturn(ASPSP);

        Aspsp result = service.update(ASPSP);

        verify(aspspRepository, times(1)).save(ASPSP);
        assertThat(result).isEqualTo(ASPSP);
    }

    @Test
    public void deleteById() {
        doNothing().when(aspspRepository).deleteById(ID);

        service.deleteById(ID);

        verify(aspspRepository, times(1)).deleteById(ID);
    }

    @Test
    public void readAll() {
        when(aspspRepository.findAll()).thenReturn(ASPSPS);

        List<Aspsp> result = service.readAll();

        verify(aspspRepository, times(1)).findAll();
        assertThat(result).isEqualTo(ASPSPS);
    }

    @Test(expected = RuntimeException.class)
    public void importAspsps_failure_pathToFilePropertyIsEmpty() {
        removePathToCsvFileSystemProperty();

        service.importAspsps(ASPSPS);
    }

    @Test(expected = RuntimeException.class)
    public void importAspsps_failure_fileDoesNotExist() {
        setPathToCsvFileSystemProperty(PATH_TO_NOT_EXISTING_FILE);

        service.importAspsps(ASPSPS);
    }

    @Test
    public void importAspsps_success() {
        setPathToCsvFileSystemProperty(PATH_TO_EXISTING_FILE);

        doNothing().when(aspspRepository).deleteAll();
        doNothing().when(aspspRepository).saveAll(ASPSPS);
        doNothing().when(aspspCsvService).saveCsv();

        service.importAspsps(ASPSPS);

        verify(aspspRepository, times(1)).deleteAll();
        verify(aspspRepository, times(1)).saveAll(ASPSPS);
        verify(aspspCsvService, times(1)).saveCsv();
    }

    private static Aspsp buildAspsp() {
        Aspsp aspsp = new Aspsp();

        aspsp.setId(ID);
        aspsp.setName(ASPSP_NAME);
        aspsp.setBic(BIC);
        aspsp.setUrl(URL);
        aspsp.setAdapterId(ADAPTER_ID);
        aspsp.setBankCode(BANK_CODE);
        aspsp.setIdpUrl(IDP_URL);
        aspsp.setScaApproaches(SCA_APPROACHES);

        return aspsp;
    }

    private void removePathToCsvFileSystemProperty() {
        System.clearProperty(CSV_ASPSP_ADAPTER_CONFIG_FILE_PATH);
    }

    private void setPathToCsvFileSystemProperty(String pathToFile) {
        System.setProperty(CSV_ASPSP_ADAPTER_CONFIG_FILE_PATH, pathToFile);
    }
}
