package lk.anan.ri.service;



import lk.anan.ri.util.Stackscanner;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/you")
public class MyRestResource {

    @EJB
    private MyEjbInterface myEjb;

    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@PathParam("name") String name) {
        Stackscanner.getCaller();
        return myEjb.hello(name);
    }
}
