package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.ais.AccountReferenceTO;
import de.adorsys.xs2a.gateway.model.ais.AccountReportTO;
import de.adorsys.xs2a.gateway.model.ais.BalanceList;
import de.adorsys.xs2a.gateway.model.ais.TransactionsResponse200Json;
import de.adorsys.xs2a.gateway.service.AccountReference;
import de.adorsys.xs2a.gateway.service.account.AccountReport;
import de.adorsys.xs2a.gateway.service.account.Balance;
import de.adorsys.xs2a.gateway.service.account.TransactionsReport;
import de.adorsys.xs2a.gateway.service.model.Link;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.adorsys.xs2a.gateway.mapper.AccountReferenceMapperTest.buildAccountReference;
import static de.adorsys.xs2a.gateway.mapper.AccountReportMapperTest.buildAccountReport;
import static de.adorsys.xs2a.gateway.mapper.BalanceMapperTest.buildBalance;
import static org.assertj.core.api.Assertions.assertThat;

public class TransactionsReportMapperTest {
    private static final AccountReference ACCOUNT_REFERENCE = buildAccountReference();
    private static final AccountReport TRANSACTIONS = buildAccountReport();
    private static final Balance BALANCE = buildBalance();
    private static final List<Balance> BALANCES = Collections.singletonList(BALANCE);
    private static final String LINK_NAME = "linkName";
    private static final String LINK_HREF = "linkHref";
    private static final Link LINK = buildLink();
    private static final Map<String, Link> LINKS_MAP = buildLinksMap();

    @Test
    public void toTransactionsResponse200Json() {
        TransactionsResponse200Json transactionsResponse200Json
                = Mappers.getMapper(TransactionsReportMapper.class).toTransactionsResponse200Json(buildTransactionsReport());

        assertThat(transactionsResponse200Json).isNotNull();

        AccountReferenceTO accountTO = transactionsResponse200Json.getAccount();
        assertThat(accountTO).isNotNull();
        assertThat(accountTO).isEqualToComparingFieldByField(ACCOUNT_REFERENCE);

        AccountReportTO transactionsTO = transactionsResponse200Json.getTransactions();
        assertThat(transactionsTO).isNotNull();

        BalanceList balancesTO = transactionsResponse200Json.getBalances();
        assertThat(balancesTO).isNotNull();
        assertThat(balancesTO.size()).isEqualTo(BALANCES.size());

        Map<String, Link> linksMapTO = transactionsResponse200Json.getLinks();
        assertThat(linksMapTO).isNotNull();
        assertThat(linksMapTO).isEqualTo(LINKS_MAP);
    }

    static TransactionsReport buildTransactionsReport() {
        TransactionsReport transactionsReport = new TransactionsReport();

        transactionsReport.setAccountReference(ACCOUNT_REFERENCE);
        transactionsReport.setTransactions(TRANSACTIONS);
        transactionsReport.setBalances(BALANCES);
        transactionsReport.setLinks(LINKS_MAP);

        return transactionsReport;
    }

    private static Link buildLink() {
        Link link = new Link();
        link.setHref(LINK_HREF);
        return link;
    }

    private static Map<String, Link> buildLinksMap() {
        Map<String, Link> linksMap = new HashMap<>();
        linksMap.put(LINK_NAME, LINK);
        return linksMap;
    }
}