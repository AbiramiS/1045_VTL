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

public class AuthenticationIdGeneration extends org.apache.struts.action.Action 
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
    	String userid=dvf.get("userid").toString();
    	con=dbconn.getConnect();
    		System.out.println("------\n\nUSERID:"+userid);
    		Thread.sleep(1000);
    		int authenticationid=rr.nextInt(999);
    		//String aid="AUID_VEH"+String.valueOf(authenticationid);
    		String aid=String.valueOf(authenticationid);
    		String totcontent=userid+"***"+aid;
    		
    		System.out.println(totcontent);
    		
    		stmt=con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from VTL_USERREGISTRATION where USERID='"+userid+"'");
            Boolean result=rs.next();
            
		        if(result){
		        	stmt.executeUpdate("update VTL_USERREGISTRATION set AUTHENTICATIONID='"+aid+"' where USERID='"+userid+"'");	
		        }
		        con.close();
                stmt.close();            		
    		PrintWriter pw=response.getWriter();    	
        	pw.write(totcontent); 
        	
    		}
    		catch(Exception e){	 
    			e.printStackTrace();
    		}   		
    		
        	return null;
  }
}