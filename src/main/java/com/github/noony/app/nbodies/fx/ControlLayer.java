/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.noony.app.nbodies.fx;

import com.github.noony.app.nbodies.MainApp;
import static javafx.application.Platform.runLater;
import javafx.scene.Group;
import javafx.scene.Node;

/**
 *
 * @author ahamon
 */
public class ControlLayer {

    private final Group mainNode;

    private double width = MainApp.DEFAULT_WIDTH;
    private double height = MainApp.DEFAULT_HEIGHT;

    public ControlLayer() {
        mainNode = new Group();
    }

    public Node getNode() {
        return mainNode;
    }

    public void addController(GraphicalController controller) {
        mainNode.getChildren().add(controller.getNode());
    }

    public void updateSize(double newWidth, double newHeight) {
        // TODO some tests
        width = newWidth;
        height = newHeight;
        runLater(this::updateDrawingsOnResize);
    }

    private void updateDrawingsOnResize() {
    }

}
