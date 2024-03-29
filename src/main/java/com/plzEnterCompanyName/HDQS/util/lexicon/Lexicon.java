package com.plzEnterCompanyName.HDQS.util.lexicon;

import com.plzEnterCompanyName.HDQS.SETTINGS;
import com.plzEnterCompanyName.HDQS.io.FileAndString;
import com.plzEnterCompanyName.HDQS.util.FormatCheck;
import com.plzEnterCompanyName.HDQS.util.StringEscapeUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * 用于以Lexicon格式读取、写入配置文件
 *
 * <p>事实上，{@code JSON}拥有与{@code com.plzEnterCompanyName.HDQS.util.LEXICON}相似的功能，且更为完善，
 * {@code JSON}的鲁棒性也强了不知道多少倍，{@code LEXICON}只是一位Java初学者对自己
 * 能力的一次尝试。
 *
 * <p>{@code LEXICON}实现了以下功能：
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
 * <p>{@code LEXICON}中的数据通过键值对(key-value)的方式存储，类似于
 * {@link java.util.Map}。其中，key的数据类型只能为{@code String}，
 * value的数据类型可以为{@code String}或{@code LEXICON}。这表明
 * {@code LEXICON}是支持嵌套的，值得注意的是，当尝试嵌套时，{@code LEXICON}中的
 * value不允许<i>存在一个{@code LEXICON}x，使得x与任意一个{@code LEXICON}
 * 进行 == 逻辑运算时，返回值为{@code true}</i>，这是为了避免进行{@code LEXICON}转
 * {@code String}操作时出现{@code StackOverFlowError}。
 *
 * <p>key的值不允许是零长度字符串。
 *
 * <p>key允许重复，除非其他因素的限制（例如内存），{@code LEXICON}允许无数个
 * 重复的key。key不允许零长度字符串。添加键值对时，key的值可
 * 以是null，此时key的值是 String : "null"。
 *
 * <p>{@code LEXICON}支持与{@code String}的转换，当前者转换为后者时，格式就像下面
 * 这个示例：
 * <blockquote><pre>{@code
 * LEXICON root = new LEXICON("ROOT");
 * root.add("市场", "0");
 * root.add("市场容量", "16.6667");
 * root.add("生产力指标", "3.24");
 * LEXICON company = new LEXICON("COMPANY");
 * company.add("员工", "张三");
 * company.add("员工", "李四");
 * company.add("员工", "王五");
 * company.add("设备", "1 普通设备套装");
 * company.add(new LEXICON("BLANK"));
 * root.add(company);
 * System.out.println(root);
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
 * <p>当String转换为Lexicon时，Lexicon具有处理注释的能力，但只能处理单行注释。并且
 * 只支持双斜线的形式。
 *
 * <pre>
 * example
 * {
 *     Truth = Beauty // 位于双斜线"//"后的字符会被删除
 * }
 * </pre>
 *
 * <p>{@code LEXICON}中的数据通过键值对(key-value)的方式存储，类似于
 * {@link java.util.Map}。但这不代表{@code LEXICON}实现了{@code Map}。相反，
 * {@code Map}中的绝大多数方法都无法在{@code LEXICON}中找到对应。同时，
 * {@code LEXICON}中的“键值对”实际上放在一个List集合中（这何尝不是一种......）。
 * 这可能会在未来造成问题，需要修改
 */
public class Lexicon implements Iterable<Content> {

    /**
     * {@code LEXICON}之间可能存在父子关系，而{@code LEXICON}的数据又是以键值对的
     * 方式存储。所以需要{@code 子LEXICON}的此变量作为{@code 父LEXICON}的键值对中
     * 的key
     */
    protected final String name;

    /**
     * {@code LEXICON}中的所有键值对集合
     */
    private final List<Content> contents;

    /**
     * 构造一个没有元素在内的 Lexicon 对象
     *
     * @param name 直接作为 Lexicon 对象的 name 属性的值，传入{@code null}时会重新
     *             赋值为"null"
     */
    public Lexicon(String name) {
        FormatCheck.specialString(name, FormatCheck.NULL +
                FormatCheck.ZERO_LENGTH +
                FormatCheck.NEW_LINE);
        this.name = name;
        this.contents = new ArrayList<>();
    }

    /**
     * 完成文本与对象的转换，类似于反序列化
     *
     * @param txtF 纯文本文件，文件编码必须与{@link SETTINGS}中{@code ENCODE}相同
     * @return 根据{@code txtF}创建的对象
     */
    public static List<Lexicon> valueOf(File txtF) {
        String s = FileAndString.read(txtF);
        LScanner scanner = new LScanner(s);
        List<Token> tokens = scanner.scanTokens();
        Token2Lexicon converter = new Token2Lexicon(tokens, txtF);
        return converter.convert();
    }

    /**
     * 完成字符串与对象的转换，类似于反序列化
     *
     * @param s 方法调用者必须确保此值有效并且符合格式
     * @return 根据{@code s}创建的对象
     */
    public static List<Lexicon> valueOf(String s) {
        LScanner scanner = new LScanner(s);
        List<Token> tokens = scanner.scanTokens();
        Token2Lexicon converter = new Token2Lexicon(tokens);
        return converter.convert();
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
    public void add(Lexicon lexicon) {
        Objects.requireNonNull(lexicon);
        List<Lexicon> allLexicons = this.listOutLexicons();
        allLexicons.add(this);
        if (Lexicon.contains(allLexicons, lexicon))
            throw new IllegalContentException();
        contents.add(new Content(lexicon));
    }

    /**
     * 判断下面这个条件：
     * LEXICON中的 value存不存在在一个 LEXICON x，使得x与任意一个 LEXICON
     * 进行 == 逻辑运算时，返回值为 true
     */
    private static boolean contains(List<Lexicon> lexicons, Lexicon lexicon) {
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
     *
     * @return 全部匹配的key的对应的value将会被返回，若无匹配的key，返回零
     * 长度数组
     */
    public Object[] getAll(String key) {
        return getAll(key, false);
    }

    /**
     * 获取这一Lexicon中全部匹配key的数据，一般用于读取属性
     *
     * @param fuzzy 是否启用模糊查询
     * @return 全部匹配的key的对应的value将会被返回，若无匹配的key，返回零
     * 长度数组，返回的类型可能是Lexicon与String
     */
    public Object[] getAll(String key, boolean fuzzy) {
        if (key == null)
            key = "null";
        ArrayList<Object> values = new ArrayList<>();
        if (fuzzy)
            for (Content content : contents) {
                if (content.key.contains(key)) {
                    values.add(content.value);
                }
            }
        else
            for (Content content : contents) {
                if (Objects.equals(key, content.key)) {
                    values.add(content.value);
                }
            }
        return values.toArray();
    }

    public List<KVEntry> getAllKVEntry() {
        List<KVEntry> entries = new ArrayList<>();
        for (Content content : contents) {
            if (!content.isLEXICON) {
                String key = content.key;
                String value = content.value.toString();
                entries.add(new KVEntry(key, value));
            }
        }
        return entries;
    }

    /**
     * 获取这一Lexicon中匹配key的数据，一般用于读取属性
     * 默认不启用模糊查询
     *
     * @return 匹配的key的对应的value将会被返回，若无匹配的key，抛出异常
     */
    public String getFirst(String key) {
        return getFirst(key, false);
    }

    /**
     * 获取这一Lexicon中匹配key的数据，一般用于读取属性
     *
     * @param fuzzy 是否启用模糊查询
     */
    public String getFirst(String key, boolean fuzzy) {
        if (key == null)
            key = "null";
        if (fuzzy)
            for (Content content : contents) {
                if (content.key.contains(key)) {
                    return content.value.toString();
                }
            }
        else
            for (Content content : contents) {
                if (Objects.equals(key, content.key)) {
                    return content.value.toString();
                }
            }
        //
        throw new ContentNotFoundException(key + " 在此 LEXICON 中无对应 value!");
    }

    /**
     * 列出所有的Lexicon对象便于contains方法完成它的任务
     */
    private List<Lexicon> listOutLexicons() {
        List<Lexicon> listingOutLexicons = new ArrayList<>();
        for (Content content : contents) {
            if (content.isLEXICON) {
                listingOutLexicons.add((Lexicon) content.value);
                listingOutLexicons.addAll(((Lexicon) content.value).listOutLexicons());
            }
        }
        return listingOutLexicons;
    }

    /**
     * 按 key 移除元素
     */
    public void remove(String key) {
        List<Content> removal = new ArrayList<>();
        for (Content content : contents) {
            if (Objects.equals(key, content.key)) {
                removal.add(content);
            }
        }
        contents.removeAll(removal);
    }

    @Override
    public Iterator<Content> iterator() {
        return contents.iterator();
    }

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

    /**
     * 按格式转换Lexicon对象为字符串
     */
    private String[] getStrings() {
        String[] contentStrings = new String[lineCount()];
        int currentLineCount = 0;
        contentStrings[currentLineCount] = name;
        currentLineCount++;
        contentStrings[currentLineCount] = "{";
        currentLineCount++;
        for (Content content : contents) {
            if (content.isLEXICON) {
                String[] lexiconStrings = ((Lexicon) content.value).getStrings();
                for (String lexiconString : lexiconStrings) {
                    contentStrings[currentLineCount] = "    " + lexiconString;
                    currentLineCount++;
                }
            } else {
                String escaped = content.value.toString();
                String unescaped = StringEscapeUtils.escapeLexicon(escaped);
                contentStrings[currentLineCount] = "    " + content.key + " = " + unescaped;
                currentLineCount++;
            }
        }
        contentStrings[currentLineCount] = "}";
        return contentStrings;
    }

    /**
     * 数出此Lexicon转换为字符串后会占几行
     *
     * @return 所占行数
     */
    public int lineCount() {
        int lineCount = 0;
        for (Content content : contents) {
            lineCount += content.lineCount();
        }
        return lineCount + 3; // 额外的3行中，一行为LEXICON名，一行为正大括号，一行为反大括号。
    }
}
