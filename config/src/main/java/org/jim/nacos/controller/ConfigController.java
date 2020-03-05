package org.jim.nacos.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RefreshScop注解：自动刷新配置
 *
 * @author Jim
 */
@Slf4j
@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {

    /**
     * 配置项
     */
    @Value("${name:jim}")
    private String name;

    @RequestMapping("/name")
    public String getName() {
        log.info("Name entrance: {}", name);
        return name;
    }

}

