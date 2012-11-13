package net.bsuir.client.labs.lab3;

import com.google.inject.Inject;
import net.bsuir.client.place.NameTokens;
import net.bsuir.client.tools.Canvas;
import net.bsuir.client.view.AbstractAlgoritmView;

public class BezeAlgoritmView extends AbstractAlgoritmView implements BezeAlgoritmPresenter.MyView {

    @Inject
    public BezeAlgoritmView(Binder binder) {
        super(binder);
        getCanvas().setAlgoritm(NameTokens.BEZE);
    }

    public Canvas getCanvas(){
        return super.canvas;
    }

}
