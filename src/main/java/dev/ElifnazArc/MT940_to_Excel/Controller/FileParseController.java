package dev.ElifnazArc.MT940_to_Excel.Controller;

import dev.ElifnazArc.MT940_to_Excel.Service.MT940ParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class FileParseController {

    @Autowired
    private MT940ParseService mt940Service;

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
