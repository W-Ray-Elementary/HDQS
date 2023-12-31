package com.plzEnterCompanyName.HDQS.io;

import com.plzEnterCompanyName.HDQS.util.Lexicon;

import java.io.File;

/**
 * A read only configure file.
 */
public class ConfigureFile {
    public final File cfgFile;
    public final Lexicon defaultValue;

    private String comments = "";

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ConfigureFile(File cfgFile, String defaultValue) {
        this.cfgFile = cfgFile;
        this.defaultValue = Lexicon.valueOf(defaultValue).get(0);
    }

    public Lexicon read() {
        if (!cfgFile.exists()) {
            String cfgStr = "";
            if (!comments.isEmpty()) {
                cfgStr += comments;
                cfgStr += "\n";
            }
            cfgStr += String.valueOf(defaultValue);
            FileAndString.write(cfgFile, cfgStr, true);
            return defaultValue;
        }
        return Lexicon.valueOf(cfgFile).get(0);
    }
}
