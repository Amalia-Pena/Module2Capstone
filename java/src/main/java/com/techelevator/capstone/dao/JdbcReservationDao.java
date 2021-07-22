package com.techelevator.capstone.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class JdbcReservationDao implements ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
}

   /* Select DISTINCT site_number, campground.campground_id, reservation_id, from_date, to_date
        From site
        Left Join reservation on reservation.site_id = site.site_id
        Join campground on site.campground_id = campground.campground_id
        WHERE site_number not in (Select site_number
        From site
        Left Join reservation on reservation.site_id = site.site_id
        Join campground on site.campground_id = campground.campground_id
        WHERE (from_date, to_date) overlaps ('?', '?') AND site.campground_id = ?
        ORDER BY site_number)
        and campground.campground_id = ?
        ORDER BY site_number
        Limit 5;


        insert into reservation (site_id, name, from_date, to_date, create_date)
        values (?, ?, ?, ?, ?);
    */


