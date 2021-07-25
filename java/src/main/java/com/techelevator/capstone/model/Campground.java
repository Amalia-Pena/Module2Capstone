package com.techelevator.capstone.model;


public class Campground {

  private long campgroundId;
  private long parkId;
  private String name;
  private String openFromMm;
  private String openToMm;
  private String dailyFee;


  public long getCampgroundId() {
    return campgroundId;
  }

  public void setCampgroundId(long campgroundId) {
    this.campgroundId = campgroundId;
  }


  public long getParkId() {
    return parkId;
  }

  public void setParkId(long parkId) {
    this.parkId = parkId;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getOpenFromMm() {
    return openFromMm;
  }

  public void setOpenFromMm(String openFromMm) {
    this.openFromMm = openFromMm;
  }


  public String getOpenToMm() {
    return openToMm;
  }

  public void setOpenToMm(String openToMm) {
    this.openToMm = openToMm;
  }


  public String getDailyFee() {
    return dailyFee;
  }

  public void setDailyFee(String dailyFee) {
    this.dailyFee = dailyFee;
  }

  public String toString(){
    return name + " " + openFromMm + " " + openToMm + " " + dailyFee;
  }

}
