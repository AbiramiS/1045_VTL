package com.source;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.database.DBConnection;

@SuppressWarnings("serial")
public class BlockUser extends HttpServlet {
	
	String str="";
	Statement stmt = null, stmt1 = null;
	Connection con = null;
	String sessionidvalue="",query="";
	ResultSet rs=null, rs1=null;
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException, ServletException {		
		try {
			String userid=req.getParameter("useridvalue");
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nRECEIVED::::"+userid);
			DBConnection dbconn=new DBConnection();	
			Random rr=new Random();
			con=dbconn.getConnect();		
			stmt=con.createStatement();
			stmt.executeUpdate("update VTL_SERVER set PROCESS='"+"OFF"+"' where USERID='"+userid+"'");	
			con.close();
            stmt.close();
			
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
}