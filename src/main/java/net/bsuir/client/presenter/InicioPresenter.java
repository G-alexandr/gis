package net.bsuir.client.presenter;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.box.AutoProgressMessageBox;
import com.sencha.gxt.widget.core.client.info.Info;
import net.bsuir.client.events.ColorChanged;
import net.bsuir.client.events.MouseClick;
import net.bsuir.client.events.MouseMove;
import net.bsuir.client.place.NameTokens;
import net.bsuir.client.tools.Canvas;

public class InicioPresenter extends
		Presenter<InicioPresenter.MyView, InicioPresenter.MyProxy> {

    private String currentColor;

    boolean mousePressed = false;

    public interface MyView extends View {
        Canvas getCanvas();
        String getColor();
    }

	@ProxyCodeSplit
	@NameToken(NameTokens.MAIN)
	public interface MyProxy extends ProxyPlace<InicioPresenter> {}

	@Inject
	public InicioPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy) {
		super(eventBus, view, proxy);
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
                event.getRectangle().setFillColor(currentColor);
                if(mousePressed) mousePressed=false;
                else mousePressed = true;
            }
        }));

        registerHandler(getEventBus().addHandler(MouseMove.getType(),new MouseMove.MouseMoveHandler() {
            @Override
            public void onMove(MouseMove event) {
                if(getView().getCanvas().getAlgoritm() != event.getAlgoritm()) return;
                if(mousePressed)
                    event.getRectangle().setFillColor(currentColor);
            }
        }));

        registerHandler(getEventBus().addHandler(ColorChanged.getType(),new ColorChanged.ColorChangedHandler() {
            @Override
            public void onChanged(ColorChanged event) {
                currentColor = getView().getColor();
            }
        }));

	}

    @Override
    protected void onUnbind() {
        super.onUnbind();    //To change body of overridden methods use File | Settings | File Templates.
        final AutoProgressMessageBox box = new AutoProgressMessageBox("Progress", "Saving your data, please wait...");
        box.setProgressText("Saving...");
        box.auto();
        box.show();

        Timer t = new Timer() {
            @Override
            public void run() {
                Info.display("Message", "Your fake data was saved");
                box.hide();
            }
        };
        t.schedule(5000);
    }

    @Override
    protected void onReveal() {
        super.onReveal();
        getView().getCanvas().clear();

    }


}
