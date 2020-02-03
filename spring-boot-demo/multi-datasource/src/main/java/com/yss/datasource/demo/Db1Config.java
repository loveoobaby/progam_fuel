package com.yss.datasource.demo;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class Db1Config {
	
	@Bean(name = "db1DataSourceProperties")
	@ConfigurationProperties("db1.datasource")
	public DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean(name = "db1DataSource")
	@ConfigurationProperties("db1.datasource.configuration")
	public DataSource dataSource(@Qualifier("db1DataSourceProperties") DataSourceProperties db1DataSourceProperties) {
		log.info("db1.url = {}", db1DataSourceProperties.getUrl());
		return db1DataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class)
				.build();
	}

	@Bean(name = "db1TransactionManager")
	public PlatformTransactionManager transactionManager(
			@Qualifier("db1DataSource") DataSource db1EntityManagerFactory) {
		return new DataSourceTransactionManager(db1EntityManagerFactory);
	}
	
}
