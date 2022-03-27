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
