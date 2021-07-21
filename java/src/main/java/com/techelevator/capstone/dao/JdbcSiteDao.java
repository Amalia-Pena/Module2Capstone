package com.techelevator.capstone.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class JdbcSiteDao implements SiteDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcSiteDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
}