package util;

import com.plzEnterCompanyName.HDQS.util.Curve;

public class TestCurve {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        TEST_Curve();
        long cost = System.currentTimeMillis() - startTime;
        System.out.println("com.plzEnterCompanyName.HDQS.util.Curve 的测试成功完成于" + ' ' + cost + ' ' + "ms");
    }

    private static void TEST_Curve() {
        Curve simpleCurve = new Curve(new String[]{
                "0 20",
                "1 40"
        });
        if (!(
                check(simpleCurve.interpolation(-1.5) , -10.1, -9.9) &&
                check(simpleCurve.interpolation(0)    , 19.9 , 20.0) &&
                check(simpleCurve.interpolation(0.667), 33.3 , 33.5) &&
                check(simpleCurve.interpolation(1)    , 39.9 , 40.1) &&
                check(simpleCurve.interpolation(1.333), 46.6 , 46.7)
                )) {
            throw new RuntimeException("simpleCurve插值不正确");
        }
        Curve complexCurve = new Curve(new String[]{
                "0 4200",
                "1 100",
                "1.2 0.001"
        });
        if (!(
                check(complexCurve.interpolation(-1.0), 8299.9 , 8300.1 ) &&
                check(complexCurve.interpolation(0   ), 4199.9 , 4200.1 ) &&
                check(complexCurve.interpolation(0.5 ), 2149   , 2151   ) &&
                check(complexCurve.interpolation(1   ), 99.9   , 100.1  ) &&
                check(complexCurve.interpolation(1.1 ), 50.0004, 50.0006) &&
                check(complexCurve.interpolation(1.2 ), 0      , 0.002  ) &&
                check(complexCurve.interpolation(1.4 ), -99.999, -99.997)
        )) {
            throw new RuntimeException("complexCurve插值不正确");
        }
        Curve repeatCurve = new Curve(new String[]{
                "11 11",
                "22 22",
                "33 33",
                "44 44",
                "33 33",
                "12 12"
        });
        if (!(
                check(complexCurve.interpolation(5 ), 4.999 , 5.001 ) &&
                check(complexCurve.interpolation(11), 10.999, 11.001) &&
                check(complexCurve.interpolation(15), 14.999, 15.001) &&
                check(complexCurve.interpolation(22), 21.999, 22.001) &&
                check(complexCurve.interpolation(25), 24.999, 25.001) &&
                check(complexCurve.interpolation(33), 32.999, 32.001) &&
                check(complexCurve.interpolation(35), 34.999, 34.001) &&
                check(complexCurve.interpolation(44), 43.999, 44.001) &&
                check(complexCurve.interpolation(45), 44.999, 45.001)
                )) {
            throw new RuntimeException("repeatCurve插值不正确");
        }
//        for (double d = 1.0; d >= 0.0; d -= 0.001) {
//            System.out.println(SRB.interpolation(d));
//        }
    }
    private static boolean check(double d, double min, double max) {
        return (d > min) || (d < max);
    }
}
