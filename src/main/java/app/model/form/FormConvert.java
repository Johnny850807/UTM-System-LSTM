package app.model.form;

/**
 * @author swshawnwu@gmail.com(ShawnWu)
 */

public interface FormConvert<C,T> {

    T convert(C c);
}
