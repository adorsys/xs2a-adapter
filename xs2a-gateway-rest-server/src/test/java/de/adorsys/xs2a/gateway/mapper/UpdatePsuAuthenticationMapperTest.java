package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.shared.PsuDataTO;
import de.adorsys.xs2a.gateway.model.shared.UpdatePsuAuthenticationTO;
import de.adorsys.xs2a.gateway.service.model.UpdatePsuAuthentication;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdatePsuAuthenticationMapperTest {
    private static final String PASSWORD = "password";
    private static final String ENCRYPTED_PASSWORD = "encrypted password";

    UpdatePsuAuthenticationMapper updatePsuAuthenticationMapper = Mappers.getMapper(UpdatePsuAuthenticationMapper.class);

    @Test
    public void toUpdatePsuAuthentication() {
        UpdatePsuAuthentication updatePsuAuthentication =
                updatePsuAuthenticationMapper.toUpdatePsuAuthentication(updatePsuAuthenticationTO());
        assertThat(updatePsuAuthentication.getPsuData().getPassword()).isEqualTo(PASSWORD);
        assertThat(updatePsuAuthentication.getPsuData().getEncryptedPassword()).isEqualTo(ENCRYPTED_PASSWORD);
    }

    private UpdatePsuAuthenticationTO updatePsuAuthenticationTO() {
        UpdatePsuAuthenticationTO updatePsuAuthenticationTO = new UpdatePsuAuthenticationTO();
        updatePsuAuthenticationTO.setPsuData(psuDataTO());
        return updatePsuAuthenticationTO;
    }

    private PsuDataTO psuDataTO() {
        PsuDataTO psuDataTO = new PsuDataTO();
        psuDataTO.setPassword(PASSWORD);
        psuDataTO.setEncryptedPassword(ENCRYPTED_PASSWORD);
        return psuDataTO;
    }
}