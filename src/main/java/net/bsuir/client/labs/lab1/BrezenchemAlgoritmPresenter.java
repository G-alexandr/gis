package net.bsuir.client.labs.lab1;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.sencha.gxt.core.client.GXT;
import net.bsuir.client.events.ColorChanged;
import net.bsuir.client.events.MouseClick;
import net.bsuir.client.events.MouseMove;
import net.bsuir.client.place.NameTokens;
import net.bsuir.client.presenter.LayoutPresenter;
import net.bsuir.client.tools.Canvas;
import net.bsuir.client.utils.CanvasLayersMagic;
import org.vaadin.gwtgraphics.client.shape.Rectangle;

import java.util.ArrayList;

public class BrezenchemAlgoritmPresenter extends
		Presenter<BrezenchemAlgoritmPresenter.MyView, BrezenchemAlgoritmPresenter.MyProxy> {

    private String currentColor = "#000000";

    private CanvasLayersMagic magic = new CanvasLayersMagic() {
        @Override
        public void commit() {
            super.commit();
            getTransactionMap().clear();
        }

        @Override
        public void rollback() {
            for (Integer key: getTransactionMap().keySet()){
                if(getDrawPoints().containsKey(key)){
                    String old_color = getDrawPoints().get(key);
                    ((Rectangle)getView().getCanvas()
                            .getVectorObject(key))
                            .setFillColor(old_color);
                }
                else {
                    ((Rectangle)getView().getCanvas()
                            .getVectorObject(key))
                            .setFillColor("#FFFFFF");
                }
            }
            getTransactionMap().clear();
        }
    };

    boolean mousePressed = false;
    public interface MyView extends View {
        Canvas getCanvas();
        String getColor();
    }

    private int x1, y1;
//   private ArrayList<Integer> old_x, old_y;

    @ProxyCodeSplit
	@NameToken(NameTokens.BREZENHEM)
	public interface MyProxy extends ProxyPlace<BrezenchemAlgoritmPresenter> {}

	@Inject
	public BrezenchemAlgoritmPresenter(final EventBus eventBus, final MyView view,
                                       final MyProxy proxy) {
		super(eventBus, view, proxy);
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, LayoutPresenter.SLOT_content, this);
	}

    @Override
    protected void onReveal() {
        super.onReveal();
        getView().getCanvas().clear();
            }

    @Override
	protected void onBind() {
		super.onBind();
        getView().getCanvas().setEventBus(getEventBus());

        registerHandler(getEventBus().addHandler(MouseClick.getType(), new MouseClick.MouseClickHandler() {
            @Override
            public void onClick(MouseClick event) {
                if(getView().getCanvas().getAlgoritm() != event.getAlgoritm()) return;
                event.getRectangle().setFillColor(currentColor);
                if(mousePressed){
                    mousePressed=false;
                    magic.commit();
                }
                else mousePressed = true;
                x1=event.getPosX();
                y1=event.getPosY();
//                old_x=null;
//                old_y=null;

            }
        }));

        registerHandler(getEventBus().addHandler(MouseMove.getType(),new MouseMove.MouseMoveHandler() {
            @Override
            public void onMove(MouseMove event) {
                if(getView().getCanvas().getAlgoritm() != event.getAlgoritm()) return;
                if(mousePressed){
                    event.getRectangle().setFillColor(currentColor);
//                    if(old_x!=null || old_y!=null){
//                        for (int i=0;i<old_x.size();i++){
//                            getView().getCanvas().getPixelByPos(Math.round(old_x.get(i)), Math.round(old_y.get(i))).setFillColor("#FFFFFF");
//                        }
//                    }
                    magic.rollback();
                    drawBresenhamLine(x1, y1, event.getPosX(), event.getPosY());
                }
            }
        }));

        registerHandler(getEventBus().addHandler(ColorChanged.getType(),new ColorChanged.ColorChangedHandler() {
            @Override
            public void onChanged(ColorChanged event) {
                currentColor = getView().getColor();
            }
        }));
	}


    private int sign (int x) {
        return (x > 0) ? 1 : (x < 0) ? -1 : 0;
    }

    public void drawBresenhamLine (int xstart, int ystart, int xend, int yend)
    /**
     * xstart, ystart - начало;
     * xend, yend - конец;
     */
    {
//        old_y = new ArrayList<Integer>(100);
//        old_x = new ArrayList<Integer>(100);
        int x, y, dx, dy, incx, incy, pdx, pdy, es, el, err;

        dx = xend - xstart;//проекция на ось икс
        dy = yend - ystart;//проекция на ось игрек

        incx = sign(dx);
        /*
         * Определяем, в какую сторону нужно будет сдвигаться. Если dx < 0, т.е. отрезок идёт
         * справа налево по иксу, то incx будет равен -1.
         * Это будет использоваться в цикле постороения.
         */
        incy = sign(dy);
        /*
         * Аналогично. Если рисуем отрезок снизу вверх -
         * это будет отрицательный сдвиг для y (иначе - положительный).
         */

        if (dx < 0) dx = -dx;//далее мы будем сравнивать: "if (dx < dy)"
        if (dy < 0) dy = -dy;//поэтому необходимо сделать dx = |dx|; dy = |dy|
        //эти две строчки можно записать и так: dx = Math.abs(dx); dy = Math.abs(dy);

        if (dx > dy)
        //определяем наклон отрезка:
        {
            /*
            * Если dx > dy, то значит отрезок "вытянут" вдоль оси икс, т.е. он скорее длинный, чем высокий.
            * Значит в цикле нужно будет идти по икс (строчка el = dx;), значит "протягивать" прямую по иксу
            * надо в соответствии с тем, слева направо и справа налево она идёт (pdx = incx;), при этом
            * по y сдвиг такой отсутствует.
            */
            pdx = incx;     pdy = 0;
            es = dy;        el = dx;
        }
        else//случай, когда прямая скорее "высокая", чем длинная, т.е. вытянута по оси y
        {
            pdx = 0;        pdy = incy;
            es = dx;        el = dy;//тогда в цикле будем двигаться по y
        }

        x = xstart;
        y = ystart;
        err = el/2;

        drawPoint(x,y);//ставим первую точку

        //все последующие точки возможно надо сдвигать, поэтому первую ставим вне цикла

        for (int t = 0; t < el; t++)//идём по всем точкам, начиная со второй и до последней
        {
            err -= es;
            if (err < 0)
            {
                err += el;
                x += incx;//сдвинуть прямую (сместить вверх или вниз, если цикл проходит по иксам)
                y += incy;//или сместить влево-вправо, если цикл проходит по y
            }
            else
            {
                x += pdx;//продолжить тянуть прямую дальше, т.е. сдвинуть влево или вправо, если
                y += pdy;//цикл идёт по иксу; сдвинуть вверх или вниз, если по y
            }

            drawPoint(x,y);
        }
    }

    void drawPoint(int x, int y){
        Rectangle pixel = getView().getCanvas().getPixelByPos(x, y);
        if(pixel!=null) {
            pixel.setFillColor(currentColor);
//            old_y.add(y);
//            old_x.add(x);
            magic.add(getView().getCanvas().getNumber(x,y),currentColor);
        }
        else return;
    }
}
