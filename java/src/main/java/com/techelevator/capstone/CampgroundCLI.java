package com.techelevator.capstone;

import com.techelevator.capstone.dao.JdbcParkDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@SpringBootApplication
public class CampgroundCLI implements CommandLineRunner {

    @Autowired
    private DataSource dataSource; // Don't need to manual create the datasource it is provided by Spring

    public CampgroundCLI(DataSource dataSource){
        this.dataSource = dataSource;
        // create your DAOs here
        JdbcParkDao jdbcParkDao = new JdbcParkDao(dataSource);

    }

    public static void main(String[] args) {
        SpringApplication.run(CampgroundCLI.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
