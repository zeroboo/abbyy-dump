//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.09.23 at 01:58:50 PM ICT 
//


package ABBYY;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SUBS_TYPEType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SUBS_TYPEType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="HypPart1"/>
 *     &lt;enumeration value="HypPart2"/>
 *     &lt;enumeration value="Abbreviation"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SUBS_TYPEType", namespace = "http://www.loc.gov/standards/alto/ns-v3#")
@XmlEnum
public enum SUBSTYPEType {

    @XmlEnumValue("HypPart1")
    HYP_PART_1("HypPart1"),
    @XmlEnumValue("HypPart2")
    HYP_PART_2("HypPart2"),
    @XmlEnumValue("Abbreviation")
    ABBREVIATION("Abbreviation");
    private final String value;

    SUBSTYPEType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SUBSTYPEType fromValue(String v) {
        for (SUBSTYPEType c: SUBSTYPEType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
