package net.bsuir.client.utils;

import java.util.HashMap;

/**
 * User: PC1405
 * Date: 11/21/12
 * Time: 9:30 AM
 */
public abstract class CanvasLayersMagic {

    private HashMap<Integer, String> drawPoints = new HashMap<Integer, String>(1000);
    private HashMap<Integer, String> transactionMap = new HashMap<Integer, String>(100);


//    public void addToDrawPoints(int x, int y, String color){
//        Integer key = String.valueOf(x)+String.valueOf(y);
//        if(drawPoints.containsKey(key)){
//            transactionMap.put(key,drawPoints.get(key));
//            drawPoints.remove(key);
//        }
//        drawPoints.put(key,color);
//    }

    public void add(Integer pixelNumber, String color){
        transactionMap.put(pixelNumber,color);
    }
    public void clearDrawPoints(){
        drawPoints.clear();
    }
    public void commit() {
        String color="#FFFFFF";
        for(Integer o: transactionMap.keySet()){
            color = transactionMap.get(o);
            drawPoints.remove(o);
            drawPoints.put(o,color);
        }
    }
    public abstract void rollback();

    public HashMap<Integer, String> getDrawPoints() {
        return drawPoints;
    }

    public HashMap<Integer, String> getTransactionMap() {
        return transactionMap;
    }

    //
}
