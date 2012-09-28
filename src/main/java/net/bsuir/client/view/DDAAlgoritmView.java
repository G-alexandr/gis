package net.bsuir.client.view;

import com.google.inject.Inject;
import net.bsuir.client.presenter.*;

public class DDAAlgoritmView extends AbstractAlgoritmView implements DDAAlgoritmPresenter.MyView {

    @Inject
    public DDAAlgoritmView(Binder binder) {
        super(binder);
    }
}
