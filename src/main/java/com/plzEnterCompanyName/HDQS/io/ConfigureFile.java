package com.plzEnterCompanyName.HDQS.io;

import com.plzEnterCompanyName.HDQS.util.lexicon.Lexicon;

import java.io.File;

/**
 * A read only configure file.
 */
public class ConfigureFile {
    public final File cfgFile;
    public final String defaultValue;

    private String comments = "";

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ConfigureFile(File cfgFile, String defaultValue) {
        this.cfgFile = cfgFile;
        this.defaultValue = defaultValue;
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
            return Lexicon.valueOf(defaultValue).get(0);
        }
        return Lexicon.valueOf(cfgFile).get(0);
    }
}
