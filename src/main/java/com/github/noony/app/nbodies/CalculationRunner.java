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

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hamon
 */
public class CalculationRunner extends Thread {

    private final SolarSystem solarSystem;
    private final int asleepTime;

    private ExecutionState state = ExecutionState.PAUSED;

    public CalculationRunner(SolarSystem aSolarSystem, int anAsleepTime) {
        super(aSolarSystem.getName() + "__Runner");
        solarSystem = aSolarSystem;
        asleepTime = anAsleepTime;
    }

    @Override
    public void run() {
        while (true) {
            incrementSystemTime();
            try {
                Thread.sleep(asleepTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(CalculationRunner.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void incrementSystemTime() {
        System.err.println(" incrementSystemTime !!");
    }
}
