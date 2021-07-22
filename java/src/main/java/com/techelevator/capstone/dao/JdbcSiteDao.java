package com.techelevator.capstone.dao;


import com.techelevator.capstone.model.Site;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class JdbcSiteDao implements SiteDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcSiteDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Site> getAvailableSites(Long campgroundSelection, LocalDate from_date, LocalDate to_date) {
        String sql = "Select DISTINCT site_number, campground.campground_id\n" +
                "From site\n" +
                "         Left Join reservation on reservation.site_id = site.site_id\n" +
                "         Join campground on site.campground_id = campground.campground_id\n" +
                "WHERE site_number not in (Select site_number\n" +
                "                          From site\n" +
                "                                   Left Join reservation on reservation.site_id = site.site_id\n" +
                "                                   Join campground on site.campground_id = campground.campground_id\n" +
                "                          WHERE (reservation.from_date, reservation.to_date) overlaps (?,?) AND site.campground_id = ?\n" +
                "                          ORDER BY site_number)\n" +
                "                and campground.campground_id = ?\n" +
                "ORDER BY site_number\n" +
                "limit 5;";

        String sql2 = "Select DISTINCT site.site_id, site.campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities\n" +
                "From site\n" +
                "         Join reservation on reservation.site_id = site.site_id\n" +
                "         Join campground on site.campground_id = campground.campground_id\n" +
                "WHERE (?,?) overlaps (reservation.from_date, reservation.to_date) is false AND site.campground_id = ?\n" +
                "ORDER BY site_number;";


        List<Site> siteList = jdbcTemplate.query(sql2, new SiteRowMapper(), from_date, to_date, campgroundSelection);
        return siteList;
    }



    class SiteRowMapper implements RowMapper {
        @Override
        public Object mapRow(ResultSet resultSet, int i) throws SQLException {
            Site site = new Site();
            site.setSiteId(resultSet.getLong("site_id"));
            site.setCampgroundId(resultSet.getLong("campground_id"));
            site.setSiteNumber(resultSet.getLong("site_number"));
            site.setMaxOccupancy(resultSet.getLong("max_occupancy"));
            site.setAccessible(resultSet.getString("accessible"));
            site.setMaxRvLength(resultSet.getLong("max_rv_length"));
            site.setUtilities(resultSet.getString("utilities"));

            return site;
        }
    }



}