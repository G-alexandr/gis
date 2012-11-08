package net.bsuir.client.labs.lab1;

import com.google.inject.Inject;
import net.bsuir.client.place.NameTokens;
import net.bsuir.client.tools.Canvas;
import net.bsuir.client.view.AbstractAlgoritmView;

public class BrezenchemAlgoritmView extends AbstractAlgoritmView implements BrezenchemAlgoritmPresenter.MyView {

    @Inject
    public BrezenchemAlgoritmView(Binder binder) {
        super(binder);
        getCanvas().setAlgoritm(NameTokens.BREZENHEM);
    }

    public Canvas getCanvas(){
        return super.canvas;
    }

}
