package net.bsuir.client.labs.lab3;

import com.google.inject.Inject;
import net.bsuir.client.place.NameTokens;
import net.bsuir.client.tools.Canvas;
import net.bsuir.client.view.AbstractAlgoritmView;

public class ErmitAlgoritmView extends AbstractAlgoritmView implements ErmitAlgoritmPresenter.MyView {

    @Inject
    public ErmitAlgoritmView(Binder binder) {
        super(binder);
        getCanvas().setAlgoritm(NameTokens.ERMIT);
    }

    public Canvas getCanvas(){
        return super.canvas;
    }

}
