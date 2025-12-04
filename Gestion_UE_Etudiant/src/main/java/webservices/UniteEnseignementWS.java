package webservices;

import entities.UniteEnseignement;
import entities.UniteEnseignementList;
import metiers.UniteEnseignementBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/UE")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class UniteEnseignementWS {

    private final UniteEnseignementBusiness business = new UniteEnseignementBusiness();

    @POST
    public Response create(UniteEnseignement ue) {
        if (business.getUEByCode(ue.getCode()) != null) {
            return Response.status(404).build();
        }
        business.addUniteEnseignement(ue);
        return Response.status(201).entity(ue).build();
    }


    @GET
    @Path("")
    public Response getAll() {
        return Response.ok(new UniteEnseignementList(business.getListeUE())).build();
    }

    @GET
    @Path("/bySemestre")
    public Response getBySemestre(@QueryParam("semestre") Integer semestre) {
        if (semestre == null) {
            return Response.status(400).entity("Paramètre semestre obligatoire").build();
        }
        List<UniteEnseignement> liste = business.getUEBySemestre(semestre);
        if (liste.isEmpty()) {
            return Response.status(404).build();
        }
        return Response.ok(new UniteEnseignementList(liste)).build();
    }

    @DELETE
    @Path("/{code}")
    public Response delete(@PathParam("code") int code) {
        return business.deleteUniteEnseignement(code)
                ? Response.ok().build()
                : Response.status(404).build();
    }
    // 5) Modification d'une UE
    @PUT
    @Path("/{code}")
    @Consumes(MediaType.APPLICATION_XML)
    public Response update(@PathParam("code") int code, UniteEnseignement ueModifiee) {
        UniteEnseignement existante = business.getUEByCode(code);
        if (existante == null) {
            return Response.status(404).build();
        }
        existante.setDomaine(ueModifiee.getDomaine());
        existante.setResponsable(ueModifiee.getResponsable());
        existante.setCredits(ueModifiee.getCredits());
        existante.setSemestre(ueModifiee.getSemestre());
        return Response.ok().build();
    }

    // 6) Récupération d'une seule UE par code
    @GET
    @Path("/detail")
    public Response getByCode(@QueryParam("code") int code) {
        UniteEnseignement ue = business.getUEByCode(code);
        if (ue == null) {
            return Response.status(404).build();
        }
        return Response.ok(ue).build();
    }
}