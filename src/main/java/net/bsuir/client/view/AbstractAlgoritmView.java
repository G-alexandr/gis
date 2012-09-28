package net.bsuir.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.ViewImpl;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.menu.ColorMenu;
import net.bsuir.client.events.ColorChanged;
import net.bsuir.client.tools.Canvas;
import org.vaadin.gwtgraphics.client.DrawingArea;

public abstract class AbstractAlgoritmView extends ViewImpl {


    public interface Binder extends UiBinder<Widget, AbstractAlgoritmView> {    }

    protected final Widget widget;

    protected String color ="#000000";

    @Inject
    EventBus eventBus;

    @UiField
    protected ContentPanel panel;

    @UiField
    protected ContentPanel settingsPanel;

    protected Canvas canvas;

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
        canvas=new Canvas(1000,900);
        panel.add(canvas);
        configure();
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

    public String getColor() {
        return color;
    }
    void configure(){
        getColorPanel().getPalette().addSelectionHandler(new SelectionHandler<String>() {
            @Override
            public void onSelection(SelectionEvent<String> event) {
                color = "#" + event.getSelectedItem();
                eventBus.fireEvent(new ColorChanged());
            }
        });

        getClearButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                canvas.clear();
            }
        });
    }
}
