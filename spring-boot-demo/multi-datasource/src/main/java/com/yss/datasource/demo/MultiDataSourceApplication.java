package com.yss.datasource.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.Connection;


/**
 *
 * 管理多数据源与SpringBoot协同工作需排除Spring Boot的自动配置
 *    1. DataSourceAutoConfiguration
 *    2. DataSourceTransactionManagerAutoConfiguration
 *    3. JdbcTemplateAutoConfiguration
 *
 * 同时自己控制数据源的注入
 *
 */


@Slf4j
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class,
		JdbcTemplateAutoConfiguration.class})
public class MultiDataSourceApplication {


	public static void main(String[] args) {
		SpringApplication.run(MultiDataSourceApplication.class, args);
	}


}
