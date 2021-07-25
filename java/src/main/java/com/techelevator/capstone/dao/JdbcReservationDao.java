package com.techelevator.capstone.dao;


import com.techelevator.capstone.model.Campground;
import com.techelevator.capstone.model.Reservation;
import com.techelevator.capstone.model.Site;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class JdbcReservationDao implements ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long makeReservation(Long siteSelection, String name, LocalDate from_date, LocalDate to_date){
        String sql = "insert into reservation (site_id, name, from_date, to_date)\n" +
                "        values (?, ?, ?, ?);";
        jdbcTemplate.update(sql, siteSelection, name, from_date, to_date);

        sql = "SELECT reservation_id FROM reservation WHERE site_id = ? AND from_date = ? AND to_date = ?;";
        return (Long) jdbcTemplate.queryForObject(sql, new ReservationRowMapper(), siteSelection, from_date, to_date);
    }

    class ReservationRowMapper implements RowMapper {
        @Override
        public Object mapRow(ResultSet resultSet, int i) throws SQLException {

            return resultSet.getLong("reservation_id");
        }
    }
}


