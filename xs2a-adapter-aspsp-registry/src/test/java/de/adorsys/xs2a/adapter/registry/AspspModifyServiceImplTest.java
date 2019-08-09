package de.adorsys.xs2a.adapter.registry;

import de.adorsys.xs2a.adapter.service.AspspModifyRepository;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AspspModifyServiceImplTest {

    @InjectMocks
    private AspspModifyServiceImpl modifyService;

    @Mock
    private AspspModifyRepository repository;

    @Test
    public void create() {
        ArgumentCaptor<Aspsp> aspspCaptor = ArgumentCaptor.forClass(Aspsp.class);
        when(repository.create(aspspCaptor.capture())).thenReturn(null);

        modifyService.create(new Aspsp());

        Aspsp aspsp = aspspCaptor.getValue();
        verify(repository, times(1)).create(aspsp);

        assertThat(aspsp.getId()).isNotBlank();
    }

    @Test
    public void update() {
        Aspsp aspsp = new Aspsp();
        doNothing().when(repository).update(aspsp);

        modifyService.update(aspsp);

        verify(repository, times(1)).update(aspsp);
    }

    @Test
    public void remove() {
        String aspspId = "1";
        doNothing().when(repository).remove(aspspId);

        modifyService.remove(aspspId);

        verify(repository, times(1)).remove(aspspId);
    }
}
