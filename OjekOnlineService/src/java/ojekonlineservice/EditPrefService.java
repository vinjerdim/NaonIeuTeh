/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ojekonlineservice;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.ws.WebServiceRef;
import ojekidentityservice.AccountService_Service;

@WebService(serviceName = "EditPrefService")
public class EditPrefService {
    @WebMethod(operationName = "addPreference")
    public boolean addPreference(@WebParam(name = "accountID") int accountID, String loc) {
        //TODO write your implementation code here:
       return QueryOjekController.addPrefLoc(accountID, loc);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "updatePreference")
    public Boolean updatePreference(@WebParam(name = "prefID") int prefID, @WebParam(name = "location") String location) {
        //TODO write your implementation code here:
        if (location != null && !location.trim().equals("")) {
            return QueryOjekController.updatePrefLoc(prefID, location);
        }
        return false;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "deletePreference")
    public Boolean deletePreference(@WebParam(name = "prefID") int prefID) {
        //TODO write your implementation code here:
        return QueryOjekController.deletePrefLoc(prefID);
    }
}