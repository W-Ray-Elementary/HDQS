package com.plzEnterCompanyName.HDQS.text.frame;

import java.util.ArrayList;
import java.util.List;

import com.plzEnterCompanyName.HDQS.util.Configuration;
import com.plzEnterCompanyName.HDQS.util.ConfigurationNotFoundException;
import com.plzEnterCompanyName.HDQS.util.Configurations;

public class Layer {

    protected final String type;
    protected final String position;
    protected final BlockTypesetter typesetter;

    public Layer(Configuration layerConfig, List<Configuration> btConfigs) {
        this.type = layerConfig.get("type");
        this.position = layerConfig.get("position");
        List<Configuration> cs = new ArrayList<>();
        try {
            cs = layerConfig.subSets("BlockTypesetter");
        } catch (ConfigurationNotFoundException e) {
        }
        SupportedBT_Position posHandle = convertEnum();
        switch (type) {
            case "SeparateLine" -> typesetter = new BT_SeparateLine(
                    posHandle,
                    priorityForBT(cs, btConfigs, "SeparateLine"));
            case "Tittle" -> typesetter = new BT_Tittle(
                    posHandle,
                    priorityForBT(cs, btConfigs, "Tittle"));
            case "Info" -> typesetter = new BT_Info(
                    posHandle,
                    priorityForBT(cs, btConfigs, "Info"));
            case "Operation" -> typesetter = new BT_Operation(
                    posHandle,
                    priorityForBT(cs, btConfigs, "Operation"));
            case "Warning" -> typesetter = new BT_Warning(
                    posHandle,
                    priorityForBT(cs, btConfigs, "Warning"));
            case "Text" -> typesetter = new BT_Text(
                    posHandle,
                    priorityForBT(cs, btConfigs, "Text"));
            default ->
                throw new UnsupportedOperationException("Unsupported BlockComposition type: " + type);
        }
    }

    private static Configuration priorityForBT(
            List<Configuration> first,
            List<Configuration> second,
            String name) {
        Configuration firstContent = Configurations.orderName(first, "BlockTypesetter", name);
        return firstContent == null ? Configurations.strictOrderName(second, "BlockTypesetter", name) : firstContent;
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
