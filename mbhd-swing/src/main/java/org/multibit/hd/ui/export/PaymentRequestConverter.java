package org.multibit.hd.ui.export;

import com.google.common.base.Joiner;
import com.googlecode.jcsv.writer.CSVEntryConverter;
import org.multibit.hd.core.config.Configurations;
import org.multibit.hd.core.dto.PaymentRequestData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


/**
 * Convert WalletTableData into single fields for use in a CSV file.
 */
public class PaymentRequestConverter implements CSVEntryConverter<PaymentRequestData> {

  DateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy HH:mm", Configurations.currentConfiguration.getLocale());


  @Override
  public String[] convertEntry(PaymentRequestData paymentRequestData) {
    String[] columns = new String[12];

    // Date.
    columns[0] = paymentRequestData.getDate() == null ? "" : dateFormatter.format(paymentRequestData.getDate().toDate());

    // Type
    columns[1] = paymentRequestData.getType() == null ? "" : paymentRequestData.getType().toString();

    // Bitcoin address
    columns[2] = paymentRequestData.getAddress() == null ? "" : paymentRequestData.getAddress();

    // Description.
    columns[3] = paymentRequestData.getDescription() == null ? "" : paymentRequestData.getDescription();

    // QR code label
    columns[4] = paymentRequestData.getLabel() == null ? "" : paymentRequestData.getLabel();

    // Note
    columns[5] = paymentRequestData.getNote() == null ? "" : paymentRequestData.getNote();

    // Amount in BTC.
    columns[6] = paymentRequestData.getAmountBTC() == null ? "" : paymentRequestData.getAmountBTC().toString();

    // Amount in fiat
    columns[7] = "";
    if (paymentRequestData.getAmountFiat() != null && paymentRequestData.getAmountFiat().getAmount() != null) {
      columns[7] = paymentRequestData.getAmountFiat().getAmount().toString();
    }

    // Exchange rate
    columns[8] = "";
    if (paymentRequestData.getAmountFiat() != null && paymentRequestData.getAmountFiat().getRate() != null) {
      columns[8] = paymentRequestData.getAmountFiat().getRate().or("");
    }

    // Exchange rate provider
    columns[9] = "";
    if (paymentRequestData.getAmountFiat() != null && paymentRequestData.getAmountFiat().getExchangeName() != null) {
      columns[9] = paymentRequestData.getAmountFiat().getExchangeName().or("");
    }

    // Paid amount in BTC
    columns[10] = paymentRequestData.getPaidAmountBTC() == null ? "" : paymentRequestData.getPaidAmountBTC().toString();

    // Funding transactions
    columns[11] = paymentRequestData.getPayingTransactionHashes() == null ? "" : Joiner.on(" ").join(paymentRequestData.getPayingTransactionHashes());

    return columns;
  }
}

