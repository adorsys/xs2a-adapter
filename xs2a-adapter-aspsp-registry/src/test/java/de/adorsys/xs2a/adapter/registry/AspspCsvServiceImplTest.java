package de.adorsys.xs2a.adapter.registry;

import de.adorsys.xs2a.adapter.service.AspspRepository;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.assertThat;

public class AspspCsvServiceImplTest {

    private static final String ID = "81cecc67-6d1b-4169-b67c-2de52b99a0cc";
    private static final String ASPSP_NAME = "BNP Paribas Germany, Consorsbank";
    private static final String BIC = "CSDBDE71XXX";
    private static final String URL = "https://xs2a-sndbx.consorsbank.de";
    private static final String ADAPTER_ID = "consors-bank-adapter";
    private static final String BANK_CODE = "76030080";

    private static final byte[] STORED_BYTES_TEMPLATE = "81cecc67-6d1b-4169-b67c-2de52b99a0cc,\"BNP Paribas Germany, Consorsbank\",CSDBDE71XXX,https://xs2a-sndbx.consorsbank.de,consors-bank-adapter,76030080\n".getBytes();

    @Mock
    private AspspRepository aspspRepository;

    @InjectMocks
    private AspspCsvServiceImpl aspspCsvServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void exportCsv() {
        Aspsp aspsp = new Aspsp();

        aspsp.setId(ID);
        aspsp.setName(ASPSP_NAME);
        aspsp.setBic(BIC);
        aspsp.setUrl(URL);
        aspsp.setAdapterId(ADAPTER_ID);
        aspsp.setBankCode(BANK_CODE);

        when(aspspRepository.findAll()).thenReturn(Collections.singletonList(aspsp));

        byte[] output = aspspCsvServiceImpl.exportCsv();

        assertThat(Arrays.equals(output, STORED_BYTES_TEMPLATE)).isTrue();
    }

    @Test
    public void importCsv() {
        List<Aspsp> repository = new ArrayList<>();

        ArgumentCaptor<ArrayList> captor = ArgumentCaptor.forClass(ArrayList.class);

        Aspsp aspsp = new Aspsp();
        aspsp.setId(ID);
        aspsp.setName(ASPSP_NAME);
        aspsp.setBic(BIC);
        aspsp.setUrl(URL);
        aspsp.setAdapterId(ADAPTER_ID);
        aspsp.setBankCode(BANK_CODE);

        repository.add(aspsp);

        doNothing().when(aspspRepository).deleteAll();

        doNothing().when(aspspRepository).saveAll(repository);

        aspspCsvServiceImpl.importCsv(STORED_BYTES_TEMPLATE);

        verify(aspspRepository, times(1)).deleteAll();
        verify(aspspRepository, times(1)).saveAll(captor.capture());
        assertThat(repository.size()).isEqualTo(captor.getValue().size());

        Aspsp output = (Aspsp) captor.getValue().get(0);
        assertThat(output.getId()).isEqualTo(aspsp.getId());
    }
}
