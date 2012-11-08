package net.bsuir.client.exception;

/**
 * Created with IntelliJ IDEA.
 * User: PC1405
 * Date: 10/2/12
 * Time: 11:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class IndexOutOfBound extends Exception {
    @Override
    public String getMessage() {
        return "Index out of bound";
    }
}
