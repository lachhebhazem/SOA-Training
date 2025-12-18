package webservices;

import entities.UniteEnseignement;
import metiers.UniteEnseignementBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Path("/ues")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class UniteEnseignementWS {

    private final UniteEnseignementBusiness business = new UniteEnseignementBusiness();

    // =========================================================
    // Wrapper pour les listes (obligatoire pour JAXB/XML)
    // =========================================================
    @XmlRootElement(name = "unitesEnseignement")
    public static class UniteEnseignementList {
        private List<UniteEnseignement> ues;

        public UniteEnseignementList() {} // JAXB

        public UniteEnseignementList(List<UniteEnseignement> ues) {
            this.ues = ues;
        }

        @XmlElement(name = "uniteEnseignement")
        public List<UniteEnseignement> getUnitesEnseignement() {
            return ues;
        }

        public void setUnitesEnseignement(List<UniteEnseignement> ues) {
            this.ues = ues;
        }
    }

    // ---------------------------------------------------------
    // 1) Créer une nouvelle UE
    // ---------------------------------------------------------
    @POST
    public Response create(UniteEnseignement ue) {
        if (ue == null || ue.getCode() <= 0) {
            return Response.status(400).entity("Code UE obligatoire et positif").build();
        }

        if (business.getUEByCode(ue.getCode()) != null) {
            return Response.status(409).entity("Une UE avec ce code existe déjà").build();
        }

        boolean added = business.addUniteEnseignement(ue);
        if (!added) {
            return Response.serverError().entity("Échec de l'ajout").build();
        }

        return Response.status(201).entity(ue).build();
    }

    // ---------------------------------------------------------
    // 2) Récupérer toutes les UEs
    // ---------------------------------------------------------
    @GET
    public Response getAll() {
        List<UniteEnseignement> all = business.getListeUE();
        return Response.ok(new UniteEnseignementList(all)).build();
    }

    // ---------------------------------------------------------
    // 3) Récupérer une UE par code (recommandé : via PathParam)
    // ---------------------------------------------------------
    @GET
    @Path("/{code}")
    public Response getByCode(@PathParam("code") int code) {
        UniteEnseignement ue = business.getUEByCode(code);
        if (ue == null) {
            return Response.status(404).entity("UE non trouvée").build();
        }
        return Response.ok(ue).build();
    }

    // ---------------------------------------------------------
    // 4) Récupérer les UEs par semestre
    // ---------------------------------------------------------
    @GET
    @Path("/semestre/{semestre}")
    public Response getBySemestre(@PathParam("semestre") int semestre) {
        if (semestre < 1 || semestre > 6) { // optionnel : validation réaliste
            return Response.status(400).entity("Semestre invalide (1-6)").build();
        }

        List<UniteEnseignement> result = business.getUEBySemestre(semestre);
        return Response.ok(new UniteEnseignementList(result)).build();
    }

    // ---------------------------------------------------------
    // 5) Mettre à jour une UE (le code NE DOIT PAS changer)
    // ---------------------------------------------------------
    @PUT
    @Path("/{code}")
    public Response update(@PathParam("code") int code, UniteEnseignement ueUpdate) {

        if (ueUpdate == null) {
            return Response.status(400).entity("Données manquantes").build();
        }

        // Le code dans le body DOIT correspondre à celui du path
        if (ueUpdate.getCode() != code) {
            return Response.status(400)
                    .entity("Le code dans l'URL et dans le corps doivent être identiques")
                    .build();
        }

        UniteEnseignement existante = business.getUEByCode(code);
        if (existante == null) {
            return Response.status(404).entity("UE non trouvée").build();
        }

        // On met à jour via le business (plus propre)
        boolean updated = business.updateUniteEnseignement(code, ueUpdate);
        if (!updated) {
            return Response.serverError().entity("Échec de la mise à jour").build();
        }

        return Response.ok(ueUpdate).build();
    }

    // ---------------------------------------------------------
    // 6) Supprimer une UE
    // ---------------------------------------------------------
    @DELETE
    @Path("/{code}")
    public Response delete(@PathParam("code") int code) {
        boolean deleted = business.deleteUniteEnseignement(code);
        if (!deleted) {
            return Response.status(404).entity("UE non trouvée").build();
        }
        return Response.noContent().build(); // 204 No Content = standard
    }

    // ---------------------------------------------------------
    // 7) (Bonus) Recherche par domaine
    // ---------------------------------------------------------
    @GET
    @Path("/domaine/{domaine}")
    public Response getByDomaine(@PathParam("domaine") String domaine) {
        List<UniteEnseignement> result = business.getUEByDomaine(domaine);
        if (result.isEmpty()) {
            return Response.status(404).entity("Aucune UE trouvée pour ce domaine").build();
        }
        return Response.ok(new UniteEnseignementList(result)).build();
    }
}