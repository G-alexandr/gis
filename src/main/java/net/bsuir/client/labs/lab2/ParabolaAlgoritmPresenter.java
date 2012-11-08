package net.bsuir.client.labs.lab2;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import net.bsuir.client.events.ColorChanged;
import net.bsuir.client.events.MouseClick;
import net.bsuir.client.events.MouseMove;
import net.bsuir.client.place.NameTokens;
import net.bsuir.client.presenter.LayoutPresenter;
import net.bsuir.client.tools.Canvas;
import org.vaadin.gwtgraphics.client.shape.Rectangle;

import java.util.ArrayList;

public class ParabolaAlgoritmPresenter extends
		Presenter<ParabolaAlgoritmPresenter.MyView, ParabolaAlgoritmPresenter.MyProxy> {

    private String currentColor  = "#000000";

    boolean mousePressed = false;
    public interface MyView extends View {
        Canvas getCanvas();
        String getColor();
    }

    private int x1, y1;
//   private float[] old_x, old_y;
    private ArrayList<Integer> old_x = new ArrayList<Integer>(100);
    private ArrayList<Integer> old_y = new ArrayList<Integer>(100);

    @ProxyCodeSplit
	@NameToken(NameTokens.PARABOLA)
	public interface MyProxy extends ProxyPlace<ParabolaAlgoritmPresenter> {}

	@Inject
	public ParabolaAlgoritmPresenter(final EventBus eventBus, final MyView view,
                                     final MyProxy proxy) {
		super(eventBus, view, proxy);
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, LayoutPresenter.SLOT_content, this);
	}
    @Override
    protected void onHide() {
        super.onHide();

        mousePressed=false;
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
                if(mousePressed) mousePressed=false;
                else mousePressed = true;
                x1=event.getPosX();
                y1=event.getPosY();
                old_x.clear();
                old_y.clear();
            }
        }));

        registerHandler(getEventBus().addHandler(MouseMove.getType(),new MouseMove.MouseMoveHandler() {
            @Override
            public void onMove(MouseMove event) {
                if(getView().getCanvas().getAlgoritm() != event.getAlgoritm()) return;
                if(mousePressed){
//                    event.getRectangle().setFillColor(currentColor);

                    if(old_x!=null || old_y!=null){
                        for (int i=0;i<old_x.size();i++){
                            getView().getCanvas().getPixelByPos(Math.round(old_x.get(i)), Math.round(old_y.get(i))).setFillColor("#FFFFFF");
                        }
                    }
                    old_x.clear();
                    old_y.clear();
                    algoritm(x1,y1,event.getPosX(),event.getPosY());
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
    void drawPoint(int x, int y){
        Rectangle pixel = getView().getCanvas().getPixelByPos(x, y);
        if(pixel!=null)
            pixel.setFillColor(currentColor);
        else return;
        old_x.add(x);
        old_y.add(y);
    }
    public void  algoritm(int x1, int y1, int x2, int y2) {
        int Sh, Sv, Sd;
        int y = 0;
        int x = 0;
        int p=(int) (Math.pow(y2,2)/(2*x2));
        Sd = ((y + 1) * (y + 1) )- 2 * p * (x + 1);
        Sv = ((y + 1)*(y + 1)) - 2 * p * x;
        Sh = (y*y) - 2 * p * (x + 1);

        drawPoint(x1 + x, y1 + y);
//        line.add(new Pixel(p1.x + x, p1.y + y, color));

        while (x < 200) //пока полотно не кончится
        {
            if (Math.abs(Sh) - Math.abs(Sv) <= 0)
            {
                if (Math.abs(Sd) - Math.abs(Sh) < 0) {
                    y++;
                }
                x++;
            }
            else
            {
                if (Math.abs(Sv) - Math.abs(Sd) > 0) {
                    x++;
                }
                y++;

            }
            drawPoint(x1 - y, y1 -x );
            drawPoint(x1 + y, y1 -x );
//            line.add(new Pixel(p1.x - y, p1.y -x , color));
//            line.add(new Pixel(p1.x + y, p1.y -x , color));

            Sd = ((y + 1) * (y + 1)) - 2 * p * (x + 1);
            Sv = ((y + 1) * (y + 1)) - 2 * p * x;
            Sh = (y * y) - 2 * p * (x + 1);
        }
    }

//
//void porabola(int x, int y, int r)
//    {
//        int p = 2;
//        int x0 = 0; //  центр
//        int y0 = 0; //
//        int Sh, Sv, Sd;
//        Sd = ((y + 1) * (y + 1) )- 2 * p * (x + 1);
//        Sv = ((y + 1)*(y + 1)) - 2 * p * x;
//        Sh = (y*y) - 2 * p * (x + 1);
//        drawPoint(x0, y0);
//        while (x + x0 < r) //пока полотно не кончится
//        {
//            if (Math.abs(Sh) - Math.abs(Sv)<=0)
//            {
//                if (Math.abs(Sd) - Math.abs(Sh) < 0)
//                    y++;
//                x++;
//
//            }
//            else
//            {
//                if (Math.abs(Sv) - Math.abs(Sd) > 0)
//                    x++;
//                y++;
//
//            }
//
//            drawPoint(x + x0, y + y0);
//
//            Sd = ((y + 1) * (y + 1)) - 2 * p * (x + 1);
//            Sv = ((y + 1) * (y + 1)) - 2 * p * x;
//            Sh = (y * y) - 2 * p * (x + 1);
//        }
//    }
    /*
    class Dot{
        public int x;
        public int y;

        Dot(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    void porabola(int x, int y, int r){
        int hy = m_hight;

        ArrayList right;

        // параметры а и b из уравнения гиперболы
        int a = Math.abs(m_end.x() - m_start.x());
        int b = Math.abs(m_end.y() - m_start.y());

        // координаты начальной точки
        int x = 0;
        int y = 0;

        // сдвиг точек
        int lim = 0;
        int limx = 0;

        y = b;
        limx = m_end.x();

        if (m_start.y() > m_end.y())
        {
            lim = 2 * m_start.y() - m_end.y() - y;
        }
        else
        {
            lim = 2 * m_end.y() - m_start.y() - 2 * y;
        }

        // начальное значение ошибки
        int delta = b * b - 2 * a * a * b - a * a;
        int sigma = 0;

        right.push_back(Dot(x + limx, y + lim));

        // проверяем знак ошибки
        while (hy > 0)
        {
            hy = hy - 1;

            // если ошибка положительна, выбираем между вертикальным и диогональным пикселями
            if (delta > 0)
            {
                // для определения того, какой из пикселей выбрать вычисляем разность между ними
                sigma = 2 * delta - b * b * (2 * x + 1);

                //если разность меньше нуля, выбираем диогональнай пиксель
                if (sigma < 0)
                {
                    x = x + 1;
                    y = y + 1;

                    // пересчитываем ошибку
                    delta = delta + b * b * (2 * x + 1) - a * a * (2 * y + 1);
                    right.push_back(Dot(x + limx, y + lim));
                }
                // если разность положительна, выбираем вертикальный пиксель
                else
                {
                    y = y + 1;
                    // пересчитываем ошибку
                    delta = delta - a * a * (2 * y + 1);
                    right.push_back(Dot(x + limx, y + lim));
                }

            }
            // если ошибка отрицательна, выбираем между горизонтальным и диогональным пикселями
            else
            {
                if (delta < 0)
                {
                    // для определения того, какой из пикселей выбрать вычисляем разность между ними
                    sigma = 2 * delta + a * a * (2 * y + 1);

                    // если разность положительна, выбираем диогональный пиксель
                    if (sigma > 0)
                    {
                        x = x + 1;
                        y = y + 1;

                        // пересчитываем ошибку
                        delta = delta + b * b * (2 * x + 1) - a * a * (2 * y + 1);
                        right.push_back(Dot(x + limx, y + lim));
                    }
                    // если разность меньше нуля, выбираем горизонтальный пиксель
                    else
                    {
                        x = x + 1;

                        // пересчитываем ошибку
                        delta = delta + b * b * (2 * x + 1);
                        right.push_back(Dot(x + limx, y + lim));
                    }
                }
                // если ошибка равна нулю, то выбираем диогональный пиксель
                else
                {
                    x = x + 1;
                    y = y + 1;

                    // пересчитываем ошибку
                    delta = delta + b * b * (2 * x + 1) - a * a * (2 * y + 1);
                    right.push_back(Dot(x + limx, y + lim));
                }
            }
        }

        int rx = 0;
        int ry = 0;

        int size = right.size();

        cont.resize(4*size);

        for (int i = size - 1; i >= 0 ; i--)
        {
            rx = right[i].x();
            ry = right[i].y();

            cont[size - i - 1] = right[i];
            cont[size + i] = Dot(-rx + 2 * m_end.x(), ry);
            cont[3*size - i - 1] = Dot(-rx + 2 * m_end.x(), -ry + 2 * m_start.y());
            cont[3*size + i] = Dot(rx, -ry + 2 * m_start.y());
        }

        for (int i = 0; i < cont.size() ; i++)
        {
            cont[i].setX(cont[i].x()+m_start.x()-m_end.x());
        }
        m_state = Start;
    }
    void drawPoint(int x, int y){
        Rectangle pixel = getView().getCanvas().getPixelByPos(x, y);
        if(pixel!=null)
            pixel.setFillColor(currentColor);
        else return;
        old_x.add(x);
        old_y.add(y);
    }
    */
}
