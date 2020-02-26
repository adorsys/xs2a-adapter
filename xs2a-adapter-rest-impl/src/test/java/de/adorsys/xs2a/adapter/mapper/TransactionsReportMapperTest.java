package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.*;
import de.adorsys.xs2a.adapter.service.model.*;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.adorsys.xs2a.adapter.mapper.AccountReportMapperTest.buildAccountReport;
import static org.assertj.core.api.Assertions.assertThat;

public class TransactionsReportMapperTest {
    private static final AccountReference ACCOUNT_REFERENCE = AccountReferenceMapperTest.buildAccountReference();
    private static final AccountReport TRANSACTIONS = buildAccountReport();
    private static final Balance BALANCE = BalanceMapperTest.buildBalance();
    private static final List<Balance> BALANCES = Collections.singletonList(BALANCE);
    private static final String LINK_NAME = "linkName";
    private static final String LINK_HREF = "linkHref";
    private static final Link LINK = buildLink();
    private static final Map<String, Link> LINKS_MAP = buildLinksMap();

    @Test
    public void toTransactionsResponse200Json() {
        TransactionsResponse200JsonTO transactionsResponse200Json
                = Mappers.getMapper(TransactionsReportMapper.class).toTransactionsResponse200Json(buildTransactionsReport());

        assertThat(transactionsResponse200Json).isNotNull();

        AccountReferenceTO accountTO = transactionsResponse200Json.getAccount();
        assertThat(accountTO).isNotNull();
        assertThat(accountTO).isEqualToComparingFieldByField(ACCOUNT_REFERENCE);

        AccountReportTO transactionsTO = transactionsResponse200Json.getTransactions();
        assertThat(transactionsTO).isNotNull();

        List<BalanceTO> balancesTO = transactionsResponse200Json.getBalances();
        assertThat(balancesTO).isNotNull();
        assertThat(balancesTO.size()).isEqualTo(BALANCES.size());

        Map<String, HrefTypeTO> linksMapTO = transactionsResponse200Json.getLinks();
        assertThat(linksMapTO).isNotNull();
        assertThat(linksMapTO).containsOnlyKeys(LINK_NAME);
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
