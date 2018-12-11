package com.tipsypixie.meetingroom.views.components;

import com.tipsypixie.meetingroom.models.Reservation;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

@SuppressWarnings(value = "FieldCanBeLocal")
public class ReservationGrid extends VerticalLayout {
    private Logger logger = LoggerFactory.getLogger(ReservationGrid.class);

    private Grid<Reservation> grid;
    private String labelContent;
    private Label label;

    public ReservationGrid(String labelContent) {
        this.labelContent = labelContent;


        init();
    }

    private void init() {
        try {
            setAlignItems(Alignment.CENTER);

            // grid label
            label = new Label(labelContent);

            // set column formatting
            grid = new Grid<>();
            grid.setWidth("100%");
            grid.addColumn(reservation -> {
                LocalDateTime startDateTime = reservation.getStartsAt();
                LocalDateTime endDateTime = reservation.getEndsAt();
                return String.format("%02d:%02d - %02d:%02d\t%s",
                    startDateTime.getHour(), startDateTime.getMinute(),
                    endDateTime.getHour(), endDateTime.getMinute(),
                    reservation.getAuthor());
            });

            add(label, grid);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    public void setItems(List<Reservation> reservations) {
        grid.setItems(reservations);
    }
}
