package net.bsuir.client;

import com.google.gwt.core.client.GWT;
import com.gwtplatform.mvp.client.DelayedBindRegistry;
import com.google.gwt.core.client.EntryPoint;
import net.bsuir.client.gin.ClientGinjector;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class gis implements EntryPoint {

    private final ClientGinjector ginjector = GWT.create(ClientGinjector.class);

    public void onModuleLoad() {

        DelayedBindRegistry.bind(ginjector);

        ginjector.getPlaceManager().revealCurrentPlace();
    }
}
