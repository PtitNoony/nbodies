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
