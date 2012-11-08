package net.bsuir.client.tools;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Created with IntelliJ IDEA.
 * User: aurim
 * Date: 07.11.12
 * Time: 11:45
 * To change this template use File | Settings | File Templates.
 */
public class LoadingMenu {
    private final String HTMLMessage ="<div id='ball'/>";

    private HorizontalPanel panel;

    private HTML html;

    public LoadingMenu() {
        HTML html = new HTML("<p>This is html with a <a href='www.google.com'>link</a></p>");
    }

    public HorizontalPanel getPanel() {
        if(panel == null){
            panel = new HorizontalPanel();
            panel.add(html);
            return panel;
        }
        else  return panel;
    }

    public HTML getHtml() {
        return html;
    }
}
