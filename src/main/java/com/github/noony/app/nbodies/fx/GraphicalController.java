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

import javafx.scene.Group;
import javafx.scene.Node;

/**
 *
 * @author ahamon
 */
public class GraphicalController {

    private static final double DEFAULT_POS_X = 25;
    private static final double DEFAULT_POS_Y = 25;

    private final Group controlContainer;
    private final Group handleContainer;
    private final Group contentContainer;

    private double posX = DEFAULT_POS_X;
    private double posY = DEFAULT_POS_Y;

    public GraphicalController(double x, double y) {
        controlContainer = new Group();
        handleContainer = new Group();
        contentContainer = new Group();
        controlContainer.getChildren().addAll(contentContainer, handleContainer);
        controlContainer.setTranslateX(x);
        controlContainer.setTranslateY(y);
    }

    public GraphicalController() {
        this(DEFAULT_POS_X, DEFAULT_POS_Y);
    }

    public Node getNode() {
        return controlContainer;
    }

    protected final void setHandle(Node node) {
        handleContainer.getChildren().setAll(node);
    }

    protected final void setContent(Node node) {
        contentContainer.getChildren().setAll(node);
    }

//    public static
}
