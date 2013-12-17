package com.source;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;
import org.apache.struts.validator.DynaValidatorForm;

import com.database.DBConnection;

public class AdminUpdation extends org.apache.struts.action.Action 
{
	ResultSet rs;
	PreparedStatement ps,ps1;
	Statement stmt = null;
	Connection con = null;
	Vector vc1=new Vector();
	HashMap test = new HashMap();
	Vector vc2=new Vector();
	DBConnection dbconn=new DBConnection();	
    @SuppressWarnings("unchecked")
	@Override
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		    	
		Boolean status=true;
		try {
			FileInputStream fis = null;
			Random rr=new Random();
			int ch;
			HttpSession session = request.getSession(true);
  			con=dbconn.getConnect();		
      		stmt=con.createStatement();
			String totvalue="",val1="",tripidvalue="",val2="",totalvalues="";
	    	DynaValidatorForm dvf=(DynaValidatorForm)form;
	    	String latitude=dvf.get("latitude").toString().trim();
	    	String longitude=dvf.get("longitude").toString().trim();    	
	    	System.out.println("latitude:"+latitude);
	    	System.out.println("longitude:"+longitude);
	    	String check = latitude+"^"+longitude;
	    	vc1.clear();vc2.clear();
    		vc1=new Vector();
    		vc2=new Vector();
	    		if(latitude.equals(longitude)) {
	    			String ret = "Error... Same Location Detected";
	    		}
	    		else {	    			 
	    			fis = new FileInputStream("VTL Positions.txt");
	    		   	byte b[]=new byte[1024];
	    		   	while((ch=fis.read(b))!=-1){
	    		   		String tempvalue = new String(b);
	    		   		totvalue+=tempvalue.trim();
	    		   	}
	    		   	
	    		   	System.out.println("totvalue-"+totvalue); 
	    		   	
	    		   	StringTokenizer st = new StringTokenizer(totvalue.trim(), "\n");
	    		   	String criteria ="";
	    		   	while(st.hasMoreTokens()){
	    		   		tripidvalue=st.nextToken().trim();
	    		   		StringTokenizer st1 = new StringTokenizer(tripidvalue, ",");
	    		    	while(st1.hasMoreTokens()){
		    		    		val1=st1.nextToken();
		    		    		val2=st1.nextToken();
		    		    		String temp = val1+"^"+val2;
		    		    		System.out.println(temp.trim()+"-----"+check.trim());
		    		    		if(check.trim().equals(temp.trim())) {
		    		    			System.out.println("--EXISTING---");
		    		    			criteria = "EXISTING";
		    		    		}
		    		    		else{
		    		    			//System.out.println("--else---");
		    		    		}
		    		    		
	    		    		}
	    		    	if(criteria.equals("EXISTING")){
	    		    		ResultSet rs = stmt.executeQuery("select * from VTL_TRIPID where LATITUDE='"+latitude+"' and LONGITUDE='"+longitude+"'");
		   	                 Boolean result=rs.next();
	   				         if(result) {
	   				        	 System.out.println("--VALUE EXISTING IN DB---");
	   				        	 String str="TRIP LINES ALREADY EXISTING";
	   				        	 System.out.println(str);
	   				        	 con.close();
	   			                 stmt.close();
	   				        	 session.setAttribute("responsevalues",str);
	   		                	 return mapping.findForward("responsevalues");
	   						 }
	   				         else {
	   				        	 System.out.println("--SUCCESS---");
		   			        	 String triplineid="TLID"+rr.nextInt(99999);
		   			        	 stmt.executeUpdate("insert into VTL_TRIPID values('"+latitude+"','"+longitude+"','"+triplineid+"')");
		   			        	 con.close();
		   		                 stmt.close();
		   			        	 System.out.println("DETAILS ADDED TO DATABASE");
		   			        	 session.setAttribute("responsevalues","SUCCESSFULLY UPDATED IN DATABASE");
	   		                	 return mapping.findForward("responsevalues");
	   			        	 }
	    		    	}
	    		    	/*else {
	    		    	     System.out.println("--FAILED---");
	    		    	     con.close();
	    		             stmt.close();
	    		    		 session.setAttribute("responsevalues","VALUES NOT EXISTING IN SERVER");
	   		             	 return mapping.findForward("responsevalues");
	    		    	}*/
	    		    }
	    		   	
	    		   		/*for(int j=0;j<vc2.size();j++){
	    		   			System.out.println(vc1.get(i)+"-----"+latitude);
	    		   			System.out.println(vc2.get(j)+"-----"+longitude);
	    		   			if(vc1.get(i).equals(latitude.trim()) && vc2.get(j).equals(longitude.trim())){
			   	        		 ResultSet rs = stmt.executeQuery("select * from VTL_TRIPID where LATITUDE='"+latitude+"' and LONGITUDE='"+longitude+"'");
			   	                 Boolean result=rs.next();
		   				         if(result) {
		   				        	 String str="TRIP LINES ALREADY EXISTING";
		   				        	 System.out.println(str);
		   				        	 session.setAttribute("responsevalues",str);
		   		                	 return mapping.findForward("responsevalues");
		   						 }
		   				         else {
			   			        	 String triplineid="TLID"+rr.nextInt(99999);
			   			        	 stmt.executeUpdate("insert into VTL_TRIPID values('"+latitude+"','"+longitude+"','"+triplineid+"')");
			   			        	 System.out.println("DETAILS ADDED TO DATABASE");
			   			        	 session.setAttribute("responsevalues","SUCCESSFULLY UPDATED IN DATABASE");
		   		                	 return mapping.findForward("responsevalues");
		   			        	 }
		    		   		}
	    		   			else if(!vc1.get(i).equals(latitude.trim()) && !vc2.get(j).equals(longitude.trim())){
	   				        	System.out.println("---***---");
	    		   			}
		    		   		else {
		    		   			 session.setAttribute("responsevalues","VALUES NOT EXISTING IN SERVER");
	  		                	 return mapping.findForward("responsevalues");
		    		   		}
		    		   		
		    		   	}
	    		   	
	    		   	
   		   			 session.setAttribute("responsevalues","VALUES NOT EXISTING IN SERVER");
		             return mapping.findForward("responsevalues");*/
	    		   		
	    	  }
		} 
		catch(Exception e) {	 
			e.printStackTrace();
		}  		
    	return null;
    }
}