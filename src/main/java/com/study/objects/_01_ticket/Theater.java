package com.study.objects._01_ticket;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Theater {

  private final TicketSeller ticketSeller;

  public void enter(Audience audience) {
    ticketSeller.sellTo(audience);
  }
}
