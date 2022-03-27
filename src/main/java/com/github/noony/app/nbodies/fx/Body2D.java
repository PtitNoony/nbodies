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
            pastPositions[i] = new Circle(1, this.body.getColor());
        }
        c = new Circle(this.body.getRadius() * 10, this.body.getColor());
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
        var x = body.getCurrentPosition().getX().multiply(BigDecimal.valueOf(200)).divide(Constants.AU_2_M, 150, RoundingMode.HALF_UP).doubleValue();
        var y = body.getCurrentPosition().getY().multiply(BigDecimal.valueOf(200)).divide(Constants.AU_2_M, 150, RoundingMode.HALF_UP).doubleValue();
        c.setCenterX(x);
        c.setCenterY(y);
        if (indexPastP == NB_PAST_POSITIONS - 2) {
            indexPastP = 0;
        } else {
            indexPastP++;
        }
        var pastP = pastPositions[indexPastP];
        pastP.setCenterX(x);
        pastP.setCenterY(y);
    }

}
