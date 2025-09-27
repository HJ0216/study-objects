package com.study.objects._02_movie;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Screening {

  private final Movie movie;
  private final int sequence;
  private final LocalDateTime whenScreened;

  public LocalDateTime getStartTime(){
    return whenScreened;
  }

  public boolean isSequence(int sequence) {
    return this.sequence == sequence;
  }

  public Money getMoviewFee(){
    return movie.getFee();
  }

  public Reservation reserve(Customer customer, int audienceCount){
    return new Reservation(customer, this, calculateFee(audienceCount), audienceCount);
  }

  private Money calculateFee(int audienceCount) {
    return movie.calculateMovieFee(this).times(audienceCount);
  }
}
