package com.future.demo.txlcn;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages={"com.future.demo"})
@EntityScan("com.future.demo")
public class ConfigJpa {
}
