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

public class UserLogin extends org.apache.struts.action.Action 
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
		collectionsFramework cf = new collectionsFramework();
		if(cf.get()){	
			
		DBConnection dbconn=new DBConnection();	
		Random rr=new Random();
    	DynaValidatorForm dvf=(DynaValidatorForm)form;
    	String userid=dvf.get("userid").toString();
    	String password1=dvf.get("password").toString();
    	
    		System.out.println("USERID:"+userid);
    		System.out.println("PASSWORD:"+password1);
    			
    		con=dbconn.getConnect();		
    		stmt=con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from VTL_USERREGISTRATION where USERID='"+userid+"' and PASSWORD='"+password1+"'");
            Boolean result=rs.next();
    		        if(result){
    		        	String str="LOGIN SUCCESS";
    		        	System.out.println(str);con.close();
    	                stmt.close();
    		        	PrintWriter pw=response.getWriter();    	
    		        	pw.write(str); 
    				}
    		        else{
    		        	String str="LOGIN FAILURE";
    		        	System.out.println(str);con.close();
    	                stmt.close();
    		        	PrintWriter pw=response.getWriter();    	
    		        	pw.write(str); 
    		        }
				}
    		}
    		catch(Exception e){	 
    			e.printStackTrace();
    		}   		
    		
        	return null;
  }
}