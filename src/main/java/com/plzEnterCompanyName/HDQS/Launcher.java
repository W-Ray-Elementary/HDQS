package com.plzEnterCompanyName.HDQS;

public class Launcher {
    private static void configureLaunch() {

    }

    public static void main(String[] args) {
        configureLaunch();
        for (int i = 0; i < args.length; i++) {
            if ("-helloWorld".equals(args[i])) {
                helloWorld(args[i + 1]);
            }
        }
        cleanUp();
    }

    private static void helloWorld(String arg) {
        switch (arg) {
            case "true", "True", "TRUE" -> {
                System.out.println("你好世界！");
                System.out.println("Hello world!");
            }
            case "false", "False", "FALSE" -> {}
            default -> throw new RuntimeException("Invalid parameter: " + arg);
        }
    }

    private static void cleanUp() {

    }
}
