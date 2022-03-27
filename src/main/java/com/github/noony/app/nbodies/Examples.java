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

import javafx.scene.paint.Color;

/**
 *
 * @author hamon
 */
public class Examples {

    public static SolarSystem createOurSolarSystem() {
        var solarSystem = new SolarSystem("My Testing Solar System");
        //
        Body sun = new Body("Sun", 333000, 2, 0, 0, Color.GOLD);
        Body mercure = new Body("Mercure", 0.055, 0.38295, 0.4, 47362, Color.GOLDENROD);
        Body venus = new Body("Venus", 0.815, 0.9499, 0.7, 35020, Color.ANTIQUEWHITE);
        Body earth = new Body("Earth", 1, 1, 1.1, 29782, Color.DEEPSKYBLUE);
        Body mars = new Body("Mars", 0.107, 0.533, 1.5, 24077, Color.ORANGERED);
        solarSystem.addBody(sun);
        solarSystem.addBody(mercure);
        solarSystem.addBody(venus);
        solarSystem.addBody(earth);
        solarSystem.addBody(mars);
        return solarSystem;
    }

}
