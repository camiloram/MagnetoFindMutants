package co.com.magneto.magnetofindmutants.rest;

import co.com.magneto.magnetofindmutants.business.StatsResponse;
import co.com.magneto.magnetofindmutants.business.DnaRequest;
import co.com.magneto.magnetofindmutants.business.IBusiness;
import co.com.magneto.magnetofindmutants.business.exception.BusinessException;
import co.com.magneto.magnetofindmutants.business.exception.RequestValidationException;
import co.com.magneto.magnetofindmutants.business.validation.RequestMutantValidator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("")
public class FindMutantsResource {

    @Context
    private UriInfo context;

    public FindMutantsResource() {
    }

    @EJB
    private IBusiness business;
    
    @GET
    @Path("stats")
    @Produces(MediaType.APPLICATION_JSON)
    public StatsResponse stats() {
        try {
            return business.getStat();
        } catch (Exception e) {
            throw new UnsupportedOperationException();
        }
    }
    
    @POST
    @Path("mutant")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response isMutant(DnaRequest request){
        boolean response = false;
        try {
            response = RequestMutantValidator.validMutant(request);
            response = business.isMutant(request);
        } catch (RequestValidationException ex) {
            Logger.getLogger(FindMutantsResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BusinessException ex) {
            Logger.getLogger(FindMutantsResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(response){
            return Response.status(200).build();
        } else {
            return Response.status(403).build();
        }
    }
}
