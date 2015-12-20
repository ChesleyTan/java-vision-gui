package util;

public class DebugPrinter {

    public static final boolean VERBOSE = true;

    public static void println(Object x) {
        if (VERBOSE) {
            System.out.println(x);
        }
    }
}
