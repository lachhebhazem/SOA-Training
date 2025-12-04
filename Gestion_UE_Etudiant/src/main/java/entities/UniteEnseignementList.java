package entities;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlRootElement(name = "uniteEnseignements")
public class UniteEnseignementList {

    private List<UniteEnseignement> list;

    public UniteEnseignementList() {}

    public UniteEnseignementList(List<UniteEnseignement> list) {
        this.list = list;
    }

    @XmlElement(name = "uniteEnseignement")
    public List<UniteEnseignement> getList() {
        return list;
    }

    public void setList(List<UniteEnseignement> list) {
        this.list = list;
    }
}