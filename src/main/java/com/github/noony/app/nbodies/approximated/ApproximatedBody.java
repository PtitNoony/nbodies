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
package com.github.noony.app.nbodies.approximated;

import com.github.noony.app.nbodies.AbstractBody;
import com.github.noony.app.nbodies.Constants;
import com.github.noony.app.nbodies.SolarSystem;
import java.util.LinkedList;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;

/**
 *
 * @author ahamon
 */
public class ApproximatedBody extends AbstractBody {


    public static final double DEFAULT_TIME_STEP = SolarSystem.DEFAULT_TIME_INCREMENT;

    private final List<ApproximatedBody> otherBodies;

    private final double mass;
    private final double radius;
    //
    //
    private Point2D currentPosition;
    private Point2D currentSpeed;
    //
    private Point2D nextPosition;
    private Point2D nextSpeed;
    //
    private double deltaT;
    private double deltaTSquare;

    public ApproximatedBody(String aName, double aMass, double aRadius, Point2D initialPosition, Point2D initialSpeed, Color aColor) {
        super(aName, aColor);
        deltaT = DEFAULT_TIME_STEP;
        deltaTSquare = deltaT * deltaT;
        otherBodies = new LinkedList<>();
        mass = aMass;
        radius = aRadius;
        currentPosition = initialPosition;
        currentSpeed = initialSpeed;
        //
        nextPosition = currentPosition;
        nextSpeed = currentSpeed;
    }

    public Point2D getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public Point3D getDisplayablePosition() {
        var x = currentPosition.getX();
        var y = currentPosition.getY();
        return new Point3D(x, y, 0);
    }

    @Override
    public double getRadiusAsDouble() {
        return radius;
    }

    public Point2D getCurrentSpeed() {
        return currentSpeed;
    }


    @Override
    protected void calculateNextPosition() {
        Point2D totalForce = new Point2D(0.0, 0.0);
        otherBodies.stream().map(b -> calculateForce(b)).forEachOrdered(f -> {
            totalForce.add(f);
        });
        //
        double aX = totalForce.getX() / mass;
        double aY = totalForce.getY() / mass;
        //
        double vX = (aX * deltaT) + (currentSpeed.getX());
        double vY = (aY * deltaT) + (currentSpeed.getY());
        //
        nextSpeed = new Point2D(vX, vY);
        //
        double x = (aX * (deltaTSquare) / 2.0) + (currentSpeed.getX() * (deltaT)) + currentPosition.getX();
        double y = (aY * (deltaTSquare) / 2.0) + (currentSpeed.getY() * (deltaT)) + currentPosition.getY();
        //
        nextPosition = new Point2D(x, y);
    }

    @Override
    protected void setDeltaT(double aDeltaT) {
        deltaT = aDeltaT;
        deltaTSquare = deltaT * deltaT;
    }

    @Override
    protected void moveToNextPosition() {
        currentPosition = nextPosition;
        currentSpeed = nextSpeed;
    }

    @Override
    protected void linkToOtherBody(AbstractBody anotherBody) {
        if (anotherBody != this && anotherBody != null) {
            if (anotherBody instanceof ApproximatedBody body) {
                otherBodies.add(body);
            } else {
                throw new UnsupportedOperationException("Cannot add " + anotherBody + " in " + this);
            }
        }
    }

    private Point2D calculateForce(ApproximatedBody b) {
        double distance = currentPosition.distance(b.getCurrentPosition());
        Point2D nDirection = new Point2D(
                b.currentPosition.getX() - currentPosition.getX(),
                b.currentPosition.getY() - currentPosition.getY())
                .normalize();
        double fNorm = Constants.GRAVITY_DOUBLE_VALUE * mass * b.mass / (distance * distance);
        Point2D force = new Point2D(fNorm * nDirection.getX(), fNorm * nDirection.getY());
        return force;
    }


}
