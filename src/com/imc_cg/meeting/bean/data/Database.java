package com.imc_cg.meeting.bean.data;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

import java.util.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;



public class Database
{

// Database objects
Connection conn;
Statement stmt;
PreparedStatement  pst;
static String dsName = "jdbc/intraweb";    //数据源jndi name，见文件pdDB-ora-type2.xml

boolean connected;

public Database()
{
}

public void disconnect()
{
        try
        {
                        if (stmt != null)
                  stmt.close();
                  stmt = null;
                  if(pst != null)
                          pst.close();
                  pst = null;

  }
  catch (SQLException e)
  {
    Debug.print_log(e);
  }

  try
  {
                if (conn != null) {
                  conn.close();
                  conn = null;
                }
  }
  catch (SQLException e)
  {
    Debug.print_log(e);
  }
}

public static Connection getConnection() {
  Connection connection = null;
  try {
    InitialContext initContext = new InitialContext();
    DataSource dsOracle = (DataSource)initContext.lookup(dsName);
    connection = dsOracle.getConnection();
  } catch(Exception e) {
    Debug.print_log(e);  
  }
  return connection;
}

public boolean connect()
{
  try
    {
      InitialContext ctx = new InitialContext();
      Connection dbConn;
      DataSource dataSource = (javax.sql.DataSource)ctx.lookup(dsName);
                        if (dataSource == null)
                  {
              System.out.println("Database connect failed: Datasource object is null");
                    return false;
                  }
                        else
                  {
                    dbConn = dataSource.getConnection();
                    if (dbConn == null)
              {
                       System.out.println("Database connect failed: Connection object is null");
                              return false;
              }
                  }

                        conn = dbConn;

                        stmt = conn.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,java.sql.ResultSet.CONCUR_READ_ONLY);

                        connected = true;


                        return true;
    }
  catch (Exception e)
        {
          Debug.print_log(e);
          return false;
        }
}

public Statement getStatement(){
        return this.stmt;
}

public java.sql.ResultSet query(String sql)
{

  if (!connected)
  {
                System.out.println("Could not run query. No connection to database");
                return null;
  }

  ResultSet rs;

  if (stmt != null)
  {
                try
          {
            rs = stmt.executeQuery(sql);
          }
                catch (SQLException e)
          {
            Debug.print_log(e);
            return null;
          }
  }
  else
  {
                System.out.println("Statement object is null");
                return null;
  }
  return rs;
}

public java.sql.ResultSet query(java.sql.PreparedStatement pStmt)
{

  if (!connected)
  {
                System.out.println("Could not run query. No connection to database");
                return null;
  }

  java.sql.ResultSet rs;

  if (pStmt != null)
  {
                try
          {
            rs = pStmt.executeQuery();
          }
                catch (SQLException e)
          {
            Debug.print_log(e);
            return null;
          }
  }
  else
  {
                System.out.println("Statement object is null");
                return null;
  }

  return rs;
}

public void setAutoCommit(boolean flag)
{
  if (!connected)
  {
                System.out.println("Could not setAutoCommit. No connection to database");
                return;
  }

  try
  {
                conn.setAutoCommit(flag);
  }
  catch (SQLException e)
  {
     Debug.print_log(e);
  }
}

public void commit()
{
  if (!connected)
  {
                System.out.println("Could not commit. No connection to database");
                return;
  }

  try
  {
                conn.commit();
  }
  catch (SQLException e)
  {
    Debug.print_log(e);
  }
}

public void rollback()
{
  if (!connected)
  {
                System.out.println("Could not rollback. No connection to database");
                return;
  }

  try
  {
                conn.rollback();
  }
  catch (SQLException e)
  {
     Debug.print_log(e);
  }
}

public java.sql.PreparedStatement prepareStatement(String sql)
{
  if (!connected)
  {
                System.out.println("Could not prepare statement. No connection to database");
                return null;
  }

  try
  {
                pst = conn.prepareStatement(sql,java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,java.sql.ResultSet.CONCUR_READ_ONLY);
                return pst;
  }
  catch (SQLException e)
  {
                Debug.print_log(e);
                return null;
  }
}

//对blob字段进行操作，用此函数
public java.sql.PreparedStatement prepareStatementBlob(String sql)
{
  if (!connected)
  {
                System.out.println("Could not prepare statement. No connection to database");
                return null;
  }

  try
  {
                pst= conn.prepareStatement(sql);
                return pst;
  }
  catch (SQLException e)
  {
               Debug.print_log(e);
                return null;
  }
}


public int update(String sql)
{

  if (!connected)
  {
                System.out.println("Could not run update. No connection to database");
                return -1;
  }

  int rows;

  if (stmt != null)
  {
                try
                {
                  rows = stmt.executeUpdate(sql);
                }
                catch (SQLException e)
                {
                  Debug.print_log(e);
                  return -1;
                }
  }
  else
  {
                System.out.println("Statement object is null");
                return -1;
  }
  return rows;
}

public int update(java.sql.PreparedStatement pStmt)
{

  if (!connected)
  {
                System.out.println("Could not run update. No connection to database");
                return -1;
  }

  int rows;

  if (pStmt != null)
  {
                try
                {
                  rows = pStmt.executeUpdate();
                }
                catch (SQLException e)
                {
                  Debug.print_log(e);
                  return -1;
                }
  }
  else
  {
          System.out.println("Statement object is null");
          return -1;
  }
  return rows;
}

}

// end Database.java
