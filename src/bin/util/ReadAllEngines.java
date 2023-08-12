package bin.util;

import bin.io.FileAndString;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ReadAllEngines {
    public static void main(String[] args) {
        List<File> allEngineFilesInRO;
        List<LEXICON> allEnginesInRO = new ArrayList<>();
        allEngineFilesInRO = findCFG(new File("E:\\KSP Lite\\GameData\\RealismOverhaul\\Engine_Configs"));
        preProcess(allEngineFilesInRO, allEnginesInRO);

        List<LEXICON> blockList = new ArrayList<>();
        for (LEXICON engineConfig : allEnginesInRO) {
            String s = engineConfig.getName();
            if (s.equals("EXAMPLE]]:FOR[RealismOverhaulEngine")) blockList.add(engineConfig);
            if (s.equals("ult[*],~minActiveEngines[*")) blockList.add(engineConfig);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("parent");
        sb.append('\t');

        sb.append('\n');
        allEnginesInRO.removeAll(blockList);
        for (LEXICON PART : allEnginesInRO) {

        }
        System.out.println(sb);
    }

    private static void preProcess(List<File> files, List<LEXICON> lexicons) {
        for (File file : files) {
            lexicons.add(LEXICON.getInstance(file));
        }
        for (LEXICON engineConfig : lexicons) {
            String o = engineConfig.getName();
            String engineName;
            engineName = o.substring(o.indexOf(':') + 17, o.lastIndexOf(':') - 2);
            engineConfig.setName(engineName);
        }
    }

    public static List<File> findCFG(File target) {
        File[] files = target.listFiles();
        Objects.requireNonNull(files);
        List<File> returnVal = new ArrayList<>();
        for (File file : files) {
            if (file.isDirectory()) {
                returnVal.addAll(findCFG(file));
            }
            if (file.getAbsolutePath().endsWith(".cfg")) {
                returnVal.add(file);
            }
        }
        return returnVal;
    }
}
