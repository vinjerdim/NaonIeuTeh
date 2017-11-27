/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ojekidentityservice;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author VINJERDIM
 */
@WebService(serviceName = "ValidationService")
public class ValidationService {

    /**
     * This is a sample web service operation
     * @param token
     * @return 
     */
    @WebMethod(operationName = "isTokenValid")
    public int isTokenValid(@WebParam(name = "token") String token) {
        boolean result = new AccountManager().isTokenValid(token);
        return (result) ? 1 : 0;
    }
}
