package net.bsuir.client.labs.lab2;

import com.google.inject.Inject;
import net.bsuir.client.labs.lab1.DDAAlgoritmPresenter;
import net.bsuir.client.place.NameTokens;
import net.bsuir.client.tools.Canvas;
import net.bsuir.client.view.AbstractAlgoritmView;

public class CircleAlgoritmView extends AbstractAlgoritmView implements CircleAlgoritmPresenter.MyView {

    @Inject
    public CircleAlgoritmView(Binder binder) {
        super(binder);
        getCanvas().setAlgoritm(NameTokens.CIRCLE);
    }

    public Canvas getCanvas(){
        return super.canvas;
    }

}
