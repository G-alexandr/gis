package net.bsuir.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class ColorChanged extends GwtEvent<ColorChanged.ColorChangedHandler> {

	public static Type<ColorChangedHandler> TYPE = new Type<ColorChangedHandler>();

	public interface ColorChangedHandler extends EventHandler {
		void onChanged(ColorChanged event);
	}

	public ColorChanged() {
	}

	@Override
	protected void dispatch(ColorChangedHandler handler) {
		handler.onChanged(this);
	}

	@Override
	public Type<ColorChangedHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<ColorChangedHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source) {
		source.fireEvent(new ColorChanged());
	}
}
