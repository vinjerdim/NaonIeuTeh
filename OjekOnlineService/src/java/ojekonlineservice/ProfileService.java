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

/**
 *
 * @author VINJERDIM
 */
@WebService(serviceName = "ProfileService")
public class ProfileService {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/OjekIdentityService/AccountService.wsdl")
    private AccountService_Service service;

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getAccount")
    public String getAccount(@WebParam(name = "accountID") int accountID) {
        //TODO write your implementation code here:
        return getAccountByID(accountID);
    }

    private String getAccountByID(int accountID) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ojekidentityservice.AccountService port = service.getAccountServicePort();
        return port.getAccountByID(accountID);
    }

    /**
     * Web service operation
     * @param accountID
     * @return 
     */
    @WebMethod(operationName = "getPrefLoc")
    public String getPrefLoc(@WebParam(name = "accountID") int accountID) {
        //TODO write your implementation code here:
        return QueryOjekController.getPreferredLoc(accountID).toString();
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getRatingVote")
    public String getRatingVote(@WebParam(name = "accountID") int accountID) {
        //TODO write your implementation code here:
        return QueryOjekController.getRatingVote(accountID).toString();
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "updateProfile")
    public Boolean updateProfile(@WebParam(name = "accountID") int accountID, @WebParam(name = "fullname") String fullname, 
            @WebParam(name = "phone") String phone, @WebParam(name = "isDriver") int isDriver) {
        //TODO write your implementation code here:
        if (fullname != null && phone != null) {
            if (!fullname.trim().equals("") && !phone.trim().equals("")) {
                return updateAccount(accountID, fullname, phone, isDriver);
            }
            return false;
        }
        return false;
    }

    private Boolean updateAccount(int accountID, java.lang.String fullname, java.lang.String phone, int isDriver) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ojekidentityservice.AccountService port = service.getAccountServicePort();
        return port.updateAccount(accountID, fullname, phone, isDriver);
    }
    
}
