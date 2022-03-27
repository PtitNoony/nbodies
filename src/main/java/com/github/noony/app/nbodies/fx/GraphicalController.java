/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
