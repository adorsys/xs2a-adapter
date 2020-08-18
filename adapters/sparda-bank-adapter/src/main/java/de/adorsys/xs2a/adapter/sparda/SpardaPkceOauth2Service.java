package de.adorsys.xs2a.adapter.sparda;

import de.adorsys.xs2a.adapter.impl.PkceOauth2Service;
import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.PkceOauth2Extension;

class SpardaPkceOauth2Service extends PkceOauth2Service {

    static final byte[] OCTET_SEQUENCE = PkceOauth2Extension.random(33);

    SpardaPkceOauth2Service(Oauth2Service oauth2Service) {
        super(oauth2Service);
    }

    @Override
    public byte[] octetSequence() {
        return OCTET_SEQUENCE;
    }
}
