package co.com.magneto.magnetofindmutants.business;

import co.com.magneto.magnetofindmutants.business.exception.BusinessException;
import javax.ejb.Local;

/**
 * interface del negocio
 * @author Camilo Andres Ramirez Pava
 * @versión 1.0
 */
@Local
public interface IBusiness {

    public boolean isMutant(DnaRequest dnaRequest) throws BusinessException;
    public StatsResponse getStat() throws BusinessException;
}
