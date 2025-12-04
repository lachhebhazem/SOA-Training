package entities;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "module")
public class Module {
    private String matricule;
    private String nom;
    private int coefficient;
    private int volumeHoraire;
    private TypeModule type;
    private UniteEnseignement uniteEnseignement; // Référence vers l'UE associée
    public Module(){}
    // Constructeur
    public Module(String matricule, String nom, int coefficient, int volumeHoraire, TypeModule type) {
        this.matricule = matricule;
        this.nom = nom;
        this.coefficient = coefficient;
        this.volumeHoraire = volumeHoraire;
        this.type = type;
    }
    public Module(String matricule, String nom, int coefficient, int volumeHoraire, TypeModule type,UniteEnseignement uniteEnseignement) {
        this.matricule = matricule;
        this.nom = nom;
        this.coefficient = coefficient;
        this.volumeHoraire = volumeHoraire;
        this.type = type;
        this.uniteEnseignement=uniteEnseignement;
    }
    // Enumération TypeModule
    public enum TypeModule {
        TRANSVERSAL,
        PROFESSIONNEL,
        RECHERCHE
    }

    // Getters et Setters
    @XmlElement public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    @XmlElement public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @XmlElement public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    @XmlElement public int getVolumeHoraire() {
        return volumeHoraire;
    }

    public void setVolumeHoraire(int volumeHoraire) {
        this.volumeHoraire = volumeHoraire;
    }

    @XmlElement public TypeModule getType() {
        return type;
    }

    public void setType(TypeModule type) {
        this.type = type;
    }

    @XmlElement public UniteEnseignement getUniteEnseignement() {
        return uniteEnseignement;
    }

    @XmlElement(name = "uniteEnseignement")
    public void setUniteEnseignement(UniteEnseignement uniteEnseignement) {
        this.uniteEnseignement = uniteEnseignement;
    }


}