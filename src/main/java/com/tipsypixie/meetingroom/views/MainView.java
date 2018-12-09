package com.tipsypixie.meetingroom.views;

import com.tipsypixie.meetingroom.presenters.MainPresenter;
import com.tipsypixie.meetingroom.views.components.ReservationGrid;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Route(value = "")
@SuppressWarnings(value = "FieldCanBeLocal")
public class MainView extends VerticalLayout {
    private Logger logger = LoggerFactory.getLogger(MainView.class);

    private MainPresenter presenter;

    private DatePicker dateSelector;
    private TextField nameInput;
    private ListBox<String> roomSelector;
    private HorizontalLayout selectorsContainer;
    private ComboBox<String> startTimeSelector;
    private ComboBox<String> endTimeSelector;
    private TextField repetitionInput;
    private Button addButton;
    private HorizontalLayout gridsContainer;
    private List<ReservationGrid> grids;

    public MainView(MainPresenter presenter) {
        this.presenter = presenter;

        try {
            init();
        } catch (Exception e) {
            logger.error(e.toString());
            System.exit(-1);
        }
    }

    private void init() {
        List<String> roomSigns = presenter.getPossibleRoomSigns();
        List<String> timeValues = presenter.getPossibleTimeValues();

        // upper input form
        dateSelector = new DatePicker("Date");
        dateSelector.setInitialPosition(LocalDate.now());
        dateSelector.setRequired(true);
        dateSelector.setMin(LocalDate.of(1900, 1, 1));
        dateSelector.setMax(LocalDate.of(2100, 12, 31));
        dateSelector.addValueChangeListener(event -> presenter.dateSelectionChanged(this));

        nameInput = new TextField("Name");
        nameInput.setMaxLength(10);
        nameInput.setRequired(true);
        nameInput.setPreventInvalidInput(true);

        roomSelector = new ListBox<>();
        roomSelector.setItems(roomSigns);
        roomSelector.setValue(roomSigns.get(0));

        startTimeSelector = new ComboBox<>("Starts at");
        endTimeSelector = new ComboBox<>("Ends at");
        Stream.of(startTimeSelector, endTimeSelector).forEach(timeSelector -> {
            timeSelector.setPattern("^[0-9]*:[0-9]*$");
            timeSelector.setItems(timeValues);
            timeSelector.setRequired(true);
            timeSelector.setAllowCustomValue(false);
            timeSelector.setPreventInvalidInput(true);
        });

        selectorsContainer = new HorizontalLayout();
        selectorsContainer.add(roomSelector, startTimeSelector, endTimeSelector);

        repetitionInput = new TextField("Repetition");
        repetitionInput.setPattern("^[0-9]*");
        repetitionInput.setMaxLength(4);
        repetitionInput.setRequired(true);
        repetitionInput.setPreventInvalidInput(true);

        addButton = new Button("Add");
        addButton.addClickListener(event -> presenter.addButtonClicked(this));

        // bottom grids
        gridsContainer = new HorizontalLayout();
        gridsContainer.setWidth("90%");
        grids = new ArrayList<>(roomSigns.size());
        roomSigns.forEach(roomSign -> {
            ReservationGrid grid = new ReservationGrid("Room " + roomSign);
            grid.setWidth(String.format("%d%%", 100 / roomSigns.size()));
            grids.add(grid);
            gridsContainer.add(grid);
        });

        // initially, set selected date to today
        dateSelector.setValue(LocalDate.now());

        add(nameInput, dateSelector, selectorsContainer, repetitionInput, roomSelector, addButton, gridsContainer);
    }

    public ListBox<String> getRoomSelector() {
        return roomSelector;
    }

    public List<ReservationGrid> getGrids() {
        return grids;
    }

    public TextField getNameInput() {
        return nameInput;
    }

    public DatePicker getDateSelector() {
        return dateSelector;
    }

    public ComboBox<String> getStartTimeSelector() {
        return startTimeSelector;
    }

    public ComboBox<String> getEndTimeSelector() {
        return endTimeSelector;
    }

    public TextField getRepetitionInput() {
        return repetitionInput;
    }

    public Button getAddButton() {
        return addButton;
    }
}
