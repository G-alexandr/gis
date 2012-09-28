package net.bsuir.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.menu.ColorMenu;
import net.bsuir.client.presenter.DDAAlgoritmPresenter;
import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.shape.Rectangle;

public abstract class AbstractAlgoritmView extends ViewImpl implements DDAAlgoritmPresenter.MyView {


    public interface Binder extends UiBinder<Widget, AbstractAlgoritmView> {    }

    protected final Widget widget;

    @UiField
    protected ContentPanel panel;

    @UiField
    protected ContentPanel settingsPanel;

    @UiField
    protected DrawingArea canvas;

    @UiField
    protected ColorMenu colorPanel;

    @UiField
    protected Button clearButton;

    @UiFactory
    DrawingArea createCanvas(){
        return new DrawingArea(900,900);
    }

    public Widget asWidget() {
        return widget;
    }

    @Inject
    public AbstractAlgoritmView(Binder binder) {
        widget=binder.createAndBindUi(this);
    }


    public DrawingArea getCanvas() {
        return canvas;
    }

    public ColorMenu getColorPanel() {
        return colorPanel;
    }

    public Button getClearButton() {
        return clearButton;
    }
}
