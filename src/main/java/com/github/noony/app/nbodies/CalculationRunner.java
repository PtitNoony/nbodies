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

import static com.github.noony.app.nbodies.GlobalClock.CLOCK_STATE_CHANGED;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Synchronized;

/**
 *
 * @author hamon
 */
public class CalculationRunner extends Thread {

    public static final String CLOCK_TIME_CHANGED = "clockTimeChanged";

    private final PropertyChangeSupport propertyChangeSupport;
    private final SolarSystem solarSystem;
    private final int asleepTime;

    private boolean running = false;

    private ExecutionState state = ExecutionState.PAUSED;

    public CalculationRunner(SolarSystem aSolarSystem, int anAsleepTime) {
        super(aSolarSystem.getName() + "__Runner");
        propertyChangeSupport = new PropertyChangeSupport(CalculationRunner.this);
        solarSystem = aSolarSystem;
        solarSystem.attachCalculationRunner(CalculationRunner.this);
        asleepTime = anAsleepTime;
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            switch (state) {
                case DONE ->
                    running = false;
                case PAUSED -> {
                    performInPause();
                }
                case PLAYING -> {
                    performPlaying();
                }
            }

        }
    }

    @Synchronized
    public void play() {
        switch (state) {
            case PAUSED -> {
                state = ExecutionState.PLAYING;
//                timeline.play();
                propertyChangeSupport.firePropertyChange(CLOCK_STATE_CHANGED, ExecutionState.PAUSED, state);
            }
            case PLAYING -> {
                // nothing to do
            }
            default ->
                throw new UnsupportedOperationException();
        }
    }

    @Synchronized
    public void pause() {
        switch (state) {
            case PAUSED -> {
                // nothing to do
            }
            case PLAYING -> {
                state = ExecutionState.PAUSED;
                propertyChangeSupport.firePropertyChange(CLOCK_STATE_CHANGED, ExecutionState.PLAYING, state);
            }
            default ->
                throw new UnsupportedOperationException();
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public ExecutionState getExecutionState() {
        return state;
    }

    private void performInPause() {
        try {
            Thread.sleep(asleepTime * 10);
        } catch (InterruptedException ex) {
            Logger.getLogger(CalculationRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void performPlaying() {
//        System.err.println("Playing");
        propertyChangeSupport.firePropertyChange(CLOCK_TIME_CHANGED, this, null); // TODO
        try {
            Thread.sleep(asleepTime);
        } catch (InterruptedException ex) {
            Logger.getLogger(CalculationRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void incrementSystemTime() {
        System.err.println(" incrementSystemTime !!");
    }
}
