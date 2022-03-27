/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.noony.app.nbodies.fx;

import com.github.noony.app.nbodies.Body;
import com.github.noony.app.nbodies.MainApp;
import com.github.noony.app.nbodies.SolarSystem;
import java.beans.PropertyChangeEvent;
import java.util.LinkedList;
import java.util.List;
import static javafx.application.Platform.runLater;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author ahamon
 */
public class SolarSystem2D {

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
    // scale factor

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
    }

    public void setSolarSystem(SolarSystem solarSystem) {
        // TODO clear all
        solarSystem.addPropertyChangeListener(this::handleSolarSystemChange);
    }

    private void handleSolarSystemChange(PropertyChangeEvent event) {
        switch (event.getPropertyName()) {
            case SolarSystem.BODY_ADDED:
                createNewBody2D((Body) event.getNewValue());
                break;
            case SolarSystem.TIME_CHANGED:
//                System.err.println("Time changed");
                updateBodiesPosition();
                break;
            default:
                throw new UnsupportedOperationException(event.getPropertyName());
        }
    }

    private void createNewBody2D(Body body) {
        Body2D body2D = new Body2D(body);
        bodies.add(body2D);
        runLater(() -> systemGroup.getChildren().add(body2D.getNode()));
    }

    private void updateBodiesPosition() {
        runLater(() -> bodies.forEach(Body2D::update));
    }

}
