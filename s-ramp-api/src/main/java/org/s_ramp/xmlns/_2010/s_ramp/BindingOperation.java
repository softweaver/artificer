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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BindingOperation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BindingOperation">
 *   &lt;complexContent>
 *     &lt;extension base="{http://s-ramp.org/xmlns/2010/s-ramp}NamedWsdlDerivedArtifactType">
 *       &lt;sequence>
 *         &lt;element name="fault" type="{http://s-ramp.org/xmlns/2010/s-ramp}bindingOperationFaultTarget" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="input" type="{http://s-ramp.org/xmlns/2010/s-ramp}bindingOperationInputTarget" minOccurs="0"/>
 *         &lt;element name="output" type="{http://s-ramp.org/xmlns/2010/s-ramp}bindingOperationOutputTarget" minOccurs="0"/>
 *         &lt;element name="operation" type="{http://s-ramp.org/xmlns/2010/s-ramp}operationTarget"/>
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
@XmlType(name = "BindingOperation", propOrder = {
    "fault",
    "input",
    "output",
    "operation"
})
public class BindingOperation
    extends NamedWsdlDerivedArtifactType
    implements Serializable
{

    private static final long serialVersionUID = -8344593260554295066L;
    protected List<BindingOperationFaultTarget> fault;
    protected BindingOperationInputTarget input;
    protected BindingOperationOutputTarget output;
    @XmlElement(required = true)
    protected OperationTarget operation;

    /**
     * Gets the value of the fault property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fault property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFault().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BindingOperationFaultTarget }
     * 
     * 
     */
    public List<BindingOperationFaultTarget> getFault() {
        if (fault == null) {
            fault = new ArrayList<BindingOperationFaultTarget>();
        }
        return this.fault;
    }

    /**
     * Gets the value of the input property.
     * 
     * @return
     *     possible object is
     *     {@link BindingOperationInputTarget }
     *     
     */
    public BindingOperationInputTarget getInput() {
        return input;
    }

    /**
     * Sets the value of the input property.
     * 
     * @param value
     *     allowed object is
     *     {@link BindingOperationInputTarget }
     *     
     */
    public void setInput(BindingOperationInputTarget value) {
        this.input = value;
    }

    /**
     * Gets the value of the output property.
     * 
     * @return
     *     possible object is
     *     {@link BindingOperationOutputTarget }
     *     
     */
    public BindingOperationOutputTarget getOutput() {
        return output;
    }

    /**
     * Sets the value of the output property.
     * 
     * @param value
     *     allowed object is
     *     {@link BindingOperationOutputTarget }
     *     
     */
    public void setOutput(BindingOperationOutputTarget value) {
        this.output = value;
    }

    /**
     * Gets the value of the operation property.
     * 
     * @return
     *     possible object is
     *     {@link OperationTarget }
     *     
     */
    public OperationTarget getOperation() {
        return operation;
    }

    /**
     * Sets the value of the operation property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperationTarget }
     *     
     */
    public void setOperation(OperationTarget value) {
        this.operation = value;
    }

}
