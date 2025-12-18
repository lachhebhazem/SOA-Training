package webservices;

import entities.Module;
import entities.UniteEnseignement;
import io.swagger.v3.oas.annotations.Operation;
import metiers.ModuleBusiness;
import metiers.UniteEnseignementBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/modules")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ModuleWS {

    private final ModuleBusiness moduleBusiness = new ModuleBusiness();
    private final UniteEnseignementBusiness ueBusiness = new UniteEnseignementBusiness();

    @Operation(summary = "Ajouter un module", description = "Ajoute un module avec son UE")
    @POST
    public Response create(Module module) {

        if (module == null)
            return Response.status(400).entity("Corps de la requête vide").build();

        if (module.getMatricule() == null || module.getMatricule().trim().isEmpty())
            return Response.status(400).entity("Matricule obligatoire").build();

        if (moduleBusiness.getModuleByMatricule(module.getMatricule()) != null)
            return Response.status(409).entity("Matricule déjà existant").build();

        if (module.getUniteEnseignement() == null)
            return Response.status(400).entity("Unité d'enseignement obligatoire").build();

        int codeUE = module.getUniteEnseignement().getCode();
        UniteEnseignement ue = ueBusiness.getUEByCode(codeUE);

        if (ue == null)
            return Response.status(400).entity("Unité d'enseignement inexistante").build();

        module.setUniteEnseignement(ue);

        boolean ok = moduleBusiness.addModule(module);
        if (!ok)
            return Response.serverError().entity("Erreur lors de l'ajout du module").build();

        return Response.status(201).entity(module).build();
    }
    @GET
    @Operation(summary = "Lister tous les modules", description = "Retourne la liste complète des modules")
    public Response getAll() {
        List<Module> modules = moduleBusiness.getAllModules();
        return Response.ok(modules).build();
    }

    @GET
    @Path("/{matricule}")
    @Operation(summary = "Récupérer un module par matricule", description = "Retourne un module selon son matricule")
    public Response getByMatricule(@PathParam("matricule") String matricule) {
        Module module = moduleBusiness.getModuleByMatricule(matricule);
        if (module == null)
            return Response.status(404).entity("Module non trouvé").build();

        return Response.ok(module).build();
    }

    @GET
    @Path("/ue/{code}")
    @Operation(summary = "Lister les modules par UE", description = "Retourne tous les modules associés à une unité d'enseignement")
    public Response getModulesByUE(@PathParam("code") int codeUE) {
        UniteEnseignement ue = ueBusiness.getUEByCode(codeUE);

        if (ue == null)
            return Response.status(404).entity("Unité d'enseignement non trouvée").build();

        List<Module> result = moduleBusiness.getModulesByUE(ue);
        return Response.ok(result).build();
    }

    @PUT
    @Path("/{matricule}")
    @Operation(summary = "Mettre à jour un module", description = "Met à jour un module existant par son matricule")
    public Response update(@PathParam("matricule") String matricule, Module moduleUpdate) {

        if (moduleUpdate == null)
            return Response.status(400).entity("Corps vide").build();

        if (!matricule.equals(moduleUpdate.getMatricule()))
            return Response.status(400).entity("Matricule incohérent entre URL et corps").build();

        Module existant = moduleBusiness.getModuleByMatricule(matricule);
        if (existant == null)
            return Response.status(404).entity("Module non trouvé").build();

        if (moduleUpdate.getUniteEnseignement() == null)
            return Response.status(400).entity("Code UE manquant").build();

        int codeUE = moduleUpdate.getUniteEnseignement().getCode();
        UniteEnseignement ue = ueBusiness.getUEByCode(codeUE);

        if (ue == null)
            return Response.status(400).entity("Unité d'enseignement invalide").build();

        moduleUpdate.setUniteEnseignement(ue);

        boolean updated = moduleBusiness.updateModule(matricule, moduleUpdate);
        if (!updated)
            return Response.serverError().entity("Échec de la mise à jour").build();

        return Response.ok(moduleUpdate).build();
    }

    @DELETE
    @Path("/{matricule}")
    @Operation(summary = "Supprimer un module", description = "Supprime un module par son matricule")
    public Response delete(@PathParam("matricule") String matricule) {
        boolean deleted = moduleBusiness.deleteModule(matricule);
        if (!deleted)
            return Response.status(404).entity("Module non trouvé").build();

        return Response.noContent().build();
    }
}
