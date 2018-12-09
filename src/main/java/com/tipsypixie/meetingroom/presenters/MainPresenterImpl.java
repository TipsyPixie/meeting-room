package com.tipsypixie.meetingroom.presenters;

import com.tipsypixie.meetingroom.exceptions.ValueInvalidException;
import com.tipsypixie.meetingroom.exceptions.ValueUnreachableException;
import com.tipsypixie.meetingroom.models.Reservation;
import com.tipsypixie.meetingroom.repositories.ReservationRepository;
import com.tipsypixie.meetingroom.views.MainView;
import com.tipsypixie.meetingroom.views.components.ReservationGrid;
import com.vaadin.flow.component.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class MainPresenterImpl implements MainPresenter {
    private static List<String> possibleTimeValues;
    private static List<String> possibleRoomSigns;
    private Logger logger = LoggerFactory.getLogger(MainPresenterImpl.class);
    private ReservationRepository repository;

    public MainPresenterImpl(ReservationRepository repository) {
        this.repository = repository;

        try {
            init();
        } catch (Exception e) {
            logger.error(e.toString());
            System.exit(-1);
        }
    }

    private void init() {
        possibleTimeValues = new ArrayList<>(47);
        for (int i = 0; i < 24; i++) {
            possibleTimeValues.add(i + ":" + "00");
            possibleTimeValues.add(i + ":" + "30");
        }

        possibleRoomSigns = Arrays.asList("A", "B", "C", "D");
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void addButtonClicked(MainView mainView) {
        try {
            mainView.getAddButton().setEnabled(false);

            LocalDate selectedDate = Optional.ofNullable(mainView.getDateSelector().getValue())
                .orElseThrow(() -> new ValueUnreachableException("invalid date value."));

            LocalTime selectedStartTime = Optional.ofNullable(mainView.getStartTimeSelector().getValue())
                .map(this::stringToLocalTime)
                .orElseThrow(() -> new ValueUnreachableException("invalid start time value."));
            LocalTime selectedEndTime = Optional.ofNullable(mainView.getEndTimeSelector().getValue())
                .map(this::stringToLocalTime)
                .orElseThrow(() -> new ValueUnreachableException("invalid end time value."));
            if (selectedEndTime.isBefore(selectedStartTime.plusMinutes(30))) {
                throw new ValueInvalidException("end time must be at least 30min after start time");
            }

            String selectedRoom = Optional.ofNullable(mainView.getRoomSelector().getValue())
                .orElseThrow(() -> new ValueUnreachableException("invalid room number"));
            String authorName = Optional.ofNullable(mainView.getNameInput().getValue())
                .orElseThrow(() -> new ValueUnreachableException("invalid author name"))
                .trim();
            if (authorName.isEmpty()) {
                throw new ValueUnreachableException("author name can't be empty");
            }

            String repetitionCountValue = Optional.ofNullable(mainView.getRepetitionInput().getValue())
                .orElseThrow(() -> new ValueUnreachableException("invalid repetition count"))
                .trim();
            if (repetitionCountValue.isEmpty()) {
                throw new ValueInvalidException("repetition count can't be empty");
            }

            int repetitionCount = Integer.parseInt(repetitionCountValue, 10);
            if (repetitionCount < 0) {
                throw new ValueInvalidException("repetition count must be greater than or equal to 0");
            }

            for (long additionalWeek = 0; additionalWeek <= repetitionCount; additionalWeek++) {
                LocalDate targetDate = selectedDate.plusWeeks(additionalWeek);

                LocalDateTime startingDateTime = targetDate.atTime(selectedStartTime);
                LocalDateTime endingDateTime = targetDate.atTime(selectedEndTime);

                if (repository.findByTimeIntersected(selectedRoom, startingDateTime, endingDateTime).size() > 0) {
                    throw new ValueInvalidException("another reservation is already there");
                }

                Reservation reservation = new Reservation()
                    .setAuthor(authorName)
                    .setRoom(selectedRoom)
                    .setStartsAt(startingDateTime)
                    .setEndsAt(endingDateTime)
                    .setRepetitionCount(repetitionCount);
                repository.save(reservation);
            }

            showNotification("reservation made successfully");

            updateGrid(mainView, selectedDate);
        } catch (ValueUnreachableException | ValueInvalidException e) {
            logger.error(e.getMessage());
            showNotification(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            showNotification("unknown error");
        } finally {
            mainView.getAddButton().setEnabled(true);
        }
    }

    @Override
    public void dateSelectionChanged(MainView mainView) {
        try {
            LocalDate selectedDate = Optional.ofNullable(mainView.getDateSelector().getValue())
                .orElseThrow(() -> new ValueUnreachableException("invalid date value."));

            updateGrid(mainView, selectedDate);
        } catch (ValueUnreachableException e) {
            logger.error(e.getMessage());
            showNotification(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            showNotification("unknown error");
        }
    }

    @Override
    public List<String> getPossibleTimeValues() {
        return possibleTimeValues;
    }

    @Override
    public List<String> getPossibleRoomSigns() {
        return possibleRoomSigns;
    }

    private LocalTime stringToLocalTime(String timeValue) {
        List<Integer> time = Stream.of(timeValue.split(":"))
            .map(Integer::parseInt).collect(Collectors.toList());
        return LocalTime.of(time.get(0), time.get(1));
    }

    private void updateGrid(MainView mainView, LocalDate selectedDate) {
        List<Reservation> reservationsOnDate = repository
            .findByStartsAtOrdered(selectedDate.atStartOfDay(), selectedDate.plusDays(1).atStartOfDay());

        List<ReservationGrid> grids = mainView.getGrids();
        for (int i = 0; i < possibleRoomSigns.size(); ++i) {
            String roomSign = possibleRoomSigns.get(i);
            grids.get(i).setItems(
                reservationsOnDate.stream().filter(reservation -> reservation.getRoom().equals(roomSign))
                    .collect(Collectors.toList()));
        }
    }

    private void showNotification(String text) {
        Notification.show(text, 3000, Notification.Position.TOP_CENTER);
    }
}
