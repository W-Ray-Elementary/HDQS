package com.plzEnterCompanyName.HDQS.util;

import java.math.BigDecimal;
import java.util.*;

/**
 * 二维插值曲线，按照关键点插值对应数值。
 *
 * <p>关键点的数据类型为double
 * <p>关键点不能没有，当关键点只有一个时，按常量输出，关键点数量大于或等于二时，进行线性插值。
 *
 * <p>
 * 构造Curve //忘记要说什么了
 */
public class Curve {

    /**
     * 所有的关键点，在构造Curve对象时，关键点已经去除重复值并且进行了升序排序
     */
    private List<Key> keys;

    /**
     * 由给定值构造Curve对象。
     * @param args 一系列关键点，允许乱序与重复，构造时会处理它们。
     */
    public Curve(String[] args) {
        setKeys(args);
    }

    /**
     * 处理 ClassCastException
     * @param src 必须确保是字符串
     */
    public Curve(Object[] src) {
        setKeys(Arrays.stream(src).toArray(String[]::new));
    }

    /**
     * 重置所有关键点
     * @param args 一系列关键点，允许乱序与重复，此方法会处理它们。
     */
    public void setKeys(String[] args) {
        List<Key> orgKeys = new ArrayList<>();
        Objects.requireNonNull(args);
        for (String arg : args) {
            char c;
            int keyStart = -1;
            int keyLen = -1;
            int valueStart = -1;
            int valueLen = -1;
            for (int i = 0; i < arg.length(); i++) {
                c = arg.charAt(i);
                if (keyStart == -1) {
                    if (c == ' ' || c == '\t') continue;
                    else keyStart = i;
                }
                if (keyLen == -1) {
                    if (c != ' ' && c != '\t') continue;
                    else keyLen = i-keyStart;
                }
                if (valueStart == -1) {
                    if (c == ' ' || c == '\t') continue;
                    else valueStart = i;
                }
                if (valueLen == -1) {
                    if (c == ' ' || c == '\t')
                     valueLen = i-valueStart;
                }
            }
            if (valueLen == -1) {
                valueLen = arg.length() -valueStart;
            }
            String k = arg.substring(keyStart, keyStart + keyLen);
            String v = arg.substring(valueStart, valueStart + valueLen);
            orgKeys.add(new Key(Double.valueOf(k), Double.valueOf(v)));
        }
        keys = new ArrayList<>(new HashSet<>(orgKeys));
        keys.sort(Key.getComparator());
    }

    /**
     * 寻找关键点进行插值，默认不检查范围
     * @param input 输入值
     * @return 输出值
     */
    public double interpolation(double input) {
        return interpolation(input, false);
    }

    /**
     * 寻找关键点进行插值
     * @param input 输入值
     * @param checkBounds 是否检查范围
     * @return 输出值
     */
    public double interpolation(double input, boolean checkBounds) {
        if (keys.isEmpty())
            throw new RuntimeException("The curve has not been defined yet.");
        if (keys.size() == 1) return keys.get(0).v;
        if (checkBounds) {
            double max = keys.get(keys.size()-1).k;
            double min = keys.get(0).k;
            if (input > max) {
                throw new IllegalArgumentException("Input is out of bounds, input is " + input  +
                        ", max value is " + max);
            }
            if (input < min) {
                throw new IllegalArgumentException("Input is out of bounds, input is " + input  +
                        ", min value is " + min);
            }
        }
        double k1;
        double v1;
        double k2;
        double v2;
        double output = Double.NaN;
        for (int i = 0; i < keys.size(); i++) {
            if (input == keys.get(i).k) return keys.get(i).v;
            k1 = keys.get(i).k;
            v1 = keys.get(i).v;
            if (i == keys.size() - 1) {
                k1 = keys.get(i-1).k;
                v1 = keys.get(i-1).v;
                k2 = keys.get(i).k;
                v2 = keys.get(i).v;
                return linear(input, k1, k2, v1, v2);
            } else if (input < k1) {
                if (i == 0) {
                    k2 = keys.get(i+1).k;
                    v2 = keys.get(i+1).v;
                } else {
                    k2 = keys.get(i-1).k;
                    v2 = keys.get(i-1).v;
                }
                return linear(input, k1, k2, v1, v2);
            }
        }
        return output;
    }

    /*
    * 线性插值，之后可能考虑非线性插值
    * */
    private double linear(double input, double k1, double k2, double v1, double v2) {
        double kc = (input - k1)/(k2 - k1);
        return ((v2 - v1) * kc) + v1;
    }


    /**
     * 本类中equals()方法仅比较部分属性！
     */
    static class Key {
        Double k;
        Double v;

        public Key(Double k, Double v) {
            this.k = k;
            this.v = v;
        }

        public static Comparator<Key> getComparator() {
            return (o1, o2) -> {
                BigDecimal bd1 = new BigDecimal(String.valueOf(o1.k));
                BigDecimal bd2 = new BigDecimal(String.valueOf(o2.k));
                return bd1.compareTo(bd2);
            };
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Key key = (Key) o;

            return k.equals(key.k);
        }

        @Override
        public int hashCode() {
            return k.hashCode();
        }
    }
}
