package com.study.objects._01_ticket;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TicketSeller {

  private final TicketOffice ticketOffice;

  public void sellTo(Audience audience) {
    ticketOffice.sellTicketTo(audience);
  }
}
