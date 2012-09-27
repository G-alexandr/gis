package net.bsuir.client.presenter;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer;


public class AccordinMenuPresenter extends
        Presenter<AccordinMenuPresenter.MyView, AccordinMenuPresenter.MyProxy> {

    public interface MyView extends View {
        AccordionLayoutContainer getContainer();
    }
    @ProxyCodeSplit
    public interface MyProxy extends Proxy<AccordinMenuPresenter> { }




    @Inject
    public AccordinMenuPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
        super(eventBus, view, proxy);
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, LayoutPresenter.SLOT_content, this);
    }

    @Override
    protected void onBind() {
        super.onBind();
    }
}
