package dev.ElifnazArc.MT940_to_Excel.service;

import com.prowidesoftware.swift.model.field.*;
import com.prowidesoftware.swift.model.mt.mt9xx.MT940;
import dev.ElifnazArc.MT940_to_Excel.entity.Transaction;
import dev.ElifnazArc.MT940_to_Excel.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    // MT940 dosyasını parse edip bir Map yapısında döndürür
    public List<Transaction> parseMT940ToRead(List<String> fileContent) {
        String msg = String.join("\n", fileContent);

        List<Transaction> transactions = new ArrayList<>();

        MT940 mt = MT940.parse(msg);

        // Batch ID tüm işlemler için
        String batchId = UUID.randomUUID().toString();

        List<Field61> field61s = mt.getField61();
        List<Field86> field86s = mt.getField86();

        // Currency bilgisi Field 60F veya 62F'ten alınabilir
        String currency = mt.getField60F() != null ? mt.getField60F().getCurrency() : "N/A";

        for (int i = 0; i < field61s.size(); i++) {
            Transaction transaction = new Transaction();
            transaction.setBatchId(batchId);

            Field61 field61 = field61s.get(i);

            LocalDate transactionDate = parseYYMMDD(field61.getValueDate());
            transaction.setTransactionDate(transactionDate);

            LocalDate actionDate = LocalDate.now();
            transaction.setActionDate(actionDate);

            transaction.setTransactionAmount(new BigDecimal(field61.getAmount().replace(',', '.')));

            String dcMark = field61.getDebitCreditMark();
            transaction.setTransactionType(dcMark != null && !dcMark.isEmpty() ? dcMark.charAt(0) : 'N');

            transaction.setTransactionDetails(i < field86s.size() ? field86s.get(i).getNarrative() : "N/A");

            // Banka Kodu (BIC)
            String sender = mt.getSender();
            transaction.setSender(sender);

            String bankCode = sender != null ? sender.substring(1, 5) : "N/A";
            transaction.setBankCode(bankCode);

            // Transaction Reference Number (Field 20)
            Field20 field20 = mt.getField20();
            transaction.setTransactionReferenceNumber(field20 != null ? field20.getValue() : "N/A");

            // Account Identification (Field 25)
            Field25 field25 = mt.getField25();
            if (field25 != null) {
                transaction.setAccountIdentification(field25.getValue());
            }

            // Statement Number (Field 28C)
            Field28C field28C = mt.getField28C();
            transaction.setStatementNumber(field28C != null ? field28C.getValue() : "N/A");

            // Opening Balance (Field 60F)
            Field60F field60F = mt.getField60F();
            BigDecimal openingBalance = null;
            if (field60F != null) {
                openingBalance = new BigDecimal(field60F.getAmount().replace(',', '.'));
            }
            transaction.setOpeningBalance(openingBalance);

            // Opening Currency (Field 60F)
            String openingCurrency = field60F != null ? field60F.getCurrency() : "N/A";
            transaction.setOpeningCurrency(openingCurrency); // Make sure your entity has this field

            // Closing Balance (Field 62F)
            Field62F field62F = mt.getField62F();
            BigDecimal closingBalance = null;
            if (field62F != null) {
                closingBalance = new BigDecimal(field62F.getAmount().replace(',', '.'));
            }
            transaction.setClosingBalance(closingBalance);

            // Closing Currency (Field 62F)
            String closingCurrency = field62F != null ? field62F.getCurrency() : "N/A";
            transaction.setClosingCurrency(closingCurrency); // Make sure your entity has this field

            transactions.add(transaction);
        }

        transactionRepository.saveAll(transactions);
        return transactions;
    }

    LocalDate parseYYMMDD(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        LocalDate date = LocalDate.parse(dateStr, formatter);
        int currentYear = LocalDate.now().getYear();
        int parsedYear = date.getYear();
        if (parsedYear > currentYear % 100) {
            date = date.minusYears(100);
        }
        return date;
    }
}
