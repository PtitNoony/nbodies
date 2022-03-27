/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.noony.app.nbodies.fx;

import com.github.noony.app.nbodies.Body;
import com.github.noony.app.nbodies.Constants;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 *
 * @author ahamon
 */
public class Body2D {

    private static final int NB_PAST_POSITIONS = 100000;

    private final Body body;

    private final Group mainNode;
    private final Circle c;
    private final Line speedV;
    private final Circle speedTip;
    private final Circle[] pastPositions;
    private int indexPastP = 0;

    // to move out
    public Body2D(Body body) {
        this.body = body;
        mainNode = new Group();
        pastPositions = new Circle[NB_PAST_POSITIONS];
        for (int i = 0; i < NB_PAST_POSITIONS; i++) {
//            final pastP = new Circle
            pastPositions[i] = new Circle(1, this.body.getColor());
        }
        c = new Circle(this.body.getRadius() * 10, this.body.getColor());
//        c.setCenterX(this.body.getCurrentPosition().getX());
//        c.setCenterY(this.body.getCurrentPosition().getY());
        //
        speedV = new Line();
        speedV.setStroke(Color.LIGHTGRAY);
        speedTip = new Circle(2, Color.LIGHTGRAY);
        //
        mainNode.getChildren().addAll(pastPositions);
        mainNode.getChildren().addAll(c, speedV, speedTip);
        //
        updatePosition();
    }

    public Node getNode() {
        return mainNode;
    }

    public void update() {
        updatePosition();
    }

    private void updatePosition() {
//        if("Mars".equals(body.getName())){
//            BigDecimal x = body.getCurrentPosition().getX().multiply(BigDecimal.valueOf(200)).divide(Constants.AU_2_KM,RoundingMode.HALF_UP);
//            System.err.println("Mars should be :: "+(1.5* 1.496e8));
//            System.err.println("Mars double :: "+body.getCurrentPosition().getX().doubleValue()* 200.0 / Constants.AU_2_KM.doubleValue());
//            System.err.println(" MARS x="+ body.getCurrentPosition().getX());
//            System.err.println(" MARS "+ x);
//            System.err.println(" MARS CEILING : "+ body.getCurrentPosition().getX().multiply(BigDecimal.valueOf(200)).divide(Constants.AU_2_KM, RoundingMode.CEILING).toPlainString());
//            System.err.println(" MARS DOWN    : "+ body.getCurrentPosition().getX().multiply(BigDecimal.valueOf(200)).divide(Constants.AU_2_KM, RoundingMode.DOWN));
//            System.err.println(" MARS "+ body.getCurrentPosition().getX().multiply(BigDecimal.valueOf(200)).divide(Constants.AU_2_KM, RoundingMode.FLOOR));
//            System.err.println(" MARS "+ body.getCurrentPosition().getX().multiply(BigDecimal.valueOf(200)).divide(Constants.AU_2_KM, RoundingMode.HALF_UP));
//            System.err.println(" MARS "+ body.getCurrentPosition().getX().multiply(BigDecimal.valueOf(200)).divide(Constants.AU_2_KM, RoundingMode.HALF_DOWN));
//        }
//        System.err.println("Update Body2D "+body+ " "+body.getCurrentPosition().getX()+" || "+body.getCurrentPosition().getY() * 200);

//        double x = body.getCurrentPosition().getX().doubleValue() * 200.0 / Constants.AU_2_KM.doubleValue();
//        double y = body.getCurrentPosition().getY().doubleValue() * 200.0 / Constants.AU_2_KM.doubleValue();
//        mainNode.setTranslateX(x);
//        mainNode.setTranslateY(y);
        double x = body.getCurrentPosition().getX().multiply(BigDecimal.valueOf(200)).divide(Constants.AU_2_M, 150, RoundingMode.HALF_UP).doubleValue();
        double y = body.getCurrentPosition().getY().multiply(BigDecimal.valueOf(200)).divide(Constants.AU_2_M, 150, RoundingMode.HALF_UP).doubleValue();
        c.setCenterX(x);// / Constants.AU_2_KM.doubleValue()
        c.setCenterY(y);// / Constants.AU_2_KM.doubleValue()
//        speedV.setEndX(body.getCurrentSpeed().getX() / 1000);
//        speedV.setEndY(body.getCurrentSpeed().getY() / 1000);
//        speedTip.setCenterX(body.getCurrentSpeed().getX() / 1000);
//        speedTip.setCenterY(body.getCurrentSpeed().getY() / 1000);

        if (indexPastP == NB_PAST_POSITIONS - 2) {
            indexPastP = 0;
        } else {
            indexPastP++;
        }

        Circle pastP = pastPositions[indexPastP];
        pastP.setCenterX(x);
        pastP.setCenterY(y);
    }

}
