package de.adorsys.xs2a.adapter.registry;

import de.adorsys.xs2a.adapter.service.AspspModifyRepository;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AspspModifyServiceImplTest {

    @InjectMocks
    private AspspModifyServiceImpl modifyService;

    @Mock
    private AspspModifyRepository repository;

    @Test
    public void save() {
        Aspsp aspsp = new Aspsp();
        when(repository.save(aspsp)).thenReturn(aspsp);

        modifyService.save(aspsp);

        verify(repository, times(1)).save(aspsp);
    }

    @Test
    public void deleteById() {
        String aspspId = "1";
        doNothing().when(repository).deleteById(aspspId);

        modifyService.deleteById(aspspId);

        verify(repository, times(1)).deleteById(aspspId);
    }
}
