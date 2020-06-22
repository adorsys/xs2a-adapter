package de.adorsys.xs2a.adapter.controller;

import de.adorsys.xs2a.adapter.mapper.AspspMapper;
import de.adorsys.xs2a.adapter.rest.api.AspspSearchApi;
import de.adorsys.xs2a.adapter.rest.api.model.AspspTO;
import de.adorsys.xs2a.adapter.service.AspspReadOnlyRepository;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class AspspSearchController implements AspspSearchApi {
    private final AspspReadOnlyRepository aspspSearchService;
    private final AspspMapper aspspMapper = Mappers.getMapper(AspspMapper.class);

    public AspspSearchController(AspspReadOnlyRepository aspspSearchService) {
        this.aspspSearchService = aspspSearchService;
    }

    @Override
    public ResponseEntity<List<AspspTO>> getAspsps(@RequestParam(required = false) String name,
                                                   @RequestParam(required = false) String bic,
                                                   @RequestParam(required = false) String bankCode,
                                                   @RequestParam(required = false) String iban, // if present - other params ignored
                                                   @RequestParam(required = false) String after,
                                                   @RequestParam(required = false, defaultValue = "10") int size) {

        List<Aspsp> aspsps;
        if (iban != null) {
            aspsps = aspspSearchService.findByIban(iban, after, size);
        } else if (name == null && bic == null && bankCode == null) {
            aspsps = aspspSearchService.findAll(after, size);
        } else {
            Aspsp aspsp = new Aspsp();
            aspsp.setName(name);
            aspsp.setBic(bic);
            aspsp.setBankCode(bankCode);
            aspsps = aspspSearchService.findLike(aspsp, after, size);
        }

        return ResponseEntity.ok(aspspMapper.toAspspTOs(aspsps));
    }

    @Override
    public ResponseEntity<AspspTO> getById(String id) {
        Optional<Aspsp> aspsp = aspspSearchService.findById(id);
        return aspsp.map(value -> ResponseEntity.ok(aspspMapper.toAspspTO(value)))
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
