package com.techelevator.capstone.model;


public class Park {

  private long parkId;
  private String name;
  private String location;
  private java.sql.Date establishDate;
  private long area;
  private long visitors;
  private String description;


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


  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }


  public java.sql.Date getEstablishDate() {
    return establishDate;
  }

  public void setEstablishDate(java.sql.Date establishDate) {
    this.establishDate = establishDate;
  }


  public long getArea() {
    return area;
  }

  public void setArea(long area) {
    this.area = area;
  }


  public long getVisitors() {
    return visitors;
  }

  public void setVisitors(long visitors) {
    this.visitors = visitors;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void displayPark(){
    int counter = 0;
    int lineBreak = 80;
    System.out.println("\nPark Information Screen");
    System.out.println(name + " National Park");
    System.out.println("Location: " + location);
    System.out.println("Established: " + establishDate);
    System.out.println("Area: " + area);
    System.out.println("Annual visitors: " + visitors +"\n");
    while (counter < description.length()){
      if(counter + lineBreak > description.length()){
        System.out.println(description.substring(counter));
        counter = description.length();
      }
      else{
        System.out.println(description.substring(counter,lineBreak));
        counter += 80;
        lineBreak += 80;
      }

    }

  }

}
