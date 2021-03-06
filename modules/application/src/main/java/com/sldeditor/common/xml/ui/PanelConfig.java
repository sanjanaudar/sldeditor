//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference
// Implementation, v2.2.11
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: [TEXT REMOVED by maven-replacer-plugin]
//

package com.sldeditor.common.xml.ui;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Describes how panel is made up
 *
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice maxOccurs="unbounded" minOccurs="0"&gt;
 *         &lt;element name="Group" type="{}XMLGroupConfig"/&gt;
 *         &lt;element name="MultiOptionGroup" type="{}XMLMultiOptionGroup"/&gt;
 *       &lt;/choice&gt;
 *       &lt;attribute name="vendorOption" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="start" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="end" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="panelTitle" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="localisation" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "",
        propOrder = {"groupOrMultiOptionGroup"})
@XmlRootElement(name = "PanelConfig")
public class PanelConfig {

    @XmlElements({
        @XmlElement(name = "Group", type = XMLGroupConfig.class),
        @XmlElement(name = "MultiOptionGroup", type = XMLMultiOptionGroup.class)
    })
    protected List<Object> groupOrMultiOptionGroup;

    @XmlAttribute(name = "vendorOption")
    protected String vendorOption;

    @XmlAttribute(name = "start")
    protected String start;

    @XmlAttribute(name = "end")
    protected String end;

    @XmlAttribute(name = "panelTitle")
    protected String panelTitle;

    @XmlAttribute(name = "localisation")
    protected String localisation;

    /**
     * Gets the value of the groupOrMultiOptionGroup property.
     *
     * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the groupOrMultiOptionGroup property.
     *
     * <p>For example, to add a new item, do as follows:
     *
     * <pre>
     *    getGroupOrMultiOptionGroup().add(newItem);
     * </pre>
     *
     * <p>Objects of the following type(s) are allowed in the list {@link XMLGroupConfig } {@link
     * XMLMultiOptionGroup }
     */
    public List<Object> getGroupOrMultiOptionGroup() {
        if (groupOrMultiOptionGroup == null) {
            groupOrMultiOptionGroup = new ArrayList<Object>();
        }
        return this.groupOrMultiOptionGroup;
    }

    /**
     * Gets the value of the vendorOption property.
     *
     * @return possible object is {@link String }
     */
    public String getVendorOption() {
        return vendorOption;
    }

    /**
     * Sets the value of the vendorOption property.
     *
     * @param value allowed object is {@link String }
     */
    public void setVendorOption(String value) {
        this.vendorOption = value;
    }

    /**
     * Gets the value of the start property.
     *
     * @return possible object is {@link String }
     */
    public String getStart() {
        return start;
    }

    /**
     * Sets the value of the start property.
     *
     * @param value allowed object is {@link String }
     */
    public void setStart(String value) {
        this.start = value;
    }

    /**
     * Gets the value of the end property.
     *
     * @return possible object is {@link String }
     */
    public String getEnd() {
        return end;
    }

    /**
     * Sets the value of the end property.
     *
     * @param value allowed object is {@link String }
     */
    public void setEnd(String value) {
        this.end = value;
    }

    /**
     * Gets the value of the panelTitle property.
     *
     * @return possible object is {@link String }
     */
    public String getPanelTitle() {
        return panelTitle;
    }

    /**
     * Sets the value of the panelTitle property.
     *
     * @param value allowed object is {@link String }
     */
    public void setPanelTitle(String value) {
        this.panelTitle = value;
    }

    /**
     * Gets the value of the localisation property.
     *
     * @return possible object is {@link String }
     */
    public String getLocalisation() {
        return localisation;
    }

    /**
     * Sets the value of the localisation property.
     *
     * @param value allowed object is {@link String }
     */
    public void setLocalisation(String value) {
        this.localisation = value;
    }
}
