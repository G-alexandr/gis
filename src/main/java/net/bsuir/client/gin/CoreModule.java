package net.bsuir.client.gin;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import net.bsuir.client.labs.lab1.BrezenchemAlgoritmPresenter;
import net.bsuir.client.labs.lab1.BrezenchemAlgoritmView;
import net.bsuir.client.labs.lab2.CircleAlgoritmPresenter;
import net.bsuir.client.labs.lab2.CircleAlgoritmView;
import net.bsuir.client.labs.lab2.ParabolaAlgoritmPresenter;
import net.bsuir.client.labs.lab2.ParabolaAlgoritmView;
import net.bsuir.client.labs.lab3.BezeAlgoritmPresenter;
import net.bsuir.client.labs.lab3.BezeAlgoritmView;
import net.bsuir.client.labs.lab3.ErmitAlgoritmPresenter;
import net.bsuir.client.labs.lab3.ErmitAlgoritmView;
import net.bsuir.client.place.ClientPlaceManager;
import net.bsuir.client.place.DefaultPlace;
import net.bsuir.client.place.NameTokens;
import net.bsuir.client.presenter.AccordinMenuPresenter;
import net.bsuir.client.presenter.InicioPresenter;
import net.bsuir.client.presenter.LayoutPresenter;
import net.bsuir.client.view.*;
import net.bsuir.client.labs.lab1.DDAAlgoritmPresenter;
import net.bsuir.client.labs.lab1.DDAAlgoritmView;

public class CoreModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		install(new DefaultModule(ClientPlaceManager.class));

        bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.MAIN);

		bindPresenter(LayoutPresenter.class, LayoutPresenter.MyView.class,
                LayoutView.class, LayoutPresenter.MyProxy.class);

		bindPresenter(InicioPresenter.class,InicioPresenter.MyView.class,
				InicioView.class, InicioPresenter.MyProxy.class);

        bindPresenter(AccordinMenuPresenter.class, AccordinMenuPresenter.MyView.class,
                AccordinMenuView.class, AccordinMenuPresenter.MyProxy.class);

        bindPresenter(DDAAlgoritmPresenter.class, DDAAlgoritmPresenter.MyView.class,
                DDAAlgoritmView.class, DDAAlgoritmPresenter.MyProxy.class);

        bindPresenter(BrezenchemAlgoritmPresenter.class, BrezenchemAlgoritmPresenter.MyView.class,
                BrezenchemAlgoritmView.class, BrezenchemAlgoritmPresenter.MyProxy.class);

        bindPresenter(CircleAlgoritmPresenter.class, CircleAlgoritmPresenter.MyView.class,
                CircleAlgoritmView.class, CircleAlgoritmPresenter.MyProxy.class);

        bindPresenter(ParabolaAlgoritmPresenter.class, ParabolaAlgoritmPresenter.MyView.class,
                ParabolaAlgoritmView.class, ParabolaAlgoritmPresenter.MyProxy.class);

        bindPresenter(ErmitAlgoritmPresenter.class, ErmitAlgoritmPresenter.MyView.class,
                ErmitAlgoritmView.class, ErmitAlgoritmPresenter.MyProxy.class);

        bindPresenter(BezeAlgoritmPresenter.class, BezeAlgoritmPresenter.MyView.class,
                BezeAlgoritmView.class, BezeAlgoritmPresenter.MyProxy.class);
    }
}
