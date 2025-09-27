package com.study.objects._02_movie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ScreeningTest {

  @Nested
  @DisplayName("reserve()는")
  class Describe_reserve {

    @Nested
    @DisplayName("올바른 예약 요청 시")
    class Context_with_valid_request {


      @Test
      @DisplayName("예약 객체를 반환한다")
      void it_returns_correct_reservation() {
        // given
        Customer customer = new Customer();
        int audienceCount = 3;
        Movie movie = Movie.getAvatar();
        Screening screening = new Screening(movie, 3, LocalDateTime.of(2025, 2, 16, 9, 15));

        // when
        Reservation reservation = screening.reserve(customer, audienceCount);

        // then
        assertNotNull(reservation);
        assertInstanceOf(Reservation.class, reservation);

        assertEquals(BigDecimal.valueOf(30_000.0), reservation.getMoney().getAmount());
      }
    }
  }
}