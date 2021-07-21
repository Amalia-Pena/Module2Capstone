package com.techelevator.capstone.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class JdbcReservationDao implements ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
}