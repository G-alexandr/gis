package net.bsuir.client.tools;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.sencha.gxt.widget.core.client.tips.ToolTip;
import com.sencha.gxt.widget.core.client.tips.ToolTipConfig;
import net.bsuir.client.events.MouseClick;
import net.bsuir.client.events.MouseMove;
import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.shape.Rectangle;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;

/**
 * Created with IntelliJ IDEA.
 * User: aurim
 * Date: 9/28/12
 * Time: 6:36 PM
 */
public class Canvas extends DrawingArea {
    public Canvas(int width, int height) {
        super(width, height);
        configureCanvas();
    }

    final int PIXEL_HEIGHT = 15;
    final int PIXEL_WIDTH = 15;

    private int max_x;
    private int max_y;

    private String algoritm;

    @Inject
    private EventBus eventBus;

    private void configureCanvas(){

        int maxHeight = getHeight()/PIXEL_HEIGHT;
        int maxWidth = getWidth()/PIXEL_WIDTH;

        max_x=maxHeight;
        max_y=maxWidth;

        for (int i=0; i<maxHeight; i++){
            for (int j=0; j<maxWidth; j++){
                Rectangle rect = new Rectangle(i*PIXEL_WIDTH, j*PIXEL_HEIGHT, PIXEL_WIDTH, PIXEL_HEIGHT);
                add(rect);
                rect.setFillColor("white");
                rect.addClickHandler(new ClickHandler() {
                    public void onClick(ClickEvent event) {
                        Rectangle rect = (Rectangle) event.getSource();
                        mouseClick(event,rect);
                    }
                });
                rect.addMouseMoveHandler(new MouseMoveHandler() {
                    @Override
                    public void onMouseMove(MouseMoveEvent event) {
                        Rectangle rect = (Rectangle) event.getSource();
                        mouseMove(event,rect);
                    }
                });

            }
        }

    }


    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    private void mouseMove(MouseMoveEvent event, Rectangle rect) {
        int x = rect.getX()/PIXEL_HEIGHT;
        int y = rect.getY()/PIXEL_WIDTH;
        eventBus.fireEvent(new MouseMove(rect,x,y,algoritm));
    }

    private void mouseClick(ClickEvent event, Rectangle rect) {

        int x = rect.getX()/PIXEL_HEIGHT;
        int y = rect.getY()/PIXEL_WIDTH;
        eventBus.fireEvent(new MouseClick(rect,x,y,algoritm));
    }

    final public void clear(){
        for (int i=0;i<getVectorObjectCount(); i++)
            ((Rectangle)getVectorObject(i))
                    .setFillColor("white");
    }

    //todo
    final public Rectangle getAbsolutePos(int x, int y) {

        int pixelRow = x*PIXEL_HEIGHT;
        int pixelColumn = y*PIXEL_WIDTH;

        int pixelNumber = max_x*pixelRow + max_y*pixelColumn;
        return (Rectangle)getVectorObject(pixelNumber);
    }

    final public Rectangle getPixelByPos(int x, int y) {

        if(x>=max_x ) x=max_x-1;
        if(y>=max_y) y=max_y-1;
        if(x<=0) x=0;
        if(y<=0) y=0;
        int pixelNumber = 0;
        if(x>=1) {
            pixelNumber = x*max_y + y;
        }
        else{
            pixelNumber=y;
        }
        if(getVectorObjectCount()<pixelNumber) return null;
        return (Rectangle)getVectorObject(pixelNumber);
    }

    public String getAlgoritm() {
        return algoritm;
    }

    public void setAlgoritm(String algoritm) {
        this.algoritm = algoritm;
    }

    void drawPoint(int x, int y, String currentColor){
        Rectangle pixel = getPixelByPos(x, y);
        if(pixel!=null)
            pixel.setFillColor(currentColor);
        else return;

    }

    public int getMax_x() {
        return max_x;
    }

    public int getMax_y() {
        return max_y;
    }
}
