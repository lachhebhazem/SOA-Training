package entities;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum(String.class)
public enum TypeModule {
    @XmlEnumValue("TRANSVERSAL") TRANSVERSAL,
    @XmlEnumValue("PROFESSIONNEL") PROFESSIONNEL,
    @XmlEnumValue("RECHERCHE") RECHERCHE
}