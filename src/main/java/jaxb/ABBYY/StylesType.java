//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.09.23 at 01:58:50 PM ICT 
//


package ABBYY;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StylesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StylesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TextStyle" type="{http://www.loc.gov/standards/alto/ns-v3#}TextStyleType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ParagraphStyle" type="{http://www.loc.gov/standards/alto/ns-v3#}ParagraphStyleType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StylesType", namespace = "http://www.loc.gov/standards/alto/ns-v3#", propOrder = {
    "textStyle",
    "paragraphStyle"
})
public class StylesType {

    @XmlElement(name = "TextStyle", namespace = "http://www.loc.gov/standards/alto/ns-v3#")
    protected List<TextStyleType> textStyle;
    @XmlElement(name = "ParagraphStyle", namespace = "http://www.loc.gov/standards/alto/ns-v3#")
    protected List<ParagraphStyleType> paragraphStyle;

    /**
     * Gets the value of the textStyle property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the textStyle property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTextStyle().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TextStyleType }
     * 
     * 
     */
    public List<TextStyleType> getTextStyle() {
        if (textStyle == null) {
            textStyle = new ArrayList<TextStyleType>();
        }
        return this.textStyle;
    }

    /**
     * Gets the value of the paragraphStyle property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paragraphStyle property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParagraphStyle().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ParagraphStyleType }
     * 
     * 
     */
    public List<ParagraphStyleType> getParagraphStyle() {
        if (paragraphStyle == null) {
            paragraphStyle = new ArrayList<ParagraphStyleType>();
        }
        return this.paragraphStyle;
    }

}
