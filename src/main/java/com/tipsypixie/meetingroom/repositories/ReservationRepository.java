package com.tipsypixie.meetingroom.repositories;

import com.tipsypixie.meetingroom.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
