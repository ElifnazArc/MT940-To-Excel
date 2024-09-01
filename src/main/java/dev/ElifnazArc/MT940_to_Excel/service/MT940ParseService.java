package dev.ElifnazArc.MT940_to_Excel.service;

import com.prowidesoftware.swift.model.field.*;
import com.prowidesoftware.swift.model.mt.mt9xx.MT940;
import dev.ElifnazArc.MT940_to_Excel.entity.Transaction;
import dev.ElifnazArc.MT940_to_Excel.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class MT940ParseService {
    private final TransactionRepository transactionRepository;

    public MT940ParseService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    // Dosyayı okuyup satırları bir liste olarak döndürür
    public List<String> getResourceFileAsString(String fileName) {
        InputStream is = getResourceFileAsInputStream(fileName);
        if (is != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            return reader.lines().toList();
        } else {
            throw new RuntimeException("resource not found");
        }
    }

    // Dosyayı InputStream olarak alır
    private InputStream getResourceFileAsInputStream(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }

    // MT940 dosyasını parse edip bir Map yapısında döndürür
    public List<Transaction> parseMT940ToRead(List<String> fileContent) {
        // Dosya içeriğini birleştirerek tek bir string haline getir
        String msg = String.join("\n", fileContent);

        List<Transaction> transactions = new ArrayList<>();

        // MT940 formatını parse et
        MT940 mt = MT940.parse(msg);


        // Transactions (Field 61) and Transaction Details (Field 86)
        List<Field61> field61s = mt.getField61();
        List<Field86> field86s = mt.getField86();

        for (int i = 0; i < field61s.size(); i++) {

            Transaction transaction = new Transaction();
            Field61 field61 = field61s.get(i);

            LocalDate transactionDate = parseYYMMDD(field61.getValueDate());
            transaction.setTransactionDate(transactionDate);

            transaction.setTransactionAmount(new BigDecimal(field61.getAmount().replace(',', '.')));

            String dcMark = field61.getDebitCreditMark();
            transaction.setTransactionType(dcMark != null && !dcMark.isEmpty() ? dcMark.charAt(0) : 'N');

            transaction.setTransactionDetails(i < field86s.size() ? field86s.get(i).getNarrative() : "N/A");


            // Banka Kodu (BIC) --------------------------------
            String sender = mt.getSender();
            //System.out.println("Sender: " + sender);
            transaction.setSender(sender);


            String bankCode = sender != null ? sender.substring(1, 5) : "N/A";
            transaction.setBankCode(bankCode);


            // Transaction Reference Number (Field 20) ---------------------------
            Field20 field20 = mt.getField20();
            transaction.setTransactionReferenceNumber(field20 != null ? field20.getValue() : "N/A");


            // Account Identification (Field 25) ------------------------------
            Field25 field25 = mt.getField25();
            if (field25 != null) {
                transaction.setAccountIdentification(field25.getValue());
            }


            // Statement Number (Field 28C) -------------------------------
            Field28C field28C = mt.getField28C();
            transaction.setStatementNumber(field28C != null ? field28C.getValue() : "N/A");


            // Opening Balance (Field 60F) -------------------------------------
            Field60F field60F = mt.getField60F();
            BigDecimal openingBalance = null;
            if (field60F != null) {
                openingBalance = new BigDecimal(field60F.getAmount().replace(',', '.'));
            }
            transaction.setOpeningBalance(openingBalance);


            // Closing Balance (Field 62F) -------------------------------------
            Field62F field62F = mt.getField62F();
            BigDecimal closingBalance = null;
            if (field62F != null) {
                closingBalance = new BigDecimal(field62F.getAmount().replace(',', '.'));
            }
            transaction.setClosingBalance(closingBalance);

            // Closing Available Balance (Field 64) ----------------------------------
            Field64 closingAvailableBalance = mt.getField64();
            if (closingAvailableBalance != null) {
                transaction.setClosingAvailableBalance(String.valueOf(new BigDecimal(closingAvailableBalance.getAmount().replace(',', '.'))));
            }

            // Forward Available Balance (Field 65) ------------------------------
            List<Field65> forwardAvailableBalances = mt.getField65();
            List<String> forwardBalances = new ArrayList<>();
            if (forwardAvailableBalances != null && !forwardAvailableBalances.isEmpty()) {
                for (Field65 forwardAvailableBalance : forwardAvailableBalances) {
                    forwardBalances.add(forwardAvailableBalance.getValue());
                }
            }
            transaction.setForwardAvailableBalance(forwardBalances.isEmpty() ? null : String.join(", ", forwardBalances));

            System.out.println(transaction);
            transactions.add(transaction);

        }
        transactionRepository.saveAll(transactions);
        return transactions;
    }

    LocalDate parseYYMMDD(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        LocalDate date = LocalDate.parse(dateStr, formatter);

        // Assuming the year is within a reasonable range (e.g., within 100 years from now)
        int currentYear = LocalDate.now().getYear();
        int parsedYear = date.getYear();

        // If the parsed year is greater than the current year, assume it's from the previous century
        if (parsedYear > currentYear % 100) {
            date = date.minusYears(100);
        }

        return date;
    }

}
