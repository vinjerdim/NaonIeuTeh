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
@WebService(serviceName = "HistoryService")
public class HistoryService {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getHistoryPassenger")
    public String getHistoryPassenger(@WebParam(name = "passengerID") int passengerID) {
        //TODO write your implementation code here:
        return QueryOjekController.getHistoryPassenger(passengerID).toString();
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getHistoryDriver")
    public String getHistoryDriver(@WebParam(name = "driverID") int driverID) {
        //TODO write your implementation code here:
        return QueryOjekController.getHistoryDriver(driverID).toString();
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "updateHideStatus")
    public Boolean updateHideStatus(@WebParam(name = "transactionID") String transactionID, @WebParam(name = "isDriver") String isDriver) {
        //TODO write your implementation code here:
        return QueryOjekController.updateHideStatus(transactionID, isDriver);
    }
}
