package com.imc_cg.meeting.bean.util;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 大连超维计算机技术有限公司</p>
 * @author 孙婷婷
 * @version 1.0
 */
import java.util.*;
import com.imc_cg.meeting.bean.data.*;

public class SectionPage {
  /**
   * 得到传入页数要显示的记录集
   * @param PageNum 当前页数
   * @param defaultPageNum 每页显示页数
   * @param colName 要查询的字段名
   * @param tableName 表名
   * @param qualification 查询的条件
   * @return
   */
  public String[][] recordQry(int pageNum,int defaultRecordNum,String[] colName,String tableName,String qualification) {
    SqlGenerator sg = new SqlGenerator();
    String[] colNames = new String[colName.length+1];
    for (int i = 0; i < colName.length; i++) {
      colNames[i] = colName[i];
    }
    colNames[colNames.length-1] = "rown";
    StringBuffer strBufferFore = new StringBuffer();
    StringBuffer strBuffer = new StringBuffer();
    strBufferFore.append("select ");
    // 循环设置列名
    for (int i = 0; i < colName.length - 1; i++) {
      strBufferFore.append(colName[i]);
      strBufferFore.append(",");
    }
    // 最后列名
    strBufferFore.append(colName[colName.length - 1]);
    strBuffer.append(strBufferFore.toString());
    strBuffer.append(",rown from (");
    strBuffer.append(strBufferFore.toString());
    strBuffer.append(",rownum rown from (");
    strBuffer.append(strBufferFore.toString());
    strBuffer.append(" from ");
    strBuffer.append(tableName);
    strBuffer.append(" where ");
    strBuffer.append(qualification);
    strBuffer.append(") where rownum<=");
    strBuffer.append(pageNum * defaultRecordNum);
    strBuffer.append(") where rown>");
    strBuffer.append((pageNum-1) * defaultRecordNum);
    String[][] results = sg.getMultiRowMultiCol(strBuffer.toString(),colNames);
    String sql1 = strBuffer.toString();
    return results;
  }
  /**
   * 得到传入页数要显示的记录集
   * @param PageNum 当前页数
   * @param defaultPageNum 每页显示页数
   * @param colName 要查询的字段名
   * @param tableName 表名
   * @param qualification 查询的条件
   * @return
   */
  public String[][] recordQry2(int pageNum,int defaultRecordNum,String[] colName,String tableName,String qualification) {
    SqlGenerator sg = new SqlGenerator();
    String[] colNames = new String[colName.length+1];
    for (int i = 0; i < colName.length; i++) {
      colNames[i] = colName[i];
    }
    colNames[colNames.length-1] = "rown";
    StringBuffer strBufferFore = new StringBuffer();
    StringBuffer strBuffer = new StringBuffer();
    strBufferFore.append("select distinct ");
    // 循环设置列名
    for (int i = 0; i < colName.length - 1; i++) {
      strBufferFore.append(colName[i]);
      strBufferFore.append(",");
    }
    // 最后列名
    strBufferFore.append(colName[colName.length - 1]);
    strBuffer.append(strBufferFore.toString());
    strBuffer.append(",rown from (");
    strBuffer.append(strBufferFore.toString());
    strBuffer.append(",rownum rown from (");
    strBuffer.append(strBufferFore.toString());
    strBuffer.append(" from ");
    strBuffer.append(tableName);
    strBuffer.append(" where ");
    strBuffer.append(qualification);
    strBuffer.append(") where rownum<=");
    strBuffer.append(pageNum * defaultRecordNum);
    strBuffer.append(") where rown>");
    strBuffer.append((pageNum-1) * defaultRecordNum);
    String[][] results = sg.getMultiRowMultiCol(strBuffer.toString(),colNames);
    String sql1 = strBuffer.toString();
    return results;
  }
  /**
   * 根据表名获得表总记录数
   * @param tableName
   * @return
   */
  public int totalRecordNum (String tableName) {
    SqlGenerator sg = new SqlGenerator();
    String sql = "select * from " + tableName;
    return sg.getRecordCount(sql);
  }

  public int totalRecordNum (String qualification,String tableName) {
    SqlGenerator sg = new SqlGenerator();
    String sql = "select count(*) count from " + tableName + " where " + qualification;
    return Integer.parseInt(sg.getSnglRowSnglCol(sql,"count"));
  }

  public int diffdate(String invalidDate) {
    Calendar rightNow = Calendar.getInstance();
    rightNow.set(Integer.parseInt(invalidDate.substring(1, 4)),
                 Integer.parseInt(invalidDate.substring(6, 7)),
                 Integer.parseInt(invalidDate.substring(9, 10)));

    Date invalid = rightNow.getTime();
    Date now = new Date();
    long invalidTime = invalid.getTime();
    long nowTime = now.getTime();
    long chaTime = invalidTime - nowTime;
    long diffTime = chaTime / 1000 / 60 / 60 / 24;
    return (int) diffTime;
  }

  /**
   * 得到传入页数要显示的记录集
   * @param PageNum 当前页数
   * @param defaultPageNum 每页显示页数
   * @param colName 要查询的字段名
   * @param tableName 表名
   * @param qualification 查询的条件
   * @return
   */
  public String[] recordSngQry(int pageNum,int defaultRecordNum,String[] colName,String tableName,String qualification) {
    SqlGenerator sg = new SqlGenerator();
    String[] colNames = new String[colName.length+1];
    for (int i = 0; i < colName.length; i++) {
      colNames[i] = colName[i];
    }
    colNames[colNames.length-1] = "rown";
    StringBuffer strBufferFore = new StringBuffer();
    StringBuffer strBuffer = new StringBuffer();
    strBufferFore.append("select ");
    // 循环设置列名
    for (int i = 0; i < colName.length - 1; i++) {
      strBufferFore.append(colName[i]);
      strBufferFore.append(",");
    }
    // 最后列名
    strBufferFore.append(colName[colName.length - 1]);
    strBuffer.append(strBufferFore.toString());
    strBuffer.append(",rown from (");
    strBuffer.append(strBufferFore.toString());
    strBuffer.append(",rownum rown from (");
    strBuffer.append(strBufferFore.toString());
    strBuffer.append(" from ");
    strBuffer.append(tableName);
    strBuffer.append(" where ");
    strBuffer.append(qualification);
    strBuffer.append(") where rownum<=");
    strBuffer.append(pageNum * defaultRecordNum);
    strBuffer.append(") where rown>");
    strBuffer.append((pageNum-1) * defaultRecordNum);
    String[] results = sg.getSnglRowMultiCol(strBuffer.toString(),colNames);
    return results;
  }

  /**
   * 得到传入页数要显示的记录集
   * @param PageNum 当前页数
   * @param defaultPageNum 每页显示页数
   * @param colName 要查询的字段名
   * @param tableName 表名
   * @param qualification 查询的条件
   * @return
   */
  public String[][] recordQry(int pageNum,int defaultRecordNum,String[] secColName,String tableName,String qualification, String[] colName) {
    SqlGenerator sg = new SqlGenerator();
    String[] colNames = new String[colName.length+1];
    for (int i = 0; i < colName.length; i++) {
      colNames[i] = colName[i];
    }
    colNames[colNames.length-1] = "rown";
    StringBuffer strBufferFore = new StringBuffer();
    StringBuffer strBuffer = new StringBuffer();
    strBufferFore.append("select ");
    // 循环设置列名
    for (int i = 0; i < colName.length - 1; i++) {
      strBufferFore.append(colName[i]);
      strBufferFore.append(",");
    }
    // 最后列名
    strBufferFore.append(colName[colName.length - 1]);
    strBuffer.append(strBufferFore.toString());
    strBuffer.append(",rown from (");
    strBuffer.append(strBufferFore.toString());
    strBuffer.append(",rownum rown from (");
    strBuffer.append("select ");
    for (int i = 0; i < secColName.length - 1; i++) {
      strBuffer.append(secColName[i]);
      strBuffer.append(",");
    }
    strBuffer.append(secColName[secColName.length - 1]);
    strBuffer.append(" from ");
    strBuffer.append(tableName);
    strBuffer.append(" where ");
    strBuffer.append(qualification);
    strBuffer.append(") where rownum<=");
    strBuffer.append(pageNum * defaultRecordNum);
    strBuffer.append(") where rown>");
    strBuffer.append((pageNum-1) * defaultRecordNum);
    String sql1 = strBuffer.toString();
    String[][] results = sg.getMultiRowMultiCol(strBuffer.toString(),colNames);
    return results;
  }
}