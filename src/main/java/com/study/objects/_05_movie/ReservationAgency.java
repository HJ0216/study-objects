package com.study.objects._05_movie;

public class ReservationAgency {

  public Reservation reserve(Screening screening, Customer customer, int audienceCount) {
    return screening.reserve(customer, audienceCount);
  }
}
