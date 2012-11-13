package net.bsuir.client.gin;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import net.bsuir.client.labs.lab1.BrezenchemAlgoritmPresenter;
import net.bsuir.client.labs.lab2.CircleAlgoritmPresenter;
import net.bsuir.client.labs.lab2.ParabolaAlgoritmPresenter;
import net.bsuir.client.labs.lab3.BezeAlgoritmPresenter;
import net.bsuir.client.labs.lab3.ErmitAlgoritmPresenter;
import net.bsuir.client.presenter.AccordinMenuPresenter;
import net.bsuir.client.presenter.InicioPresenter;
import net.bsuir.client.presenter.LayoutPresenter;
import net.bsuir.client.labs.lab1.DDAAlgoritmPresenter;

@GinModules(value = CoreModule.class)
public interface ClientGinjector extends Ginjector {

	EventBus getEventBus();

	PlaceManager getPlaceManager();

	AsyncProvider<LayoutPresenter> getLayoutPresenter();

	AsyncProvider<InicioPresenter> getInicioPresenter();

    AsyncProvider<AccordinMenuPresenter> getAccordinMenuPresenter();

    AsyncProvider<DDAAlgoritmPresenter> getDDAAlgoritmPresenter();

    AsyncProvider<BrezenchemAlgoritmPresenter> getBrezenchemAlgoritmPresenter();

    AsyncProvider<CircleAlgoritmPresenter> getCircleAlgoritmPresenter();

    AsyncProvider<ParabolaAlgoritmPresenter> getParabolaAlgoritmPresenter();

    AsyncProvider<ErmitAlgoritmPresenter> getErmitAlgoritmPresenter();

    AsyncProvider<BezeAlgoritmPresenter> getBezeAlgoritmPresenter();
}
