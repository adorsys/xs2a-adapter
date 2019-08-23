package de.adorsys.xs2a.adapter.service.impl.mapper;

import de.adorsys.xs2a.adapter.service.StartScaProcessResponse;
import de.adorsys.xs2a.adapter.service.impl.model.UnicreditStartScaProcessResponse;
import de.adorsys.xs2a.adapter.service.impl.service.UnicreditLinkBuilderService;
import de.adorsys.xs2a.adapter.service.model.Link;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class UnicreditStartAuthorisationResponseMapper implements UnicreditResponseMapper<UnicreditStartScaProcessResponse, StartScaProcessResponse> {
    private static final Logger LOG = LoggerFactory.getLogger(UnicreditStartAuthorisationResponseMapper.class);
    private static final String NEXT_LINK = "next";
    private static final String SELECT_AUTHENTICATION_METHOD_LINK = "selectAuthenticationMethod";
    private static final String AUTHORISE_TRANSACTION_LINK = "authoriseTransaction";

    private final StartScaProcessResponseUnicreditMapper startScaProcessResponseMapper = new StartScaProcessResponseUnicreditMapper();
    private final UnicreditLinkBuilderService linkBuilderService = new UnicreditLinkBuilderService();

    @Override
    public StartScaProcessResponse modifyResponse(UnicreditStartScaProcessResponse unicreditResponse) {
        StartScaProcessResponse startScaProcessResponse = startScaProcessResponseMapper.toStartScaProcessResponse(unicreditResponse);

        Map<String, Link> links = startScaProcessResponse.getLinks();
        // 1.1 is the obsolete version for now
        if (isBerlinGroupVersionObsolete(links, NEXT_LINK)) {
            if (startScaProcessResponse.isSelectScaMethodStage()) {
                modifyLinksToActualVersion(links, NEXT_LINK, SELECT_AUTHENTICATION_METHOD_LINK, linkBuilderService::buildUpdatePsuDataUri);
            } else if (startScaProcessResponse.isChosenScaMethodStage()) {
                modifyLinksToActualVersion(links, NEXT_LINK, AUTHORISE_TRANSACTION_LINK, linkBuilderService::buildUpdatePsuDataUri);
            } else {
                // else - do nothing, as this is an unexpected behaviour for us
                LOG.warn("Unexpected embedded authorisation stage according to the response body: {}", startScaProcessResponse);
            }
        }

        return startScaProcessResponse;
    }
}
