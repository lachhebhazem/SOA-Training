package entities;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "uniteEnseignement")
public class UniteEnseignement {
    private int code;
    private String domaine;
    private String responsable;
    private int credits;
    private int semestre;

    public UniteEnseignement()
    {}
    public UniteEnseignement(int code, String domaine, String responsable, int credits, int semestre) {
        this.code = code;
        this.domaine = domaine;
        this.responsable = responsable;
        this.credits = credits;
        this.semestre = semestre;
    }

    // Getters et Setters
    @XmlElement public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @XmlElement public String getDomaine() {
        return domaine;
    }

     public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    @XmlElement public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    @XmlElement public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    @XmlElement public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }


}