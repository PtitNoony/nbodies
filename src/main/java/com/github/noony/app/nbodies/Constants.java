/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

    private Constants() {
        // private utility constructor
    }
}
