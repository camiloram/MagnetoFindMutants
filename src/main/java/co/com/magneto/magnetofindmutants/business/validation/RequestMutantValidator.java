package co.com.magneto.magnetofindmutants.business.validation;

import co.com.magneto.magnetofindmutants.business.DnaRequest;
import co.com.magneto.magnetofindmutants.business.constants.EError;
import co.com.magneto.magnetofindmutants.business.exception.RequestValidationException;

/**
 * Clase para validar valores del request
 *
 * @author Camilo Andres Ramirez Pava
 * @versión 1.0
 */
public class RequestMutantValidator {
    
    private static String dnaRegex = "[ACGT]+";

    public static boolean validMutant(DnaRequest request) throws RequestValidationException{
        String[] dnaRequest = request.getDna();
        
        if (dnaRequest == null || dnaRequest.length == 0){
            throw new RequestValidationException(EError.REQUEST_VALIDATION,
                    EError.REQUEST_VALIDATION.getDescription());
        } else {
            for (String dnaRow : dnaRequest) {
                if (!dnaRow.matches(dnaRegex)){
                    throw new RequestValidationException(EError.REQUEST_VALUE,
                            dnaRow + " - " + EError.REQUEST_VALUE.getDescription());
                }
            }
            for (String dnaRow : dnaRequest) {
                if(dnaRequest.length != dnaRow.length()){
                    throw new RequestValidationException(EError.REQUEST_VALUE_NUMBER,
                            dnaRow + " - " + dnaRow.length() + " - " + EError.REQUEST_VALUE_NUMBER.getDescription() + dnaRequest.length);
                }
            }
        }        
        return true;
    }
}
