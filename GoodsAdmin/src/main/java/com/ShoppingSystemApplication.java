package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@MapperScan("com.mapper")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ShoppingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingSystemApplication.class, args);
    }

}

