package com.tipsypixie.meetingroom.views;

import com.tipsypixie.meetingroom.views.components.ReservationGrid;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;

public interface MainView {
    ListBox<String> getRoomSelector();

    List<ReservationGrid> getGrids();

    TextField getNameInput();

    DatePicker getDateSelector();

    ComboBox<String> getStartTimeSelector();

    ComboBox<String> getEndTimeSelector();

    TextField getRepetitionInput();
}
