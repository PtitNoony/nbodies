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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author ahamon
 */
public class SolarSystem {

    public static final int DEFAULT_TIME_INCREMENT = 1000;

    public static final String BODY_ADDED = "bodyAdded";
    public static final String TIME_CHANGED = "timeChanged";

    private final List<Body> bodies;
    private final String name;
    private final PropertyChangeSupport propertyChangeSupport;

    private final int timeIncrement;

    public SolarSystem(String aName, int aTimeIncrement) {
        name = aName;
        bodies = new LinkedList<>();
        timeIncrement = aTimeIncrement;
        propertyChangeSupport = new PropertyChangeSupport(SolarSystem.this);
    }

    public SolarSystem(String aName) {
        this(aName, DEFAULT_TIME_INCREMENT);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public String getName() {
        return name;
    }

    public List<Body> getBodies() {
        return Collections.unmodifiableList(bodies);
    }

    public int getTimeIncrement() {
        return timeIncrement;
    }

    public void addBody(Body body) {
        bodies.forEach(b -> {
            b.addOtherBody(body);
            body.addOtherBody(b);
        });
        bodies.add(body);
        body.setDeltaT(timeIncrement);
        propertyChangeSupport.firePropertyChange(BODY_ADDED, null, body);
    }

    public void setGlobalClock(GlobalClock clock) {
        // TODO handle != deltaT
        clock.addPropertyChangeListener(event -> processNextStep());
    }

    private void processNextStep() {
        // parallel stream?
        bodies.forEach(body -> body.calculateNextPosition());// 86400
        bodies.forEach(Body::moveToNextPosition);
        propertyChangeSupport.firePropertyChange(TIME_CHANGED, null, null);
    }

}
