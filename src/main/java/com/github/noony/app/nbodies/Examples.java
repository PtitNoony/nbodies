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

import java.math.BigDecimal;
import javafx.scene.paint.Color;

/**
 *
 * @author hamon
 */
public class Examples {

    public static SolarSystem createOurSolarSystem() {
        var solarSystem = new SolarSystem("My Testing Solar System");
        //
        Body sun = new Body("Sun",
                BigDecimal.valueOf(333000).multiply(Constants.EARTH_MASS),
                BigDecimal.valueOf(2),
                new BigPoint2D(Constants.AU_2_M.multiply(BigDecimal.valueOf(0)), BigDecimal.ZERO),
                new BigPoint2D(BigDecimal.ZERO, BigDecimal.valueOf(0)),
                Color.GOLD);
        //
        Body mercure = new Body("Mercure",
                BigDecimal.valueOf(0.055).multiply(Constants.EARTH_MASS),
                BigDecimal.valueOf(0.38295),
                new BigPoint2D(Constants.AU_2_M.multiply(BigDecimal.valueOf(0.4)), BigDecimal.ZERO),
                new BigPoint2D(BigDecimal.ZERO, BigDecimal.valueOf(47362)),
                Color.GOLDENROD);
        //
        Body venus = new Body("Venus",
                BigDecimal.valueOf(0.815).multiply(Constants.EARTH_MASS),
                BigDecimal.valueOf(0.9499),
                new BigPoint2D(Constants.AU_2_M.multiply(BigDecimal.valueOf(0.7)), BigDecimal.ZERO),
                new BigPoint2D(BigDecimal.ZERO, BigDecimal.valueOf(35020)),
                Color.ANTIQUEWHITE);
        //
        Body earth = new Body("Earth",
                BigDecimal.valueOf(1).multiply(Constants.EARTH_MASS),
                BigDecimal.valueOf(1),
                new BigPoint2D(Constants.AU_2_M.multiply(BigDecimal.valueOf(1.1)), BigDecimal.ZERO),
                new BigPoint2D(BigDecimal.ZERO, BigDecimal.valueOf(29782)),
                Color.DEEPSKYBLUE);
        //
        Body mars = new Body("Mars",
                BigDecimal.valueOf(0.107).multiply(Constants.EARTH_MASS),
                BigDecimal.valueOf(0.533),
                new BigPoint2D(Constants.AU_2_M.multiply(BigDecimal.valueOf(1.5)), BigDecimal.ZERO),
                new BigPoint2D(BigDecimal.ZERO, BigDecimal.valueOf(24077)),
                Color.ORANGERED);
        //
        solarSystem.addBody(sun);
        solarSystem.addBody(mercure);
        solarSystem.addBody(venus);
        solarSystem.addBody(earth);
        solarSystem.addBody(mars);
        return solarSystem;
    }

}
