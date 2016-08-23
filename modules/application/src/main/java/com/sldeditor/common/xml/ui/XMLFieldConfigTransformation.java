//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: [TEXT REMOVED by maven-replacer-plugin]
//


package com.sldeditor.common.xml.ui;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *                 Configuration for a render transformation field
 *             
 * 
 * <p>Java class for XMLFieldConfigTransformation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="XMLFieldConfigTransformation"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{}XMLFieldConfigData"&gt;
 *       &lt;attribute name="editButtonText" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="clearButtonText" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XMLFieldConfigTransformation")
public class XMLFieldConfigTransformation
    extends XMLFieldConfigData
{

    @XmlAttribute(name = "editButtonText")
    protected String editButtonText;
    @XmlAttribute(name = "clearButtonText")
    protected String clearButtonText;

    /**
     * Gets the value of the editButtonText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEditButtonText() {
        return editButtonText;
    }

    /**
     * Sets the value of the editButtonText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEditButtonText(String value) {
        this.editButtonText = value;
    }

    /**
     * Gets the value of the clearButtonText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClearButtonText() {
        return clearButtonText;
    }

    /**
     * Sets the value of the clearButtonText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClearButtonText(String value) {
        this.clearButtonText = value;
    }

}