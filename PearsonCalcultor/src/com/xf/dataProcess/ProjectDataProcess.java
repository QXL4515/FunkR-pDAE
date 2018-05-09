package com.xf.dataProcess;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xf.util.Util;

/**
 * 项目数据处理
 * @author xf
 *
 */
public class ProjectDataProcess {
	public static void processProjectData() {
		try {
			Map<String, Integer> userWatchedMap = getUserWatchedMap();
			Map<String, Integer> userForkedMap = getUserForkedMap();
			Map<String, Integer> userIssueMap = getUserIssueMap();
			Map<String, Integer> userPullRequestMap = getUserPullRequestMap();
			Map<String, Integer> userMap = getAllUserMap();
			Map<String, Integer> projectMap = getAllProjectMap();
			
			
			List<String> headers = new ArrayList<String>();
			for(Map.Entry<String, Integer> projectEntry:projectMap.entrySet()) {
				headers.add(projectEntry.getKey());
			}
			
			
			
			List<List<String>> content = new ArrayList<List<String>>();
			
			for(Map.Entry<String, Integer> userEntry : userMap.entrySet()) {
				List<String> scoreList = new ArrayList<String>();
				scoreList.add(userEntry.getKey());
				for(Map.Entry<String, Integer> projectEntry:projectMap.entrySet()) {
					String key = userEntry.getKey()+"-"+projectEntry.getKey();
					Integer score = 0;
					if(userWatchedMap.containsKey(key)) {
						score += 1;
					}
					if(userForkedMap.containsKey(key)) {
						score += 2;
					}
					if(userIssueMap.containsKey(key)) {
						score += 3;
					}
					if(userPullRequestMap.containsKey(key)) {
						score += 4;
					}
					scoreList.add(score+"");
				}
				content.add(scoreList);
			}
			
			
			File mainFile_excel = new File("f:/dset_1.xlsx");
			OutputStream out = new FileOutputStream(mainFile_excel);
			Util.exportExcel(headers,content, out);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public static Integer[][] getMartixData(){
		try {
			Map<String, Integer> userWatchedMap = getUserWatchedMap();
			Map<String, Integer> userForkedMap = getUserForkedMap();
			Map<String, Integer> userIssueMap = getUserIssueMap();
			Map<String, Integer> userPullRequestMap = getUserPullRequestMap();
			Map<String, Integer> userMap = getAllUserMap();
			Map<String, Integer> projectMap = getAllProjectMap();
			
			
			List<List<Integer>> martixData = new ArrayList<List<Integer>>();
			for(Map.Entry<String, Integer> userEntry : userMap.entrySet()) {
				List<Integer> pfData = new ArrayList<Integer>();
				for(Map.Entry<String, Integer> projectEntry:projectMap.entrySet()) {
					String key = userEntry.getKey()+"-"+projectEntry.getKey();
					Integer score = 0;
					if(userWatchedMap.containsKey(key)) {
						score += 1;
					}
					if(userForkedMap.containsKey(key)) {
						score += 2;
					}
					if(userIssueMap.containsKey(key)) {
						score += 3;
					}
					if(userPullRequestMap.containsKey(key)) {
						score += 4;
					}
					pfData.add(score);
				}
				martixData.add(pfData);
			}
			Integer[][] martixDataArray2 = Util.getArray2(martixData);
			return martixDataArray2;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static Object[][] getMartixDataForUserIdProjectIdScore(){
		try {
			Map<String, Integer> userWatchedMap = getUserWatchedMap();
			Map<String, Integer> userForkedMap = getUserForkedMap();
			Map<String, Integer> userIssueMap = getUserIssueMap();
			Map<String, Integer> userPullRequestMap = getUserPullRequestMap();
			Map<String, Integer> userMap = getAllUserMap();
			Map<String, Integer> projectMap = getAllProjectMap();
			
			
			List<List<Object>> martixData = new ArrayList<List<Object>>();
			for(Map.Entry<String, Integer> userEntry : userMap.entrySet()) {
				for(Map.Entry<String, Integer> projectEntry:projectMap.entrySet()) {
					List<Object> pfData = new ArrayList<Object>();
					pfData.add(userEntry.getKey());
					pfData.add(projectEntry.getKey());
					String key = userEntry.getKey()+"-"+projectEntry.getKey();
					Integer score = 0;
					if(userWatchedMap.containsKey(key)) {
						score += 1;
					}
					if(userForkedMap.containsKey(key)) {
						score += 2;
					}
					if(userIssueMap.containsKey(key)) {
						score += 3;
					}
					if(userPullRequestMap.containsKey(key)) {
						score += 4;
					}
					pfData.add(score);
					martixData.add(pfData);
				}
			}
			Object[][] martixDataArray2 = Util.getObjectArray2(martixData);
			return martixDataArray2;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static Map<String, Integer> getAllUserMap(){
		Connection connect = getConn();
		 PreparedStatement pstmt;	
		 String sql ="SELECT id from user_dset_1";
		 Map<String, Integer> userMap = new HashMap<String, Integer>();
		 try{
			 pstmt = (PreparedStatement)connect.prepareStatement(sql);
			 ResultSet rs;
			 rs = pstmt.executeQuery(sql);
			 
			 while (rs.next()) {
					String userId = rs.getString("id");
					userMap.put(userId, 0);
				}
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		return userMap;
	}
	
	public static Map<String, Integer> getAllProjectMap(){
		Connection connect = getConn();
		 PreparedStatement pstmt;	
		 String sql ="SELECT id from projects_dset_1";
		 Map<String, Integer> projectMap = new HashMap<String, Integer>();
		 try{
			 pstmt = (PreparedStatement)connect.prepareStatement(sql);
			 ResultSet rs;
			 rs = pstmt.executeQuery(sql);
			 
			 while (rs.next()) {
					String projectId = rs.getString("id");
					projectMap.put(projectId, 0);
				}
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		return projectMap;
	}
	
	
	public static Map<String, Integer> getUserWatchedMap(){
		Connection connect = getConn();
		 PreparedStatement pstmt;	
		 String sql ="SELECT ud1.id userId,w.repo_id projectId FROM testuser ud1 LEFT JOIN ( "+
			"SELECT * FROM watchers WHERE repo_id IN ( "+
				" SELECT id FROM testproject "+
				")"+
			") w ON ud1.id = w.user_id";
		 Map<String, Integer> userWatchedMap = new HashMap<String, Integer>();
		 try{
			 pstmt = (PreparedStatement)connect.prepareStatement(sql);
			 ResultSet rs;
			 rs = pstmt.executeQuery(sql);
			 
			 while (rs.next()) {
					String userId = rs.getString("userId");
					String projectId = rs.getString("projectId");
					userWatchedMap.put(userId+"-"+projectId, 0);
				}
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		return userWatchedMap;
	}
	
	public static Map<String, Integer> getUserForkedMap(){
		Connection connect = getConn();
		 PreparedStatement pstmt;	
		 String sql ="SELECT ud1.id userId,p.forked_from projectId FROM testuser ud1 LEFT JOIN testproject  p ON ud1.id = p.owner_id AND p.forked_from IS NOT NULL";
		 Map<String, Integer> userForkedMap = new HashMap<String, Integer>();
		 try{
			 pstmt = (PreparedStatement)connect.prepareStatement(sql);
			 ResultSet rs;
			 rs = pstmt.executeQuery(sql);
			 
			 while (rs.next()) {
					String userId = rs.getString("userId");
					String projectId = rs.getString("projectId");
					userForkedMap.put(userId+"-"+projectId, 0);
				}
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		return userForkedMap;
	}
	
	public static Map<String, Integer> getUserIssueMap(){
		Connection connect = getConn();
		 PreparedStatement pstmt;	
		 String sql =" select ic.user_id userId,i.repo_id projectId from ( "
		 				+ "  SELECT * FROM issue_comments WHERE user_id IN ("
		 						+ " select id from testuser "
		 					+ ") "
		 				+ " ) ic left join ( "
		 					+ " select * from issues where repo_id in ( "
		 						+ " select id from testproject "
		 						+ ")"
		 				+ " ) i on ic.issue_id = i.id group by ic.user_id,i.repo_id  ";
		 Map<String, Integer> userIssueMap = new HashMap<String, Integer>();
		 try{
			 pstmt = (PreparedStatement)connect.prepareStatement(sql);
			 ResultSet rs;
			 rs = pstmt.executeQuery(sql);
			 
			 while (rs.next()) {
					String userId = rs.getString("userId");
					String projectId = rs.getString("projectId");
					userIssueMap.put(userId+"-"+projectId, 0);
				}
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		return userIssueMap;
	}
	
	
	public static Map<String, Integer> getUserPullRequestMap(){
		Connection connect = getConn();
		 PreparedStatement pstmt;	
		 String sql =" select prc.user_id userId,pr.head_repo_id projectId from ( "
		 				+ "  select * from pull_request_comments where user_id in ("
		 						+ " select id from testuser "
		 					+ ") "
		 				+ " ) prc left join ( "
		 					+ " select * from pull_requests where head_repo_id in ( "
		 						+ " select id from testproject "
		 						+ ")"
		 				+ " ) pr on prc.pull_request_id = pr.id group by prc.user_id,pr.head_repo_id  ";
		 Map<String, Integer> userPullRequestMap = new HashMap<String, Integer>();
		 try{
			 pstmt = (PreparedStatement)connect.prepareStatement(sql);
			 ResultSet rs;
			 rs = pstmt.executeQuery(sql);
			 
			 while (rs.next()) {
					String userId = rs.getString("userId");
					String projectId = rs.getString("projectId");
					userPullRequestMap.put(userId+"-"+projectId, 0);
				}
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		return userPullRequestMap;
	}
	
	
	
	private static Connection getConn() {
	    String driver = "com.mysql.jdbc.Driver";
	    String url = "jdbc:mysql://localhost:3306/20170616database";
	    String username = "root";
	    String password = "123456";
	    Connection conn = null;
	    try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return conn;
	}
	
	public static void main(String str[]) {
		processProjectData();
	}
}
