package com.tipsypixie.meetingroom.controllers;

import com.tipsypixie.meetingroom.repositories.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class ReservationController {
    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);
    private ReservationRepository reservation;

    public ReservationController(ReservationRepository reservation) {
        this.reservation = reservation;
    }
}
