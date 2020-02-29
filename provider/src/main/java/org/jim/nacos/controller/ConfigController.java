package org.jim.nacos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Collections;
import java.util.Objects;

/**
 * RefreshScop注解：自动刷新配置
 *
 * @author Jim
 */
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
        System.out.println(name);
        return name;
    }

}

