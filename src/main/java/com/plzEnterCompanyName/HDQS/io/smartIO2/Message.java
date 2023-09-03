package com.plzEnterCompanyName.HDQS.io.smartIO2;

import com.plzEnterCompanyName.HDQS.util.LEXICON;

import java.util.List;

public record Message(String title, List<String> texts, List<Info> infos,
                      List<Operation> operations, LEXICON advancedInfo)
{ }
