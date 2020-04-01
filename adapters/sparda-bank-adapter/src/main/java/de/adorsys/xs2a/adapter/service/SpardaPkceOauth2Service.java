package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.adapter.PkceOauth2Service;

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
