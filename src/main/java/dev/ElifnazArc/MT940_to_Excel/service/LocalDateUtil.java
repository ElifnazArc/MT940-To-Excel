package dev.ElifnazArc.MT940_to_Excel.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateUtil {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");

    LocalDate parseYYMMDD(String dateStr) {
        LocalDate date = LocalDate.parse(dateStr, formatter);
        int currentYear = LocalDate.now().getYear();
        int parsedYear = date.getYear();
        if (parsedYear > currentYear % 100) {
            date = date.minusYears(100);
        }
        return date;
    }

    public String getCurrentDate() {
        return LocalDate.now().format(formatter);
    }

}
