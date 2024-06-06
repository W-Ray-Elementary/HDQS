package com.plzEnterCompanyName.HDQS.text.frame.animate;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.text.frame.Frame;

public class Typewriter extends Thread {
    private final MessageIterator mi;
    private boolean canRun;
    private final Frame target;

    public void canNotRun() {
        this.canRun = false;
    }

    public Typewriter(MessageIterator mi, Frame target) {
        this.mi = mi;
        this.target = target;
        canRun = true;
    }

    @Override
    public void run() {
        while (canRun && mi.hasNext()) {
            Message iteration = mi.next();
            target.out(iteration);
            try {
                Thread.sleep(50);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
