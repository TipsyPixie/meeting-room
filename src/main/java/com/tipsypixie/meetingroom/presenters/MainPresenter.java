package com.tipsypixie.meetingroom.presenters;

import com.tipsypixie.meetingroom.views.MainView;

import java.util.List;

public interface MainPresenter {
    void addButtonClicked(MainView mainView);

    void dateSelectionChanged(MainView mainView);

    List<String> getPossibleTimeValues();

    List<String> getPossibleRoomSigns();
}
