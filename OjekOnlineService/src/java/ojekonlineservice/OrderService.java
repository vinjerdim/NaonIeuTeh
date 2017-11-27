/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ojekonlineservice;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author VINJERDIM
 */
@WebService(serviceName = "OrderService")
public class OrderService {

    /**
     * Web service operation
     * @param preferredDriver
     * @return 
     */
    @WebMethod(operationName = "getDrivers")
    public String getDrivers(@WebParam(name = "preferredDriver") String preferredDriver
            , @WebParam(name = "currentUserID") int currentUserID, @WebParam(name = "prefloc") String prefloc) {
        //TODO write your implementation code here:
        preferredDriver = (preferredDriver != null) ? preferredDriver.trim() : preferredDriver;
        return QueryOjekController.getDriverRatingVote(preferredDriver, currentUserID, prefloc).toString();
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "addTransaction")
    public Boolean addTransaction(@WebParam(name = "passengerID") String passengerID, @WebParam(name = "driverID") String driverID, 
            @WebParam(name = "pickLoc") String pickLoc, @WebParam(name = "destLoc") String destLoc, 
            @WebParam(name = "stars") String stars, @WebParam(name = "review") String review) {
        //TODO write your implementation code here:
        return QueryOjekController.insertTransaction(passengerID, driverID, pickLoc, destLoc, stars, review);
    }   

}