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

import com.github.noony.app.nbodies.approximated.ApproximatedBody;
import com.github.noony.app.nbodies.precise.PreciseBody;
import java.math.BigDecimal;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

/**
 *
 * @author hamon
 */
public class Examples {

    public static SolarSystem createOurSolarSystemAsPrecise() {
        var solarSystem = new SolarSystem("My Testing Solar System");
        //
        PreciseBody sun = new PreciseBody("Sun",
                BigDecimal.valueOf(333000).multiply(Constants.EARTH_MASS),
                BigDecimal.valueOf(2),
                new BigPoint2D(Constants.AU_2_M.multiply(BigDecimal.valueOf(0)), BigDecimal.ZERO),
                new BigPoint2D(BigDecimal.ZERO, BigDecimal.valueOf(0)),
                Color.GOLD);
        //
        PreciseBody sun2 = new PreciseBody("Sun",
                BigDecimal.valueOf(333000).multiply(Constants.EARTH_MASS),
                BigDecimal.valueOf(2),
                new BigPoint2D(Constants.AU_2_M.multiply(BigDecimal.valueOf(0.8)), Constants.AU_2_M.multiply(BigDecimal.valueOf(0.8))),
                new BigPoint2D(BigDecimal.ZERO, BigDecimal.valueOf(0)),
                Color.GOLD);
        //
        PreciseBody mercure = new PreciseBody("Mercure",
                BigDecimal.valueOf(0.055).multiply(Constants.EARTH_MASS),
                BigDecimal.valueOf(0.38295),
                new BigPoint2D(Constants.AU_2_M.multiply(BigDecimal.valueOf(0.4)), BigDecimal.ZERO),
                new BigPoint2D(BigDecimal.ZERO, BigDecimal.valueOf(47362)),
                Color.GOLDENROD);
        //
        PreciseBody venus = new PreciseBody("Venus",
                BigDecimal.valueOf(0.815).multiply(Constants.EARTH_MASS),
                BigDecimal.valueOf(0.9499),
                new BigPoint2D(Constants.AU_2_M.multiply(BigDecimal.valueOf(0.7)), BigDecimal.ZERO),
                new BigPoint2D(BigDecimal.ZERO, BigDecimal.valueOf(35020)),
                Color.ANTIQUEWHITE);
        //
        PreciseBody earth = new PreciseBody("Earth",
                BigDecimal.valueOf(1).multiply(Constants.EARTH_MASS),
                BigDecimal.valueOf(1),
                new BigPoint2D(Constants.AU_2_M.multiply(BigDecimal.valueOf(1.1)), BigDecimal.ZERO),
                new BigPoint2D(BigDecimal.ZERO, BigDecimal.valueOf(29782)),
                Color.DEEPSKYBLUE);
        //
        PreciseBody mars = new PreciseBody("Mars",
                BigDecimal.valueOf(0.107).multiply(Constants.EARTH_MASS),
                BigDecimal.valueOf(0.533),
                new BigPoint2D(Constants.AU_2_M.multiply(BigDecimal.valueOf(1.5)), BigDecimal.ZERO),
                new BigPoint2D(BigDecimal.ZERO, BigDecimal.valueOf(24077)),
                Color.ORANGERED);
        //
        solarSystem.addBody(sun);
//        solarSystem.addBody(sun2);
        solarSystem.addBody(mercure);
        solarSystem.addBody(venus);
        solarSystem.addBody(earth);
        solarSystem.addBody(mars);
        return solarSystem;
    }

    public static SolarSystem createOurSolarSystemAsApproximated() {
        var solarSystem = new SolarSystem("My Testing Solar System");
        var reducedEarthMass = 100;
        var reducedUA = 1500;
        var speedFactor = 0.0000001;
        //
        ApproximatedBody sun = new ApproximatedBody("Sun",
                333000 * reducedEarthMass,
                2,
                new Point2D(0, 0),
                new Point2D(0, 0),
                Color.GOLD);
        //
        ApproximatedBody mercure = new ApproximatedBody("Mercure",
                0.055 * reducedEarthMass,
                0.38295,
                new Point2D(reducedUA * 0.4, 0),
                new Point2D(0, 47362 * speedFactor),
                Color.GOLDENROD);
        //
        ApproximatedBody venus = new ApproximatedBody("Venus",
                0.815 * reducedEarthMass,
                0.9499,
                new Point2D(reducedUA * 0.7, 0),
                new Point2D(0, 35020 * speedFactor),
                Color.ANTIQUEWHITE);
        //
        ApproximatedBody earth = new ApproximatedBody("Earth",
                reducedEarthMass,
                1,
                new Point2D(reducedUA * 1.1, 0),
                new Point2D(0, 29782 * speedFactor),
                Color.DEEPSKYBLUE);
        //
        ApproximatedBody mars = new ApproximatedBody("Mars",
                0.107 * reducedEarthMass,
                0.533,
                new Point2D(reducedUA * 1.5, 0),
                new Point2D(0, 24077 * speedFactor),
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
