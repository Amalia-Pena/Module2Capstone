package com.techelevator.capstone;

import com.techelevator.capstone.dao.JdbcCampgroundDao;
import com.techelevator.capstone.dao.JdbcParkDao;
import com.techelevator.capstone.dao.JdbcSiteDao;
import com.techelevator.capstone.model.Campground;
import com.techelevator.capstone.model.Park;
import com.techelevator.capstone.model.Site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class CampgroundCLI implements CommandLineRunner {

    @Autowired
    private DataSource dataSource; // Don't need to manual create the datasource it is provided by Spring

    private JdbcParkDao jdbcParkDao;
    private JdbcCampgroundDao jdbcCampgroundDao;
    private JdbcSiteDao jdbcSiteDao;

    public CampgroundCLI(DataSource dataSource){
        this.dataSource = dataSource;
        // create your DAOs here
        jdbcParkDao = new JdbcParkDao(dataSource);
        jdbcCampgroundDao = new JdbcCampgroundDao(dataSource);
        jdbcSiteDao = new JdbcSiteDao(dataSource);

    }

    public static void main(String[] args) {
        SpringApplication.run(CampgroundCLI.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<Park> parkList = jdbcParkDao.getParks();
        System.out.println(parkList.size());
        List<Campground> campgroundList = jdbcCampgroundDao.getCampgrounds(Long.valueOf(3));
        System.out.println(campgroundList.size());
        LocalDate d1 = LocalDate.of(2021, 7, 25);
        LocalDate d2 = LocalDate.of(2021,8,1);
        List<Site> siteList = jdbcSiteDao.getAvailableSites(1L,d1, d2);
        System.out.println(siteList.size());
    }
}
