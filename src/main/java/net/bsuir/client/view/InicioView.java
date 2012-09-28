package net.bsuir.client.view;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import net.bsuir.client.presenter.InicioPresenter;
import net.bsuir.client.tools.Canvas;

import static net.bsuir.client.presenter.InicioPresenter.*;

public class InicioView extends AbstractAlgoritmView implements InicioPresenter.MyView {

    @Inject
    public InicioView(Binder binder) {
        super(binder);
    }

    public Canvas getCanvas(){
        return super.canvas;
    }

}
