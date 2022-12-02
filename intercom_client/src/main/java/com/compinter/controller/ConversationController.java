package com.compinter.controller;

import javafx.fxml.FXML;

import com.compinter.App;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ConversationController {

    @FXML
    private Button announceButton;

    @FXML
    private TextField numApartment;

    @FXML
    private TextField visitorName;

	private App main;

    @FXML
    void announceAction(ActionEvent event) {
    }

	public void setMain(App main) {
		this.main=main;
		
	}

}
