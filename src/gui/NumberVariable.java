package gui;

public interface NumberVariable {

    public Number getValue();

    public void setValue(Number n);

    public abstract void restoreDefault();

}
