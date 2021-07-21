package com.sample;


public class Reservation {

  private long reservationId;
  private long siteId;
  private String name;
  private java.sql.Date fromDate;
  private java.sql.Date toDate;
  private java.sql.Date createDate;


  public long getReservationId() {
    return reservationId;
  }

  public void setReservationId(long reservationId) {
    this.reservationId = reservationId;
  }


  public long getSiteId() {
    return siteId;
  }

  public void setSiteId(long siteId) {
    this.siteId = siteId;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public java.sql.Date getFromDate() {
    return fromDate;
  }

  public void setFromDate(java.sql.Date fromDate) {
    this.fromDate = fromDate;
  }


  public java.sql.Date getToDate() {
    return toDate;
  }

  public void setToDate(java.sql.Date toDate) {
    this.toDate = toDate;
  }


  public java.sql.Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(java.sql.Date createDate) {
    this.createDate = createDate;
  }

}
