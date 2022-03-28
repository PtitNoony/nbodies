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
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author ahamon
 */
public class ExecutionController extends GraphicalController {

    private final CalculationRunner calculationRunner;

    private PlayerController controller;

    public ExecutionController(CalculationRunner aCalculationRunner) {
        super();
        calculationRunner = aCalculationRunner;
        createHandle();
        createContent();
    }

    private void createHandle() {
        Circle c = new Circle(8, Color.CHARTREUSE);
        c.setStroke(Color.LIGHTGRAY);
        setHandle(c);
    }

    private void createContent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Player.fxml"));
            setContent(loader.load());
            controller = loader.getController();
            controller.setCalculationRunner(calculationRunner);
        } catch (IOException ex) {
            Logger.getLogger(ExecutionController.class.getName()).log(Level.SEVERE, "Error while loading Player.fxml:: {0}", ex);
        }
    }
}
