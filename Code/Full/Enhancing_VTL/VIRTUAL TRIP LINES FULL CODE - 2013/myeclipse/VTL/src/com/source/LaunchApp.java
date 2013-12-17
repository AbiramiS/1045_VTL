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
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;
import org.apache.struts.validator.DynaValidatorForm;

import com.database.DBConnection;

public class LaunchApp extends org.apache.struts.action.Action 
{
	PreparedStatement ps,ps1;
	Statement stmt = null;
	Connection con = null;
	String dbkey = "", decvalue= "";
	String currentlatitude = "",currentlongitude = "";
	String vecvalues = "";
	Vector vc = new Vector();
	ResultSet rs;
	Vector vc1 = new Vector();
	Vector vc2 = new Vector();
	Boolean status=true;
	int counts=0;
	int cc=1; 
	int count = 0, ncount = 0, countd1=0;
	String dlat="",dlon="",dbstatus="",hmackey="";
	  
	DBConnection dbconn=new DBConnection();	
	HMac hm=new HMac();
	Random rr=new Random();
    @SuppressWarnings("unchecked")
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	con=dbconn.getConnect();
		stmt=con.createStatement();
		try {			
    	DynaValidatorForm dvf=(DynaValidatorForm)form;
    	String authenid=dvf.get("authenid").toString();
    	String phonelineno=dvf.get("phonelineno").toString();
    	String imeino=dvf.get("imeino").toString();
    	String userid=dvf.get("userid").toString();
    	String keyvalue=dvf.get("keyvalue").toString();
    	String totval=dvf.get("totval").toString();     	    	
    	hmackey = hm.Sourcemac1(userid, authenid);
		String ffvalues = "";
		Calendar cal1 = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		Decryption dec = new Decryption(); 
		counts++;
		ResultSet rs1 = stmt.executeQuery("select * from VTL_USERREGISTRATION where USERID='"+userid+"'");
        Boolean result1=rs1.next();
        if(result1) {
        	dbkey = rs1.getString("DECKEY");        	
        	if(counts==1){
        	System.out.println("VALUES INSERTING IN VTL_SERVER"+counts);  
        	stmt.executeUpdate("insert into VTL_SERVER values('"+userid+"','"+authenid+"','"+phonelineno+"','"+imeino+"','"+currentlatitude+"','"+currentlongitude+"','"+dateFormat.format(cal1.getTime())+"','MOVING','ON','"+hmackey+"')");
        	}
        }
               
        
        rs = stmt.executeQuery("select * from VTL_SERVER where USERID='"+userid+"'");
        Boolean result11=rs.next();
        if(result11) {
        	dbstatus = rs.getString("PROCESS");
        }
        System.out.println("dbstatus-----"+dbstatus+"\n");        
        if(keyvalue.equals(dbkey)){
        	decvalue="";
        	decvalue = dec.decrypt(totval);
        }
        System.out.println("decvalue: "+decvalue);
        
        
        StringTokenizer sst  = new StringTokenizer(decvalue, "&&&&&");        
        while (sst.hasMoreTokens()){
        	currentlatitude="";
            currentlongitude="";
        	currentlatitude = sst.nextToken();
        	currentlongitude = sst.nextToken();        	
        }
        //System.out.println(currentlatitude+"---"+currentlongitude+"\n\n");
        
        FileInputStream fis = null;
        int ch;String totvalue="",tripidvalue="",val1="",val2="",check="";
        
        fis = new FileInputStream("VTL Positions.txt");
	   	byte b[]=new byte[1024];
	   	while((ch=fis.read(b))!=-1){
	   		String tempvalue = new String(b);
	   		totvalue+=tempvalue.trim();
	   	}
	   	
	   	StringTokenizer st = new StringTokenizer(totvalue.trim(), "\n");
	   	String criteria ="";
	   	while(st.hasMoreTokens()){
	   		tripidvalue=st.nextToken().trim();
	   		StringTokenizer st1 = new StringTokenizer(tripidvalue, ",");
	    	while(st1.hasMoreTokens()){
		    		val1=st1.nextToken();
		    		val2=st1.nextToken();
		    		String temp = val1+"^"+val2;
		    		
		    		String val111 = val1.substring(0, 6);
		    		String val122 = val2.substring(0, 6);		    		
		    		float f1=Float.parseFloat(val111);
		    		float f2=Float.parseFloat(val122);
		    		
		    		
		    		String currentlatitude1 = currentlatitude.substring(0, 6);
		    		String currentlongitude1 = currentlongitude.substring(0, 6);		    		
		    		float currentlatitude11=Float.parseFloat(currentlatitude1);
		    		float currentlongitude11=Float.parseFloat(currentlongitude1);
		    		
		    		
		    		if(f1==currentlatitude11 && f2==currentlongitude11){
		    			System.out.println(f1+"**"+currentlatitude11+"**"+f2+"**"+currentlongitude11);
		    			count++;
		    			System.out.println("count-"+count); 
		    			if(count>30) {		    			
			    			if(dbstatus.equals("ON")){
			    				stmt.executeUpdate("update VTL_SERVER set STATUS='"+"NOTMOVING"+"' where USERID='"+userid+"'");	
			    				phonelineno = "PROCESS TERMINATED";
						    	System.out.println("\n"+phonelineno+"\n");
			    			}  
			    			
			    			if(dbstatus.equals("OFF")){
			    				phonelineno = "PROCESS EXIT";
			    				stmt.executeUpdate("delete VTL_SERVER where USERID='"+userid+"'");	
			    				System.out.println("\n"+phonelineno+"\n");
			    			}				    	
				    	}
		    			else if(count<30){
		    			  phonelineno = "CROSSING";		    			  
		    			  rs = stmt.executeQuery("select * from VTL_SERVER where USERID='"+userid.trim()+"'");
		    		        Boolean result=rs.next();
		    		        //System.out.println("RESULT BOOLEAN STATUS: "+result);
		    		           
		    				        if(result){
		    				        	System.out.println("VALUES EXISTING IN VTL_SERVER");
		    				        	stmt.executeUpdate("update VTL_SERVER set LATITUDE='"+currentlatitude+"' where USERID='"+userid+"'");
		    				        	stmt.executeUpdate("update VTL_SERVER set LONGITUDE='"+currentlongitude+"' where USERID='"+userid+"'");
		    				        	stmt.executeUpdate("update VTL_SERVER set DATEANDTIME='"+dateFormat.format(date)+"' where USERID='"+userid+"'");
		    				        	stmt.executeUpdate("update VTL_SERVER set STATUS='"+"MOVING"+"' where USERID='"+userid+"'");
		    				        	stmt.executeUpdate("update VTL_SERVER set PROCESS='"+"ON"+"' where USERID='"+userid+"'");
		    							}
				    	     System.out.println("\nCROSSING\n");				    	  
		    			}
		    			
		    		}
	    	  }
	   	} 
	   	stmt.close();
		con.close();
	    String str = phonelineno+"***"+imeino+"***"+userid+"***"+authenid;
	    PrintWriter pw=response.getWriter();    	
    	pw.write(str);   
			}
    		catch(Exception e){	 
    			//e.printStackTrace();
    		}
        	return null;
    	}
    
    public static float Round(double final12, int Rpl) {
  	  float p = (float)Math.pow(10,Rpl);
  	  final12 = final12 * p;
  	  float tmp = Math.round(final12);
  	  return (float)tmp/p;
  	  }
}