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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    	
    		System.out.println("USERNAME:"+username1);
    		System.out.println("PASSWORD:"+password1);
    		System.out.println("GENDER:"+gender1);
    		System.out.println("MOBILE NO.:"+mobileno1);
    		System.out.println("EMAIL ID.:"+emailid1);   
    			
    		con=dbconn.getConnect();		
    		stmt=con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from VTL_USERREGISTRATION where USERNAME='"+username1+"' and PASSWORD='"+password1+"'");
            Boolean result=rs.next();
    		        if(result){
    		        	String str="ALREADY EXISTING***NULL";
    		        	System.out.println(str);con.close();
    	                stmt.close();
    		        	PrintWriter pw=response.getWriter();    	
    		        	pw.write(str); 
    				}
    		        else{
    		        	String userid=username1+"_"+rr.nextInt(9999);
    		        	String str="REGISTRATION SUCCESSFULL***"+userid;
    		        	String authenid = "-",deckey="";
    		        	stmt.executeUpdate("insert into VTL_USERREGISTRATION values('"+username1+"','"+password1+"','"+gender1+"','"+mobileno1+"','"+emailid1+"','"+userid+"','"+authenid+"','"+deckey+"')");
    		        	System.out.println("DETAILS ADDED TO DATABASE");   
    		        	
    		        	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    		     	    //get current date time with Calendar()
    		        	Calendar cal = Calendar.getInstance();
    		     	    System.out.println("Current Date Time : " + dateFormat.format(cal.getTime()));
    		        	cal = Calendar.getInstance();
    		     	    cal.add(Calendar.MONTH, 1);
    		     	    System.out.println("Add one month to current date : " + dateFormat.format(cal.getTime()));
    		        	String expdate=dateFormat.format(cal.getTime());
    		     	    stmt.executeUpdate("insert into VTL_USERUPDATION values('"+userid+"','"+expdate+"')");con.close();
    	                stmt.close();
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