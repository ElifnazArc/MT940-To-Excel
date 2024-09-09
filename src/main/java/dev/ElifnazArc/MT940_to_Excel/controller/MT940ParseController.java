package dev.ElifnazArc.MT940_to_Excel.controller;

import dev.ElifnazArc.MT940_to_Excel.entity.Transaction;
import dev.ElifnazArc.MT940_to_Excel.repository.TransactionRepository;
import dev.ElifnazArc.MT940_to_Excel.service.MT940ParseService;
import dev.ElifnazArc.MT940_to_Excel.service.TransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MT940ParseController {
    private final MT940ParseService mt940Service;
    private final TransactionRepository transactionRepository;
    private final TransactionService transactionService;

    public MT940ParseController(MT940ParseService mt940Service, TransactionRepository transactionRepository, TransactionService transactionService) {
        this.mt940Service = mt940Service;
        this.transactionRepository = transactionRepository;
        this.transactionService = transactionService;
    }

    @GetMapping("/parse-file")
    public List<String> parseFile() {
        return mt940Service.getResourceFileAsString("mt940-npp.txt");
    }

    @GetMapping("/parse-mt940")
    public List<Transaction> parseMT940() {
        // Dosya içeriğini al ve parse et
        List<String> fileContent = mt940Service.getResourceFileAsString("mt940-npp.txt");
        transactionService.parseMT940ToRead(fileContent);

        return transactionRepository.findAll();
    }

    @GetMapping("/transactions/show")
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

}
