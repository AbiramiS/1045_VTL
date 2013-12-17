package com.source;
// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   AutoCompletedate.java

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.database.DBConnection;

import oracle.jdbc.driver.OracleDriver;

public class AutoCompletedate extends HttpServlet {
	
	ResultSet rs=null,rs1=null;
	Statement stat=null;
	Connection con=null;
	DBConnection dbconn=new DBConnection();	
	static String userid="",lat="",lon="",mobno="",emailid="",status="";
    public AutoCompletedate() {
    }

    public void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        StringBuffer sb = new StringBuffer();
        con = dbconn.getConnect();
        
        try {    
        	
        			Properties prop=null;        	
        			prop=new Properties();
        			prop.load(new FileInputStream("LatLon.properties"));
        			String defaultlat = prop.getProperty("defaultlat");
        			String defaultlon = prop.getProperty("defaultlon");
        	

            stat = con.createStatement(); 
            rs = stat.executeQuery("select * from VTL_SERVER");
            while(rs.next()){
            	userid=rs.getString(1);
            	lat=rs.getString(5);
            	lon=rs.getString(6);
            	status=rs.getString(8);
            	rs1 = stat.executeQuery("select * from VTL_USERREGISTRATION where USERID='"+userid+"'");
                while(rs1.next()){
                	mobno=rs1.getString(4);
                	emailid=rs1.getString(5); 	
                }
            }
            //System.out.println(userid+"------"+lat+"------"+lon);
            //System.out.println(mobno+"------"+emailid);
            
            
            if(lat.equals("") && lon.equals("")){
            	userid="admin";
            	mobno="9999999999";
            	emailid="admin@gmail.com";
            	System.out.println(sb.append((new StringBuilder("<CHATID>")).append("<ID>").append(userid).append("</ID>").append("<MOBILENO>").append(mobno).append("</MOBILENO>").append("<EMAILID>").append(emailid).append("</EMAILID>").append("<LATITUDE>").append(defaultlat).append("</LATITUDE>").append("<LONGITUDE>").append(defaultlon).append("</LONGITUDE>").append("</CHATID>").toString()));
            	response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write((new StringBuilder("<CHATING>")).append(sb.toString()).append("</CHATING>").toString());
                out.close();
            } 
            else if(status.equals("MOVING") && !lat.equals("") && !lon.equals("") && !userid.equals("") && !mobno.equals("")  && !emailid.equals("")) {
            	System.out.println(sb.append((new StringBuilder("<CHATID>")).append("<ID>").append(userid).append("</ID>").append("<MOBILENO>").append(mobno).append("</MOBILENO>").append("<EMAILID>").append(emailid).append("</EMAILID>").append("<LATITUDE>").append(lat).append("</LATITUDE>").append("<LONGITUDE>").append(lon).append("</LONGITUDE>").append("</CHATID>").toString()));
            	response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write((new StringBuilder("<CHATING>")).append(sb.toString()).append("</CHATING>").toString());
                out.close();
            }
            else if(status.equals("NOTMOVING") && !lat.equals("") && !lon.equals("") && !userid.equals("") && !mobno.equals("")  && !emailid.equals("")) {
            	mobno="NOTMOVING";
            	System.out.println(sb.append((new StringBuilder("<CHATID>")).append("<ID>").append(userid).append("</ID>").append("<MOBILENO>").append(mobno).append("</MOBILENO>").append("<EMAILID>").append(emailid).append("</EMAILID>").append("<LATITUDE>").append(defaultlat).append("</LATITUDE>").append("<LONGITUDE>").append(defaultlon).append("</LONGITUDE>").append("</CHATID>").toString()));
            	response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write((new StringBuilder("<CHATING>")).append(sb.toString()).append("</CHATING>").toString());
                out.close();
            }/*
            else{
            	userid="admin";
            	mobno="9999999999";
            	emailid="admin@gmail.com";
            	System.out.println(sb.append((new StringBuilder("<CHATID>")).append("<ID>").append(userid).append("</ID>").append("<MOBILENO>").append(mobno).append("</MOBILENO>").append("<EMAILID>").append(emailid).append("</EMAILID>").append("<LATITUDE>").append(defaultlat).append("</LATITUDE>").append("<LONGITUDE>").append(defaultlon).append("</LONGITUDE>").append("</CHATID>").toString()));
            	response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write((new StringBuilder("<CHATING>")).append(sb.toString()).append("</CHATING>").toString());
                out.close();
            }*/
            stat.close();
            con.close();
            
        }
        catch(Exception e) {
            e.printStackTrace();
        }        
    }
    private static final long serialVersionUID = 1L;
}

