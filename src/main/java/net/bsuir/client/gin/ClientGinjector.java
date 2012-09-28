package net.bsuir.client.gin;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import net.bsuir.client.presenter.DDAAlgoritmView;
import net.bsuir.client.presenter.AccordinMenuPresenter;
import net.bsuir.client.presenter.InicioPresenter;
import net.bsuir.client.presenter.LayoutPresenter;

@GinModules(ClientModule.class)
public interface ClientGinjector extends Ginjector {

	EventBus getEventBus();

	PlaceManager getPlaceManager();

	AsyncProvider<LayoutPresenter> getLayoutPresenter();

	AsyncProvider<InicioPresenter> getInicioPresenter();

    AsyncProvider<AccordinMenuPresenter> getAccordinMenuPresenter();

    AsyncProvider<DDAAlgoritmView> getDDLAlgoritmPresenter();

}
