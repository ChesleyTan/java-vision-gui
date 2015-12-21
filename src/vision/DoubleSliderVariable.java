package vision;

public class DoubleSliderVariable implements SliderVariable {
    public final double DEFAULT, MIN, MAX;
    public final String LABEL;
    private double val;

    protected DoubleSliderVariable(String label, double defaultVal, double minVal, double maxVal) {
        this.DEFAULT = defaultVal;
        this.MIN = minVal;
        this.MAX = maxVal;
        this.LABEL = label;
        this.val = defaultVal;
    }

    public double value() {
        return val;
    }

    public void set(double d) {
        val = d;
    }
}
