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

import com.github.noony.app.nbodies.AbstractBody;
import com.github.noony.app.nbodies.MainApp;
import com.github.noony.app.nbodies.SolarSystem;
import java.beans.PropertyChangeEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import static javafx.application.Platform.runLater;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author ahamon
 */
public class SolarSystem2D {

    private static final double VIEWING_RATIO = 1.5;
    private static final double DEFAULT_SCALE_FACTOR = 1.0 / 1000.0;

    private final Group mainNode;
    private final Group systemGroup;
    //
    private final Line xAxis;
    private final Line yAxis;

    private final List<Body2D> bodies;

    private double width = MainApp.DEFAULT_WIDTH;
    private double height = MainApp.DEFAULT_HEIGHT;

    private double centerX = width / 2.0;
    private double centerY = height / 2.0;

    private SolarSystem solarSystem = null;
    private double scaleFactor = DEFAULT_SCALE_FACTOR;

    private int updateCount = 0;

    public SolarSystem2D() {
        mainNode = new Group();
        systemGroup = new Group();
        bodies = new LinkedList<>();
        xAxis = new Line();
        yAxis = new Line();
        xAxis.setStroke(Color.WHITESMOKE); // TODO
        yAxis.setStroke(Color.WHITESMOKE);

        initGroups();
        updateDrawingsOnResize();
    }

    public void updateSize(double newWidth, double newHeight) {
        // TODO some tests
        width = newWidth;
        height = newHeight;
        centerX = width / 2.0;
        centerY = height / 2.0;
        runLater(this::updateDrawingsOnResize);
    }

    public Node getNode() {
        return mainNode;
    }

    private void initGroups() {
        mainNode.getChildren().addAll(xAxis, yAxis, systemGroup);
    }

    private void updateDrawingsOnResize() {
        xAxis.setStartY(centerY);
        xAxis.setEndX(width);
        xAxis.setEndY(centerY);
        //
        yAxis.setStartX(centerX);
        yAxis.setEndX(centerX);
        yAxis.setEndY(height);
        //
        systemGroup.setTranslateX(centerX);
        systemGroup.setTranslateY(centerY);
        updateScaleFactor();
    }

    public void setSolarSystem(SolarSystem aSolarSystem) {
        if (solarSystem != null) {
            solarSystem.removePropertyChangeListener(this::handleSolarSystemChange);
        }
        bodies.clear();
        solarSystem = aSolarSystem;

        updateScaleFactor();

        solarSystem.getBodies().forEach(this::createBody2D);
        solarSystem.addPropertyChangeListener(this::handleSolarSystemChange);
    }

    private void handleSolarSystemChange(PropertyChangeEvent event) {
        switch (event.getPropertyName()) {
            case SolarSystem.BODY_ADDED ->
                createBody2D((AbstractBody) event.getNewValue());
            case SolarSystem.TIME_CHANGED ->
                updateBodiesPosition((Map<AbstractBody, Point3D>) event.getNewValue());
            default ->
                throw new UnsupportedOperationException(event.getPropertyName());
        }
    }

    private void updateScaleFactor() {
        if (solarSystem != null) {
            scaleFactor = height / (VIEWING_RATIO * solarSystem.getCurrentMaxDistanceAsDouble());
        } else {
            scaleFactor = DEFAULT_SCALE_FACTOR;
        }
        runLater(() -> bodies.forEach(b -> b.updateScale(scaleFactor)));
    }

    private void createBody2D(AbstractBody body) {
        Body2D body2D = new Body2D(body);
        body2D.updateScale(scaleFactor);
        bodies.add(body2D);
        runLater(() -> systemGroup.getChildren().add(body2D.getNode()));
    }

    private void updateBodiesPosition(Map<AbstractBody, Point3D> newPositions) {
        if (updateCount == 50) {
            updateCount = 0;
            System.err.println("update");
            runLater(() -> bodies.forEach(b2D -> b2D.update(newPositions.get(b2D.getBody()))));
        } else {
            updateCount++;
        }
    }

}
