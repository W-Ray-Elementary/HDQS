package com.plzEnterCompanyName.HDQS.module;

import com.plzEnterCompanyName.HDQS.util.LEXICON;

public interface PreservableModule {
    void load(LEXICON l);
    LEXICON save();
}
