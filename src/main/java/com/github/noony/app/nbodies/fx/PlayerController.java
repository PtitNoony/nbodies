/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.noony.app.nbodies.fx;

import com.github.noony.app.nbodies.GlobalClock;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;

/**
 * FXML Controller class
 *
 * @author ahamon
 */
public class PlayerController implements Initializable {

    @FXML
    private ToggleButton playTB;

    @FXML
    private ToggleButton pauseTB;

    private GlobalClock globalClock;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateState();
    }

    @FXML
    protected void onPauseAction(ActionEvent event) {
        if (globalClock != null) {
            globalClock.pause();
        }
    }

    @FXML
    protected void onPlayAction(ActionEvent event) {
        if (globalClock != null) {
            globalClock.play();
        }
    }

    protected void setClock(GlobalClock clock) {
        globalClock = clock;
        updateState();
        globalClock.addPropertyChangeListener(event -> updateState());
    }

    private void updateState() {
        if (globalClock != null) {
            switch (globalClock.getState()) {
                case PAUSED:
                    playTB.setDisable(false);
                    playTB.setSelected(false);
                    pauseTB.setDisable(true);
                    pauseTB.setSelected(true);
                    break;
                case PLAYING:
                    playTB.setDisable(true);
                    playTB.setSelected(true);
                    pauseTB.setDisable(false);
                    pauseTB.setSelected(false);
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        } else {
            playTB.setDisable(true);
            pauseTB.setDisable(true);
        }
    }
}
