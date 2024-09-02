package dev.ElifnazArc.MT940_to_Excel.service;

import dev.ElifnazArc.MT940_to_Excel.entity.Transaction;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class TransactionExportService {

    public String exportToExcel(List<Transaction> transactions) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Transactions");

        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] columnHeaders = {
                "ID", "Account Identification", "Bank Code", "Closing Available Balance", "Closing Balance",
                "Forward Available Balance", "Opening Balance", "Statement Number", "Transaction Amount",
                "Transaction Date", "Transaction Details", "Transaction Reference Number", "Transaction Type", "Sender"
        };

        for (int i = 0; i < columnHeaders.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnHeaders[i]);
        }

        // Add data
        int rowNum = 1;
        for (Transaction transaction : transactions) {
            Row row = sheet.createRow(rowNum++);
            mapTransactionToRow(transaction, row);
        }

        // Write the file
        try (FileOutputStream outputStream = new FileOutputStream("transactions.xlsx")) {
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error while exporting data to Excel.";
        }

        return "Data exported successfully to transactions.xlsx";
    }

    private void mapTransactionToRow(Transaction transaction, Row row) {
        row.createCell(0).setCellValue(transaction.getId());
        row.createCell(1).setCellValue(transaction.getAccountIdentification());
        row.createCell(2).setCellValue(transaction.getBankCode());
        row.createCell(3).setCellValue(transaction.getClosingAvailableBalance());
        row.createCell(4).setCellValue(transaction.getClosingBalance().doubleValue());
        row.createCell(5).setCellValue(transaction.getForwardAvailableBalance());
        row.createCell(6).setCellValue(transaction.getOpeningBalance().doubleValue());
        row.createCell(7).setCellValue(transaction.getStatementNumber());
        row.createCell(8).setCellValue(transaction.getTransactionAmount().doubleValue());
        row.createCell(9).setCellValue(transaction.getTransactionDate().toString());
        row.createCell(10).setCellValue(transaction.getTransactionDetails());
        row.createCell(11).setCellValue(transaction.getTransactionReferenceNumber());
        row.createCell(12).setCellValue(transaction.getTransactionType().toString());
        row.createCell(13).setCellValue(transaction.getSender());
    }
}

