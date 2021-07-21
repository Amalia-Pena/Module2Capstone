
package com.techelevator.capstone.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class JdbcCampgroundDao implements CampgroundDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCampgroundDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
}