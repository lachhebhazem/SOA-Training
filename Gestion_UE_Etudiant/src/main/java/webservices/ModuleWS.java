package webservices;

import entities.*;
import entities.Module;
import metiers.ModuleBusiness;
import metiers.UniteEnseignementBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/modules")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class ModuleWS {
    private final ModuleBusiness business = new ModuleBusiness();
    private final UniteEnseignementBusiness ueBusiness = new UniteEnseignementBusiness();

    // 1. Création d'un module
    @POST
    public Response create(Module module) {
        if (module.getMatricule() == null || business.getModuleByMatricule(module.getMatricule()) != null) {
            return Response.status(409).entity("Matricule manquant ou déjà existant").build();
        }
        if (!business.addModule(module)) {
            return Response.status(400).entity("UniteEnseignement inexistante").build();
        }
        return Response.status(201).entity(module).build();
    }

    // 2. Liste complète
    @GET
    public Response getAll() {
        return Response.ok(new ModuleList(business.getAllModules())).build();
    }

    // 3. Modules d'une UE spécifique
    @GET
    @Path("/ue/{codeUE}")
    public Response getByUE(@PathParam("codeUE") int codeUE) {
        List<Module> result = business.getModulesByUE(new UniteEnseignement(codeUE, null, null, 0, 0));
        if (result.isEmpty()) {
            return Response.status(404).build();
        }
        return Response.ok(new ModuleList(result)).build();
    }

    // 4. Suppression
    @DELETE
    @Path("/{matricule}")
    public Response delete(@PathParam("matricule") String matricule) {
        boolean deleted = business.deleteModule(matricule);
        return deleted ? Response.ok().build() : Response.status(404).build();
    }
    // 5) Modification d'un module

    @PUT
    @Path("/{matricule}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response update(@PathParam("matricule") String matricule, Module moduleMisAJour) {
        Module existant = business.getModuleByMatricule(matricule);
        if (existant == null) {
            return Response.status(404).build();
        }
        UniteEnseignementBusiness ueBusiness = new UniteEnseignementBusiness();
        if (moduleMisAJour.getUniteEnseignement() == null ||
                moduleMisAJour.getUniteEnseignement().getCode() <= 0 ||
                ueBusiness.getUEByCode(moduleMisAJour.getUniteEnseignement().getCode()) == null) {
            return Response.status(400).entity("UniteEnseignement invalide").build();
        }
        existant.setNom(moduleMisAJour.getNom());
        existant.setCoefficient(moduleMisAJour.getCoefficient());
        existant.setVolumeHoraire(moduleMisAJour.getVolumeHoraire());
        existant.setType(moduleMisAJour.getType());
        existant.setUniteEnseignement(ueBusiness.getUEByCode(moduleMisAJour.getUniteEnseignement().getCode()));
        return Response.ok().build();

    }
    // 6) Récupérer les modules d'une UE
    @GET
    @Path("/UE")
    public Response getByUEQuery(@QueryParam("codeUE") int codeUE) {
        List<Module> result = business.getModulesByUE(new UniteEnseignement(codeUE, null, null, 0, 0));
        if (result.isEmpty()) {
            return Response.status(404).build();
        }
        return Response.ok(result).build();
    }
}