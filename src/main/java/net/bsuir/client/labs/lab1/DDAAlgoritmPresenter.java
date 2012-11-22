package net.bsuir.client.labs.lab1;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import net.bsuir.client.events.ColorChanged;
import net.bsuir.client.events.MouseClick;
import net.bsuir.client.events.MouseMove;
import net.bsuir.client.place.NameTokens;
import net.bsuir.client.presenter.LayoutPresenter;
import net.bsuir.client.tools.Canvas;
import net.bsuir.client.utils.CanvasLayersMagic;
import org.vaadin.gwtgraphics.client.shape.Rectangle;

public class DDAAlgoritmPresenter extends
		Presenter<DDAAlgoritmPresenter.MyView, DDAAlgoritmPresenter.MyProxy> {

    public interface MyView extends View {
        Canvas getCanvas();
        String getColor();
        Button getClearButton();
    }
    @ProxyCodeSplit
    @NameToken(NameTokens.DDA)
    public interface MyProxy extends ProxyPlace<DDAAlgoritmPresenter> {}
    //----------------------------------------------
    @Inject
    public DDAAlgoritmPresenter(final EventBus eventBus, final MyView view,
                                final MyProxy proxy) {
        super(eventBus, view, proxy);
    }
    //----------------------------------------------
    private String currentColor  = "#000000";
    private int x1, y1;
    boolean mousePressed = false;
    //----------------------------------------------
    private CanvasLayersMagic magic = new CanvasLayersMagic() {

        @Override
        public void rollback() {
            for (Integer key: getTransactionMap().keySet()){
                if(getDrawPoints().containsKey(key)){
                    String old_color = getDrawPoints().get(key);
                    ((Rectangle)getView().getCanvas()
                            .getVectorObject(key))
                            .setFillColor(old_color);
                }
                else {
                    ((Rectangle)getView().getCanvas()
                            .getVectorObject(key))
                            .setFillColor("#FFFFFF");
                }
            }
            getTransactionMap().clear();
        }
    };
	//=================================================

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, LayoutPresenter.SLOT_content, this);
	}

	@Override
	protected void onBind() {
		super.onBind();
        getView().getCanvas().setEventBus(getEventBus());

        registerHandler(getEventBus().addHandler(MouseClick.getType(), new MouseClick.MouseClickHandler() {
            @Override
            public void onClick(MouseClick event) {
                if(getView().getCanvas().getAlgoritm() != event.getAlgoritm()) return;
                event.getRectangle().setFillColor(currentColor);
                if(mousePressed) {
                    mousePressed=false;
                    magic.commit();
                }
                else mousePressed = true;
                x1=event.getPosX();
                y1=event.getPosY();
            }
        }));

        registerHandler(getEventBus().addHandler(MouseMove.getType(),new MouseMove.MouseMoveHandler() {
            @Override
            public void onMove(MouseMove event) {
                if(getView().getCanvas().getAlgoritm() != event.getAlgoritm()) return;
                if(mousePressed){
                    event.getRectangle().setFillColor(currentColor);
                    magic.rollback();
                    dda_line(x1, y1, event.getPosX(), event.getPosY());
                }
            }
        }));


        registerHandler(getEventBus().addHandler(ColorChanged.getType(),new ColorChanged.ColorChangedHandler() {
            @Override
            public void onChanged(ColorChanged event) {
                currentColor = getView().getColor();
            }
        }));

        getView().getClearButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                magic.clearDrawPoints();
            }
        });
	}
//
//    @Override
//    protected void onReveal() {
//        super.onReveal();
//        getView().getCanvas().clear();
//    }

    void dda_line (float x1, float y1, float x2, float y2)
    {
        int i, L, xstart, ystart, xend, yend;
        float dX, dY;
        float x[] = new float[100], y[] = new float[100];
        xstart = Math.round(x1);
        ystart = Math.round(y1);
        xend = Math.round(x2);
        yend = Math.round(y2);
        L = Math.max(Math.abs(xend - xstart), Math.abs(yend - ystart));
        if(L==0) return;
        dX = (x2-x1) / L;
        dY = (y2-y1) / L;
        i = 0;
        x[i] = x1;
        y[i] = y1;
        i++;
        while (i < L)
        {
            x[i] = x[i-1] + dX;
            y[i] = y[i-1] + dY;
            i++;
        }
        x[i] = x2;
        y[i] = y2;

        i = 0;
        while (i <= L)
        {
            drawPoint(Math.round(x[i]), Math.round(y[i]));
            i++;
        }
    }

    void drawPoint(int x, int y){
        Rectangle pixel = getView().getCanvas().getPixelByPos(x, y);
        if(pixel!=null) {
            pixel.setFillColor(currentColor);
            magic.add(getView().getCanvas().getNumber(x,y),currentColor);
        }
        else return;
    }
}
