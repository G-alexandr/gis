package net.bsuir.client.labs.lab2;

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

import java.util.ArrayList;

public class CircleAlgoritmPresenter extends
		Presenter<CircleAlgoritmPresenter.MyView, CircleAlgoritmPresenter.MyProxy> {

    @ProxyCodeSplit
    @NameToken(NameTokens.CIRCLE)
    public interface MyProxy extends ProxyPlace<CircleAlgoritmPresenter> {}

    public interface MyView extends View {
        Canvas getCanvas();
        String getColor();
        Button getClearButton();
    }

    private String currentColor  = "#000000";
    boolean mousePressed = false;
    private int x1, y1;
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

	@Inject
	public CircleAlgoritmPresenter(final EventBus eventBus, final MyView view,
                                   final MyProxy proxy) {
		super(eventBus, view, proxy);
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, LayoutPresenter.SLOT_content, this);
	}
    @Override
    protected void onHide() {
        super.onHide();
        mousePressed=false;
    }

    @Override
	protected void onBind() {
		super.onBind();
        getView().getCanvas().setEventBus(getEventBus());

        registerHandler(getEventBus().addHandler(MouseClick.getType(), new MouseClick.MouseClickHandler() {
            @Override
            public void onClick(MouseClick event) {
                if(getView().getCanvas().getAlgoritm() != event.getAlgoritm()) return;
                if(mousePressed){
                    mousePressed=false;
                    magic.commit();
                }
                else mousePressed = true;

                x1=event.getPosX();
                y1=event.getPosY();
                magic.addToDraw(getView().getCanvas().getNumber(x1,y1),"red");
                getView().getCanvas().getPixelByPos(x1,y1).setFillColor("red");
            }
        }));

        registerHandler(getEventBus().addHandler(MouseMove.getType(),new MouseMove.MouseMoveHandler() {
            @Override
            public void onMove(MouseMove event) {
                if(getView().getCanvas().getAlgoritm() != event.getAlgoritm()) return;
                if(mousePressed){
                    magic.rollback();
                    circle_a(x1, y1, event.getPosX(), event.getPosY());
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


    private void circle_a(int x, int y, int x0, int y0) {
        int r = (int) Math.round(Math.sqrt(Math.pow(x0 - x1, 2) + Math.pow(y0 - y1, 2)));
        int sx=0;
        int sy=r;
        int d=3-2*r;
        while(sx<=sy) {
            
            drawPoint(x + sx, y - sy);
            drawPoint(x + sx, y + sy);
            drawPoint(x - sx, y - sy);
            drawPoint(x - sx, y + sy);

            drawPoint(x + sy, y + sx);
            drawPoint(x - sy, y + sx);
            drawPoint(x + sy, y - sx);
            drawPoint(x - sy, y - sx);

            if (d<0) {
                d = d + 4 * sx + 6;
            } else {
                d = d + 4 * (sx - sy) + 10;
                sy = sy - 1;
            }
            sx += 1;
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
