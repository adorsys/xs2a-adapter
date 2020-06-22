package de.adorsys.xs2a.tpp.controller;

import de.adorsys.xs2a.adapter.remote.api.AspspClient;
import de.adorsys.xs2a.adapter.rest.api.model.AspspTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AspspController {
    private final AspspClient aspspClient;

    public AspspController(AspspClient aspspClient) {
        this.aspspClient = aspspClient;
    }

    @RequestMapping("/v1/aspsps")
    ResponseEntity<List<AspspTO>> getAspsps(@RequestParam(required = false) String name,
                                            @RequestParam(required = false) String bic,
                                            @RequestParam(required = false) String bankCode,
                                            @RequestParam(required = false) String iban, // if present - other params ignored
                                            @RequestParam(required = false) String after,
                                            @RequestParam(required = false, defaultValue = "10") int size) {

        ResponseEntity<List<AspspTO>> aspsps = aspspClient.getAspsps(name, bic, bankCode, iban, after, size);

        return ResponseEntity.ok(aspsps.getBody());
    }
}
