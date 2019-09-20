package de.adorsys.xs2a.adapter.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Profile("dev")
@Controller
public class AspspManagerController {

    private final String ASPSP_MANAGER_URL = "/manager";

    @GetMapping(ASPSP_MANAGER_URL)
    public String manage() {
        return "index.html";
    }
}
