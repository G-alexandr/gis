package net.bsuir.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import org.vaadin.gwtgraphics.client.shape.Rectangle;

public class MouseMove extends GwtEvent<MouseMove.MouseMoveHandler> {

	public static Type<MouseMoveHandler> TYPE = new Type<MouseMoveHandler>();

    private Rectangle rectangle;

	public interface MouseMoveHandler extends EventHandler {
		void onMove(MouseMove event);
	}

	public MouseMove(Rectangle rectangle) {
        this.rectangle=rectangle;
	}

	@Override
	protected void dispatch(MouseMoveHandler handler) {
		handler.onMove(this);
	}

	@Override
	public Type<MouseMoveHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<MouseMoveHandler> getType() {
		return TYPE;
	}

    public Rectangle getRectangle() {
        return rectangle;
    }
}
