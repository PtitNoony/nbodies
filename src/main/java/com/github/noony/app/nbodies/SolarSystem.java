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
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javafx.geometry.Point3D;

/**
 *
 * @author ahamon
 */
public class SolarSystem {

    public static final int DEFAULT_TIME_INCREMENT = 1000;

    public static final String BODY_ADDED = "bodyAdded";
    public static final String TIME_CHANGED = "timeChanged";

    private final List<AbstractBody> bodies;
    private final String name;
    private final BigDecimal gravitationalConstant;
    private final PropertyChangeSupport propertyChangeSupport;

    private final int timeIncrement;

    public SolarSystem(String aName, BigDecimal aGravitationalConstant, int aTimeIncrement) {
        name = aName;
        gravitationalConstant = aGravitationalConstant;
        bodies = new LinkedList<>();
        timeIncrement = aTimeIncrement;
        propertyChangeSupport = new PropertyChangeSupport(SolarSystem.this);
    }

    public SolarSystem(String aName) {
        this(aName, Constants.GRAVITY, DEFAULT_TIME_INCREMENT);
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

    public List<AbstractBody> getBodies() {
        return Collections.unmodifiableList(bodies);
    }

    public int getTimeIncrement() {
        return timeIncrement;
    }

    public void addBody(AbstractBody body) {
        bodies.forEach(b -> {
            b.linkToOtherBody(body);
            b.setGConstant(gravitationalConstant);
            body.linkToOtherBody(b);
        });
        bodies.add(body);
        body.setDeltaT(timeIncrement);
        propertyChangeSupport.firePropertyChange(BODY_ADDED, null, body);
    }

    public double getCurrentMaxDistanceAsDouble() {
        double maxDistance = 0.0;
        for (AbstractBody body : bodies) {
            // doing the sum on purpose to artificially create margins (experimental)
            var p = body.getDisplayablePosition();
            var d = p.getX() + p.getY() + p.getZ();
            maxDistance = Math.max(maxDistance, d);
        }
        return maxDistance;
    }

    public void attachCalculationRunner(CalculationRunner calculationRunner) {
        // TODO handle != deltaT
        calculationRunner.addPropertyChangeListener(event -> {
            if (event.getPropertyName().equals(CalculationRunner.CLOCK_TIME_CHANGED)) {
                processNextStep();
            }
        });
    }

    private void processNextStep() {
        // parallel stream?
        bodies.forEach(AbstractBody::calculateNextPosition);// 86400
        bodies.forEach(AbstractBody::moveToNextPosition);
        Map<AbstractBody, Point3D> positions = new HashMap<>();
        bodies.forEach(body -> positions.put(body, body.getDisplayablePosition()));
        propertyChangeSupport.firePropertyChange(TIME_CHANGED, null, positions);
    }

}
