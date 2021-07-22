package com.techelevator.capstone.dao;

import com.techelevator.capstone.model.Site;

import java.time.LocalDate;
import java.util.List;

public interface SiteDao {
    List<Site> getAvailableSites(Long campgroundSelection, LocalDate from_date, LocalDate to_date);

}
