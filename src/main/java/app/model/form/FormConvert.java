package app.model.form;

public interface FormConvert<C,T> {

    T convert(C c);
}
