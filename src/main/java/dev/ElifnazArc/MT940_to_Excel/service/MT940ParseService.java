package dev.ElifnazArc.MT940_to_Excel.service;

import dev.ElifnazArc.MT940_to_Excel.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class MT940ParseService {
    private final TransactionRepository transactionRepository;
    private final FileReaderUtil fileReaderUtil;

    public MT940ParseService(TransactionRepository transactionRepository, FileReaderUtil fileReaderUtil) {
        this.transactionRepository = transactionRepository;
        this.fileReaderUtil = fileReaderUtil;
    }
    // Dosyayı okuyup satırları bir liste olarak döndürür
    public List<String> getResourceFileAsString(String fileName) {
        InputStream is = fileReaderUtil.getResourceFileAsInputStream(fileName);
        return fileReaderUtil.readLinesFromInputStream(is);
    }

    // InputStream üzerinden satırları bir liste olarak döndürür
    public List<String> getResourceFileAsInputStream(InputStream is) {
        return fileReaderUtil.readLinesFromInputStream(is);
    }
}
