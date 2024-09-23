package dev.ElifnazArc.MT940_to_Excel.service;

import dev.ElifnazArc.MT940_to_Excel.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MT940ParseServiceTest {

    private MT940ParseService mt940ParseService;
    private FileReaderUtil fileReaderUtilMock;
    private TransactionRepository transactionRepositoryMock;

    @BeforeEach
    void setUp() {
        transactionRepositoryMock = mock(TransactionRepository.class);
        fileReaderUtilMock = mock(FileReaderUtil.class);
        mt940ParseService = new MT940ParseService(transactionRepositoryMock, fileReaderUtilMock);
    }

    @Test
    void getResourceFileAsString_ShouldReturnLines_WhenFileIsValid() {
        String fileName = "testfile.txt";
        InputStream mockInputStream = mock(InputStream.class);
        List<String> expectedLines = List.of("line1", "line2", "line3");

        when(fileReaderUtilMock.getResourceFileAsInputStream(fileName)).thenReturn(mockInputStream);
        when(fileReaderUtilMock.readLinesFromInputStream(mockInputStream)).thenReturn(expectedLines);

        List<String> actualLines = mt940ParseService.getResourceFileAsString(fileName);

        assertEquals(expectedLines, actualLines);
        verify(fileReaderUtilMock).getResourceFileAsInputStream(fileName);
        verify(fileReaderUtilMock).readLinesFromInputStream(mockInputStream);
    }

    @Test
    void getResourceFileAsInputStream_ShouldReturnLines_WhenInputStreamIsValid() {
        InputStream mockInputStream = mock(InputStream.class);
        List<String> expectedLines = List.of("line1", "line2", "line3");

        when(fileReaderUtilMock.readLinesFromInputStream(mockInputStream)).thenReturn(expectedLines);

        List<String> actualLines = mt940ParseService.getResourceFileAsInputStream(mockInputStream);

        assertEquals(expectedLines, actualLines);
        verify(fileReaderUtilMock).readLinesFromInputStream(mockInputStream);
    }
}
