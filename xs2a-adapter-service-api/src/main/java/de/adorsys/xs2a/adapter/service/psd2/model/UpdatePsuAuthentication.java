package de.adorsys.xs2a.adapter.service.psd2.model;

import de.adorsys.xs2a.adapter.service.model.PsuData;

public class UpdatePsuAuthentication {
    private de.adorsys.xs2a.adapter.service.model.PsuData psuData;

    public de.adorsys.xs2a.adapter.service.model.PsuData getPsuData() {
        return psuData;
    }

    public void setPsuData(PsuData psuData) {
        this.psuData = psuData;
    }
}
