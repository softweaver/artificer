/*
 * Copyright 2011 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.03.21 at 10:23:48 AM EDT 
//


package org.s_ramp.xmlns._2010.s_ramp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Element complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Element">
 *   &lt;complexContent>
 *     &lt;extension base="{http://s-ramp.org/xmlns/2010/s-ramp}PolicySubject">
 *       &lt;sequence>
 *         &lt;element name="represents" type="{http://s-ramp.org/xmlns/2010/s-ramp}elementTarget" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="uses" type="{http://s-ramp.org/xmlns/2010/s-ramp}elementTarget" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="performs" type="{http://s-ramp.org/xmlns/2010/s-ramp}serviceTarget" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="directsOrchestration" type="{http://s-ramp.org/xmlns/2010/s-ramp}orchestrationTarget" minOccurs="0"/>
 *         &lt;element name="directsOrchestrationProcess" type="{http://s-ramp.org/xmlns/2010/s-ramp}orchestrationTarget" minOccurs="0"/>
 *         &lt;element name="generates" type="{http://s-ramp.org/xmlns/2010/s-ramp}eventTarget" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="respondsTo" type="{http://s-ramp.org/xmlns/2010/s-ramp}eventTarget" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;anyAttribute/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Element", propOrder = {
    "represents",
    "uses",
    "performs",
    "directsOrchestration",
    "directsOrchestrationProcess",
    "generates",
    "respondsTo"
})
@XmlSeeAlso({
    Actor.class,
    Service.class,
    Task.class,
    System.class
})
public class Element
    extends PolicySubject
    implements Serializable
{

    private static final long serialVersionUID = -5596425886883715957L;
    protected List<ElementTarget> represents;
    protected List<ElementTarget> uses;
    protected List<ServiceTarget> performs;
    protected OrchestrationTarget directsOrchestration;
    protected OrchestrationTarget directsOrchestrationProcess;
    protected List<EventTarget> generates;
    protected List<EventTarget> respondsTo;

    /**
     * Gets the value of the represents property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the represents property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRepresents().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ElementTarget }
     * 
     * 
     */
    public List<ElementTarget> getRepresents() {
        if (represents == null) {
            represents = new ArrayList<ElementTarget>();
        }
        return this.represents;
    }

    /**
     * Gets the value of the uses property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the uses property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ElementTarget }
     * 
     * 
     */
    public List<ElementTarget> getUses() {
        if (uses == null) {
            uses = new ArrayList<ElementTarget>();
        }
        return this.uses;
    }

    /**
     * Gets the value of the performs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the performs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPerforms().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ServiceTarget }
     * 
     * 
     */
    public List<ServiceTarget> getPerforms() {
        if (performs == null) {
            performs = new ArrayList<ServiceTarget>();
        }
        return this.performs;
    }

    /**
     * Gets the value of the directsOrchestration property.
     * 
     * @return
     *     possible object is
     *     {@link OrchestrationTarget }
     *     
     */
    public OrchestrationTarget getDirectsOrchestration() {
        return directsOrchestration;
    }

    /**
     * Sets the value of the directsOrchestration property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrchestrationTarget }
     *     
     */
    public void setDirectsOrchestration(OrchestrationTarget value) {
        this.directsOrchestration = value;
    }

    /**
     * Gets the value of the directsOrchestrationProcess property.
     * 
     * @return
     *     possible object is
     *     {@link OrchestrationTarget }
     *     
     */
    public OrchestrationTarget getDirectsOrchestrationProcess() {
        return directsOrchestrationProcess;
    }

    /**
     * Sets the value of the directsOrchestrationProcess property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrchestrationTarget }
     *     
     */
    public void setDirectsOrchestrationProcess(OrchestrationTarget value) {
        this.directsOrchestrationProcess = value;
    }

    /**
     * Gets the value of the generates property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the generates property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGenerates().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EventTarget }
     * 
     * 
     */
    public List<EventTarget> getGenerates() {
        if (generates == null) {
            generates = new ArrayList<EventTarget>();
        }
        return this.generates;
    }

    /**
     * Gets the value of the respondsTo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the respondsTo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRespondsTo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EventTarget }
     * 
     * 
     */
    public List<EventTarget> getRespondsTo() {
        if (respondsTo == null) {
            respondsTo = new ArrayList<EventTarget>();
        }
        return this.respondsTo;
    }

}
