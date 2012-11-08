package net.bsuir.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import org.vaadin.gwtgraphics.client.shape.Rectangle;

public class MouseClick extends GwtEvent<MouseClick.MouseClickHandler> {

	public static Type<MouseClickHandler> TYPE = new Type<MouseClickHandler>();

	public interface MouseClickHandler extends EventHandler {
		void onClick(MouseClick event);
	}

    private Rectangle rectangle;
    private int posX;
    private int posY;
    private String algoritm;

	public MouseClick(Rectangle rectangle, int posX, int posY, String  algoritm) {
        this.rectangle=rectangle;
        this.posX=posX;
        this.posY=posY;
        this.algoritm=algoritm;
	}

	@Override
	protected void dispatch(MouseClickHandler handler) {
		handler.onClick(this);
	}

	@Override
	public Type<MouseClickHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<MouseClickHandler> getType() {
		return TYPE;
	}

    public Rectangle getRectangle(){
        return rectangle;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public String getAlgoritm() {
        return algoritm;
    }
}
