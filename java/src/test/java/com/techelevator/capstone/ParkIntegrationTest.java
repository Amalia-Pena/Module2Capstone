package com.techelevator.capstone;

import com.techelevator.capstone.dao.JdbcParkDao;
import com.techelevator.capstone.model.Park;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JdbcParkDao.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/test-data.sql")
@Import(TestConfig.class)
public class ParkIntegrationTest {

    @Autowired
    private DataSource dataSource;
    // create test objects to compare against database
    private final Park park_1 = new Park(); // populate
    // System under test - sut
    private JdbcParkDao sut;

    @Before
    public void setup() {
        sut = new JdbcParkDao(dataSource);
    }

    // include this method in every test class to ensure the database is clean for the next run.
    @After
    public void rollback() throws SQLException {
        dataSource.getConnection().rollback();
        sut = null;
    }

    @Test
    public void getAllParksReturnsCorrectNumberOfParks() {
        assertTrue(true);
    }


}
