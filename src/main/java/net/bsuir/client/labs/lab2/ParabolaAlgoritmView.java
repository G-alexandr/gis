package net.bsuir.client.labs.lab2;

import com.google.inject.Inject;
import net.bsuir.client.place.NameTokens;
import net.bsuir.client.tools.Canvas;
import net.bsuir.client.view.AbstractAlgoritmView;

public class ParabolaAlgoritmView extends AbstractAlgoritmView implements ParabolaAlgoritmPresenter.MyView {

    @Inject
    public ParabolaAlgoritmView(Binder binder) {
        super(binder);
        getCanvas().setAlgoritm(NameTokens.PARABOLA);
    }

    public Canvas getCanvas(){
        return super.canvas;
    }

}
