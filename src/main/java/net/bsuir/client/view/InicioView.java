package net.bsuir.client.view;

import com.google.inject.Inject;
import net.bsuir.client.place.NameTokens;
import net.bsuir.client.presenter.InicioPresenter;
import net.bsuir.client.tools.Canvas;

import static net.bsuir.client.presenter.InicioPresenter.*;

public class InicioView extends AbstractAlgoritmView implements InicioPresenter.MyView {

    @Inject
    public InicioView(Binder binder) {
        super(binder);
        getCanvas().setAlgoritm(NameTokens.BREZENHEM);
    }

    public Canvas getCanvas(){
        return super.canvas;
    }


}
