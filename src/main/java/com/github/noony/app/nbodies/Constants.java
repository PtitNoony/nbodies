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

/**
 *
 * @author ahamon
 */
public class Constants {

    public static final BigDecimal AU_2_M = BigDecimal.valueOf(14959787e4);

    public static final BigDecimal EARTH_MASS = BigDecimal.valueOf(5.97237e24);

    public static final BigDecimal GRAVITY = BigDecimal.valueOf(6.673e-11);

    public static final double AU_2_M_DOUBLE_VALUE = AU_2_M.doubleValue();

    public static final double EARTH_MASS_DOUBLE_VALUE = EARTH_MASS.doubleValue();

    public static final double GRAVITY_DOUBLE_VALUE = GRAVITY.doubleValue();

    private Constants() {
        // private utility constructor
    }
}
