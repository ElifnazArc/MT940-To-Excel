package dev.ElifnazArc.MT940_to_Excel.controller;

import dev.ElifnazArc.MT940_to_Excel.entity.Transaction;
import dev.ElifnazArc.MT940_to_Excel.repository.TransactionRepository;
import dev.ElifnazArc.MT940_to_Excel.service.TransactionExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class ExcelController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionExportService transactionExportService;

    @GetMapping("/export-to-excel")
    public String exportToExcel() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactionExportService.exportToExcel(transactions);
    }

}
