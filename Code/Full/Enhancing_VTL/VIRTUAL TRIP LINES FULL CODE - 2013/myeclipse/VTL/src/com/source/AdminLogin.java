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

public class AdminLogin extends org.apache.struts.action.Action 
{
	ResultSet rs;
	PreparedStatement ps,ps1;
	Statement stmt = null;
	Connection con = null;

    @SuppressWarnings("unchecked")
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		    	
		Boolean status=true;
		try {
			
		DBConnection dbconn=new DBConnection();	
		Random rr=new Random();
    	DynaValidatorForm dvf=(DynaValidatorForm)form;
    	String adminname=dvf.get("adminname").toString();
    	String adminpassword=dvf.get("adminpassword").toString();
    	
    		System.out.println("adminname:"+adminname);
    		System.out.println("adminpassword:"+adminpassword);
    			
    		con=dbconn.getConnect();		
    		stmt=con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from VTL_ADMIN where ADMINNAME='"+adminname+"' and ADMINPASSWORD='"+adminpassword+"'");
            Boolean result=rs.next();
    		        if(result){
    		        	con.close();
    	                stmt.close();
    		        	return mapping.findForward("adminloginsuccess");
    				}
    		        else{
    		        	con.close();
    	                stmt.close();
    		        	return mapping.findForward("adminloginfailure");
    		        }
    		}
    		catch(Exception e){	 
    			e.printStackTrace();
    		}   		
    		
        	return null;
  }
}