package de.adorsys.xs2a.adapter.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Profile("dev")
@Controller
public class ViewController {

    private final String V1_ASPSP_MANAGER = "/manager";

    @GetMapping(V1_ASPSP_MANAGER)
    public String manage() {
        return "./index.html";
    }
}
