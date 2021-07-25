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
    private List<Site> siteList;

    public JdbcSiteDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void getAvailableSites(Long campgroundSelection, LocalDate from_date, LocalDate to_date) {
        // Incrementing the days by one because overlaps method is not inclusive
        from_date.minusDays(1);
        to_date.plusDays(1);

        String sql = "SELECT DISTINCT site.site_id, site.campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities\n" +
                "FROM site\n" +
                "         LEFT JOIN reservation ON reservation.site_id = site.site_id\n" +
                "         JOIN campground ON site.campground_id = campground.campground_id\n" +
                "WHERE site_number NOT IN (SELECT site_number\n" +
                "                          FROM site\n" +
                "                                   LEFT JOIN reservation ON reservation.site_id = site.site_id\n" +
                "                                   JOIN campground ON site.campground_id = campground.campground_id\n" +
                "                          WHERE (reservation.from_date, reservation.to_date) OVERLAPS (?,?) AND site.campground_id = ?\n" +
                "                          ORDER BY site_number)\n" +
                "                AND campground.campground_id = ?\n" +
                "ORDER BY site_number\n" +
                "limit 5;";


        siteList = jdbcTemplate.query(sql, new SiteRowMapper(), from_date, to_date, campgroundSelection,campgroundSelection);
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

    public List<Site> getSiteList(){
        return siteList;
    }



}