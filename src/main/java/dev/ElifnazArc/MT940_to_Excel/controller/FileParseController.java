package dev.ElifnazArc.MT940_to_Excel.controller;

import dev.ElifnazArc.MT940_to_Excel.entity.Transaction;
import dev.ElifnazArc.MT940_to_Excel.repository.TransactionRepository;
import dev.ElifnazArc.MT940_to_Excel.service.MT940ParseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FileParseController {
    private final MT940ParseService mt940Service;
    private final TransactionRepository transactionRepository;

    public FileParseController(MT940ParseService mt940Service, TransactionRepository transactionRepository) {
        this.mt940Service = mt940Service;
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/parse-file")
    public List<String> parseFile() {
        return mt940Service.getResourceFileAsString("mt940-2.txt");
    }

    @GetMapping("/parse-mt940")
    public List<Transaction> parseMT940() {
        // Dosya içeriğini al ve parse et
        List<String> fileContent = mt940Service.getResourceFileAsString("mt940-2.txt");
        mt940Service.parseMT940ToRead(fileContent);

        return transactionRepository.findAll();
    }

    @GetMapping("/see-all-list")
    public List<Transaction> seeAllTransactions() {
        return transactionRepository.findAll();
    }
}
