package com.dts.adminportal.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;

import com.dts.adminportal.util.AesEncryption;

import junit.framework.Assert;


public class SQLiteDbUtil {
	private static Connection conn = null;
	private static String url = "jdbc:sqlite:/"+System.getProperty("user.home")+"/Downloads/";
	public static String dbName;
	
	public SQLiteDbUtil(String dbName) {
		super();
		setDbName(dbName);
	}

	public void connect() {
	        
	        try {
	        	Class.forName("org.sqlite.JDBC");
	            conn = DriverManager.getConnection(url+getDbName());
	            
	            System.out.println("Connection to SQLite has been established.");
	            
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	           
	        } catch (ClassNotFoundException e) {
	        	System.out.println("! Not found JDBC Driver");
				e.printStackTrace();
			}
	         
	}
	
	public String[] getAllColumnName(ResultSet rs) {
		  String columnName[] = null;
		  try {
		    ResultSetMetaData rsmd = rs.getMetaData();
		    columnName = new String[rsmd.getColumnCount()];
		    for (int col = 1; col <= columnName.length; col++) {
		      String s = rsmd.getColumnName(col);
		      columnName[col - 1] = s;
		    }
		    return columnName;
		  } catch (Exception e) {
		    System.out.println(e);
		    return columnName;
		  }
		}
	public String[] getAllColumnName(String sql) {
		  String columnName[] = null;
		  connect();
  		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs    = stmt.executeQuery(sql);
			columnName= getAllColumnName(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  		
		  
		    return columnName;
		  }
		
	
	public String[] getAllColumnType(ResultSet rs) {
		  String columnTypes[] = null;
		  try {
		    ResultSetMetaData rsmd = rs.getMetaData();
		    columnTypes = new String[rsmd.getColumnCount()];
		    for (int col = 1; col <= columnTypes.length; col++) {
		      String s = rsmd.getColumnTypeName(col);
		      columnTypes[col - 1] = s;
		    }
		    return columnTypes;
		  } catch (Exception e) {
		    System.out.println(e);
		    return columnTypes;
		  }
		}
	
	public boolean getBlobData(String columnName, String sql, String dts_id){
		boolean isGetData = false;
		String dtscs_file = System.getProperty("user.home")+"/Downloads/profile_"+ dts_id + ".dtscs";
		try {
    		connect();
    		Statement stmt  = conn.createStatement();
    		ResultSet rs    = stmt.executeQuery(sql);
    		File file = new File(dtscs_file);
            @SuppressWarnings("resource")
			FileOutputStream fos = new FileOutputStream(file);
	        while (rs.next()) {
	        	InputStream input = rs.getBinaryStream(columnName);
	        	byte[] bytes = IOUtils.toByteArray(input);
	        	AesEncryption aesEncryption = new AesEncryption("MZygpewJsCpRrfOr");
	    		byte[] decryptedData;
				try {
					decryptedData = aesEncryption.decryptBinary(bytes);
					fos.write(decryptedData);
					isGetData = true;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	break;
	        }
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    } catch (IOException e) {
            System.out.println(e.getMessage());
        }
	    finally 
	    {
	        try {
	            if (conn != null) {
	                conn.close();
	            }
	        } catch (SQLException ex) {
	            System.out.println(ex.getMessage());
	        }
	    }
		return isGetData;
	}
	
	public ArrayList<String> getColumnData(String sql, String columnName1, String columnName2){
		ArrayList<String> result = new ArrayList<String>();
		try {
    		connect();
    		Statement stmt  = conn.createStatement();
    		ResultSet rs    = stmt.executeQuery(sql);
    		String temp1, temp2;
	        // loop through the result set
	        while (rs.next()) {
	        	if(columnName2 == null) {
	        		temp1 = rs.getObject(columnName1).toString();
	        		result.add(temp1);
	        	} else {
	        		temp1 = rs.getObject(columnName1).toString();
		        	temp2 = rs.getObject(columnName2).toString();
		        	String temp = temp1 + " " + temp2;
					result.add(temp);
	        	}
	        }
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	    finally 
	    {
	        try {
	            if (conn != null) {
	                conn.close();
	            }
	        } catch (SQLException ex) {
	            System.out.println(ex.getMessage());
	        }
	    }
		return result;
	}

	public static String getDbName() {
		return dbName;
	}

	public static void setDbName(String dbName) {
		SQLiteDbUtil.dbName = dbName;
	}
	public  ArrayList<String> getAllTables(){
		ArrayList<String> tables= new ArrayList<String>();
		String sql = "SELECT * FROM sqlite_master where type='table'";		
		connect();
		Statement stmt;
		
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				tables.add(rs.getObject("name").toString());
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return tables;
	}
	public static void main(String[] args) {
		String hd_UUID="f08a4c48-0887-11e4-9191-0800200c9a11";
		SQLiteDbUtil dbUtil = new SQLiteDbUtil("offlinedatabase_tuning_97ac063a-99ef-4d18-96b1-4dfa54f27fb9.db");
		 ArrayList<String> binFilesActual = dbUtil.getColumnData("Select * from Asset where Uri like '%"+hd_UUID+"%'", "Uri", null);
		 for (int i = 0; i < binFilesActual.size(); i++) {
			System.out.println(binFilesActual.get(i));
		}
		boolean a= dbUtil.getBlobData("Data", "select * from Asset where Uri = '"+binFilesActual.get(0)+"'", hd_UUID);
		 System.out.println(a);
	}
}
	 
	

