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
 * 数据源相关的bean：
 *      DataSource： 根据选择的连接池的实现决定
 *
 * 事务相关：
 *    PlatformTransactionManager (DataSourceTransactionManager)
 *    TransactionTemplate
 *
 * 操作相关：
 *    JdbcTemplate
 *
 * SpringBoot做了哪些操作：
 *    1. DataSourceAutoConfiguration：配置了DataSource
 *    2. DataSourceTransactionManagerAutoConfiguration: 配置了DataSourceTransactionManager
 *    3. JdbcTemplateAutoConfiguration: 配置了JdbcTemplate
 *
 */


@Slf4j
@SpringBootApplication
public class SingleDataSourceApplication implements CommandLineRunner {

	@Autowired
	private DataSource dataSource;

	public static void main(String[] args) {
		SpringApplication.run(SingleDataSourceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info(dataSource.toString());
		Connection connection = dataSource.getConnection();
		log.info(connection.toString());
		connection.close();
	}

}
