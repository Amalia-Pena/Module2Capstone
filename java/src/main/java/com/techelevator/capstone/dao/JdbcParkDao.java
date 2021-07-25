package com.techelevator.capstone.dao;

import com.techelevator.capstone.model.Park;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcParkDao implements ParkDao {
    // Instance variables
    private final JdbcTemplate jdbcTemplate;
    private List<Park> parkList;

    // Constructor
    public JdbcParkDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public void getParks() {
        String sql = "SELECT * FROM park ORDER BY name;";
        parkList =  jdbcTemplate.query(sql, new ParkRowMapper());
    }

    class ParkRowMapper implements RowMapper {
        @Override
        public Object mapRow(ResultSet resultSet, int i) throws SQLException {
            Park park = new Park();
            park.setParkId(resultSet.getLong("park_id"));
            park.setName(resultSet.getString("name"));
            park.setLocation(resultSet.getString("location"));
            try {
                park.setEstablishDate(resultSet.getDate("establish_date"));
            }
            catch (NullPointerException e) {

            }
            park.setArea(resultSet.getLong("area"));
            park.setVisitors(resultSet.getLong("visitors"));
            park.setDescription(resultSet.getString("description"));
            return park;
        }
    }

    // Getters
    public List<Park> getParkList(){
        return parkList;
    }


}
