package dev.ElifnazArc.MT940_to_Excel.service;

import dev.ElifnazArc.MT940_to_Excel.entity.Transaction;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExportService {

    public String exportToExcel(List<Transaction> transactions) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Transactions");

        // Başlıklar
        Row headerRow = sheet.createRow(0);
        String[] columnHeaders = {
                "ID", "Account Identification", "Bank Code", "Batch ID", "Closing Available Balance", "Closing Balance",
                "Closing Currency", "Forward Available Balance", "Opening Balance", "Opening Currency", "Statement Number",
                "Transaction Amount", "Transaction Date", "Transaction Details", "Transaction Reference Number", "Transaction Type", "Sender"
        };

        for (int i = 0; i < columnHeaders.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnHeaders[i]);
        }

        // Veri ekleme
        int rowNum = 1;
        for (Transaction transaction : transactions) {
            Row row = sheet.createRow(rowNum++);
            mapTransactionToRow(transaction, row);
        }

        // Dosya yazma
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
        row.createCell(3).setCellValue(transaction.getBatchId());
        row.createCell(4).setCellValue(transaction.getClosingAvailableBalance());
        row.createCell(5).setCellValue(transaction.getClosingBalance().doubleValue());
        row.createCell(15).setCellValue(transaction.getClosingCurrency());
        row.createCell(6).setCellValue(transaction.getForwardAvailableBalance());
        row.createCell(7).setCellValue(transaction.getOpeningBalance().doubleValue());
        row.createCell(15).setCellValue(transaction.getOpeningCurrency());
        row.createCell(8).setCellValue(transaction.getStatementNumber());
        row.createCell(9).setCellValue(transaction.getTransactionAmount().doubleValue());
        row.createCell(10).setCellValue(transaction.getTransactionDate().toString());
        row.createCell(11).setCellValue(transaction.getTransactionDetails());
        row.createCell(12).setCellValue(transaction.getTransactionReferenceNumber());
        row.createCell(13).setCellValue(String.valueOf(transaction.getTransactionType()));
        row.createCell(14).setCellValue(transaction.getSender());
    }
}
