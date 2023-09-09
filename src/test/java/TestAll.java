import io.smartIO2.TestMessageManager;
import text.frame.TestFrame;
import util.TestFormatCheck;

public class TestAll {
    public static void main(String[] args) {
        io.smartIO2.TestSmartIO.main(null);
        io.Test.main(null);
        io.TestReadJar.main(null);
        util.TestCurve.main(null);
        util.TestLEXICON.main(null);
        TestFormatCheck.main(null);
        TestMessageManager.main(null);
        TestFrame.main(null);
    }
}
