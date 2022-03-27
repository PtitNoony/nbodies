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

import com.google.common.math.BigIntegerMath;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 *
 * @author ahamon
 */
public class BigPoint2D {

    public static final BigPoint2D ORIGIN = new BigPoint2D();

    private final MathContext m = MathContext.DECIMAL128;

    private BigDecimal x;
    private BigDecimal y;

    public BigPoint2D(BigDecimal x, BigDecimal y) {
        this.x = x;
        this.y = y;
    }

    public BigPoint2D() {
        this(BigDecimal.ZERO, BigDecimal.ZERO);
    }

    public BigDecimal getX() {
        return x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void add(BigPoint2D bigPoint2D) {
        x = x.add(bigPoint2D.x);
        y = y.add(bigPoint2D.y);
    }

    public BigDecimal distance(BigPoint2D bigPoint2D) {
        // not optimized
//        System.err.println(" x= " + x);
//        System.err.println(" x b= " + bigPoint2D.getX());
//        System.err.println(" y= " + y);
//        System.err.println(" y b= " + bigPoint2D.getY());
        BigDecimal deltaX = bigPoint2D.getX().subtract(x, m);
        BigDecimal deltaXSquare = deltaX.multiply(deltaX, m);
        BigDecimal deltaY = bigPoint2D.getY().subtract(y, m);
        BigDecimal deltaYSquare = deltaY.multiply(deltaY);
//        BigDecimal deltaYSquare = bigPoint2D.getY().subtract(y).pow(2);
        BigDecimal sum = deltaXSquare.add(deltaYSquare);
//        System.err.println(" > delta x  = " + deltaX);
//        System.err.println(" > delta x2 = " + deltaXSquare);
//        System.err.println(" > delta y  = " + deltaY);
//        System.err.println(" > delta y2 = " + deltaYSquare);
//        System.err.println(" > sum = " + sum);
//        System.err.println(" > sqrt = " + BigIntegerMath.sqrt(sum.toBigInteger(), RoundingMode.DOWN));
        return new BigDecimal(BigIntegerMath.sqrt(sum.toBigInteger(), RoundingMode.DOWN));
    }

    public BigPoint2D normalize() {
//        System.err.println("--------");
        BigDecimal norm = distance(ORIGIN);
//        System.err.println("NORM = " + norm);
//        System.err.println("--------");
        if (norm.compareTo(BigDecimal.ONE) == 0) {
            return ORIGIN;
        }
        return new BigPoint2D(x.divide(norm, 4, RoundingMode.DOWN), y.divide(norm, 4, RoundingMode.DOWN));
//        return new BigPoint2D();
    }
}
