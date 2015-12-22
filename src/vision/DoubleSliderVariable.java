package vision;

public class DoubleSliderVariable extends SliderVariable {

    public final double DEFAULT, MIN, MAX;
    private double val;

    protected DoubleSliderVariable(String label, double defaultVal, double minVal, double maxVal) {
        super(label);
        this.DEFAULT = defaultVal;
        this.MIN = minVal;
        this.MAX = maxVal;
        this.val = defaultVal;
    }

    public double value() {
        return val;
    }

    public void set(double d) {
        assert MIN <= d && d <= MAX;
        val = d;
    }

    public void restoreDefault() {
        set(DEFAULT);
    }

}
