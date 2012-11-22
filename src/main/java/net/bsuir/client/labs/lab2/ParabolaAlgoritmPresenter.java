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

public class ParabolaAlgoritmPresenter extends
		Presenter<ParabolaAlgoritmPresenter.MyView, ParabolaAlgoritmPresenter.MyProxy> {

    @ProxyCodeSplit
    @NameToken(NameTokens.PARABOLA)
    public interface MyProxy extends ProxyPlace<ParabolaAlgoritmPresenter> {}

    public interface MyView extends View {
        Canvas getCanvas();
        String getColor();
        Button getClearButton();
    }

    @Inject
    public ParabolaAlgoritmPresenter(final EventBus eventBus, final MyView view,
                                     final MyProxy proxy) {
        super(eventBus, view, proxy);
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
                event.getRectangle().setFillColor(currentColor);
                if(mousePressed){
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
                    magic.rollback();
                    algoritm(x1,y1,event.getPosX(),event.getPosY());
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
    void drawPoint(int x, int y){
        Rectangle pixel = getView().getCanvas().getPixelByPos(x, y);
        if(pixel!=null) {
            pixel.setFillColor(currentColor);
            magic.add(getView().getCanvas().getNumber(x,y),currentColor);
        }
        else return;
    }
    public void  algoritm(int x1, int y1, int x2, int y2) {
        int Sh, Sv, Sd;
        int y = 0;
        int x = 0;
        int p=(int) (Math.pow(y2,2)/(2*x2));
        Sd = ((y + 1) * (y + 1) )- 2 * p * (x + 1);
        Sv = ((y + 1)*(y + 1)) - 2 * p * x;
        Sh = (y*y) - 2 * p * (x + 1);

        drawPoint(x1 + x, y1 + y);
//        line.add(new Pixel(p1.x + x, p1.y + y, color));

        while (x < 200) //пока полотно не кончится
        {
            if (Math.abs(Sh) - Math.abs(Sv) <= 0)
            {
                if (Math.abs(Sd) - Math.abs(Sh) < 0) {
                    y++;
                }
                x++;
            }
            else
            {
                if (Math.abs(Sv) - Math.abs(Sd) > 0) {
                    x++;
                }
                y++;

            }
            drawPoint(x1 - y, y1 -x );
            drawPoint(x1 + y, y1 -x );


            Sd = ((y + 1) * (y + 1)) - 2 * p * (x + 1);
            Sv = ((y + 1) * (y + 1)) - 2 * p * x;
            Sh = (y * y) - 2 * p * (x + 1);
        }
    }

}
