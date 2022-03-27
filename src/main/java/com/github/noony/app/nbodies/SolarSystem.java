/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.noony.app.nbodies;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author ahamon
 */
public class SolarSystem {

    public static final String BODY_ADDED = "bodyAdded";
    public static final String TIME_CHANGED = "timeChanged";

    private final List<Body> bodies;
    private final String name;
    private final PropertyChangeSupport propertyChangeSupport;

    public SolarSystem(String name) {
        this.name = name;
        bodies = new LinkedList<>();
        propertyChangeSupport = new PropertyChangeSupport(SolarSystem.this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public String getName() {
        return name;
    }

    public void addBody(Body body) {
        bodies.forEach(b -> {
            b.addOtherBody(body);
            body.addOtherBody(b);
        });

        bodies.add(body);
        propertyChangeSupport.firePropertyChange(BODY_ADDED, null, body);
    }

    public void setGlobalClock(GlobalClock clock) {
        // TODO handle != deltaT
        clock.addPropertyChangeListener(event -> processNextStep());
    }

    private void processNextStep() {
        // parallel stream?
        bodies.forEach(body -> body.calculateNextPosition(1000));// 86400
        bodies.forEach(Body::moveToNextPosition);
        propertyChangeSupport.firePropertyChange(TIME_CHANGED, null, null);
    }

}
