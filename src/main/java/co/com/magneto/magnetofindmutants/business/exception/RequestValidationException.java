package co.com.magneto.magnetofindmutants.business.exception;

import co.com.magneto.magnetofindmutants.business.constants.EError;

/**
 * Clase que representa excepiones de tipo RequestValidationException
 *
 * @author Camilo Andres Ramirez Pava
 * @versión 1.0
 */
public class RequestValidationException extends BusinessException {
    
    public RequestValidationException(EError error, String message) {
        super(message);
        this.error = error;
    }
}
