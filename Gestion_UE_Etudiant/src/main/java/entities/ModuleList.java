package entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "modules")
public class ModuleList {

    private List<Module> modules;

    // Constructeur vide (obligatoire pour JAXB)
    public ModuleList() {
        this.modules = new ArrayList<>();
    }

    // Constructeur avec liste
    public ModuleList(List<Module> modules) {
        this.modules = modules;
    }

    @XmlElement(name = "module")
    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
}