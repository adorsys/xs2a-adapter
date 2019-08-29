package de.adorsys.xs2a.adapter.rest.psd2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;
import java.util.List;
import java.util.Map;

@Generated("xs2a-codegen")
public class ErrorResponseTO {
    private List<TppMessageTO> tppMessages;

    @JsonProperty("_links")
    private Map<String, HrefTypeTO> links;

    public List<TppMessageTO> getTppMessages() {
        return tppMessages;
    }

    public void setTppMessages(List<TppMessageTO> tppMessages) {
        this.tppMessages = tppMessages;
    }

    public Map<String, HrefTypeTO> getLinks() {
        return links;
    }

    public void setLinks(Map<String, HrefTypeTO> links) {
        this.links = links;
    }
}
