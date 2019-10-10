package de.adorsys.xs2a.adapter.controller;

import de.adorsys.xs2a.adapter.service.AspspCsvService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Profile("dev")
@RestController
@RequestMapping(path = "/v1/aspsps/csv")
public class AspspCsvController {
    private static final String V1_ASPSP_CSV_EXPORT = "/export";
    private static final String V1_ASPSP_CSV_IMPORT = "/import";
    private static final String V1_ASPSP_CSV_PERSIST = "/persist";

    private final AspspCsvService aspspCsvService;

    public AspspCsvController(AspspCsvService aspspCsvService) {
        this.aspspCsvService = aspspCsvService;
    }

    @GetMapping(value = V1_ASPSP_CSV_EXPORT, produces = "text/csv")
    public ResponseEntity<byte[]> export() {
        byte[] response = aspspCsvService.exportCsv();
        String fileName = "aspsps.csv";

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentLength(response.length);
        responseHeaders.setContentType(new MediaType("text", "csv"));
        responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
    }

    @PostMapping(V1_ASPSP_CSV_PERSIST)
    public ResponseEntity<Void> persist() throws IOException {
        aspspCsvService.saveCsv();
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = V1_ASPSP_CSV_IMPORT, consumes = {"multipart/form-data"})
    public void importCsv(@RequestParam MultipartFile file) throws IOException {
        aspspCsvService.importCsv(file.getBytes());
    }
}
