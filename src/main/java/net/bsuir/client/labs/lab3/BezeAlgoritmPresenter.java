package net.bsuir.client.labs.lab3;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextArea;
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
import net.bsuir.client.events.MouseDoubleClick;
import net.bsuir.client.events.MouseMove;
import net.bsuir.client.place.NameTokens;
import net.bsuir.client.presenter.LayoutPresenter;
import net.bsuir.client.tools.Canvas;
import net.bsuir.client.utils.CanvasLayersMagic;
import org.vaadin.gwtgraphics.client.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class BezeAlgoritmPresenter extends
		Presenter<BezeAlgoritmPresenter.MyView, BezeAlgoritmPresenter.MyProxy>{


    public interface MyView extends View {
        Canvas getCanvas();
        String getColor();
        TextArea getLoger();
        Button getClearButton();
    }
    @ProxyCodeSplit
    @NameToken(NameTokens.BEZE)
    public interface MyProxy extends ProxyPlace<BezeAlgoritmPresenter> {}

    @Inject
    public BezeAlgoritmPresenter(final EventBus eventBus, final MyView view,
                                 final MyProxy proxy) {
        super(eventBus, view, proxy);
        log("Double click to end line");

    }

    private String currentColor = "#000000";
    boolean drawFlag = false;
    private int clicks =0;
    List<Point> old_points;

    private final  static int N=10;
    private final  static int treshold = 1;

    private Point last_3, last_4;

    List<Point> pointList = new ArrayList<Point>(4);

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
	protected void onBind() {
		super.onBind();
        getView().getCanvas().setEventBus(getEventBus());

        registerHandler(getEventBus().addHandler(MouseClick.getType(), new MouseClick.MouseClickHandler() {
            @Override
            public void onClick(MouseClick event) {
                if(getView().getCanvas().getAlgoritm() != event.getAlgoritm()) return;

                if (pointList.size()<4){
                    Point p =new Point(event.getPosX(),event.getPosY());
                    pointList.add(p);
                    drawReferencePoint(p.X,p.Y);
                    log("point "+(pointList.size())+" "+p.X+" | "+p.Y);
                }
                else{
                    magic.commit();
                    last_3 = pointList.get(3);
                    pointList.clear();
                    pointList.add(last_3);
                    Point p =new Point(event.getPosX(),event.getPosY());
                    pointList.add(p);
                    drawReferencePoint(p.X,p.Y);
                    log("point "+(pointList.size())+" "+p.X+" | "+p.Y);
                }

            }
        }));

        registerHandler(getEventBus().addHandler(MouseMove.getType(),new MouseMove.MouseMoveHandler() {
            @Override
            public void onMove(MouseMove event) {
                if(getView().getCanvas().getAlgoritm() != event.getAlgoritm() ) return;

                if(pointList.size() < 2 || pointList.size()>4){
                   return;
                }
                else  {
                    magic.rollback();
                    algoritm(pointList.get(0), pointList.get(1),
                            pointList.size()<3 ? new Point(event.getPosX()*treshold,event.getPosY()*treshold) : pointList.get(2),
                            pointList.size()<4 ? new Point(event.getPosX()*treshold,event.getPosY()*treshold) : pointList.get(3));
                }

            }

        }));

        registerHandler(getEventBus().addHandler(MouseDoubleClick.getType(), new MouseDoubleClick.MouseDoubleClickHandler(){

            @Override
            public void onDoubleClick(MouseDoubleClick event) {
               magic.commit();
               pointList.clear();
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
                magic.rollback();
            }
        });
	}
    public List<Point> algoritm(Point p1, Point p2, Point r1, Point r2){
        List<Point> result= new ArrayList<Point>();
        int x, y;

        for (double i = 0.0; i < 1; i=i+0.005){
           x = (int) Math.round(p1.X*Math.pow(1-i,3)+p2.X*3*i*Math.pow(i-1,2)+3*i*i*(1-i)*r1.X+r2.X*i*i*i);
           y = (int) Math.round(p1.Y*Math.pow(1-i,3)+p2.Y*3*i*Math.pow(i-1,2)+3*i*i*(1-i)*r1.Y+r2.Y*i*i*i);
            drawPoint(x,y);
            result.add(new Point(x,y));
        }
        return result;
    }

    void drawPoint(int x, int y){
        Rectangle pixel = getView().getCanvas().getPixelByPos(x, y);
        if(pixel!=null) {
            pixel.setFillColor(currentColor);
            magic.add(getView().getCanvas().getNumber(x,y),currentColor);
        }
        else return;
    }

    void drawReferencePoint(int x, int y){
        getView().getCanvas().getPixelByPos(x,y).setFillColor("red");
        magic.addToDraw(getView().getCanvas().getNumber(x, y),"red");
    }

    void log(String message){
         getView().getLoger().setText(getView().getLoger().getText()+"\n"+message);
    }
}
