package com.plzEnterCompanyName.HDQS.io;

import com.plzEnterCompanyName.HDQS.util.Lexicon;

import java.io.File;

public class ConfigureFile {
    public final String name;
    public final File cfgFile;
    public final String defaultValue;

    public ConfigureFile(String name, File cfgFile, String defaultValue) {
        this.name = name;
        this.cfgFile = cfgFile;
        this.defaultValue = defaultValue;
    }

    public Lexicon read() {
        if (!cfgFile.exists())
            return new Lexicon(defaultValue);
        return Lexicon.valueOf(cfgFile).get(0);
    }
}
