/*
 * Copyright 2012 JBoss Inc
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
package org.overlord.sramp.ui.client.local.pages;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.TransitionAnchor;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.overlord.sramp.ui.client.local.pages.artifacts.ArtifactFilters;
import org.overlord.sramp.ui.client.local.pages.artifacts.ArtifactsTable;
import org.overlord.sramp.ui.client.local.pages.artifacts.ImportArtifactDialog;
import org.overlord.sramp.ui.client.local.services.ArtifactSearchRpcService;
import org.overlord.sramp.ui.client.local.services.rpc.IRpcServiceInvocationHandler;
import org.overlord.sramp.ui.client.local.widgets.bootstrap.Pager;
import org.overlord.sramp.ui.client.local.widgets.common.HtmlSnippet;
import org.overlord.sramp.ui.client.shared.beans.ArtifactFilterBean;
import org.overlord.sramp.ui.client.shared.beans.ArtifactResultSetBean;
import org.overlord.sramp.ui.client.shared.beans.ArtifactSummaryBean;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.TextBox;

/**
 * The default "Artifacts" page.
 *
 * @author eric.wittmann@redhat.com
 */
@Templated("/org/overlord/sramp/ui/client/local/site/artifacts.html#page")
@Page(path="artifacts")
@Dependent
public class ArtifactsPage extends AbstractPage {

    @Inject
    protected ArtifactSearchRpcService searchService;

    // Breadcrumbs
    @Inject @DataField("back-to-dashboard")
    TransitionAnchor<DashboardPage> backToDashboard;

    @Inject @DataField("sramp-filter-sidebar")
    protected ArtifactFilters filtersPanel;
    @Inject @DataField("sramp-search-box")
    protected TextBox searchBox;

    @Inject @DataField("btn-import")
    protected Anchor importDialogButton;
    @Inject
    protected Instance<ImportArtifactDialog> importDialog;
    @Inject @DataField("btn-refresh")
    protected Anchor refreshButton;

    @Inject @DataField("sramp-artifacts-none")
    protected HtmlSnippet noDataMessage;
    @Inject @DataField("sramp-artifacts-searching")
    protected HtmlSnippet searchInProgressMessage;
    @Inject @DataField("sramp-artifacts-table")
    protected ArtifactsTable artifactsTable;

    @Inject @DataField("sramp-artifacts-pager")
    protected Pager pager;
    @DataField("sramp-artifacts-range-1")
    protected SpanElement rangeSpan1 = Document.get().createSpanElement();
    @DataField("sramp-artifacts-total-1")
    protected SpanElement totalSpan1 = Document.get().createSpanElement();
    @DataField("sramp-artifacts-range-2")
    protected SpanElement rangeSpan2 = Document.get().createSpanElement();
    @DataField("sramp-artifacts-total-2")
    protected SpanElement totalSpan2 = Document.get().createSpanElement();

    private int currentPage = 1;

    /**
     * Constructor.
     */
    public ArtifactsPage() {
    }

    /**
     * Called after construction.
     */
    @PostConstruct
    protected void postConstruct() {
        filtersPanel.addValueChangeHandler(new ValueChangeHandler<ArtifactFilterBean>() {
            @Override
            public void onValueChange(ValueChangeEvent<ArtifactFilterBean> event) {
                doArtifactSearch();
            }
        });
        searchBox.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                doArtifactSearch();
            }
        });
        pager.addValueChangeHandler(new ValueChangeHandler<Integer>() {
            @Override
            public void onValueChange(ValueChangeEvent<Integer> event) {
                doArtifactSearch(event.getValue());
            }
        });

        // Hide columns 2-5 when in mobile mode.
        artifactsTable.setColumnClasses(2, "desktop-only");
        artifactsTable.setColumnClasses(3, "desktop-only");
        artifactsTable.setColumnClasses(4, "desktop-only");
        artifactsTable.setColumnClasses(5, "desktop-only");

        this.rangeSpan1.setInnerText("?");
        this.rangeSpan2.setInnerText("?");
        this.totalSpan1.setInnerText("?");
        this.totalSpan2.setInnerText("?");
    }

    /**
     * Event handler that fires when the user clicks the Import Artifacts button.
     * @param event
     */
    @EventHandler("btn-import")
    public void onImportClick(ClickEvent event) {
        importDialog.get().show();
    }

    /**
     * Event handler that fires when the user clicks the refresh button.
     * @param event
     */
    @EventHandler("btn-refresh")
    public void onRefreshClick(ClickEvent event) {
        doArtifactSearch(currentPage);
    }

    /**
     * Kick off an artifact search at this point so that we show some data in the UI.
     *
     * @see org.overlord.sramp.ui.client.local.pages.AbstractPage#onPageShowing()
     */
    @Override
    protected void onPageShowing() {
        // Kick off an artifact search
        doArtifactSearch();
        // Refresh the artifact filters
        filtersPanel.refresh();
    }

    /**
     * Search for artifacts based on the current filter settings and search text.
     */
    protected void doArtifactSearch() {
        doArtifactSearch(1);
    }

    /**
     * Search for artifacts based on the current filter settings and search text.
     * @param page
     */
    protected void doArtifactSearch(int page) {
        onSearchStarting();
        currentPage = page;
        searchService.search(filtersPanel.getValue(), this.searchBox.getValue(), page, new IRpcServiceInvocationHandler<ArtifactResultSetBean>() {
            @Override
            public void onReturn(ArtifactResultSetBean data) {
                updateArtifactTable(data);
                updatePager(data);
            }
            @Override
            public void onError(Throwable error) {
                Window.alert("Error finding artifacts: " + error.getMessage());
                noDataMessage.setVisible(true);
                searchInProgressMessage.setVisible(false);
            }
        });
    }

    /**
     * Called when a new artifact search is kicked off.
     */
    protected void onSearchStarting() {
        this.pager.setVisible(false);
        this.searchInProgressMessage.setVisible(true);
        this.artifactsTable.setVisible(false);
        this.noDataMessage.setVisible(false);
        this.rangeSpan1.setInnerText("?");
        this.rangeSpan2.setInnerText("?");
        this.totalSpan1.setInnerText("?");
        this.totalSpan2.setInnerText("?");
    }

    /**
     * Updates the table of artifacts with the given data.
     * @param data
     */
    protected void updateArtifactTable(ArtifactResultSetBean data) {
        this.artifactsTable.clear();
        this.searchInProgressMessage.setVisible(false);
        if (data.getArtifacts().size() > 0) {
            for (ArtifactSummaryBean artifactSummaryBean : data.getArtifacts()) {
                this.artifactsTable.addRow(artifactSummaryBean);
            }
            this.artifactsTable.setVisible(true);
        } else {
            this.noDataMessage.setVisible(true);
        }
    }

    /**
     * Updates the pager with the given data.
     * @param data
     */
    protected void updatePager(ArtifactResultSetBean data) {
        int numPages = ((int) (data.getTotalResults() / data.getItemsPerPage())) + (data.getTotalResults() % data.getItemsPerPage() == 0 ? 0 : 1);
        int thisPage = (data.getStartIndex() / data.getItemsPerPage()) + 1;
        this.pager.setNumPages(numPages);
        this.pager.setPage(thisPage);
        if (numPages > 1)
            this.pager.setVisible(true);

        int startIndex = data.getStartIndex() + 1;
        int endIndex = startIndex + data.getArtifacts().size() - 1;
        String rangeText = "" + startIndex + "-" + endIndex;
        String totalText = String.valueOf(data.getTotalResults());
        this.rangeSpan1.setInnerText(rangeText);
        this.rangeSpan2.setInnerText(rangeText);
        this.totalSpan1.setInnerText(totalText);
        this.totalSpan2.setInnerText(totalText);
    }

}
