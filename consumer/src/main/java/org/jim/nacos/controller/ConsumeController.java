package org.jim.nacos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/consume")
public class ConsumeController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/name")
    public String consumeName() {
        return restTemplate.getForObject("http://my-provider/config/name", String.class);
    }
}
