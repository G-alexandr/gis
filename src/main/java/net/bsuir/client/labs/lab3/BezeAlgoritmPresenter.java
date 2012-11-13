package net.bsuir.client.labs.lab3;

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
import net.bsuir.client.events.MouseMove;
import net.bsuir.client.place.NameTokens;
import net.bsuir.client.presenter.LayoutPresenter;
import net.bsuir.client.tools.Canvas;
import org.vaadin.gwtgraphics.client.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class BezeAlgoritmPresenter extends
		Presenter<BezeAlgoritmPresenter.MyView, BezeAlgoritmPresenter.MyProxy>{

    private String currentColor = "#000000";

    boolean drawFlag = false;

    private int clicks =0;

    List<Point> old_points;

    private final  static int N=10;
    private final  static int treshold = 1;

    public interface MyView extends View {
        Canvas getCanvas();
        String getColor();
        TextArea getLoger();
    }

    private int offset_x , offset_y;

    private Point point_1;
    private Point point_2;

    private Point r1;
    private Point r2;


    @ProxyCodeSplit
	@NameToken(NameTokens.BEZE)
	public interface MyProxy extends ProxyPlace<BezeAlgoritmPresenter> {}

	@Inject
	public BezeAlgoritmPresenter(final EventBus eventBus, final MyView view,
                                 final MyProxy proxy) {
		super(eventBus, view, proxy);
        offset_x = 0;//getView().getCanvas().getMax_x()/2;
        offset_y = 0;//getView().getCanvas().getMax_y()/2;
	}

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
//                event.getRectangle().setFillColor(currentColor);
               if (clicks >= 4){
                   clicks=0;point_1=null;point_2=null; r1=null; r2=null; old_points=null;
                   log("=================");
               }
               if(point_1 == null ){
                   point_1=new Point(event.getPosX(),event.getPosY());
                   getView().getCanvas().getPixelByPos(event.getPosX(),event.getPosY()).setFillColor("red");
                   log("point 1: "+event.getPosX()+" | "+event.getPosY());
               }
               else if (point_2 == null){
                   point_2=new Point(event.getPosX(),event.getPosY());
                   getView().getCanvas().getPixelByPos(event.getPosX(),event.getPosY()).setFillColor("red");
                   log("point 2: "+event.getPosX()+" | "+event.getPosY());
               }
               else if (r1 == null){

                   r1 = new Point((event.getPosX()-offset_x)*treshold,(event.getPosY()-offset_y)*treshold);
                   log("point 3: "+(event.getPosX()-offset_x)*treshold+" | "+(event.getPosY()-offset_y)*treshold);
               }
               else if (r2 == null){
                   r2 = new Point((event.getPosX()-20)*treshold,(event.getPosY()-20)*treshold);
                   log("point 4: "+event.getPosX()*treshold+" | "+event.getPosY()*treshold);
               }

                clicks++;
//                Info.display("POINT",event.getPosX()+"\n"+event.getPosY());

            }
        }));

        registerHandler(getEventBus().addHandler(MouseMove.getType(),new MouseMove.MouseMoveHandler() {
            @Override
            public void onMove(MouseMove event) {
                if(getView().getCanvas().getAlgoritm() != event.getAlgoritm() ) return;

                if(clicks < 2 || clicks>=4) return;
                else  {
                    removeOld();
                    old_points=algoritm(point_1, point_2,
                                        r1==null ? new Point(event.getPosX()*treshold,event.getPosY()*treshold) : r1,
                                        r2==null ? new Point(event.getPosX()*treshold,event.getPosY()*treshold) : r2);
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
        if(pixel!=null)
            pixel.setFillColor(currentColor);
        else return;
    }
     void removeOld(){
         if(old_points!= null){
             Canvas canvas = getView().getCanvas();
             for (Point p: old_points)
                 canvas.getPixelByPos(p.X,p.Y).setFillColor("white");
         }
     }

    void log(String message){
         getView().getLoger().setText( getView().getLoger().getText()+"\n"+message);
    }
}
