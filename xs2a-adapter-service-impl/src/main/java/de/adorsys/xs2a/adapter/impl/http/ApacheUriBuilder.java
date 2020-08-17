package de.adorsys.xs2a.adapter.impl.http;

import de.adorsys.xs2a.adapter.service.exception.Xs2aAdapterException;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

// intentionally package private
class ApacheUriBuilder extends UriBuilder {

    private final URIBuilder uriBuilder;

    ApacheUriBuilder(URI baseUri) {
        uriBuilder = new URIBuilder(baseUri);
    }

    @Override
    public UriBuilder queryParam(String name, String value) {
        if (name != null && value != null) {
            uriBuilder.setParameter(name, value);
        }
        return this;
    }

    @Override
    public UriBuilder renameQueryParam(String currentName, String newName) {
        List<NameValuePair> queryParams = uriBuilder.getQueryParams();
        uriBuilder.removeQuery();
        for (NameValuePair queryParam : queryParams) {
            if (queryParam.getName().equals(currentName)) {
                uriBuilder.addParameter(newName, queryParam.getValue());
            } else {
                uriBuilder.addParameter(queryParam.getName(), queryParam.getValue());
            }
        }
        return this;
    }

    @Override
    public URI build() {
        try {
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            throw new Xs2aAdapterException(e);
        }
    }
}
