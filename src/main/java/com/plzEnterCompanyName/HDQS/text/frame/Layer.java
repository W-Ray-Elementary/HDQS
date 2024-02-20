package com.plzEnterCompanyName.HDQS.text.frame;

import java.util.ArrayList;
import java.util.List;

import com.plzEnterCompanyName.HDQS.util.lexicon.Lexicon;
import com.plzEnterCompanyName.HDQS.util.lexicon.Lexicons;

public class Layer {

    protected final String type;
    protected final String position;
    protected final BlockTypesetter typesetter;

    public Layer(Lexicon layerConfig, List<Lexicon> bTConfigs) {
        this.type = layerConfig.getFirst("type");
        this.position = layerConfig.getFirst("position");
        List<Object> contentsObjs = new ArrayList<>(List.of(layerConfig.getAll("", true)));
        List<Lexicon> ls = new ArrayList<>();
        for (Object contentObj : contentsObjs) {
            if (contentObj instanceof Lexicon l) {
                ls.add(l);
            }
        }
        SupportedBT_Position posHandle = convertEnum();
        switch (type) {
            case "SeparateLine" -> typesetter = new BT_SeparateLine(
                    posHandle,
                    priorityForBT(ls, bTConfigs, "SeparateLine"));
            case "Tittle" -> typesetter = new BT_Tittle(
                    posHandle,
                    priorityForBT(ls, bTConfigs, "Tittle"));
            case "Info" -> typesetter = new BT_Info(
                    posHandle,
                    priorityForBT(ls, bTConfigs, "Info"));
            case "Operation" -> typesetter = new BT_Operation(
                    posHandle,
                    priorityForBT(ls, bTConfigs, "Operation"));
            case "Warning" -> typesetter = new BT_Warning(
                    posHandle,
                    priorityForBT(ls, bTConfigs, "Warning"));
            case "Text" -> typesetter = new BT_Text(
                    posHandle,
                    priorityForBT(ls, bTConfigs, "Text"));
            default ->
                throw new UnsupportedOperationException("Unsupported BlockComposition type: " + type);
        }
    }

    private static Lexicon priorityForBT(
            List<Lexicon> first,
            List<Lexicon> second,
            String name) {
        Lexicon firstContent = Lexicons.orderName(first, "BlockTypesetter", name);
        return firstContent == null ? Lexicons.strictOrderName(second, "BlockTypesetter", name) : firstContent;
    }

    private SupportedBT_Position convertEnum() {
        SupportedBT_Position posHandle;
        switch (position) {
            case "RIGHT" -> posHandle = SupportedBT_Position.RIGHT;
            case "UP" -> posHandle = SupportedBT_Position.UP;
            case "LEFT" -> posHandle = SupportedBT_Position.LEFT;
            case "DOWN" -> posHandle = SupportedBT_Position.DOWN;
            default ->
                throw new UnsupportedOperationException("Unsupported BC_Position: " + position);
        }
        return posHandle;
    }

    @Override
    public String toString() {
        return typesetter.toString();
    }
}
