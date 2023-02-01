/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import dataAcces.PackManagerImplementation;
import interfaces.Packable;

/**
 *
 * @author 2dam
 */
public class PackFactory {

    private static Packable packManager = null;

    public static Packable getAccessPack() {
        packManager = new PackManagerImplementation();
        return packManager;
    }
}
