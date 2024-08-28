package dev.ElifnazArc.MT940_to_Excel.service;

import com.prowidesoftware.swift.model.field.*;
import com.prowidesoftware.swift.model.mt.mt9xx.MT940;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class MT940ParseService {

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
    public Map<String, Object> parseMT940ToRead(List<String> fileContent) {
        // Dosya içeriğini birleştirerek tek bir string haline getir
        String msg = String.join("\n", fileContent);

        // MT940 formatını parse et
        MT940 mt = MT940.parse(msg);
        Map<String, Object> result = new HashMap<>();

        // Banka Kodu (BIC)
        String sender = mt.getSender();
        String bankCode = sender != null ? sender.substring(1, 5) : "N/A";
        result.put("Bank Code", bankCode);

        // Transaction Reference Number (Field 20)
        Field20 field20 = mt.getField20();
        result.put("Transaction Reference Number", field20 != null ? field20.getValue() : "N/A");

        // Account Identification (Field 25)
        Field25 field25 = mt.getField25();
        result.put("Account Identification", field25 != null ? field25.getValue() : "N/A");

        // Statement Number (Field 28C)
        Field28C field28C = mt.getField28C();
        result.put("Statement Number", field28C != null ? field28C.getValue() : "N/A");

        // Opening Balance (Field 60F)
        Field60F openingBalance = mt.getField60F();
        result.put("Opening Balance", openingBalance != null ? openingBalance.getValue() : "N/A");


        // Transactions (Field 61) and Transaction Details (Field 86)
        List<Map<String, String>> transactions = new ArrayList<>();
        List<Field61> field61s = mt.getField61();
        List<Field86> field86s = mt.getField86();

        for (int i = 0; i < field61s.size(); i++) {
            Map<String, String> transactionDetails = new HashMap<>();
            Field61 transaction = field61s.get(i);
            transactionDetails.put("Date", transaction.getComponent(Field61.VALUE_DATE));
            transactionDetails.put("Amount", transaction.getComponent(Field61.AMOUNT));
            transactionDetails.put("Type", transaction.getComponent(Field61.DC_MARK));
            transactionDetails.put("Details", i < field86s.size() ? field86s.get(i).getNarrative() : "N/A");

            transactions.add(transactionDetails);
        }
        result.put("Transactions", transactions);

        // Closing Balance (Field 62F)
        Field62F closingBalance = mt.getField62F();
        result.put("Closing Balance", closingBalance != null ? closingBalance.getValue() : "N/A");

        // Closing Available Balance (Field 64)
        Field64 closingAvailableBalance = mt.getField64();
        result.put("Closing Available Balance", closingAvailableBalance != null ? closingAvailableBalance.getValue() : "N/A");

        // Forward Available Balance (Field 65)
        List<Field65> forwardAvailableBalances = mt.getField65();
        List<String> forwardBalances = new ArrayList<>();
        if (forwardAvailableBalances != null && !forwardAvailableBalances.isEmpty()) {
            for (Field65 forwardAvailableBalance : forwardAvailableBalances) {
                forwardBalances.add(forwardAvailableBalance.getValue());
            }
        }
        result.put("Forward Available Balance", forwardBalances.isEmpty() ? "N/A" : forwardBalances);

        return result;
    }
}
