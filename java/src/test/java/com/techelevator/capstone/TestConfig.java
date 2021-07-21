package com.techelevator.capstone;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

@TestConfiguration
public class TestConfig {
    private static SingleConnectionDataSource dataSource;

    @Bean
    public static SingleConnectionDataSource setupDataSource() throws SQLException, IOException {
        Resource resource = new ClassPathResource("/application.properties");
        Properties props = PropertiesLoaderUtils.loadProperties(resource); // read props
        dataSource = new SingleConnectionDataSource(); // create DataSource Connection
        dataSource.setDriverClassName(props.getProperty("spring.datasource.driver-class-name")); // Add driver from props
        dataSource.setUrl(props.getProperty("spring.datasource.url")); // add url from props
        dataSource.setUsername(props.getProperty("spring.datasource.username")); // user from props
        dataSource.setPassword(props.getProperty("spring.datasource.password")); // password from props
        dataSource.setAutoCommit(false); // Ensures changes are not made and rolled back
        return dataSource;
    }

}
