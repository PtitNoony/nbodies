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
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;

/**
 *
 * @author ahamon
 */
public class Body_BigD {

    private final List<Body_BigD> otherBodies;

    private final String name;
    private final BigDecimal mass;
    private final double radius;
    //
    private final Color color;
    //
    private Point3D currentPosition;
    private Point3D currentSpeed;
    //
    private Point3D nextPosition;
    private Point3D nextSpeed;

    public Body_BigD(String name, double mass, double radius, double sunDistance, double speed, Color color) {
        otherBodies = new LinkedList<>();
        this.name = name;
        this.mass = BigDecimal.valueOf(mass).multiply(Constants.EARTH_MASS);// * Constants.EARTH_MASS.doubleValue();
        this.radius = radius;
        this.color = color;
        currentPosition = new Point3D(sunDistance * Constants.AU_2_M.doubleValue(), 0, 0);
        currentSpeed = new Point3D(0, speed, 0);
        //
        nextPosition = new Point3D(sunDistance * Constants.AU_2_M.doubleValue(), 0, 0);
        nextSpeed = new Point3D(0, speed, 0);
    }

    public Point3D getCurrentPosition() {
        return currentPosition;
    }

    public Color getColor() {
        return color;
    }

    public double getRadius() {
        return radius;
    }

    public Point3D getCurrentSpeed() {
        return currentSpeed;
    }

    protected void calculateNextPosition(double deltaT) {
        Point3D totalForce = new Point3D(0, 0, 0);
        otherBodies.forEach(b -> totalForce.add(calculateForce(b)));
        System.err.println(" > " + name + "Force" + totalForce);
    }

    protected void moveToNextPosition() {
        currentPosition = nextPosition;
        currentSpeed = nextSpeed;
    }

    protected void addOtherBody(Body_BigD body) {
        if (body != this) {
            otherBodies.add(body);
        }
    }

    private Point3D calculateForce(Body_BigD b) {
        System.err.println("Calculating force " + b + " <-> " + name);
        // TODO with large values
        double distance = currentPosition.distance(b.getCurrentPosition());
        System.err.println(" > " + name + " dist:" + distance);
        Point3D direction = new Point3D(
                b.getCurrentPosition().getX() - currentPosition.getX(),
                b.getCurrentPosition().getY() - currentPosition.getY(),
                b.getCurrentPosition().getZ() - currentPosition.getZ());
//        Point3D nDirection = direction.normalize();
//        System.err.println(" > " + name + " dir:" + nDirection);
//        BigDecimal numerator = Constants.GRAVITY.multiply(mass).multiply(b.mass);
//        BigDecimal denominator = BigDecimal.valueOf(distance).pow(2);
//        System.err.println(" > " + name + " G       :" + Constants.GRAVITY);
//        System.err.println(" > " + name + " mass    :" + mass);
//        System.err.println(" > " + name + " b mass  :" + b.mass);
//        System.err.println(" > " + name + " numerator  :" + numerator);
//        System.err.println(" > " + name + " denominator:" + denominator);
        BigDecimal fNorm = Constants.GRAVITY.multiply(mass).multiply(b.mass).divide(BigDecimal.valueOf(distance).pow(2), RoundingMode.HALF_UP);

//        double forceNorm = Constants.GRAVITY.doubleValue() * b.mass * mass / (distance * distance);
//        System.err.println(" > " + name + " fN    :" + forceNorm);
        System.err.println(" > " + name + " fN_Big:" + fNorm);
        System.err.println(" > " + name + " fN_Big:" + fNorm.toPlainString());
        System.err.println(" > " + name + " fN_Big:" + fNorm.longValue());
//        System.err.println(" > " + name + " fN_Big:" + fNorm.toEngineeringString());
//        Point3D force = new Point3D(forceNorm * nDirection.getX(), forceNorm * nDirection.getY(), forceNorm * nDirection.getZ());
        return Point3D.ZERO;
    }

    @Override
    public String toString() {
        return name;
    }

}
