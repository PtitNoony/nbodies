/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.noony.app.nbodies.fx;

import com.github.noony.app.nbodies.GlobalClock;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author ahamon
 */
public class ExecutionController extends GraphicalController {

    private final GlobalClock globalClock;

    private PlayerController controller;

    public ExecutionController(GlobalClock clock) {
        super();
        globalClock = clock;
        createHandle();
        createContent();
    }

    private void createHandle() {
        Circle c = new Circle(8, Color.CHARTREUSE);
        c.setStroke(Color.LIGHTGRAY);
        setHandle(c);
    }

    private void createContent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Player.fxml"));
            setContent(loader.load());
            controller = loader.getController();
            controller.setClock(globalClock);
        } catch (IOException ex) {
            Logger.getLogger(ExecutionController.class.getName()).log(Level.SEVERE, "Error while loading Player.fxml:: {0}", ex);
        }
    }
}
