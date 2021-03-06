/*
 * Copyright 2013 JBoss Inc
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
package org.overlord.sramp.ui.server.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.overlord.sramp.atom.mappers.OntologyToRdfMapper;
import org.overlord.sramp.atom.mappers.RdfToOntologyMapper;
import org.overlord.sramp.client.ontology.OntologySummary;
import org.overlord.sramp.common.ontology.SrampOntology;
import org.overlord.sramp.common.ontology.SrampOntology.SrampOntologyClass;
import org.overlord.sramp.ui.client.shared.beans.OntologyBean;
import org.overlord.sramp.ui.client.shared.beans.OntologyClassBean;
import org.overlord.sramp.ui.client.shared.beans.OntologyResultSetBean;
import org.overlord.sramp.ui.client.shared.beans.OntologySummaryBean;
import org.overlord.sramp.ui.client.shared.exceptions.SrampUiException;
import org.overlord.sramp.ui.client.shared.services.IOntologyService;
import org.overlord.sramp.ui.server.api.SrampApiClientAccessor;
import org.w3._1999._02._22_rdf_syntax_ns_.RDF;

/**
 * Concrete implementation of the ontology service.
 *
 * @author eric.wittmann@redhat.com
 */
@ApplicationScoped
public class OntologyService implements IOntologyService {

    private static OntologyToRdfMapper o2rdf = new OntologyToRdfMapper();

    /**
     * Constructor.
     */
    public OntologyService() {
    }

    /**
     * @see org.overlord.sramp.ui.client.shared.services.IOntologyService#get(java.lang.String)
     */
    @Override
    public OntologyBean get(String uuid) throws SrampUiException {
        try {
            RDF rdf = SrampApiClientAccessor.getClient().getOntology(uuid);
            SrampOntology ontology = RdfToOntologyMapper.rdf2ontology(rdf);
            OntologyBean bean = ontologyToBean(ontology);
            return bean;
        } catch (Exception e) {
            throw new SrampUiException(e.getMessage());
        }
    }

    /**
     * @see org.overlord.sramp.ui.client.shared.services.IOntologyService#list()
     */
    @Override
    public OntologyResultSetBean list() throws SrampUiException {
        try {
            OntologyResultSetBean rval = new OntologyResultSetBean();
            List<OntologySummaryBean> ontologyBeans = new ArrayList<OntologySummaryBean>();
            List<OntologySummary> ontologies = SrampApiClientAccessor.getClient().getOntologies();
            for (OntologySummary ontologySummary : ontologies) {
                ontologyBeans.add(ontologySummaryToBean(ontologySummary));
            }
            rval.setOntologies(ontologyBeans);
            return rval;
        } catch (Exception e) {
            throw new SrampUiException(e.getMessage());
        }
    }
    
    /**
     * @see org.overlord.sramp.ui.client.shared.services.IOntologyService#add(org.overlord.sramp.ui.client.shared.beans.OntologyBean)
     */
    @Override
    public void add(OntologyBean ontology) throws SrampUiException {
        try {
            RDF rdf = ontologyBeanToRDF(ontology);
            SrampApiClientAccessor.getClient().addOntology(rdf);
        } catch (Exception e) {
            throw new SrampUiException(e.getMessage());
        }
    }

    /**
     * @see org.overlord.sramp.ui.client.shared.services.IOntologyService#update(org.overlord.sramp.ui.client.shared.beans.OntologyBean)
     */
    @Override
    public void update(OntologyBean ontology) throws SrampUiException {
        try {
            RDF rdf = ontologyBeanToRDF(ontology);
            SrampApiClientAccessor.getClient().updateOntology(ontology.getUuid(), rdf);
        } catch (Exception e) {
            throw new SrampUiException(e.getMessage());
        }
    }

    /**
     * @see org.overlord.sramp.ui.client.shared.services.IOntologyService#delete(String)
     */
    @Override
    public void delete(String uuid) throws SrampUiException {
        try {
            SrampApiClientAccessor.getClient().deleteOntology(uuid);
        } catch (Exception e) {
            throw new SrampUiException(e.getMessage());
        }
    }

    /**
     * Converts an ontology into an {@link OntologyBean}.
     * @param ontology
     */
    private OntologyBean ontologyToBean(SrampOntology ontology) {
        OntologyBean bean = new OntologyBean();
        bean.setLastModifiedBy(ontology.getLastModifiedBy());
        bean.setBase(ontology.getBase());
        bean.setComment(ontology.getComment());
        bean.setCreatedBy(ontology.getCreatedBy());
        bean.setCreatedOn(ontology.getCreatedOn());
        bean.setId(ontology.getId());
        bean.setLabel(ontology.getLabel());
        bean.setLastModifiedBy(ontology.getLastModifiedBy());
        bean.setLastModifiedOn(ontology.getLastModifiedOn());
        bean.setUuid(ontology.getUuid());
        List<SrampOntologyClass> allClasses = ontology.getAllClasses();
        
        // Create and index all the classes first
        Map<String, OntologyClassBean> classIndexById = new HashMap<String, OntologyClassBean>();
        for (SrampOntologyClass cl4ss : allClasses) {
            OntologyClassBean classBean = bean.createClass(cl4ss.getId());
            classIndexById.put(cl4ss.getId(), classBean);
            classBean.setComment(cl4ss.getComment());
            classBean.setLabel(cl4ss.getLabel());
        }
        // Then go back through and set up the tree.
        for (SrampOntologyClass cl4ss : allClasses) {
            OntologyClassBean classBean = classIndexById.get(cl4ss.getId());
            if (cl4ss.getParent() != null) {
                OntologyClassBean parentBean = classIndexById.get(cl4ss.getParent().getId());
                if (parentBean != null) {
                    parentBean.getChildren().add(classBean);
                }
            } else {
                bean.getRootClasses().add(classBean);
            }
        }
        return bean;
    }

    /**
     * Converts an ontology summary to an {@link OntologySummaryBean}.
     * @param ontologySummary
     */
    protected OntologySummaryBean ontologySummaryToBean(OntologySummary ontologySummary) {
        OntologySummaryBean bean = new OntologySummaryBean();
        bean.setBase(ontologySummary.getBase());
        bean.setComment(ontologySummary.getComment());
        bean.setCreatedBy(ontologySummary.getCreatedBy());
        bean.setCreatedOn(ontologySummary.getCreatedTimestamp());
        bean.setId(ontologySummary.getId());
        bean.setLabel(ontologySummary.getLabel());
        bean.setLastModifiedOn(ontologySummary.getLastModifiedTimestamp());
        bean.setUuid(ontologySummary.getUuid());
        return bean;
    }

    /**
     * Converts an ontology bean into an RDF.
     * @param ontology
     */
    private RDF ontologyBeanToRDF(OntologyBean ontology) {
        SrampOntology sontology = new SrampOntology();
        sontology.setBase(ontology.getBase());
        sontology.setId(ontology.getId());
        sontology.setLabel(ontology.getLabel());
        sontology.setComment(ontology.getComment());
        sontology.setUuid(ontology.getUuid());
        
        List<SrampOntologyClass> srootClasses = new ArrayList<SrampOntologyClass>();
        for (OntologyClassBean ontologyClass : ontology.getRootClasses()) {
            SrampOntologyClass c = sontology.createClass(ontologyClass.getId());
            copyOntologyClass(sontology, ontologyClass, c);
            srootClasses.add(c);
        }
        sontology.setRootClasses(srootClasses);
        
        RDF rdf = new RDF();
        o2rdf.map(sontology, rdf);
        return rdf;
    }

    /**
     * Copies the ontology class.
     * @param sontology 
     * @param from
     * @param to
     */
    private void copyOntologyClass(SrampOntology sontology, OntologyClassBean from, SrampOntologyClass to) {
        to.setComment(from.getComment());
        to.setLabel(from.getLabel());
        
        List<SrampOntologyClass> schildren = new ArrayList<SrampOntologyClass>();
        for (OntologyClassBean child : from.getChildren()) {
            SrampOntologyClass c = sontology.createClass(child.getId());
            copyOntologyClass(sontology, child, c);
            c.setParent(to);
            schildren.add(c);
        }
        to.setChildren(schildren);

    }
    
}
