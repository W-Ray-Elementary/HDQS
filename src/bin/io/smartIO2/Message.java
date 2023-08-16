package bin.io.smartIO2;

import bin.util.LEXICON;

import java.util.List;

public record Message(String title, List<String> texts, List<Info> infos,
                      List<Operation> operations, LEXICON advancedInfo)
{ }
