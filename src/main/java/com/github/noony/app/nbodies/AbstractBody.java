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

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;

/**
 *
 * @author arnaud
 */
public abstract class AbstractBody {

    private final String name;
    private final Color color;

    public AbstractBody(String aName, Color aColor) {
        name = aName;
        color = aColor;
    }

    public abstract Point3D getDisplayablePosition();

    public abstract double getRadiusAsDouble();

    protected abstract void linkToOtherBody(AbstractBody anotherBody);

    protected abstract void calculateNextPosition();

    protected abstract void moveToNextPosition();

    protected abstract void setDeltaT(double aDeltaT);

    protected abstract void setGravitationalConstant(String aGCste3);

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return name;
    }
}
