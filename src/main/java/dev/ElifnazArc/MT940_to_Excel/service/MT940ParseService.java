package dev.ElifnazArc.MT940_to_Excel.service;

import dev.ElifnazArc.MT940_to_Excel.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    // Dosyayı okuyup satırları bir liste olarak döndürür
    public List<String> getResourceFileAsString(InputStream is) {
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

}
