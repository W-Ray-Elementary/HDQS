package com.plzEnterCompanyName.HDQS.text;

import java.io.PrintStream;

public class Windows10LinePrinter implements LinePrinter {

    private PrintStream printStream;

    public Windows10LinePrinter() {
        this.printStream = System.out;
    }

    public Windows10LinePrinter(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override
    public void printb(String x) {
        printStream.print('\r' + x);
    }

    @Override
    public void printb(Object x) {
        printStream.print('\r' + String.valueOf(x));
    }
}
