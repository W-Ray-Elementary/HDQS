package com.plzEnterCompanyName.HDQS.io;

import com.plzEnterCompanyName.HDQS.io.smartIO2.In;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class PlayerIn implements In {

    private static final Scanner SCANNER = new Scanner(System.in);

    private static final BufferedReader BF = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public String next() {
        return SCANNER.next();
    }

    @Override
    public void blankOperate() {
        try {
            BF.readLine();
        } catch (Exception ignored) {
        }
    }
}
