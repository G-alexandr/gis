package net.bsuir.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import org.vaadin.gwtgraphics.client.shape.Rectangle;

public class MouseDoubleClick extends GwtEvent<MouseDoubleClick.MouseDoubleClickHandler> {

	public static Type<MouseDoubleClickHandler> TYPE = new Type<MouseDoubleClickHandler>();

    private Rectangle rectangle;
    private int posX;
    private int posY;
    private String algoritm;

    public interface MouseDoubleClickHandler extends EventHandler {
		void onDoubleClick(MouseDoubleClick event);
	}

	public MouseDoubleClick(Rectangle rectangle, int posX, int posY, String algoritm) {
        this.rectangle=rectangle;
        this.posX=posX;
        this.posY=posY;
        this.algoritm = algoritm;
	}

	@Override
	protected void dispatch(MouseDoubleClickHandler handler) {
		handler.onDoubleClick(this);
	}

	@Override
	public Type<MouseDoubleClickHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<MouseDoubleClickHandler> getType() {
		return TYPE;
	}

    public Rectangle getRectangle() {
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
