package vision;

public abstract class SliderVariable {

    public final String LABEL;

    public SliderVariable(String label) {
        this.LABEL = label;
    }

    public abstract Number getValue();
    public abstract void setValue(Number n);

    public abstract void restoreDefault();

}
