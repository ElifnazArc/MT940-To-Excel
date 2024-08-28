package dev.ElifnazArc.MT940_to_Excel.controller;

import dev.ElifnazArc.MT940_to_Excel.service.MT940ParseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class FileParseController {
    private final MT940ParseService mt940Service;

    public FileParseController(MT940ParseService mt940Service) {
        this.mt940Service = mt940Service;
    }

    @GetMapping("/parse-file")
    public List<String> parseFile() {
        return mt940Service.getResourceFileAsString("mt940-npp.txt");
    }

    @GetMapping("/parse-mt940")
    public Map<String, Object> parseMT940() {
        // Dosya içeriğini al ve parse et
        List<String> fileContent = mt940Service.getResourceFileAsString("mt940-npp.txt");
        return mt940Service.parseMT940ToRead(fileContent);
    }
}
