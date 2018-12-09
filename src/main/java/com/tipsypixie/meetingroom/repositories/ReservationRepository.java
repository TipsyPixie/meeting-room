package com.tipsypixie.meetingroom.repositories;

import com.tipsypixie.meetingroom.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT reservation " +
        "FROM Reservation reservation " +
        "WHERE reservation.startsAt BETWEEN :startsAtMin AND :startsAtMax " +
        "ORDER BY reservation.startsAt ASC")
    List<Reservation> findByStartsAtOrdered(
        @Param("startsAtMin") LocalDateTime startsAtMin,
        @Param("startsAtMax") LocalDateTime startAtMax);

    @Query("SELECT reservation " +
        "FROM Reservation reservation " +
        "WHERE reservation.room = :room AND reservation.endsAt > :startsAt AND reservation.startsAt < :endsAt")
    List<Reservation> findByTimeIntersected(
        @Param("room") String room,
        @Param("startsAt") LocalDateTime startsAt,
        @Param("endsAt") LocalDateTime endsAt);
}
