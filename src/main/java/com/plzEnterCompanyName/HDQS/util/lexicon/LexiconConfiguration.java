package com.plzEnterCompanyName.HDQS.util.lexicon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import com.plzEnterCompanyName.HDQS.util.Configuration;
import com.plzEnterCompanyName.HDQS.util.ConfigurationNotFoundException;

public class LexiconConfiguration implements Configuration {

    private final Lexicon lexicon;

    public LexiconConfiguration(Lexicon l) {
        this.lexicon = l;
    }

    @Override
    public String getName() {
        return lexicon.name;
    }

    @Override
    public int getInt(String key) {
        String str = lexicon.getFirst(key);
        return Integer.parseInt(str);
    }

    @Override
    public boolean getBoolean(String key) {
        String str = lexicon.getFirst(key);
        return Boolean.parseBoolean(str);
    }

    @Override
    public String get(String key) {
        return lexicon.getFirst(key);
    }

    @Override
    public List<String> getList(String key) {
        List<KVEntry> entries = lexicon.getAllKVEntry();
        List<String> getted = new ArrayList<>();
        for (KVEntry entry : entries) {
            if (entry.key.equals(key)) {
                getted.add(entry.getValue());
            }
        }
        return getted;
    }

    @Override
    public List<Configuration> subSets(String key) {
        Iterator<Content> it = lexicon.iterator();
        List<Configuration> getted = new ArrayList<>();
        while (it.hasNext()) {
            Content content = it.next();
            if (content.isLEXICON && Objects.equals(content.key, key)) {
                Lexicon sub = ((Lexicon) content.value);
                getted.add(new LexiconConfiguration(sub));
            }
        }
        if (getted.isEmpty()) {
            throw new ConfigurationNotFoundException();
        }
        return getted;
    }
}
