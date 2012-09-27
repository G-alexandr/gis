package net.bsuir.client.view;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.ContentPanel.ContentPanelAppearance;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer;
import net.bsuir.client.presenter.AccordinMenuPresenter;

public class AccordinMenuView extends ViewImpl implements AccordinMenuPresenter.MyView {


    public interface Binder extends UiBinder<Widget, AccordinMenuView> {    }

    @UiField
    AccordionLayoutContainer con;

    private final Widget widget;

    public Widget asWidget() {
        return widget;
    }

    @Inject
    public AccordinMenuView(Binder binder) {
        widget=binder.createAndBindUi(this);
    }

    @UiFactory
    public ContentPanel createContentPanel(ContentPanelAppearance appearance) {
        return new ContentPanel(appearance);
    }

    public AccordionLayoutContainer getContainer() {
        return con;
    }

    @Override
    public void addToSlot(Object slot, Widget content) {
        super.addToSlot(slot, content);
    }
}