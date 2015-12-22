package vision;

public class IntegerSliderVariable extends SliderVariable {

    public final int DEFAULT, MIN, MAX;
    private int val;

    public IntegerSliderVariable(String label, int defaultVal, int minVal, int maxVal) {
        super(label);
        this.DEFAULT = defaultVal;
        this.MIN = minVal;
        this.MAX = maxVal;
        this.val = defaultVal;
    }

    public int value() {
        return val;
    }

    public void set(int n) {
        assert MIN <= n && n <= MAX;
        val = n;
    }

    public void restoreDefault() {
        set(DEFAULT);
    }

}
