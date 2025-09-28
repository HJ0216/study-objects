package com.study.objects._04_movie;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Reservation {

  private final Customer customer;
  private final Screening screening;
  private final Money fee;
  private final int audienceCount;
}
