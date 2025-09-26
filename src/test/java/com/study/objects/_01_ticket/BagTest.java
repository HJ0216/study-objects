package com.study.objects._01_ticket;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BagTest {

  @Nested
  @DisplayName("hold()는")
  class Describe_hold {

    @Nested
    @DisplayName("초대장이 있으면")
    class Context_with_invitation {

      @Test
      @DisplayName("0원을 반환한다")
      void it_returns_0L() {
        //given
        Long initialAmount = 1000L;
        Bag bag = new Bag(initialAmount, new Invitation());
        Ticket ticket = new Ticket();

        //when
        Long amount = bag.hold(ticket);

        //then
        assertEquals(0L, amount);
      }
    }

    @Nested
    @DisplayName("초대장이 없으면")
    class Context_without_invitation {

      @Test
      @DisplayName("티켓 요금을 반환한다")
      void it_returns_ticket_fee() {
        //given
        Long initialAmount = 1000L;
        Bag bag = new Bag(initialAmount);
        Ticket ticket = new Ticket();

        //when
        Long amount = bag.hold(ticket);

        //then
        assertEquals(ticket.getFee(), amount);
      }

      @Test
      @DisplayName("가진 돈에서 티켓 요금이 차감된다")
      void it_reduces_amount_by_ticket_fee() {
        //given
        Long initialAmount = 1000L;
        Bag bag = new Bag(initialAmount);
        Ticket ticket = new Ticket();

        //when
        bag.hold(ticket);

        //then
        assertEquals(initialAmount - ticket.getFee(), bag.getAmount());
      }
    }
  }
}