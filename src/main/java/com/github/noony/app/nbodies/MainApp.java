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
package com.github.noony.app.nbodies;

import com.github.noony.app.nbodies.fx.ControlLayer;
import com.github.noony.app.nbodies.fx.ExecutionController;
import com.github.noony.app.nbodies.fx.SolarSystem2D;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static final double DEFAULT_WIDTH = 1600;
    public static final double DEFAULT_HEIGHT = 900;

    private static final Logger LOG = Logger.getGlobal();

    static {
        var stream = MainApp.class.getClassLoader().getResourceAsStream("logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(stream);
            LOG.setLevel(Level.FINEST);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Could not load loader properties :: {0}", e.getCause());
        }
    }

    private double width = DEFAULT_WIDTH;
    private double height = DEFAULT_HEIGHT;

    private SolarSystem solarSystem;

    //
    private SolarSystem2D solarSystem2D;
    private ControlLayer controlLayer;

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, width, height, Color.BLACK);
        scene.getStylesheets().add("/styles/Styles.css");
        //
//        GlobalClock globalClock = new GlobalClock();
        solarSystem = Examples.createOurSolarSystemAsPrecise();
//        solarSystem.setGlobalClock(globalClock);
        //
        solarSystem2D = new SolarSystem2D();
        solarSystem2D.setSolarSystem(solarSystem);
        //
        controlLayer = new ControlLayer();
        CalculationRunner calculationRunner = new CalculationRunner(solarSystem, 5);
        ExecutionController executionController = new ExecutionController(calculationRunner);
        //
        controlLayer.addController(executionController);
        //
        // mini map
        //
        root.getChildren().addAll(solarSystem2D.getNode(), controlLayer.getNode());
        //
        scene.widthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            // TODO: LOG
            width = newValue.doubleValue();
            updateSize();
        });
        scene.heightProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            // TODO: LOG
            height = newValue.doubleValue();
            updateSize();
        });

        stage.setTitle("Simple solar system creator");
        stage.setScene(scene);
        stage.show();

        calculationRunner.start();
    }

    private void updateSize() {
        solarSystem2D.updateSize(width, height);
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application. main() serves only as fallback in case the
     * application can not be launched through deployment artifacts, e.g., in IDEs with limited FX support. NetBeans
     * ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
