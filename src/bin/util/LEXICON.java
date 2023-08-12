package bin.util;

import bin.io.FileAndString;

import java.io.File;
import java.util.*;

/**
 * 用于以cfg格式读取、写入配置文件
 *
 * <p>
 * 事实上，{@code JSON}拥有与{@code bin.util.LEXICON}相似的功能，且更为完善，
 * {@code JSON}的鲁棒性也强了不知道多少倍，{@code LEXICON}只是一位Java初学者对自己
 * 能力的一次尝试。
 *
 * <p>
 * {@code LEXICON}实现了以下功能：
 * <blockquote><pre>{@code
 *      - String 键值对的增删改查
 *      - String 键值对的值可以是 LEXICON
 *      - LEXICON 与 String 间的互转
 *      - 转为 String 后，结果应是便于人类阅读与修改的
 * }</pre></blockquote>
 * 其中{@code - LEXICON 与 String 间的互转}可以借助{@link FileAndString}实现类似
 * {@code java.io.Serial}的功能，所以{@code LEXICON}不支持{@code java.io.Serial}，
 * {@code LEXICON}同时实现了类似{@code java.io.Properties}的功能，以便于在开发中不经
 * 过编译就修改嵌套型配置文件
 *
 * <p>
 * {@code LEXICON}中的数据通过键值对(key-value)的方式存储，类似于
 * {@link java.util.Map}。其中，key的数据类型只能为{@code String}，
 * value的数据类型可以为{@code String}或{@code LEXICON}。这表明
 * {@code LEXICON}是支持嵌套的，值得注意的是，当尝试嵌套时，{@code LEXICON}中的
 * value不允许<i>存在一个{@code LEXICON}x，使得x与任意一个{@code LEXICON}
 * 进行 == 逻辑运算时，返回值为{@code true}</i>，这是为了避免进行{@code LEXICON}转
 * {@code String}操作时出现{@code StackOverFlowError}。
 *
 * <p>
 * key的值不允许是零长度字符串。
 *
 * <p>
 * key允许重复，除非其他因素的限制（例如内存），{@code LEXICON}允许无数个
 * 重复的key。key不允许零长度字符串。添加键值对时，key的值可
 * 以是null，此时key的值是 String : "null"。
 *
 * <p>
 * {@code LEXICON}支持与{@code String}的转换，当前者转换为后者时，格式就像下面这个
 * 示例：
 * <blockquote><pre>{@code
 *      LEXICON root = new LEXICON("ROOT");
 *      root.add("市场", "0");
 *      root.add("市场容量", "16.6667");
 *      root.add("生产力指标", "3.24");
 *      LEXICON company = new LEXICON("COMPANY");
 *      company.add("员工", "张三");
 *      company.add("员工", "李四");
 *      company.add("员工", "王五");
 *      company.add("设备", "1 普通设备套装");
 *      company.add(new LEXICON("BLANK"));
 *      root.add(company);
 *      System.out.println(root);
 * }</pre></blockquote>
 * 其返回值为
 * <blockquote><pre>{@code
 *     ROOT
 *     {
 *         市场 = 0
 *         市场容量 = 16.6667
 *         生产力指标 = 3.24
 *         COMPANY
 *         {
 *             员工 = 张三
 *             员工 = 李四
 *             员工 = 王五
 *             设备 = 1 普通设备套装
 *             BLANK
 *             {
 *             }
 *         }
 *     }
 * }</pre></blockquote>
 *
 * <p>
 * 当String转换为Lexicon时，key之前的缩进字符（包含“空格”和“制表符”）会被忽略，
 * key到"="之间的空格会被忽略，但当key作为子Lexicon的name时，其后的空格目前不会
 * 被忽略，仍然作为key的一部分。"="到value之间的空格会被忽略，但目前value后的空
 * 格不会忽略，仍然作为value的一部分。不忽略的原因如下：Lexicon的String形式以换
 * 行符'\n'作为单个“语句”的结束。key作为子Lexicon的name时，Lexicon无法处理
 * <i>key与"{"在同一行</i>的情况，所以此时的key后必须换行，若子Lexicon的key后
 * 存在空格，会被视作在同一“语句”内，此时的空格就作为key的一部分。value同理。
 * 故此，以下情况等效：
 * <pre>
 * Truth = Beauty
 * Truth                    =Beauty
 * </pre>
 * 而以下情况不等效（标注了换行符的位置）
 * <pre>
 * example\n
 * {
 * }
 * example    \n
 * {
 * }
 * </pre>
 * value不忽略空格的情况以此类推
 *
 * <p>
 * 当String转换为Lexicon时，Lexicon具有处理注释的能力，但只能处理单行注释。并且
 * 只支持双斜线的形式。
 * <pre>
 * example
 * {
 *     Truth = Beauty // 位于双斜线"//"后的字符会被删除
 * }
 * </pre>
 *
 * <p>
 * {@code LEXICON}中的数据通过键值对(key-value)的方式存储，类似于
 * {@link java.util.Map}。但这不代表{@code LEXICON}实现了{@code Map}。相反，
 * {@code Map}中的绝大多数方法都无法在{@code LEXICON}中找到对应。同时，
 * {@code LEXICON}中的“键值对”实际上放在一个List集合中（这何尝不是一种......）。
 * 这可能会在未来造成问题，需要修改
 *
 * @version LEXICON 0.3.0
 */
public class LEXICON {

    /**
     * {@code LEXICON}之间可能存在父子关系，而{@code LEXICON}的数据又是以键值对的
     * 方式存储。所以需要{@code 子LEXICON}的此变量作为{@code 父LEXICON}的键值对中
     * 的key
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null)
            name = "null";
        if (name.length() == 0)
            throw new IllegalArgumentException("zero length String");
        this.name = name;
    }

    /**
     * {@code LEXICON}中的所有键值对集合
     */
    private final List<Content> contents;

    /**
     * 构造一个没有元素在内的 Lexicon 对象
     * @param name 直接作为 Lexicon 对象的 name 属性的值，传入{@code null}时会重新
     *             赋值为"null"
     */
    public LEXICON(String name) {
        if (name == null)
            name = "null";
        if (name.length() == 0)
            throw new IllegalArgumentException("zero length String");
        this.name = name;
        this.contents = new ArrayList<>();
    }

    /**
     * 完成文本与对象的转换，类似于反序列化
     * @param txtF 纯文本文件，文件编码必须与{@link bin.SETTINGS}中{@code ENCODE}相同
     * @return 根据{@code txtF}创建的对象
     */
    public static LEXICON getInstance(File txtF) {
        return getInstance(FileAndString.read(txtF));
    }

    /**
     * 完成字符串与对象的转换，类似于反序列化
     * @return 根据{@code s}创建的对象
     */
    public static LEXICON getInstance(String s) {
        String[] lines = preProcess(s);
        return readLexicon(lines);
    }

    /*
    * 对即将转换为Lexicon对象的字符串进行预处理，步骤如下
    *     1.按换行符切分为String数组
    *     2.删除注释
    *     3.删除空行
    * */
    private static String[] preProcess(String s) {
        List<String> lines = new ArrayList<>(Arrays.asList(s.split("\n")));
        delComments(lines);
        lines = delBlankLine(lines);
        String[] returnVal = new String[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            returnVal[i] = lines.get(i);
        }
        return returnVal;
    }

    /*
    * 删除所有的注释，注释格式仅支持单行注释，例如：
    *     key = value // 位于双斜线"//"后的字符会被删除
    * */
    public static void delComments(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.contains("//")) {
                lines.set(i, line.substring(0, line.indexOf("//")));
            }
        }
    }

    /*
    * 删除空行，由于删除空行后会导致行数变化，故特殊处理
    * 若一行中仅存在空格或制表符，同样视为空行
    * */
    public static List<String> delBlankLine(List<String> lines) {
        List<String> result = new ArrayList<>();
        for (String line : lines) {
            String lineBur = line;
            lineBur = lineBur.replace("\t", "");
            lineBur = lineBur.replace(" ", "");
            if (!("".equals(lineBur))) {
                result.add(line);
            }
        }
        return result;
    }

    /*
    * 仅接受处理好后的字符串数组，不能包含注释与空行，但支持缩进
    * 由于删除注释有时会产生新的空行，建议的处理顺序如下：
    *     1.删除注释
    *     2.删除空行
    * */
    private static LEXICON readLexicon(String[] lines) {
        return readLexicon(lines, 0);
    }

    /*
    * 把字符串转换为Lexicon的核心，start变量用于处理嵌套
    * 具体格式要求见类注释
    * */
    private static LEXICON readLexicon(String[] lines, int start) {
        String name = lines[start];
        while (name.startsWith("\t") || name.startsWith(" ")) {
            name = name.substring(1);
        }
        LEXICON returnVal = new LEXICON(name);
        syntax :
        {
            if (!(lines[start + 1]).contains("{")) {
                break syntax;
            }
            for (start += 2; start < lines.length; start++) {
                String line = lines[start];
                if (line.contains("}"))
                    return returnVal;
                if (line.contains("=")) {
                    String key = line.substring(0, line.indexOf("="));
                    while (key.startsWith("\t") || key.startsWith(" ")) {
                        key = key.substring(1);
                    }
                    while (key.endsWith(" ")) {
                        key = key.substring(0, key.length() - 1);
                    }
                    String value = line.substring(line.indexOf("=") + 1);
                    while (value.startsWith("\t") || value.startsWith(" ")) {
                        value = value.substring(1);
                    }
                    returnVal.add(key, value);
                } else {
                    LEXICON subLexicon = readLexicon(lines, start);
                    returnVal.add(subLexicon);
                    start += subLexicon.lineCount() - 1;
                }
            }
        }
        // syntax error
        throw new IllegalArgumentException();
    }

    /**
     * 向Lexicon对象中增加键值对，按理说这方法应该叫“put”
     */
    public void add(String key, String value) {
        contents.add(new Content(key, value));
    }

    /**
     * 向Lexicon对象中增加子Lexicon，你不需要传入key，因为Lexicon带有name
     * 属性来作为key
     */
    public void add(LEXICON lexicon) {
        Objects.requireNonNull(lexicon);
        List<LEXICON> allLexicons = this.listOutLexicons();
        allLexicons.add(this);
        if (LEXICON.contains(allLexicons, lexicon))
            throw new IllegalContentException();
        contents.add(new Content(lexicon));
    }

    /*
    * 判断下面这个条件：
    *     LEXICON中的 value存不存在在一个 LEXICON x，使得x与任意一个 LEXICON
    *     进行 == 逻辑运算时，返回值为 true
    * */
    private static boolean contains(List<LEXICON> lexicons, LEXICON lexicon) {
        Objects.requireNonNull(lexicons);
        Object[] ls = lexicons.toArray();
        if (lexicon == null) {
            for (Object l : ls) {
                if (l == null) {
                    return true;
                }
            }
        } else {
            for (Object l : ls) {
                if (lexicon == l) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取这一Lexicon中全部匹配key的数据，一般用于读取子Lexicon和线性数据
     * 默认不启用模糊查询
     * @return 全部匹配的key的对应的value将会被返回，若无匹配的key，返回零
     *         长度数组
     */
    public Object[] getAll(String key) {
        return getAll(key, false);
    }

    /**
     * 获取这一Lexicon中全部匹配key的数据，一般用于读取属性
     * @param fuzzy 是否启用模糊查询
     * @return 全部匹配的key的对应的value将会被返回，若无匹配的key，返回零
     *         长度数组
     */
    public Object[] getAll(String key, boolean fuzzy) {
        if (key == null)
            key = "null";
        ArrayList<Object> values = new ArrayList<>();
        if (fuzzy) for (Content content : contents) {
            if (content.key.contains(key)) {
                values.add(content.value);
            }
        }
        else for (Content content : contents) {
            if (Objects.equals(key, content.key)) {
                values.add(content.value);
            }
        }
        return values.toArray();
    }

    /**
     * 获取这一Lexicon中匹配key的数据，一般用于读取属性
     * 默认不启用模糊查询
     * @return 匹配的key的对应的value将会被返回，若无匹配的key，抛出异常
     */
    public String getFirst(String key) {
        return getFirst(key, false);
    }

    /**
     * 获取这一Lexicon中匹配key的数据，一般用于读取属性
     * @param fuzzy 是否启用模糊查询
     */
    public String getFirst(String key, boolean fuzzy) {
        if (key == null)
            key = "null";
        if (fuzzy) for (Content content : contents) {
            if (content.key.contains(key)) {
                return content.value.toString();
            }
        }
        else for (Content content : contents) {
            if (Objects.equals(key, content.key)) {
                return content.value.toString();
            }
        }
        //
        throw new ContentNotFoundException(key + " 在此 LEXICON 中无对应 value!");
    }

    /*
    * 列出所有的Lexicon对象便于contains方法完成它的任务
    * */
    private List<LEXICON> listOutLexicons() {
        List<LEXICON> listingOutLexicons = new ArrayList<>();
        for (Content content : contents) {
            if (content.isLEXICON) {
                listingOutLexicons.add((LEXICON)content.value);
                listingOutLexicons.addAll(((LEXICON)content.value).listOutLexicons());
            }
        }
        return listingOutLexicons;
    }

    /*
    * 按 key 移除元素
    * */
    public boolean remove(String key) {
        boolean returnVal = false;
        List<Content> removal = new ArrayList<>();
        for (Content content : contents) {
            if (Objects.equals(key, content.key)) {
                removal.add(content);
                returnVal = true;
            }
        }
        contents.removeAll(removal);
        return returnVal;
    }

    /*
    * 设定单一元素
    * */

    @Override
    public String toString() {
        String[] contentsStrings = getStrings();
        StringBuilder sb = new StringBuilder();
        for (String contentsStr : contentsStrings) {
            sb.append(contentsStr);
            sb.append('\n');
        }
        return Objects.toString(sb);
    }

    /*
    * 按格式转换Lexicon对象为字符串
    * */
    private String[] getStrings() {
        String[] contentStrings = new String[lineCount()];
        int currentLineCount = 0;
        contentStrings[currentLineCount] = name;
        currentLineCount++;
        contentStrings[currentLineCount] = "{";
        currentLineCount++;
        for (Content content : contents) {
            if (content.isLEXICON) {
                String[] lexiconStrings = ((LEXICON)content.value).getStrings();
                for (String lexiconString : lexiconStrings) {
                    contentStrings[currentLineCount] = "    " + lexiconString;
                    currentLineCount++;
                }
            } else {
                contentStrings[currentLineCount] =
                        "    " + content.key + " = " + content.value;
                currentLineCount++;
            }
        }
        contentStrings[currentLineCount] = "}";
        return contentStrings;
    }

    /**
     * 数出此Lexicon转换为字符串后会占几行
     * @return 所占行数
     */
    public int lineCount() {
        int lineCount = 0;
        for (Content content : contents) {
            lineCount += content.lineCount();
        }
        return lineCount + 3; // 额外的3行中，一行为LEXICON名，一行为正大括号，一行为反大括号。
    }

    /* 懒得写注释了 */
    static class Content {

        String key;
        Object value;
        boolean isLEXICON;

        public Content(String key, String value) {
            this.key = key;
            this.value = value;
            this.isLEXICON = false;
        }

        public Content(LEXICON lexicon) {
            this.key = lexicon.name;
            this.value = lexicon;
            this.isLEXICON = true;
        }

        public int lineCount() {
            if (isLEXICON) {
                return ((LEXICON)value).lineCount();
            } else {
                return 1;
            }
        }
    }
}
