package com.source;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;
import org.apache.struts.validator.DynaValidatorForm;

import com.database.DBConnection;

public class VehicleUserRegistration extends org.apache.struts.action.Action 
{
	ResultSet rs;
	PreparedStatement ps,ps1;
	Statement stmt = null;
	Connection con = null;

    @SuppressWarnings("unchecked")
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		    	
		Boolean status=true;
		try{
			
		DBConnection dbconn=new DBConnection();	
		Random rr=new Random();
    	DynaValidatorForm dvf=(DynaValidatorForm)form;
    	String username1=dvf.get("username").toString();
    	String password1=dvf.get("password").toString();
    	String gender1=dvf.get("gender").toString();
    	String mobileno1=dvf.get("mobileno").toString();
    	String emailid1=dvf.get("emailid").toString();
    	
    		System.out.println("1-"+username1);
    		System.out.println("2-"+password1);
    		System.out.println("3-"+gender1);
    		System.out.println("4-"+mobileno1);
    		System.out.println("5-"+emailid1);   
    			
    		con=dbconn.getConnect();		
    		stmt=con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from VTL_USERREGISTRATION where USERNAME='"+username1+"' and PASSWORD='"+password1+"'");
            Boolean result=rs.next();
    		        if(result){
    		        	String str="***ALREADY EXISTING";
    		        	System.out.println(str);
    		        	PrintWriter pw=response.getWriter();    	
    		        	pw.write(str); 
    				}
    		        else{
    		        	String str="***REGISTRATION SUCCESSFULL";
    		        	String userid=username1+"_"+rr.nextInt(9999);
    		        	stmt.executeUpdate("insert into VTL_USERREGISTRATION values('"+username1+"','"+password1+"','"+gender1+"','"+mobileno1+"','"+emailid1+"','"+userid+"')");
    		        	System.out.println("DETAILS ADDED TO DATABASE");   
    		        	PrintWriter pw=response.getWriter();    	
    		        	pw.write(str); 
    		        }
    		}
    		catch(Exception e){	 
    			e.printStackTrace();
    		}   		
    		
        	return null;
  }
}