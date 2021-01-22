package de.adorsys.xs2a.adapter.api.model;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class SelectPsuAuthenticationMethod {
    private String authenticationMethodId;

    public String getAuthenticationMethodId() {
        return authenticationMethodId;
    }

    public void setAuthenticationMethodId(String authenticationMethodId) {
        this.authenticationMethodId = authenticationMethodId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SelectPsuAuthenticationMethod that = (SelectPsuAuthenticationMethod) o;
        return Objects.equals(authenticationMethodId, that.authenticationMethodId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authenticationMethodId);
    }
}
