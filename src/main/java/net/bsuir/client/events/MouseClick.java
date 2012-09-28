package net.bsuir.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import org.vaadin.gwtgraphics.client.shape.Rectangle;

public class MouseClick extends GwtEvent<MouseClick.MouseClickHandler> {

	public static Type<MouseClickHandler> TYPE = new Type<MouseClickHandler>();

	public interface MouseClickHandler extends EventHandler {
		void onClick(MouseClick event);
	}

    private Rectangle rectangle;
	public MouseClick(Rectangle rectangle) {
        this.rectangle=rectangle;
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
}
