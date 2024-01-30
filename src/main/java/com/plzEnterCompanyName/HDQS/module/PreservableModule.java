package com.plzEnterCompanyName.HDQS.module;

import com.plzEnterCompanyName.HDQS.util.lexicon.Lexicon;

public interface PreservableModule {
    void load(Lexicon l);
    Lexicon save();
}
