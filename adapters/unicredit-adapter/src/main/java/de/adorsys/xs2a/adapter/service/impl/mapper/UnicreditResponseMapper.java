package de.adorsys.xs2a.adapter.service.impl.mapper;

import de.adorsys.xs2a.adapter.service.model.Link;

import java.util.Map;
import java.util.function.Function;

public interface UnicreditResponseMapper<T, R> {

    R modifyResponse(T t);

    default void modifyLinksToActualVersion(Map<String, Link> links, String linkToRemove, String linkToAdd, Function<String, String> linkModifier) {
        Link authoriseTransactionLink = links.get(linkToRemove);
        Link startAuthorisationLink = new Link(linkModifier.apply(authoriseTransactionLink.getHref()));

        links.remove(linkToRemove);
        links.put(linkToAdd, startAuthorisationLink);
    }
}
