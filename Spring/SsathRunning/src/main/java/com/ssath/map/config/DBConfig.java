package com.ssath.map.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.ssath.map.model.dao")
public class DBConfig {

}
