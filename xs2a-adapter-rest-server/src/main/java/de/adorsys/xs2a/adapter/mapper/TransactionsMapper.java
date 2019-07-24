package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.PurposeCodeTO;
import de.adorsys.xs2a.adapter.model.TransactionDetailsTO;
import de.adorsys.xs2a.adapter.service.account.BankTransactionCode;
import de.adorsys.xs2a.adapter.service.account.PurposeCode;
import de.adorsys.xs2a.adapter.service.account.Transactions;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Objects;

@Mapper(uses = {AmountMapper.class, AccountReferenceMapper.class, ExchangeRateMapper.class})
public interface TransactionsMapper {

    @Mapping(source = "exchangeRate", target = "currencyExchange")
    TransactionDetailsTO toTransactionDetails(Transactions transactions);

    @InheritInverseConfiguration
    Transactions toTransactions(TransactionDetailsTO to);

    default PurposeCodeTO toPurposeCodeTO(PurposeCode purposeCode) {
        if (Objects.isNull(purposeCode)) {
            return null;
        }
        return PurposeCodeTO.fromValue(purposeCode.getCode());
    }

    default PurposeCode toPurposeCode(PurposeCodeTO to){
        if (Objects.isNull(to)) {
            return null;
        }
        return new PurposeCode(to.toString());
    }

    default String toBankTransactionCode(BankTransactionCode bankTransactionCode) {
        if (Objects.isNull(bankTransactionCode)) {
            return null;
        }
        return bankTransactionCode.getCode();
    }

    default BankTransactionCode toBankTransactionCode(String code) {
        if (Objects.isNull(code) || code.trim().isEmpty()){
            return null;
        }
        return new BankTransactionCode(code);
    }
}
