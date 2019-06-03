package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.AccountReportTO;
import de.adorsys.xs2a.gateway.model.HrefTypeTO;
import de.adorsys.xs2a.gateway.model.TransactionDetailsTO;
import de.adorsys.xs2a.gateway.service.account.AccountReport;
import de.adorsys.xs2a.gateway.service.account.Transactions;
import de.adorsys.xs2a.gateway.service.model.Link;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.adorsys.xs2a.gateway.mapper.TransactionsMapperTest.buildTransactions;
import static org.assertj.core.api.Assertions.assertThat;

public class AccountReportMapperTest {
    private static final Transactions BOOKED = buildTransactions();
    private static final Transactions PENDING = buildTransactions();
    private static final List<Transactions> BOOKED_LIST = Collections.singletonList(BOOKED);
    private static final List<Transactions> PENDING_LIST = Collections.singletonList(PENDING);
    private static final byte[] TRANSACTION_RAW = "transactionsRaw".getBytes();
    private static final String LINK_NAME = "linkName";
    private static final String LINK_HREF = "linkHref";
    private static final Link LINK = buildLink();
    private static final Map<String, Link> LINKS_MAP = buildLinksMap();

    @Test
    public void toAccountReportTO() {
        AccountReportTO accountReportTO = Mappers.getMapper(AccountReportMapper.class).toAccountReportTO(buildAccountReport());

        assertThat(accountReportTO).isNotNull();

        List<TransactionDetailsTO> bookedTransactionList = accountReportTO.getBooked();
        assertThat(bookedTransactionList).isNotNull();
        assertThat(bookedTransactionList.size()).isEqualTo(BOOKED_LIST.size());

        List<TransactionDetailsTO> pendingTransactionList = accountReportTO.getPending();
        assertThat(pendingTransactionList).isNotNull();
        assertThat(pendingTransactionList.size()).isEqualTo(PENDING_LIST.size());

        Map<String, HrefTypeTO> linksMapTO = accountReportTO.getLinks();
        assertThat(linksMapTO).isNotNull();
        assertThat(linksMapTO).hasSameSizeAs(LINKS_MAP);
    }

    static AccountReport buildAccountReport() {
        AccountReport accountReport = new AccountReport();

        accountReport.setBooked(BOOKED_LIST);
        accountReport.setPending(PENDING_LIST);
        accountReport.setTransactionsRaw(TRANSACTION_RAW);
        accountReport.setLinks(LINKS_MAP);

        return accountReport;
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