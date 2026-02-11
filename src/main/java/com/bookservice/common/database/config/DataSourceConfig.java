package com.bookservice.common.database.config;

import com.bookservice.common.database.DataSourceType;
import com.bookservice.common.database.ReplicationRoutingDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
public class DataSourceConfig {
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.master")
	public DataSourceProperties masterDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	public DataSource masterDataSource(@Qualifier("masterDataSourceProperties") DataSourceProperties properties) {
		return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.slave")
	public DataSourceProperties slaveDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	public DataSource slaveDataSource(@Qualifier("slaveDataSourceProperties") DataSourceProperties properties) {
		return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}

	@Bean
	public DataSource routingDataSource(
			@Qualifier("masterDataSource") DataSource masterDataSource,
			@Qualifier("slaveDataSource") DataSource slaveDataSource){
		ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();

		HashMap<Object, Object> dataSourceMap = new HashMap<>();
		dataSourceMap.put(DataSourceType.MASTER, masterDataSource);
		dataSourceMap.put(DataSourceType.SLAVE, slaveDataSource);

		routingDataSource.setTargetDataSources(dataSourceMap);
		routingDataSource.setDefaultTargetDataSource(masterDataSource); //기본은 master

		return routingDataSource;
	}

	//쿼리 실행될 때 데이터 가져오기
	@Bean
	@Primary
	public DataSource dataSource(@Qualifier("routingDataSource") DataSource routingDataSource){
		return new LazyConnectionDataSourceProxy(routingDataSource);
	}

	@Bean
	public PlatformTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource){
		return new DataSourceTransactionManager(dataSource);
	}
}
