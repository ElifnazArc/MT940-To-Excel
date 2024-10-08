package dev.ElifnazArc.MT940_to_Excel.controller;

import dev.ElifnazArc.MT940_to_Excel.entity.Transaction;
import dev.ElifnazArc.MT940_to_Excel.repository.TransactionRepository;
import dev.ElifnazArc.MT940_to_Excel.service.MT940ParseService;
import dev.ElifnazArc.MT940_to_Excel.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
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
        return mt940Service.getResourceFileAsString("mt940-1.txt");
    }

    @GetMapping("/parse-mt940")
    public List<Transaction> parseMT940() {
        // Dosya içeriğini al ve parse et
        List<String> fileContent = mt940Service.getResourceFileAsString("mt940-1.txt");
        transactionService.parseMT940ToRead(fileContent);

        return transactionRepository.findAll();
    }

    @PostMapping("/upload")
    public ResponseEntity<List<Transaction>> handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        List<Transaction> transactions;

        try (InputStream inputStream = file.getInputStream()) {
            List<String> fileContent = mt940Service.getResourceFileAsInputStream(inputStream);
            transactions = transactionService.parseMT940ToRead(fileContent);
            transactionRepository.saveAll(transactions);
        }
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/transactions/show")
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}