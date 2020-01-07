package com.future.demo.java.pinyin;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class PinyinTests {
    private final static Logger log = LoggerFactory.getLogger(PinyinTests.class);

    /**
     *
     */
    @Test
    public void test() throws BadHanyuPinyinOutputFormatCombination {
        String chineseString = "90 中文 english にほんご 测试";
        StringBuffer buffer = new StringBuffer();
        //字符串转换字节数组
        char[] arr = chineseString.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        //转换类型（大写or小写）
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        //定义中文声调的输出格式
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        //定义字符的输出格式
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);
        for (int i = 0; i < arr.length; i++) {
            //判断是否是汉子字符
            if (java.lang.Character.toString(arr[i]).matches("[\\u4E00-\\u9FA5]+")) {
                String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
                buffer.append(temp[0]);
            } else {
                // 如果不是汉字字符，直接拼接
                buffer.append(arr[i]);
            }
        }
        log.info(buffer.toString());
    }

    /**
     *
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    @Test
    public void test1() throws BadHanyuPinyinOutputFormatCombination {
        String chineseString = "梁国杨氏子九岁，甚聪惠。孔君平诣其父，父不在，乃呼儿出。为设果，果有杨梅。孔指以示儿曰：“此是君家果。”儿应声答曰：“未闻孔雀是夫子家禽。” ";
        StringBuffer buffer = new StringBuffer();
        //字符串转换字节数组
        char[] arr = chineseString.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        //转换类型（大写or小写）
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        //定义中文声调的输出格式
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        //定义字符的输出格式
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);
        for (int i = 0; i < arr.length; i++) {
            //判断是否是汉子字符
            if (java.lang.Character.toString(arr[i]).matches("[\\u4E00-\\u9FA5]+")) {
                String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
                buffer.append(temp[0] + " ");
            } else {
                // 如果不是汉字字符，直接拼接
                buffer.append(arr[i] + " ");
            }
        }
        log.info(buffer.toString());
    }

    /**
     *
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    @Test
    public void test2() throws BadHanyuPinyinOutputFormatCombination {
        String chineseString = "孔君平看到杨梅，联想到孩子的姓，就故意逗孩子：“这是你家的水果。”意思是，你姓杨，它叫杨梅，你们本是一家嘛!这信手拈来的玩笑话，很幽默，也很有趣。孩子应声答道：“没听说孔雀是先生您家的鸟。”这回答巧妙在哪里呢?孔君平在姓上做文章，孩子也在姓上做文章，由孔君平的“孔”姓想到了孔雀；最妙的是，他没有生硬地直接说“孔雀是夫子家禽”，而是采用了否定的方式，说“未闻孔雀是夫子家禽”，婉转对答，既表现了应有的礼貌，又表达了“既然孔雀不是您家的鸟，杨梅不是我家的果，所以请您知道这个道理”这个意思，因为他要承认孔雀是他家的鸟，他说的话才立得住脚。这足以反映出孩子思维的敏捷，语言的机智幽默。使孔君平无语可答了。这是故事中的重点部分";
        StringBuffer buffer = new StringBuffer();
        //字符串转换字节数组
        char[] arr = chineseString.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        //转换类型（大写or小写）
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        //定义中文声调的输出格式
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        //定义字符的输出格式
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);
        for (int i = 0; i < arr.length; i++) {
            //判断是否是汉子字符
            if (java.lang.Character.toString(arr[i]).matches("[\\u4E00-\\u9FA5]+")) {
                String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
                buffer.append(temp[0] + " ");
            } else {
                // 如果不是汉字字符，直接拼接
                buffer.append(arr[i] + " ");
            }
        }
        log.info(buffer.toString());
    }

    @Test
    public void testTraditionalChinese() throws BadHanyuPinyinOutputFormatCombination {
        String chineseString = "在線繁體字轉換器";
        StringBuffer buffer = new StringBuffer();
        //字符串转换字节数组
        char[] arr = chineseString.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        //转换类型（大写or小写）
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        //定义中文声调的输出格式
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        //定义字符的输出格式
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);
        for (int i = 0; i < arr.length; i++) {
            //判断是否是汉子字符
            if (java.lang.Character.toString(arr[i]).matches("[\\u4E00-\\u9FA5]+")) {
                String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
                buffer.append(temp[0] + " ");
            } else {
                // 如果不是汉字字符，直接拼接
                buffer.append(arr[i] + " ");
            }
        }
        log.info(buffer.toString());
    }
}
