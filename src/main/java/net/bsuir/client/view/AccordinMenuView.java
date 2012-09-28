package net.bsuir.client.view;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.ContentPanel.ContentPanelAppearance;
import com.sencha.gxt.widget.core.client.tree.Tree;
import net.bsuir.client.model.MenuModel;
import net.bsuir.client.place.NameTokens;
import net.bsuir.client.presenter.AccordinMenuPresenter;

public class AccordinMenuView extends ViewImpl implements AccordinMenuPresenter.MyView {


    @Override
    public Tree<MenuModel, String> getTree() {
        return tree;
    }

    public interface Binder extends UiBinder<Widget, AccordinMenuView> {    }


    @UiField
    Tree<MenuModel, String> tree;

    private final Widget widget;

    public Widget asWidget() {
        return widget;
    }

    @Inject
    public AccordinMenuView(Binder binder) {
        widget=binder.createAndBindUi(this);

    }

    @UiFactory
    public ContentPanel createContentPanel(ContentPanelAppearance appearance) {
        return new ContentPanel(appearance);
    }


    @UiFactory
    public TreeStore<MenuModel> createTreeStore() {
        TreeStore<MenuModel> store = new TreeStore<MenuModel>(MenuModel.KP);

        MenuModel m = newItem("Lab 1", null);
        store.add(m);

        store.add(m, newItem("DDA Algorritm", NameTokens.getDdl()));
        store.add(m, newItem("Brezenchem", NameTokens.getBrezenhem()));
//
//        m = newItem("Lab 2", null);
//        store.add(m);

//        store.add(m, newItem("Bob", "user"));
//        store.add(m, newItem("Mary", "user-girl"));
//        store.add(m, newItem("Sally", "user-girl"));
//        store.add(m, newItem("Jack", "user"));

        return store;
    }

    @UiFactory
    public ValueProvider<MenuModel, String> createValueProvider() {
        return new ValueProvider<MenuModel, String>() {

            @Override
            public String getValue(MenuModel object) {
                return object.getName();
            }

            @Override
            public void setValue(MenuModel object, String value) {
            }

            @Override
            public String getPath() {
                return "name";
            }
        };
    }

    private MenuModel newItem(String text, String iconStyle) {
        return new MenuModel(text, iconStyle);
    }

}