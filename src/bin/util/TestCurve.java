package bin.util;

public class TestCurve {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        TEST_Curve();
        long cost = System.currentTimeMillis() - startTime;
        System.out.println("bin.util.Curve 的测试成功完成于" + ' ' + cost + ' ' + "ms");
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
        Curve SRB = new Curve(LEXICON.getInstance("""
                thrustCurve
                      {
                      	key = 0.99972 0.719
                      	key = 0.98539 0.939
                      	key = 0.97106 0.939
                      	key = 0.95673 0.939
                      	key = 0.94227 0.948
                      	key = 0.92769 0.956
                      	key = 0.91298 0.964
                      	key = 0.89811 0.975
                      	key = 0.88302 0.989
                      	key = 0.86785 0.995
                      	key = 0.85268 0.994
                      	key = 0.83746 0.997
                      	key = 0.82221 1
                      	key = 0.80695 1
                      	key = 0.7917 1
                      	key = 0.77644 1
                      	key = 0.76119 1
                      	key = 0.74593 1
                      	key = 0.73068 1
                      	key = 0.71543 1
                      	key = 0.70017 1
                      	key = 0.68496 0.997
                      	key = 0.66992 0.986
                      	key = 0.65513 0.969
                      	key = 0.6408 0.939
                      	key = 0.62728 0.886
                      	key = 0.61481 0.817
                      	key = 0.60293 0.779
                      	key = 0.59143 0.754
                      	key = 0.58023 0.734
                      	key = 0.56953 0.701
                      	key = 0.55909 0.685
                      	key = 0.5489 0.668
                      	key = 0.539 0.649
                      	key = 0.52932 0.635
                      	key = 0.51985 0.621
                      	key = 0.51063 0.604
                      	key = 0.50162 0.591
                      	key = 0.49282 0.577
                      	key = 0.48419 0.566
                      	key = 0.47573 0.555
                      	key = 0.46731 0.552
                      	key = 0.45877 0.56
                      	key = 0.45014 0.566
                      	key = 0.44143 0.571
                      	key = 0.43263 0.577
                      	key = 0.42371 0.585
                      	key = 0.4147 0.59
                      	key = 0.40561 0.596
                      	key = 0.39644 0.601
                      	key = 0.38718 0.607
                      	key = 0.37779 0.615
                      	key = 0.36832 0.621
                      	key = 0.35877 0.626
                      	key = 0.34913 0.632
                      	key = 0.33936 0.64
                      	key = 0.32951 0.646
                      	key = 0.31958 0.651
                      	key = 0.30952 0.659
                      	key = 0.29938 0.665
                      	key = 0.28915 0.67
                      	key = 0.2788 0.679
                      	key = 0.26836 0.684
                      	key = 0.25779 0.693
                      	key = 0.24714 0.698
                      	key = 0.23641 0.704
                      	key = 0.22564 0.706
                      	key = 0.21482 0.709
                      	key = 0.20392 0.715
                      	key = 0.19297 0.717
                      	key = 0.18199 0.72
                      	key = 0.17092 0.726
                      	key = 0.15981 0.728
                      	key = 0.14865 0.731
                      	key = 0.13746 0.734
                      	key = 0.12618 0.739
                      	key = 0.11494 0.737
                      	key = 0.10371 0.737
                      	key = 0.09255 0.731
                      	key = 0.08148 0.726
                      	key = 0.07054 0.717
                      	key = 0.05964 0.714
                      	key = 0.04883 0.709
                      	key = 0.03814 0.701
                      	key = 0.02754 0.695
                      	key = 0.01913 0.551
                      	key = 0.01204 0.404
                      	key = 0.00877 0.32
                      	key = 0.00682 0.20
                      	key = 0.00483 0.15
                      	key = 0.0028 0.11
                      	key = 0.0018 0.08
                      	key = 0.0005 0.05
                      	key = 0.0000 0.03
                      }""").getAll("key"));
//        for (double d = 1.0; d >= 0.0; d -= 0.001) {
//            System.out.println(SRB.interpolation(d));
//        }
    }
    private static boolean check(double d, double min, double max) {
        return (d > min) || (d < max);
    }
}
