package dev.ElifnazArc.MT940_to_Excel.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

class LocalDateUtilTest {

    private LocalDateUtil dateUtil;
    private String currentDateString;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");

    @BeforeEach
    void setUp() {

        dateUtil = new LocalDateUtil();
        currentDateString = dateUtil.getCurrentDate();
    }

    @Test
    void testCurrentDate_ShouldHasSixDigitFormat_WhenCalled() {

        assertTrue(currentDateString.matches("\\d{6}"), "Date format is not correct."); // Changed to match 6 digits
    }

    @Test
    void testCurrentDate_ShouldReturnCorrectYearMonthDay_WhenParsed() {

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");

        LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(currentDateString, formatter);
            assertEquals(currentDate.getYear() % 100, parsedDate.getYear() % 100, "Returned year is not correct.");
            assertEquals(currentDate.getMonthValue(), parsedDate.getMonthValue(), "Returned month is not correct.");
            assertEquals(currentDate.getDayOfMonth(), parsedDate.getDayOfMonth(), "Returned day is not correct.");
        } catch (DateTimeParseException e) {
            fail("Date parsing failed: " + e.getMessage());
        }
    }
}
