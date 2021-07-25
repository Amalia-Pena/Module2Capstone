package com.techelevator.capstone.dao;

import com.techelevator.capstone.model.Reservation;
import com.techelevator.capstone.model.Site;

import java.time.LocalDate;

public interface ReservationDao {
    public Long makeReservation(Long siteSelection, String name, LocalDate from_date, LocalDate to_date);
}

