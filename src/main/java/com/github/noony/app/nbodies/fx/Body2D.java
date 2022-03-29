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

import com.github.noony.app.nbodies.AbstractBody;
import javafx.geometry.Point3D;
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

    private final AbstractBody body;

    private final Group mainNode;
    private final Circle c;
    private final Line speedV;
    private final Circle speedTip;
    private final Circle[] pastPositions;
    private int indexPastP = 0;

    private double scale;
    private Point3D position;

    // to move out
    public Body2D(AbstractBody aBody) {
        scale = 1.0;
        body = aBody;
        position = aBody.getDisplayablePosition();
        mainNode = new Group();
        pastPositions = new Circle[NB_PAST_POSITIONS];
        for (int i = 0; i < NB_PAST_POSITIONS; i++) {
            pastPositions[i] = new Circle(1, body.getColor());
        }
        c = new Circle(body.getRadiusAsDouble() * 10, body.getColor());
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

    public void update(Point3D newPosition) {
        position = newPosition;
        updatePosition();
    }

    protected Node getNode() {
        return mainNode;
    }

    public AbstractBody getBody() {
        return body;
    }


    protected void updateScale(double newScale) {
        scale = newScale;
        updatePosition();
    }

    private void updatePosition() {
        // TODO to sort out because of threading
//        var position = body.getDisplayablePosition();
        var x = position.getX() * scale;
        var y = position.getY() * scale;
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
