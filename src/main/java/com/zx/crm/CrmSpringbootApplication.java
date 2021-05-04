package com.zx.crm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = {"com.zx.crm.settings.mapper","com.zx.crm.workbench.mapper"})
@ServletComponentScan
public class CrmSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrmSpringbootApplication.class, args);
    }

}
