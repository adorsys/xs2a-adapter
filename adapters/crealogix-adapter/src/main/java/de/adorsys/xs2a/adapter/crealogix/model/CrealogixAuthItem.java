package de.adorsys.xs2a.adapter.crealogix.model;

import java.util.Objects;

public class CrealogixAuthItem {
    private String authOptionId;

    private CrealogixAuthType authType;

    private String authInfo;

    public String getAuthOptionId() {
        return authOptionId;
    }

    public void setAuthOptionId(String authOptionId) {
        this.authOptionId = authOptionId;
    }

    public CrealogixAuthType getAuthType() {
        return authType;
    }

    public void setAuthType(CrealogixAuthType authType) {
        this.authType = authType;
    }

    public String getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(String authInfo) {
        this.authInfo = authInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrealogixAuthItem that = (CrealogixAuthItem) o;
        return Objects.equals(authOptionId, that.authOptionId) &&
            authType == that.authType &&
            Objects.equals(authInfo, that.authInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authOptionId, authType, authInfo);
    }
}
