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
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;
import org.apache.struts.validator.DynaValidatorForm;

public class ProxyResponse extends org.apache.struts.action.Action 
{
	Connection con,con1;
	Statement stmt;
	ResultSet rs;
	PreparedStatement ps,ps1;

    @SuppressWarnings("unchecked")
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Boolean status=true;
		
		try{
			
    	DynaValidatorForm dvf=(DynaValidatorForm)form;
    	String proxyvalue=dvf.get("proxyvalue").toString();    	
        System.out.println("-----proxyvalue-----"+proxyvalue);        	
    		
        PrintWriter pw=response.getWriter();
        pw.write("Hi "+proxyvalue);
    	}
		catch(Exception e){
			e.printStackTrace();
		}
	return null;
  }
}