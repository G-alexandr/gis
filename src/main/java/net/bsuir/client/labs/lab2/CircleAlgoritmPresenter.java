package net.bsuir.client.labs.lab2;

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
import org.vaadin.gwtgraphics.client.shape.Rectangle;

import java.util.ArrayList;

public class CircleAlgoritmPresenter extends
		Presenter<CircleAlgoritmPresenter.MyView, CircleAlgoritmPresenter.MyProxy> {

    private String currentColor  = "#000000";

    boolean mousePressed = false;
    public interface MyView extends View {
        Canvas getCanvas();
        String getColor();
    }

    private int x1, y1;
//   private float[] old_x, old_y;
    private ArrayList<Integer> old_x = new ArrayList<Integer>(100);
    private ArrayList<Integer> old_y = new ArrayList<Integer>(100);

    @ProxyCodeSplit
	@NameToken(NameTokens.CIRCLE)
	public interface MyProxy extends ProxyPlace<CircleAlgoritmPresenter> {}

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
                event.getRectangle().setFillColor(currentColor);
                if(mousePressed) mousePressed=false;
                else mousePressed = true;
                x1=event.getPosX();
                y1=event.getPosY();
                old_x.clear();
                old_y.clear();
            }
        }));

        registerHandler(getEventBus().addHandler(MouseMove.getType(),new MouseMove.MouseMoveHandler() {
            @Override
            public void onMove(MouseMove event) {
                if(getView().getCanvas().getAlgoritm() != event.getAlgoritm()) return;
                if(mousePressed){
//                    event.getRectangle().setFillColor(currentColor);

                    if(old_x!=null || old_y!=null){
                        for (int i=0;i<old_x.size();i++){
                            getView().getCanvas().getPixelByPos(Math.round(old_x.get(i)), Math.round(old_y.get(i))).setFillColor("#FFFFFF");
                        }
                    }
                    old_x.clear();
                    old_y.clear();
                    int radius = (int) Math.round(Math.sqrt(Math.pow(event.getPosX() - x1, 2) + Math.pow(event.getPosY() - y1, 2)));
                    circle_a(x1, y1, radius);
                }
            }
        }));


        registerHandler(getEventBus().addHandler(ColorChanged.getType(),new ColorChanged.ColorChangedHandler() {
            @Override
            public void onChanged(ColorChanged event) {
                currentColor = getView().getColor();
            }
        }));
	}

    void circle(int x, int y, int r)
    {
        int x1,y1,yk = 0;
        int sigma,delta,f;

        x1 = 0;
        y1 = r;
        delta = 2*(1-r);

        do
        {
            getView().getCanvas().getPixelByPos(x+x1,y+y1).setFillColor(currentColor);
            getView().getCanvas().getPixelByPos(x-x1,y+y1).setFillColor(currentColor);
            getView().getCanvas().getPixelByPos(x+x1,y-y1).setFillColor(currentColor);
            getView().getCanvas().getPixelByPos(x-x1,y-y1).setFillColor(currentColor);

            f = 0;
            if (y1 < yk)
                break;
            if (delta < 0)
            {
                sigma = 2*(delta+y1)-1;
                if (sigma <= 0)
                {
                    x1++;
                    delta += 2*x1+1;
                    f = 1;
                }
            }
            else
            if (delta > 0)
            {
                sigma = 2*(delta-x1)-1;
                if (sigma > 0)
                {
                    y1--;
                    delta += 1-2*y1;
                    f = 1;
                }
            }
            if (!(f>0))
            {
                x1++;
                y1--;
                delta += 2*(x1-y1-1);
            }
        }
        while(true);
    }
    private void circle_a(int x, int y, int r) {
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
        if(pixel!=null)
            pixel.setFillColor(currentColor);
        else return;
        old_x.add(x);
        old_y.add(y);
    }
}
