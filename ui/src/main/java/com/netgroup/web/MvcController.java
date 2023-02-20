package com.netgroup.web;


import com.netgroup.domain.client.api.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MvcController {
    private final ClientService service;
    @GetMapping("/hello")
    public String hello() {
        return service.test();
    }
}
