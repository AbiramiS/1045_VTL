package com.source;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;
import org.apache.struts.validator.DynaValidatorForm;

import com.database.DBConnection;

public class SpeedUpdates extends org.apache.struts.action.Action 
{
	ResultSet rs;
	PreparedStatement ps,ps1;
	Statement stmt = null;
	Connection con = null;
	String totvalue = "", tripidvalue="", lat = "", lon = "";
	DBConnection dbconn=new DBConnection();	
    @SuppressWarnings("unchecked")
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		    	
		Boolean status=true;
		int ch;
		FileInputStream fis = null;
		
		try {
		con=dbconn.getConnect();		
		stmt=con.createStatement();
		Random rr=new Random();
    	DynaValidatorForm dvf=(DynaValidatorForm)form;
    	
    	String authenid=dvf.get("authenid").toString();
    	String useridvalue=dvf.get("useridvalue").toString();
    	String latitude=dvf.get("latitude").toString();
    	String longitude=dvf.get("longitude").toString();
    	String currentSpeed=dvf.get("currentSpeed").toString();
    	String kmphSpeed=dvf.get("kmphSpeed").toString();
    	String avgSpeed=dvf.get("avgSpeed").toString();
    	String avgKmph=dvf.get("avgKmph").toString();
    	
    	
    		System.out.println("authenid:"+authenid+"--useridvalue:"+useridvalue);
    		System.out.println("latitude:"+latitude+"--longitude:"+longitude);
    		System.out.println("currentSpeed:"+currentSpeed+"==kmphSpeed:"+kmphSpeed);
    		System.out.println("avgSpeed.:"+avgSpeed+"--avgKmph.:"+avgKmph);   
    		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            System.out.println("\n"+dateFormat.format(date));
    			
    		Thread.sleep(3000);
    		stmt.executeUpdate("insert into VTL_SPEEDUPDATIONS values('"+useridvalue+"','"+authenid+"','"+latitude+"','"+longitude+"','"+currentSpeed+"','"+kmphSpeed+"','"+avgSpeed+"','"+avgKmph+"','"+dateFormat.format(date)+"')");
        	 
    		/*fis = new FileInputStream("VTL Positions.txt");
	    	byte b[]=new byte[1024];
	    	while((ch=fis.read(b))!=-1){
	    		String tempvalue = new String(b);
	    		totvalue+=tempvalue.trim();
	    	}
	    	StringTokenizer st = new StringTokenizer(totvalue, "\n");
	    	while(st.hasMoreTokens()){
	    		tripidvalue=st.nextToken();
	    		StringTokenizer st1 = new StringTokenizer(tripidvalue, ",");
		    	while(st1.hasMoreTokens()){
		    		lat=st1.nextToken();
		    		lon=st1.nextToken();
		    	}
	    	}*/
    		
    		
            /*ResultSet rs = stmt.executeQuery("select * from VTL_USERREGISTRATION where USERNAME='"+username1+"' and PASSWORD='"+password1+"'");
            Boolean result=rs.next();
    		        if(result){
    		        	String str="***ALREADY EXISTING";
    		        	System.out.println(str);
    		        	PrintWriter pw=response.getWriter();    	
    		        	pw.write(str); 
    				}
    		        else{
    		        	String str="***REGISTRATION SUCCESSFULL";
    		        	String authenid = "-";
    		        	String userid=username1+"_"+rr.nextInt(9999);
    		        	stmt.executeUpdate("insert into VTL_USERREGISTRATION values('"+username1+"','"+password1+"','"+gender1+"','"+mobileno1+"','"+emailid1+"','"+userid+"','"+authenid+"')");
    		        	System.out.println("DETAILS ADDED TO DATABASE");   
    		        	PrintWriter pw=response.getWriter();    	
    		        	pw.write(str); 
    		        }*/
    		}
    		
    		catch(Exception e) {	 
    			e.printStackTrace();
    		}   
    		con.close();
            stmt.close();
    		PrintWriter pw=response.getWriter();    	
        	pw.write("TRACKING THE SPEED"); 
    		
        	return null;
  }
}