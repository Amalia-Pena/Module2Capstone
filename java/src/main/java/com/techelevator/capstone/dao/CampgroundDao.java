package com.techelevator.capstone.dao;

import com.techelevator.capstone.model.Campground;

import java.util.List;

public interface CampgroundDao {
    List <Campground> getCampgrounds(Long parkSelection);
}
