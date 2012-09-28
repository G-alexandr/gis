package net.bsuir.client.tools;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.web.bindery.event.shared.EventBus;
import net.bsuir.client.events.MouseClick;
import net.bsuir.client.events.MouseMove;
import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.shape.Rectangle;

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

    private EventBus eventBus;

    private void configureCanvas(){

        long maxHeight = getHeight()/PIXEL_HEIGHT;
        long maxWidth = getWidth()/PIXEL_WIDTH;

        for (int i=0; i<maxHeight; i++){
            for (int j=0; j<maxWidth; j++){
                Rectangle rect = new Rectangle(i*PIXEL_WIDTH, j*PIXEL_HEIGHT, PIXEL_WIDTH, PIXEL_HEIGHT);
                add(rect);
                rect.setFillColor("white");
                rect.addClickHandler(new ClickHandler() {
                    public void onClick(ClickEvent event) {
                        Rectangle rect = (Rectangle) event.getSource();
//                        rect.setFillColor(color);
//                        if(onMousePush)
//                            onMousePush=false;
//                        else onMousePush = true;
                        mouseClick(event,rect);
                    }
                });
                rect.addMouseMoveHandler(new MouseMoveHandler() {
                    @Override
                    public void onMouseMove(MouseMoveEvent event) {
                        Rectangle rect = (Rectangle) event.getSource();
//                        if(onMousePush)
//                             rect.setFillColor(color);
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
        eventBus.fireEvent(new MouseMove(rect));
    }

    private void mouseClick(ClickEvent event, Rectangle rect) {

        eventBus.fireEvent(new MouseClick(rect));
    }

    final public void clear(){
        for (int i=0;i<getVectorObjectCount(); i++)
            ((Rectangle)getVectorObject(i))
                    .setFillColor("white");
    }

    //todo
    final public Rectangle getByPos(int x, int y){
        int pixelsInRow = getHeight()/PIXEL_HEIGHT;
        int pixelsInColumn = getWidth()/PIXEL_WIDTH;
        int pixelRow = x/PIXEL_HEIGHT;
        int pixelColumn = y/PIXEL_WIDTH;

        int pixelNumber = pixelsInRow*pixelRow + pixelsInColumn*pixelColumn;
        return (Rectangle)getVectorObject(pixelNumber);
    }

}
