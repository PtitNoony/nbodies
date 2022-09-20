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
package com.github.noony.app.nbodies.precise;

import com.github.noony.app.nbodies.AbstractBody;
import com.github.noony.app.nbodies.BigPoint2D;
import com.github.noony.app.nbodies.Constants;
import com.github.noony.app.nbodies.SolarSystem;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;

/**
 *
 * @author ahamon
 */
public class PreciseBody extends AbstractBody {

    public static final double DEFAULT_TIME_STEP = SolarSystem.DEFAULT_TIME_INCREMENT;

    private static final int SCALE = 25;

    private final List<PreciseBody> otherBodies;

    private final BigDecimal mass;
    private final BigDecimal radius;
    //
    //
    private BigPoint2D currentPosition;
    private BigPoint2D currentSpeed;
    //
    private BigPoint2D nextPosition;
    private BigPoint2D nextSpeed;
    //
    private BigDecimal deltaT;
    private BigDecimal deltaTSquare;
    //
    private BigDecimal gCste;

    public PreciseBody(String aName, BigDecimal aMass, BigDecimal aRadius, BigPoint2D initialPosition, BigPoint2D initialSpeed, Color aColor) {
        super(aName, aColor);
        deltaT = BigDecimal.valueOf(DEFAULT_TIME_STEP);
        deltaTSquare = deltaT.pow(2);
        otherBodies = new LinkedList<>();
        mass = aMass;
        radius = aRadius;
        currentPosition = initialPosition;
        currentSpeed = initialSpeed;
        //
        nextPosition = currentPosition;
        nextSpeed = currentSpeed;
        gCste = Constants.GRAVITY;
    }

    @Override
    protected void setGConstant(BigDecimal aGcst) {
        gCste = aGcst;
    }

    public BigPoint2D getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public Point3D getDisplayablePosition() {
        var x = currentPosition.getX().doubleValue();
        var y = currentPosition.getY().doubleValue();
        return new Point3D(x, y, 0);
    }

    @Override
    public double getRadiusAsDouble() {
        return radius.doubleValue();
    }

    public BigPoint2D getCurrentSpeed() {
        return currentSpeed;
    }


    @Override
    protected void calculateNextPosition() {
        BigPoint2D totalForce = new BigPoint2D();
        otherBodies.stream().map(b -> calculateForce(b)).forEachOrdered(f -> {
            totalForce.add(f);
        });
        //
        BigDecimal aX = totalForce.getX().divide(mass, SCALE, RoundingMode.HALF_UP);
        BigDecimal aY = totalForce.getY().divide(mass, SCALE, RoundingMode.HALF_UP);
        //
        BigDecimal vX = (aX.multiply(deltaT)).add(currentSpeed.getX());
        BigDecimal vY = (aY.multiply(deltaT)).add(currentSpeed.getY());
        //
        nextSpeed = new BigPoint2D(vX, vY);
        //
        BigDecimal x = ((aX.multiply(deltaTSquare).multiply(BigDecimal.valueOf(0.5))).add(currentSpeed.getX().multiply(deltaT))).add(currentPosition.getX());
        BigDecimal y = ((aY.multiply(deltaTSquare).multiply(BigDecimal.valueOf(0.5))).add(currentSpeed.getY().multiply(deltaT))).add(currentPosition.getY());
        //
        nextPosition = new BigPoint2D(x, y);
    }

    @Override
    protected void setDeltaT(double aDeltaT) {
        deltaT = BigDecimal.valueOf(aDeltaT);
        deltaTSquare = deltaT.pow(2);
    }

    @Override
    protected void moveToNextPosition() {
        currentPosition = nextPosition;
        currentSpeed = nextSpeed;
    }

    @Override
    protected void linkToOtherBody(AbstractBody anotherBody) {
        if (anotherBody != this && anotherBody != null) {
            if (anotherBody instanceof PreciseBody body) {
                otherBodies.add(body);
            } else {
                throw new UnsupportedOperationException("Cannot add " + anotherBody + " in " + this);
            }
        }
    }

    private BigPoint2D calculateForce(PreciseBody b) {
        BigDecimal distance = currentPosition.distance(b.getCurrentPosition());
        BigPoint2D nDirection = new BigPoint2D(
                b.currentPosition.getX().subtract(currentPosition.getX()),
                b.currentPosition.getY().subtract(currentPosition.getY()))
                .normalize();
        BigDecimal fNorm = gCste.multiply(mass).multiply(b.mass).divide(distance.pow(2), SCALE, RoundingMode.HALF_UP);
        BigPoint2D force = new BigPoint2D(fNorm.multiply(nDirection.getX()), fNorm.multiply(nDirection.getY()));
        return force;
    }


}
