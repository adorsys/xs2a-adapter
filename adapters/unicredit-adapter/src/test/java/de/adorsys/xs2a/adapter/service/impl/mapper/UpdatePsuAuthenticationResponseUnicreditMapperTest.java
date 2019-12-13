package de.adorsys.xs2a.adapter.service.impl.mapper;

import de.adorsys.xs2a.adapter.service.impl.model.UnicreditStartScaProcessResponse;
import de.adorsys.xs2a.adapter.service.model.ConsentStatus;
import de.adorsys.xs2a.adapter.service.model.ScaStatus;
import de.adorsys.xs2a.adapter.service.model.UpdatePsuAuthenticationResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UpdatePsuAuthenticationResponseUnicreditMapperTest {

    private UpdatePsuAuthenticationResponseUnicreditMapper mapper;

    private UnicreditStartScaProcessResponse unicreditResponse = buildUnicreditResponse();

    @Before
    public void setUp() {
        mapper = new UpdatePsuAuthenticationResponseUnicreditMapper();
    }

    @Test
    public void toUpdatePsuAuthenticationResponse() {
        UpdatePsuAuthenticationResponse commonResponse = mapper.toUpdatePsuAuthenticationResponse(unicreditResponse);

        assertNotNull(commonResponse);
        assertEquals(commonResponse.getScaStatus(), ScaStatus.RECEIVED);
    }

    private UnicreditStartScaProcessResponse buildUnicreditResponse() {
        UnicreditStartScaProcessResponse response = new UnicreditStartScaProcessResponse();

        response.setConsentStatus(ConsentStatus.RECEIVED);

        return response;
    }
}
