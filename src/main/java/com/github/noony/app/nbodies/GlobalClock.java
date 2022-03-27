/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.noony.app.nbodies;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

/**
 *
 * @author ahamon
 */
public class GlobalClock {

    public static final String CLOCK_STATE_CHANGED = "clockStateChanged";
    public static final String CLOCK_TIME_CHANGED = "clockTimeChanged";

    public enum ExecutionState {
        PAUSED, PLAYING
    }

    private final PropertyChangeSupport propertyChangeSupport;
    private final Timeline timeline;

    private int period = 20;

    private long currentTime = 0;

    private ExecutionState state = ExecutionState.PAUSED;

    public GlobalClock() {
        propertyChangeSupport = new PropertyChangeSupport(GlobalClock.this);
        timeline = new Timeline(new KeyFrame(
                Duration.millis(period),
                this::incrementTime));
        timeline.setCycleCount(Animation.INDEFINITE);
        state = ExecutionState.PAUSED;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void play() {
        switch (state) {
            case PAUSED:
                state = ExecutionState.PLAYING;
                timeline.play();
                propertyChangeSupport.firePropertyChange(CLOCK_STATE_CHANGED, ExecutionState.PAUSED, state);
                break;
            case PLAYING:
                // nothing to do
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public void pause() {
        switch (state) {
            case PAUSED:
                // nothing to do
                break;
            case PLAYING:
                state = ExecutionState.PAUSED;
                timeline.pause();
                propertyChangeSupport.firePropertyChange(CLOCK_STATE_CHANGED, ExecutionState.PLAYING, state);
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public ExecutionState getState() {
        return state;
    }

    private void incrementTime(ActionEvent event) {
        //TODO: log
        currentTime++;
        propertyChangeSupport.firePropertyChange(CLOCK_TIME_CHANGED, null, currentTime);
//        System.err.println("Time=" + currentTime);
    }

}
