package net.bsuir.client.presenter;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.sencha.gxt.widget.core.client.menu.ColorMenu;
import com.sencha.gxt.widget.core.client.menu.Item;
import net.bsuir.client.place.NameTokens;
import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.shape.Rectangle;

public class DDAAlgoritmPresenter extends
        Presenter<DDAAlgoritmPresenter.MyView, DDAAlgoritmPresenter.MyProxy> {

    final int PIXEL_HEIGHT = 15;
    final int PIXEL_WIDTH = 15;
    private DrawingArea canvas = getView().getCanvas();

    boolean onMousePush=false;

    public interface MyView extends View {
        DrawingArea getCanvas();
        ColorMenu getColorPanel();
        Button getClearButton();
    }
    @ProxyCodeSplit
    @NameToken(NameTokens.DDL)
    public interface MyProxy extends ProxyPlace<DDAAlgoritmPresenter> {}

    private String color ="#000000";


    @Inject
    public DDAAlgoritmPresenter(final EventBus eventBus, final MyView view,
                                final MyProxy proxy) {
        super(eventBus, view, proxy);
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, LayoutPresenter.SLOT_content, this);
    }

    private void configureCanvas(){

        long maxHeight = canvas.getHeight()/PIXEL_HEIGHT;
        long maxWidth = canvas.getWidth()/PIXEL_WIDTH;

        for (int i=0; i<maxHeight; i++){
            for (int j=0; j<maxWidth; j++){
                Rectangle rect = new Rectangle(i*PIXEL_WIDTH, j*PIXEL_HEIGHT, PIXEL_WIDTH, PIXEL_HEIGHT);
                canvas.add(rect);
                rect.setFillColor("white");
                rect.addClickHandler(new ClickHandler() {
                    public void onClick(ClickEvent event) {
                        Rectangle rect = (Rectangle) event.getSource();
                        rect.setFillColor(color);
                        if(onMousePush)
                            onMousePush=false;
                        else onMousePush = true;
                    }
                });
                rect.addMouseMoveHandler(new MouseMoveHandler() {
                    @Override
                    public void onMouseMove(MouseMoveEvent event) {
                        Rectangle rect = (Rectangle) event.getSource();
                        if(onMousePush)
                             rect.setFillColor(color);
                    }
                });
            }
        }
    }

    @Override
    protected void onBind() {
        super.onBind();

        getView().getColorPanel().getPalette().addSelectionHandler(new SelectionHandler<String>() {
            @Override
            public void onSelection(SelectionEvent<String> event) {
                color = "#" + event.getSelectedItem();
            }
        });

        getView().getClearButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                clear();
            }
        });

        configureCanvas();
    }
    //todo
    public Rectangle getByPos(int x, int y){
        int pixelsInRow = canvas.getHeight()/PIXEL_HEIGHT;
        int pixelsInColumn = canvas.getWidth()/PIXEL_WIDTH;
        int pixelRow = x/PIXEL_HEIGHT;
        int pixelColumn = y/PIXEL_WIDTH;

        int pixelNumber = pixelsInRow*pixelRow + pixelsInColumn*pixelColumn;
        return (Rectangle) canvas.getVectorObject(pixelNumber);
    }

    public void clear(){
        for (int i=0;i<canvas.getVectorObjectCount(); i++)
            ((Rectangle)canvas.getVectorObject(i))
                    .setFillColor("white");
    }
}
