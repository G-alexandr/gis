package net.bsuir.client.presenter;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer;
import com.sencha.gxt.widget.core.client.tree.Tree;
import net.bsuir.client.model.MenuModel;

import java.util.List;


public class AccordinMenuPresenter extends
        Presenter<AccordinMenuPresenter.MyView, AccordinMenuPresenter.MyProxy> {

    public interface MyView extends View {
        Tree<MenuModel, String> getTree();
    }
    @ProxyCodeSplit
    public interface MyProxy extends Proxy<AccordinMenuPresenter> { }

    @Inject
    PlaceManager placeManager;

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

    @Override
    protected void onReset() {
        super.onReset();

        getView().getTree().getSelectionModel().addSelectionHandler(new SelectionHandler<MenuModel>() {

            public void onSelection(SelectionEvent<MenuModel> event) {
                MenuModel mnu = event.getSelectedItem();
                if(!mnu.getToken().equals("")){
                    PlaceRequest request = new PlaceRequest(mnu.getToken());
                    placeManager.revealPlace(request);
                }
            }
        });
        String token = placeManager.getCurrentPlaceRequest().getNameToken();
        List<MenuModel> mnus = getView().getTree().getStore().getAll();
        for(MenuModel mnu: mnus){
            if(mnu.getToken()==null) continue;
            if(mnu.getToken().equals(token)){
                getView().getTree().getSelectionModel().select(mnu, true);
                break;
            }
        }
    }
}
