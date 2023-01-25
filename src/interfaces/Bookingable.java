/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Booking;
import entities.BookingState;
import entities.Pack;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Markel Fernandez
 */
public interface Bookingable {
     public void updateBooking_XML(Object requestEntity) throws ClientErrorException;

    public void updateBooking_JSON(Object requestEntity) throws ClientErrorException;

    public <T> T findBookingsByState_XML(Class<T> responseType, String state) throws ClientErrorException;

    public <T> T findBookingsByState_JSON(Class<T> responseType, String state) throws ClientErrorException;

    public <T> T findUserOwnBookings_XML(Class<T> responseType, String id) throws ClientErrorException;

    public <T> T findUserOwnBookings_JSON(Class<T> responseType, String id) throws ClientErrorException;

    public <T> T find_XML(Class<T> responseType, String id) throws ClientErrorException;

    public <T> T find_JSON(Class<T> responseType, String id) throws ClientErrorException;

    public void removeBooking(String id) throws ClientErrorException;

    public <T> T findPacksForBooking_XML(Class<T> responseType, String id) throws ClientErrorException;

    public <T> T findPacksForBooking_JSON(Class<T> responseType, String id) throws ClientErrorException;

    public <T> T findAll_XML(GenericType<T> responseType) throws ClientErrorException;

    public <T> T findAll_JSON(Class<T> responseType) throws ClientErrorException;

    public void createBooking_XML(Object requestEntity) throws ClientErrorException;

    public void createBooking_JSON(Object requestEntity) throws ClientErrorException;
}
