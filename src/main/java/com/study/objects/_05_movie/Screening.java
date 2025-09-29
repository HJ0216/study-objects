package com.study.objects._05_movie;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Screening {

  private Movie movie;
  private int sequence;
  private LocalDateTime whenScreened;

  public Reservation reserve(Customer customer, int audienceCount){
    return new Reservation(customer, this, calculateFee(audienceCount), audienceCount);
  }

  private Money calculateFee(int audienceCount) {
    return movie.calculateMovieFee(this).times(audienceCount);
  }
}
