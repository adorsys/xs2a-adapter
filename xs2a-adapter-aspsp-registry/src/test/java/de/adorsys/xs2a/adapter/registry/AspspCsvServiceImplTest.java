package de.adorsys.xs2a.adapter.registry;

import de.adorsys.xs2a.adapter.service.AspspRepository;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.AspspScaApproach;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class AspspCsvServiceImplTest {

    private static final String ID = "81cecc67-6d1b-4169-b67c-2de52b99a0cc";
    private static final String ASPSP_NAME = "BNP Paribas Germany, Consorsbank";
    private static final String BIC = "CSDBDE71XXX";
    private static final String URL = "https://xs2a-sndbx.consorsbank.de";
    private static final String ADAPTER_ID = "consors-bank-adapter";
    private static final String BANK_CODE = "76030080";
    private static final String IDP_URL = "https://example.com";
    private static final List<AspspScaApproach> SCA_APPROACHES = Arrays.asList(AspspScaApproach.EMBEDDED, AspspScaApproach.REDIRECT);
    private static final Aspsp ASPSP = buildAspsp();

    private static final byte[] STORED_BYTES_TEMPLATE
        = "81cecc67-6d1b-4169-b67c-2de52b99a0cc,\"BNP Paribas Germany, Consorsbank\",CSDBDE71XXX,https://xs2a-sndbx.consorsbank.de,consors-bank-adapter,76030080,https://example.com,EMBEDDED;REDIRECT\n".getBytes();
    private static final byte[] STORED_BYTES_TEMPLATE_WITH_SPACES_IN_SCA_APPROACHES
        = "81cecc67-6d1b-4169-b67c-2de52b99a0cc,\"BNP Paribas Germany, Consorsbank\",CSDBDE71XXX,https://xs2a-sndbx.consorsbank.de,consors-bank-adapter,76030080,https://example.com,EMBEDDED; REDIRECT\n".getBytes();
    private static final byte[] STORED_BYTES_TEMPLATE_WITH_LOWERCASE_SCA_APPROACHES
        = "81cecc67-6d1b-4169-b67c-2de52b99a0cc,\"BNP Paribas Germany, Consorsbank\",CSDBDE71XXX,https://xs2a-sndbx.consorsbank.de,consors-bank-adapter,76030080,https://example.com,embedded;redirect\n".getBytes();
    private static final byte[] STORED_BYTES_TEMPLATE_WITH_EMPTY_SCA_APPROACHES
        = "81cecc67-6d1b-4169-b67c-2de52b99a0cc,\"BNP Paribas Germany, Consorsbank\",CSDBDE71XXX,https://xs2a-sndbx.consorsbank.de,consors-bank-adapter,76030080,https://example.com,\n".getBytes();
    private static final byte[] STORED_BYTES_TEMPLATE_OLD_SCHEMA
        = "81cecc67-6d1b-4169-b67c-2de52b99a0cc,\"BNP Paribas Germany, Consorsbank\",CSDBDE71XXX,https://xs2a-sndbx.consorsbank.de,consors-bank-adapter,76030080\n".getBytes();

    @Mock
    private AspspRepository aspspRepository;

    @Spy
    @InjectMocks
    private AspspCsvServiceImpl aspspCsvServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void exportCsv() {
        when(aspspRepository.findAll()).thenReturn(Collections.singletonList(ASPSP));

        byte[] output = aspspCsvServiceImpl.exportCsv();

        assertThat(Arrays.equals(output, STORED_BYTES_TEMPLATE)).isTrue();
    }

    @Test
    public void importCsv() {
        List<Aspsp> repository = new ArrayList<>();

        ArgumentCaptor<ArrayList> captor = ArgumentCaptor.forClass(ArrayList.class);

        repository.add(ASPSP);
        doNothing().when(aspspRepository).deleteAll();
        doNothing().when(aspspRepository).saveAll(repository);

        aspspCsvServiceImpl.importCsv(STORED_BYTES_TEMPLATE);

        verify(aspspRepository, times(1)).deleteAll();
        verify(aspspRepository, times(1)).saveAll(captor.capture());
        assertThat(repository.size()).isEqualTo(captor.getValue().size());

        Aspsp output = (Aspsp) captor.getValue().get(0);
        assertThat(output.getId()).isEqualTo(ASPSP.getId());
    }

    @Test
    public void importCsv_scaApproachesWithSpaces() {
        List<Aspsp> repository = new ArrayList<>();

        ArgumentCaptor<ArrayList> captor = ArgumentCaptor.forClass(ArrayList.class);

        repository.add(ASPSP);
        doNothing().when(aspspRepository).deleteAll();
        doNothing().when(aspspRepository).saveAll(repository);

        aspspCsvServiceImpl.importCsv(STORED_BYTES_TEMPLATE_WITH_SPACES_IN_SCA_APPROACHES);

        verify(aspspRepository, times(1)).deleteAll();
        verify(aspspRepository, times(1)).saveAll(captor.capture());
        assertThat(repository.size()).isEqualTo(captor.getValue().size());

        Aspsp output = (Aspsp) captor.getValue().get(0);
        assertThat(output.getId()).isEqualTo(ASPSP.getId());
    }

    @Test
    public void importCsv_lowercaseScaApproaches() {
        List<Aspsp> repository = new ArrayList<>();

        ArgumentCaptor<ArrayList> captor = ArgumentCaptor.forClass(ArrayList.class);

        repository.add(ASPSP);
        doNothing().when(aspspRepository).deleteAll();
        doNothing().when(aspspRepository).saveAll(repository);

        aspspCsvServiceImpl.importCsv(STORED_BYTES_TEMPLATE_WITH_LOWERCASE_SCA_APPROACHES);

        verify(aspspRepository, times(1)).deleteAll();
        verify(aspspRepository, times(1)).saveAll(captor.capture());
        assertThat(repository.size()).isEqualTo(captor.getValue().size());

        Aspsp output = (Aspsp) captor.getValue().get(0);
        assertThat(output.getId()).isEqualTo(ASPSP.getId());
    }

    @Test
    public void importCsv_emptyScaApproaches() {
        List<Aspsp> repository = new ArrayList<>();

        ArgumentCaptor<ArrayList> captor = ArgumentCaptor.forClass(ArrayList.class);

        repository.add(ASPSP);
        doNothing().when(aspspRepository).deleteAll();
        doNothing().when(aspspRepository).saveAll(repository);

        aspspCsvServiceImpl.importCsv(STORED_BYTES_TEMPLATE_WITH_EMPTY_SCA_APPROACHES);

        verify(aspspRepository, times(1)).deleteAll();
        verify(aspspRepository, times(1)).saveAll(captor.capture());
        assertThat(repository.size()).isEqualTo(captor.getValue().size());

        Aspsp output = (Aspsp) captor.getValue().get(0);
        assertThat(output.getId()).isEqualTo(ASPSP.getId());
    }

    @Test
    public void importCsv_oldSchema() {
        List<Aspsp> repository = new ArrayList<>();

        ArgumentCaptor<ArrayList> captor = ArgumentCaptor.forClass(ArrayList.class);

        repository.add(ASPSP);
        doNothing().when(aspspRepository).deleteAll();
        doNothing().when(aspspRepository).saveAll(repository);

        aspspCsvServiceImpl.importCsv(STORED_BYTES_TEMPLATE_OLD_SCHEMA);

        verify(aspspRepository, times(1)).deleteAll();
        verify(aspspRepository, times(1)).saveAll(captor.capture());
        assertThat(repository.size()).isEqualTo(captor.getValue().size());

        Aspsp output = (Aspsp) captor.getValue().get(0);
        assertThat(output.getId()).isEqualTo(ASPSP.getId());
    }

    @Test
    public void saveCsv() throws IOException {
        Path path = Files.createTempFile("tmp", null);
        System.setProperty(AspspCsvServiceImpl.CSV_ASPSP_ADAPTER_CONFIG_FILE_PATH, path.toFile().getAbsolutePath());

        byte[] output = new byte[10];

        doReturn(output).when(aspspCsvServiceImpl).exportCsv();

        aspspCsvServiceImpl.saveCsv();

        assertThat(Files.readAllBytes(path)).isEqualTo(output);

        Files.deleteIfExists(path);
    }

    @Test(expected = RuntimeException.class)
    public void saveCsv_throwsException() throws IOException {
        System.setProperty(AspspCsvServiceImpl.CSV_ASPSP_ADAPTER_CONFIG_FILE_PATH, "");

        aspspCsvServiceImpl.saveCsv();
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
}
