package dev.ElifnazArc.MT940_to_Excel.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderUtilTest {

    private FileReaderUtil fileReaderUtil;

    @BeforeEach
    void setUp() {
        fileReaderUtil = new FileReaderUtil();
    }

    @Test
    void readLinesFromInputStream_ShouldReturnListOfLines_WhenInputStreamIsValid() {
        String input = "Line 1\nLine 2\nLine 3";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        List<String> lines = fileReaderUtil.readLinesFromInputStream(inputStream);

        assertEquals(3, lines.size());
        assertEquals("Line 1", lines.get(0));
        assertEquals("Line 2", lines.get(1));
        assertEquals("Line 3", lines.get(2));
    }

    @Test
    void readLinesFromInputStream_ShouldThrowRuntimeException_WhenInputStreamIsNull() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            fileReaderUtil.readLinesFromInputStream(null);
        });

        assertEquals("resource not found", exception.getMessage());
    }

    @Test
    void getResourceFileAsInputStream_ShouldReturnInputStream_WhenFileUploadedFromFrontend() {
        // Simulating a file upload using MockMultipartFile
        MockMultipartFile mockFile = new MockMultipartFile("file", "mt940-1.txt",
                "text/plain", "Line 1\nLine 2\nLine 3".getBytes());

        // Use the InputStream from the uploaded file
        InputStream inputStream = null;
        try {
            inputStream = mockFile.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Read lines from the InputStream
        List<String> lines = fileReaderUtil.readLinesFromInputStream(inputStream);

        // Validate the contents
        assertEquals(3, lines.size());
        assertEquals("Line 1", lines.get(0));
        assertEquals("Line 2", lines.get(1));
        assertEquals("Line 3", lines.get(2));
    }

    @Test
    void getResourceFileAsInputStream_ShouldReturnNull_WhenFileDoesNotExist() {

        InputStream inputStream = fileReaderUtil.getResourceFileAsInputStream("non-existent-file.txt");
        assertNull(inputStream);
    }
}
