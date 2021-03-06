/*
 * Copyright (C) 2022 NoOnY
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.noony.app.nbodies.fx;

import com.github.noony.app.nbodies.CalculationRunner;
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

    private CalculationRunner calculationRunner;

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
        if (calculationRunner != null) {
            calculationRunner.pause();
        }
    }

    @FXML
    protected void onPlayAction(ActionEvent event) {
        if (calculationRunner != null) {
            calculationRunner.play();
        }
    }

    protected void setCalculationRunner(CalculationRunner aCalculationRunner) {
        calculationRunner = aCalculationRunner;
        updateState();
        calculationRunner.addPropertyChangeListener(event -> updateState());
    }

    private void updateState() {
        if (calculationRunner != null) {
            switch (calculationRunner.getExecutionState()) {
                case PAUSED -> {
                    playTB.setDisable(false);
                    playTB.setSelected(false);
                    pauseTB.setDisable(true);
                    pauseTB.setSelected(true);
                }
                case PLAYING -> {
                    playTB.setDisable(true);
                    playTB.setSelected(true);
                    pauseTB.setDisable(false);
                    pauseTB.setSelected(false);
                }
                default ->
                    throw new UnsupportedOperationException();
            }
        } else {
            playTB.setDisable(true);
            pauseTB.setDisable(true);
        }
    }
}
