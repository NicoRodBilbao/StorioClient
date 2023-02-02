/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import interfaces.Bookingable;
import services.BookingClient;

/**
 *
 * @author Markel Fernandez
 */
public class BookingFactory {
    private static Bookingable booking = new BookingClient();
    
    /**
     * This method creates an interface instanced as a Data Access Object.
     *
     * @return interface Bookingable as BookingImplementation
     */
    public static Bookingable getAccessBooking() {
        return null;
    }
}
