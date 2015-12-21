package vision;

public class IntegerSliderVariable implements SliderVariable {
    public final int DEFAULT, MIN, MAX;
    public final String LABEL;
    private int val;

    protected IntegerSliderVariable(String label, int defaultVal, int minVal, int maxVal) {
        this.DEFAULT = defaultVal;
        this.MIN = minVal;
        this.MAX = maxVal;
        this.LABEL = label;
        this.val = defaultVal;
    }

    public int value() {
        return val;
    }

    public void set(int n) {
        val = n;
    }
}
