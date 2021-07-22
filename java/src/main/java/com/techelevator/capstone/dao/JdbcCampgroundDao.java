
package com.techelevator.capstone.dao;

import com.techelevator.capstone.model.Campground;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcCampgroundDao implements CampgroundDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCampgroundDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Campground> getCampgrounds(Long parkSelection) {
        String sql = "SELECT * FROM campground WHERE park_id = ? ORDER BY campground_id;";
        List<Campground> campgroundList = jdbcTemplate.query(sql, new CampgroundRowMapper(), parkSelection);
        return campgroundList;

    }

    class CampgroundRowMapper implements RowMapper {
        @Override
        public Object mapRow(ResultSet resultSet, int i) throws SQLException {
            Campground campground = new Campground();
            campground.setCampgroundId(resultSet.getLong("campground_id"));
            campground.setParkId(resultSet.getLong("park_id"));
            campground.setName(resultSet.getString("name"));
            campground.setOpenFromMm(resultSet.getString("open_from_mm"));
            campground.setOpenToMm(resultSet.getString("open_to_mm"));
            campground.setDailyFee(resultSet.getString("daily_fee"));

            return campground;
        }
    }
}