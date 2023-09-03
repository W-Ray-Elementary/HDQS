package com.plzEnterCompanyName.HDQS.module;

import com.plzEnterCompanyName.HDQS.util.Lexicon;

public interface PreservableModule {
    void load(Lexicon l);
    Lexicon save();
}
