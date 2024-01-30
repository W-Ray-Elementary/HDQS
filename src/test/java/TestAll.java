import com.plzEnterCompanyName.HDQS.RequireBoot;
import com.plzEnterCompanyName.HDQS.text.AwtRuler;
import util.lexicon.TestLEXICON;
import util.lexicon.TestLexicons;

public class TestAll {
    public static void main(String[] args) {
        RequireBoot rb = new AwtRuler();
        rb.boot();
        io.smartIO2.TestMessageManager.main(null);
        io.smartIO2.TestSmartIO.main(null);
        io.Test.main(null);
        io.TestReadJar.main(null);
        text.frame.TestFrame.main(null);
        text.TestText.main(null);
        util.TestCurve.main(null);
        util.TestFormatCheck.main(null);
        TestLEXICON.main(null);
        TestLexicons.main(null);
    }
}
