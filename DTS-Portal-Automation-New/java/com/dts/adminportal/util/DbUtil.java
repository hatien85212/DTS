package com.dts.adminportal.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class DbUtil {
	private static final String USER = "ubuntu";
	private static final String PASS = "apAdm1n!";
	private static final String IP = "54.221.241.221";
	private static final String HOST = "127.0.0.1";
	//

	public static void main(String[] args){
		updateStatment("update partner set invited_at = '2015-03-20 00:00:00' where name = 'Test321321'");
	}
	
	
	
	public static String selectStatment(String selectquery) {

		String status="";
		String apkFile = "DTS.ppk";
		Session session = null;
		int assignedPort;
		JSch jsch = new JSch();
		String uri = System.getProperty("user.dir") + "\\asset\\" + apkFile;
		try {
			jsch.addIdentity(uri);
			session = jsch.getSession(USER, IP, 22);
			Properties prop = new Properties();
			prop.put("StrictHostKeyChecking", "no");
			prop.put("Compression", "yes");
			prop.put("ConnectionAttempts", "2");
			session.setConfig(prop);
			session.connect();
			if (session.isConnected()) {
				System.out.println("channel connected");
				assignedPort = session.setPortForwardingL(5431, HOST, 5432);
				System.out.println(assignedPort);
				Statement stmt = null;
				ResultSet rs = null;
				System.out.println("-------- PostgreSQL "
						+ "JDBC Connection Testing ------------");
				try {

					Class.forName("org.postgresql.Driver");

				} catch (ClassNotFoundException e) {
					System.out.println("Where is your PostgreSQL JDBC Driver? "
							+ "Include in your library path!");
					e.printStackTrace();
				}
				System.out.println("PostgreSQL JDBC Driver Registered!");

				Connection connection = null;
				try {
					System.out
							.println("Connect to jdbc:postgresql://localhost:5432/dts_adminportal");
					connection = DriverManager.getConnection(
							"jdbc:postgresql://localhost:5431/dts_adminportal",
							"apadmin", PASS);
					connection.setAutoCommit(false);
				} catch (SQLException e) {
					System.out
							.println("Connection Failed! Check output console");
					e.printStackTrace();
				}
				if (connection != null) {
					System.out
							.println("You made it, take control your database now!");
				} else {
					System.out.println("Failed to make connection!");
				}

				stmt = connection.createStatement();
				stmt.setFetchSize(0);
				//String sql = "SELECT status FROM brand where name='"+brandName+"';";
				
				rs = stmt.executeQuery(selectquery);
				while (rs.next()) {
					try{
						
						System.out.println(rs.getObject(1).toString());
						status = status + " " + rs.getObject(1).toString();
//						list.add(status);
					} catch(Exception e){
						e.printStackTrace();
					}
					
				}
				rs.close();
				stmt.close();
				session.delPortForwardingL(assignedPort);
				
				session.disconnect();
				connection.close();
				System.out.println("----------------Close connection---------------------");
			}
		} catch (JSchException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;

	}
	
	public static void updateStatment(String query) {
		String apkFile = "DTS.ppk";
		Session session = null;
		int assignedPort;
		JSch jsch = new JSch();
		String uri = System.getProperty("user.dir") + "\\asset\\" + apkFile;
		try {
			jsch.addIdentity(uri);
			session = jsch.getSession(USER, IP, 22);
			Properties prop = new Properties();
			prop.put("StrictHostKeyChecking", "no");
			prop.put("Compression", "yes");
			prop.put("ConnectionAttempts", "2");
			session.setConfig(prop);
			session.connect();
			if (session.isConnected()) {
				System.out.println("channel connected");
				assignedPort = session.setPortForwardingL(5431, HOST, 5432);
				System.out.println(assignedPort);
				Statement stmt = null;
				System.out.println("-------- PostgreSQL "
						+ "JDBC Connection Testing ------------");
				try {

					Class.forName("org.postgresql.Driver");

				} catch (ClassNotFoundException e) {
					System.out.println("Where is your PostgreSQL JDBC Driver? "
							+ "Include in your library path!");
					e.printStackTrace();
				}
				System.out.println("PostgreSQL JDBC Driver Registered!");

				Connection connection = null;
				try {
					System.out
							.println("Connect to jdbc:postgresql://localhost:5432/dts_adminportal");
					connection = DriverManager.getConnection(
							"jdbc:postgresql://localhost:5431/dts_adminportal",
							"apadmin", PASS);
					connection.setAutoCommit(false);
				} catch (SQLException e) {
					System.out
							.println("Connection Failed! Check output console");
					e.printStackTrace();
				}
				if (connection != null) {
					System.out
							.println("You made it, take control your database now!");
				} else {
					System.out.println("Failed to make connection!");
				}

				stmt = connection.createStatement();
				int rs = stmt.executeUpdate(query);
				if(rs == 1){
					System.out.println("Update statement done");
				} else{
					System.out.println("The update query is not correct");
				}
				connection.commit();
				stmt.close();
				session.delPortForwardingL(assignedPort);
				
				session.disconnect();
				connection.close();
				System.out.println("----------------Close connection---------------------");
			}
		} catch (JSchException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
