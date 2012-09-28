package net.bsuir.client.gin;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import net.bsuir.client.place.ClientPlaceManager;
import net.bsuir.client.place.DefaultPlace;
import net.bsuir.client.place.NameTokens;
import net.bsuir.client.presenter.AccordinMenuPresenter;
import net.bsuir.client.presenter.InicioPresenter;
import net.bsuir.client.presenter.LayoutPresenter;
import net.bsuir.client.view.*;

public class ClientModule extends AbstractPresenterModule {

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

//        bindPresenter(DDAAlgoritmView.class, DDAAlgoritmView.MyView.class,
//                DDAAlgoritmView.class, DDAAlgoritmView.MyProxy.class);

	}
}
