package com.imc_cg.meeting.bean.util;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class Tools{
  /**
         *
         */
  public static String changeTextToHtml(String text) {
    //若原字符串为空，则返回空对象
                if (text == null) {
                        return null;
    }

                //保存原字符串，方法结束时此字符串被转换成HTML格式的字符串
                String strSource = text;
                //转换字符串的临时空间
                StringBuffer sbTarget = new StringBuffer();

                //纯文本中要转换的字符或字符串
                char[] charArraySource  = {'<',   '>',   '&',    '"',     '\n'};
                //纯文本中要转换的字符或字符串对应的HTML表达方式
                String[] strArrayTarget = {"&lt;","&gt;","&amp;","&quot;","<br>"};

                //记录处理过特殊字符的位置
                int intStart = 0;

                //依次检查每一个源字符串的字符或字符串
                for (int i = 0; i < strSource.length(); i++) {
                        //字符串中包含要转换的字符或字符串，则进行相应转换
                        for (int j = 0; j < charArraySource.length; j++) {
                                //当前检查的字符是要转换的特殊字符，则进行处理
                                if (strSource.charAt(i) == charArraySource[j]) {
                                        sbTarget.append(strSource.substring(intStart, i));
                                        sbTarget.append(strArrayTarget[j]);
                                        intStart = i + 1;
                                        continue;
                                }
                        }
                }
                //将所有处理位置之后的字符串放入目标字符串中
          sbTarget.append(strSource.substring(intStart));

                //转换完成，返回转换结果
                return sbTarget.toString();
  }

  public static String changeTextToSQLValue(String text) {
    //若原字符串为空，则返回空对象
                if (text == null) {
                        return null;
    }

    //保存原字符串，方法结束时此字符串被转换成HTML格式的字符串
                String strSource = text;
                //转换字符串的临时空间
                StringBuffer sbTarget = new StringBuffer();

                //纯文本中要转换的字符或字符串
                char[] charArraySource  = {'\''};
                //纯文本中要转换的字符或字符串对应的HTML表达方式
                String[] strArrayTarget = {"''"};

                //记录处理过特殊字符的位置
                int intStart = 0;

                //依次检查每一个源字符串的字符或字符串
                for (int i = 0; i < strSource.length(); i++) {
                        //字符串中包含要转换的字符或字符串，则进行相应转换
                        for (int j = 0; j < charArraySource.length; j++) {
                                //当前检查的字符是要转换的特殊字符，则进行处理
                                if (strSource.charAt(i) == charArraySource[j]) {
                                        sbTarget.append(strSource.substring(intStart, i));
                                        sbTarget.append(strArrayTarget[j]);
                                        intStart = i + 1;
                                        continue;
                                }
                        }
                }
                //将所有处理位置之后的字符串放入目标字符串中
          sbTarget.append(strSource.substring(intStart));

                //转换完成，返回转换结果
                return sbTarget.toString();
  }
}
