package de.adorsys.xs2a.adapter.service.impl.service;

import de.adorsys.xs2a.adapter.service.model.Link;

import java.util.Map;
import java.util.function.Function;

public class UnicreditResponseModifierService {

    public boolean isBerlinGroupVersionObsolete(Map<String, Link> links, String linkName) {
        return links.containsKey(linkName);
    }

    public void modifyLinksToActualVersion(Map<String, Link> links, String linkToRemove, String linkToAdd, Function<String, String> linkModifier) {
        Link authoriseTransactionLink = links.get(linkToRemove);
        Link startAuthorisationLink = new Link(linkModifier.apply(authoriseTransactionLink.getHref()));

        links.remove(linkToRemove);
        links.put(linkToAdd, startAuthorisationLink);
    }
}
