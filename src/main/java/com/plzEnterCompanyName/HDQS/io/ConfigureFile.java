package com.plzEnterCompanyName.HDQS.io;

import com.plzEnterCompanyName.HDQS.util.Lexicon;

import java.io.File;

/**
 * A read only configure file.
 */
public class ConfigureFile {
    public final File cfgFile;
    public final Lexicon defaultValue;

    public ConfigureFile(File cfgFile, String defaultValue) {
        this.cfgFile = cfgFile;
        this.defaultValue = Lexicon.valueOf(defaultValue).get(0);
    }

    public Lexicon read() {
        if (!cfgFile.exists())
            return defaultValue;
        return Lexicon.valueOf(cfgFile).get(0);
    }
}
