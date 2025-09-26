package com.study.objects._01_ticket;

import lombok.Getter;
import lombok.Setter;

public class Bag {

  @Getter
  private Long amount;
  private Invitation invitation;
  @Setter
  private Ticket ticket;

  public Long hold(Ticket ticket) {
    if (hasInvitation()) {
      setTicket(ticket);
      return 0L;
    } else {
      minusAmount(ticket.getFee());
      setTicket(ticket);
      return ticket.getFee();
    }
  }

  private boolean hasInvitation() {
    return invitation != null;
  }

  public boolean hasTicket() {
    return ticket != null;
  }

  public void plusAmount(Long amount) {
    this.amount += amount;
  }

  private void minusAmount(Long amount) {
    this.amount -= amount;
  }

  public Bag(Long amount) {
    this.amount = amount;
  }

  public Bag(Long amount, Invitation invitation) {
    this.amount = amount;
    this.invitation = invitation;
  }
}
