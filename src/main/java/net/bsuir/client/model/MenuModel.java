package net.bsuir.client.model;

import com.sencha.gxt.data.shared.ModelKeyProvider;


public class MenuModel {

    public static ModelKeyProvider<MenuModel> KP = new ModelKeyProvider<MenuModel>() {
        @Override
        public String getKey(MenuModel item) {
            return item.getName();
        }
    };

    private String name;
    private String token;

    public MenuModel(String name,String token) {
        this.name = name;
        this.token = token;
    }

    public java.lang.String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
