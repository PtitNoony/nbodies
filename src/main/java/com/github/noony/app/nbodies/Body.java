/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.noony.app.nbodies;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.paint.Color;

/**
 *
 * @author ahamon
 */
public class Body {

    private static final int SCALE = 150;

    private static final double GRAVITY = 1.0 / 5000;

    private final List<Body> otherBodies;

    private final String name;
    private final BigDecimal mass;
    private final double radius;
    //
    private final Color color;
    //
    private BigPoint2D currentPosition;
    private BigPoint2D currentSpeed;
    //
    private BigPoint2D nextPosition;
    private BigPoint2D nextSpeed;

    public Body(String name, double mass, double radius, double sunDistance, double speed, Color color) {
        otherBodies = new LinkedList<>();
        this.name = name;
        this.mass = BigDecimal.valueOf(mass).multiply(Constants.EARTH_MASS);// * Constants.EARTH_MASS.doubleValue();
        this.radius = radius;
        this.color = color;
        currentPosition = new BigPoint2D(Constants.AU_2_M.multiply(BigDecimal.valueOf(sunDistance)), BigDecimal.ZERO);
        currentSpeed = new BigPoint2D(BigDecimal.ZERO, BigDecimal.valueOf(speed));
        //
        nextPosition = currentPosition;
        nextSpeed = currentSpeed;
    }

    public BigPoint2D getCurrentPosition() {
        return currentPosition;
    }

    public Color getColor() {
        return color;
    }

    public double getRadius() {
        return radius;
    }

    public BigPoint2D getCurrentSpeed() {
        return currentSpeed;
    }

    public String getName() {
        return name;
    }

    protected void calculateNextPosition(double deltaT) {
        BigPoint2D totalForce = new BigPoint2D();
//        otherBodies.forEach(b -> totalForce.add(calculateForce(b)));
        double fx = 0.0;
        double fy = 0.0;
        for (Body b : otherBodies) {
            BigPoint2D f = calculateForce(b);
            totalForce.add(f);
        }
//        System.err.println(" > " + name + "Force" + fx + "," + fy + "," + fz);
//        double ax = fx / mass;
//        double ay = fy / mass;
//        //
//        double nextVx = ax * deltaT + currentSpeed.getX();
//        double nextVy = ay * deltaT + currentSpeed.getY();
//        double nextVz = az * deltaT + currentSpeed.getZ();
//        //
//        nextSpeed = new Point3D(nextVx, nextVy, nextVz);
//        //
//        double nextX = 0.5 * ax * deltaT * deltaT + currentSpeed.getX() * deltaT + currentPosition.getX();
//        double nextY = 0.5 * ay * deltaT * deltaT + currentSpeed.getY() * deltaT + currentPosition.getY();
//        double nextZ = 0.5 * az * deltaT * deltaT + currentSpeed.getZ() * deltaT + currentPosition.getZ();
        //
        BigDecimal aX = totalForce.getX().divide(mass, SCALE, RoundingMode.HALF_UP);
        BigDecimal aY = totalForce.getY().divide(mass, SCALE, RoundingMode.HALF_UP);
        //
        BigDecimal vX = (aX.multiply(BigDecimal.valueOf(deltaT))).add(currentSpeed.getX());
        BigDecimal vY = (aY.multiply(BigDecimal.valueOf(deltaT))).add(currentSpeed.getY());
        //
        nextSpeed = new BigPoint2D(vX, vY);
        //
        BigDecimal x = ((aX.multiply(BigDecimal.valueOf(deltaT).multiply(BigDecimal.valueOf(deltaT))).multiply(BigDecimal.valueOf(0.5))).add(currentSpeed.getX().multiply(BigDecimal.valueOf(deltaT)))).add(currentPosition.getX());
        BigDecimal y = ((aY.multiply(BigDecimal.valueOf(deltaT).multiply(BigDecimal.valueOf(deltaT))).multiply(BigDecimal.valueOf(0.5))).add(currentSpeed.getY().multiply(BigDecimal.valueOf(deltaT)))).add(currentPosition.getY());
        //
        nextPosition = new BigPoint2D(x, y);
    }

    protected void moveToNextPosition() {
        currentPosition = nextPosition;
        currentSpeed = nextSpeed;
    }

    protected void addOtherBody(Body body) {
        if (body != this) {
            otherBodies.add(body);
        }
    }

    private BigPoint2D calculateForce(Body b) {
//        System.err.println("Calculating force " + b + " <-> " + name);
        // TODO with large values
        BigDecimal distance = currentPosition.distance(b.getCurrentPosition());
//        System.err.println(" > " + name + " dist:" + distance);
//        Point3D direction = new Point3D(
//                b.getCurrentPosition().getX() - currentPosition.getX(),
//                b.getCurrentPosition().getY() - currentPosition.getY(),
//                b.getCurrentPosition().getZ() - currentPosition.getZ());
        BigPoint2D direction = new BigPoint2D(
                b.currentPosition.getX().subtract(currentPosition.getX()),
                b.currentPosition.getY().subtract(currentPosition.getY()));
        BigPoint2D nDirection = direction.normalize();
//        System.err.println(" > " + name + " dir:" + nDirection);
//        BigDecimal numerator = Constants.GRAVITY.multiply(mass).multiply(b.mass);
//        BigDecimal denominator = BigDecimal.valueOf(distance).pow(2);
//        System.err.println(" > " + name + " G       :" + Constants.GRAVITY);
//        System.err.println(" > " + name + " mass    :" + mass);
//        System.err.println(" > " + name + " b mass  :" + b.mass);
//        System.err.println(" > " + name + " numerator  :" + numerator);
//        System.err.println(" > " + name + " denominator:" + denominator);
        BigDecimal fNorm = Constants.GRAVITY.multiply(mass).multiply(b.mass).divide(distance.pow(2), SCALE, RoundingMode.HALF_UP);

//BigDecimal fN
//        double forceNorm = GRAVITY * b.mass * mass / (distance * distance);
//        Point3D force = new Point3D(forceNorm * nDirection.getX(), forceNorm * nDirection.getY(), forceNorm * nDirection.getZ());
//        System.err.println(" > " + name + " from " + b.name + ":" + force);
        BigPoint2D force = new BigPoint2D(fNorm.multiply(nDirection.getX()), fNorm.multiply(nDirection.getY()));
        return force;
    }

    @Override
    public String toString() {
        return name;
    }

}
