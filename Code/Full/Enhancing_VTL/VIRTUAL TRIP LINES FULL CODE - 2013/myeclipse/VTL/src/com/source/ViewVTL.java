package com.source;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
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

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.communication.SendMail;
import com.communication.SendSMS;
import com.database.DBConnection;

public class ViewVTL extends org.apache.struts.action.Action 
{
	ResultSet rs, rs1;
	PreparedStatement ps,ps1;
	Statement stmt = null;
    String s1 = null,s2 = null,s3 = null,s4 = null;
	Connection con = null;
	Random rr = new Random();
	String tripid = "" , tripidvalue = "";
	String lat = "", lon = "", currentdate = "";
	String da = "", mo = "", ye = "", currdatetime = "", v1 = "", v2 = "", vh = "", vm = "", vs = "",ye1="",mo1="",da1="", mailuserid="",mailpassword="";;
	int estimatedvalue, totalh, totalm, totals; 
	String ffinal = "";
	int vvin;

    @SuppressWarnings("unchecked")
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		    	
		Boolean status=true;
		Properties prop=new Properties();
		FileInputStream fis = null;
		int ch;
    	Date date1 = new Date();
        int day, month, year;
        int second, minute, hour;
        Calendar currentcal = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        GregorianCalendar date = new GregorianCalendar();
		String totvalue = "", exptime = "", expdate = "";
		int altercal = 0;
		String currdata = "";
		try {	
			 prop.load(new FileInputStream("MailProperties.properties"));
			 mailuserid = prop.getProperty("userid");
			 mailpassword =prop.getProperty("password");
			
			String DATE_FORMAT = "yyyy-MM-dd";
		    java.text.SimpleDateFormat sdf =
		          new java.text.SimpleDateFormat(DATE_FORMAT);
		    Calendar calender1 = Calendar.getInstance();

		    Calendar calender2 = Calendar.getInstance();
		    
			RSA rsa = new RSA();
			DBConnection dbconn=new DBConnection();	
			Random rr=new Random();
	    	con=dbconn.getConnect();		
    		stmt=con.createStatement(); 
	    	DynaValidatorForm dvf=(DynaValidatorForm)form;
	    	String luserid=dvf.get("luserid").toString();	
	    	String authenid=dvf.get("authenid").toString();
	    	String dbaid = "", dbmobileno = "", emailid = "";
	    	ResultSet rs12 = stmt.executeQuery("select * from VTL_USERREGISTRATION where USERID='"+luserid+"'");
    		Boolean result2=rs12.next();
	        if(result2){
	        	dbaid = rs12.getString("AUTHENTICATIONID");
	        	dbmobileno = rs12.getString("MOBILENO");
	        	emailid = rs12.getString("EMAILID");
	        	if(dbaid.equals(authenid)){
	        		
	        		/* expiration time calculation*/
	        		day = date.get(Calendar.DAY_OF_MONTH);
	    	        month = date.get(Calendar.MONTH);
	    	        year = date.get(Calendar.YEAR);
	    	        second = date.get(Calendar.SECOND);
	    	        minute = date.get(Calendar.MINUTE);
	    	        hour = date.get(Calendar.HOUR);

	    	        String strDateFormat = "HH:mm:ss a";
	    	  	    SimpleDateFormat sdf1 = new SimpleDateFormat(strDateFormat);
	    	        System.out.println("Time with AM/PM field : " + sdf1.format(date1));
	    	        currdatetime = sdf1.format(date1);
	    	        StringTokenizer timeestimation = new StringTokenizer(currdatetime, " ");
	    	    	while(timeestimation.hasMoreTokens()){
	    	    		v1=timeestimation.nextToken();
	    	    		v2=timeestimation.nextToken();
	    	    		StringTokenizer timeestimation1 = new StringTokenizer(v1, ":");
	    		    	while(timeestimation1.hasMoreTokens()){
	    		    		vh=timeestimation1.nextToken();
	    		    		vm=timeestimation1.nextToken();
	    		    		vs=timeestimation1.nextToken(); 
	    		    		System.out.println(vh+"###"+vm+"###"+vs);
	    		    	}
	    	    	}
	    	    	int thours = 23 - Integer.parseInt(vh);
	    	    	int tminutes = 59 - Integer.parseInt(vm);
	    	    	int tseconds = 60 - Integer.parseInt(vs);
	    	    	
	    	    	//System.out.println(thours+"@@@@@"+tminutes+"@@@@@"+tseconds); 	    	        
	    	        
	    	        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	 	     	    //get current date time with Calendar()
	 	     	    Calendar cal1 = Calendar.getInstance();
	 	     	    System.out.println("Current Date Time : " + dateFormat.format(cal1.getTime())); 
	 	     	    currdata=dateFormat.format(cal1.getTime());
	    	        
	    	        rs1 = stmt.executeQuery("select * from VTL_USERUPDATION where USERID='"+luserid+"'");
	    	        Boolean result1=rs1.next();
	    	        if(result1) {
	    	        	expdate = rs1.getString("EXPIRATIONDATE");
	    	        	
	    	        	//System.out.println(expdate+"!!!!!!!!!!!!!"+currdata);  
	    	        	if(expdate.equals(currdata)){
	    	        		System.out.println("-----expired");  
	    	        	}
	    	        	String value1="",value2="";
	    	        	StringTokenizer ext1 = new StringTokenizer(expdate, " ");
	    		    	while(ext1.hasMoreTokens()) {
	    		    		value1=ext1.nextToken();
	    		    		value2=ext1.nextToken();
	    		    	}
	    	        	
	    	        	StringTokenizer ext = new StringTokenizer(value1, "/");
	    		    	while(ext.hasMoreTokens()) {
	    		    		ye=ext.nextToken();
	    		    		mo=ext.nextToken();
	    		    		da=ext.nextToken();
	    		    	}
	    		    	altercal = Integer.parseInt(mo) - 1;
	    	        	cal.set(Integer.parseInt(ye), altercal, Integer.parseInt(da));
	    	        	String nvalue1="",nvalue2="";
	    	        	StringTokenizer ext123 = new StringTokenizer(currdata, " ");
	    		    	while(ext123.hasMoreTokens()) {
	    		    		nvalue1=ext123.nextToken();
	    		    		nvalue2=ext123.nextToken();
	    		    	}
	    	        	
	    	        	StringTokenizer ext1234 = new StringTokenizer(nvalue1, "/");
	    		    	while(ext1234.hasMoreTokens()) {
	    		    		ye1=ext1234.nextToken();
	    		    		mo1=ext1234.nextToken();
	    		    		da1=ext1234.nextToken();
	    		    	}
	    	        	
	    	        	
	    	        	
	    	        	calender1.set(Integer.parseInt(ye), Integer.parseInt(mo) , Integer.parseInt(da));
	    	        	calender2.set(Integer.parseInt(ye1), Integer.parseInt(mo1) , Integer.parseInt(da1));
	    	        	
	    	        		System.out.print(sdf.format(calender1.getTime()));
	    	        		if (calender1.before(calender2)) {
	    	        	       System.out.print(" is before ");
	    	        	       }
	    	        	    if (calender1.after(calender2)) {
	    	        	       System.out.print(" is after ");
	    	        	      }
	    	        	    if (calender1.equals(calender2)) {
	    	        	       System.out.print(" same as ");
	    	        	      }
	    	        	    System.out.print(sdf.format(calender2.getTime()));
	    	        	
	    	        		  if(cal.before(currentcal)) {
	    	        			  String dada = "EXPIRED";
	    	        			  System.out.println(dada);
	    	        			  ffinal = "Sorry";
	    	        			  //System.out.println("Current date(" + new SimpleDateFormat("dd/MM/yyyy").format(currentcal.getTime()) + ") is greater than the given date " + new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime()));
	    	        		  }
	    	        		  else if(cal.after(currentcal)) {
	    		        			String dada = "VALIDITY ON";
	    		        		    System.out.println(dada);
	    		        		    String valval = getTimeDiff(cal.getTime(),currentcal.getTime());
	    		        		    //System.out.println("^^^^^^^^^"+valval);
	    		        		    StringTokenizer innerval = new StringTokenizer(valval, " ");
	    		    		    	while(innerval.hasMoreTokens()){
	    		    		    		s1=innerval.nextToken();
	    		    		    		s2=innerval.nextToken();
	    		    		    		s3=innerval.nextToken();
	    		    		    		s4=innerval.nextToken();
	    		    		    	}

	    		        		    vvin = Integer.parseInt(s1) / 24;
	    		        		    //System.out.println("^^^^^^^^^"+vvin);
	    		    		    	totalh = Integer.parseInt(s1) + thours;
	    		    		    	totalm = Integer.parseInt(s3) + tminutes;
	    		    		    	totals = tseconds;
	    		    		    	
	    		    		    	
	    		    		    	System.out.println("\n\n"+"()"+totalh+"()"+totalm+"()"+totals+"()"+"\n\n");
	    		    		    	
	    		    		    	ffinal = totalh+":"+totalm+":"+totals;
	    		        		    
	    		        		    //estimatedvalue = 
	    	        			//System.out.println("Current date(" + new SimpleDateFormat("dd/MM/yyyy").format(currentcal.getTime()) + ") is less than the given date " + new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime()));
	    	        		  }
	    	        		  else {
	    	        			String dada = "EQUAL-VALIDITY STILL ON";
	    	        			System.out.println(dada);
	    	        			System.out.print("Both date are equal.");
	    	        		  } 
	    		        }
	    		        else {
	    		        	stmt.executeUpdate("insert into VTL_USERUPDATION values('"+luserid+"','"+currdata+"')");    
	    		        }
	    	        
	    	        
	    	        
	    	        fis = new FileInputStream("VTL Positions.txt");
	    	    	byte b[]=new byte[1024];
	    	    	while((ch=fis.read(b))!=-1){
	    	    		String tempvalue = new String(b);
	    	    		totvalue+=tempvalue.trim();
	    	    	}
	    	    	
	    	        
	    	        //trip line id generation to corresponding latitude and longitude positions
	    	    	/*StringTokenizer st = new StringTokenizer(totvalue, "\n");
	    	    	while(st.hasMoreTokens()){
	    	    		tripidvalue=st.nextToken();
	    	    		StringTokenizer st1 = new StringTokenizer(tripidvalue, ",");
	    		    	while(st1.hasMoreTokens()){
	    		    		lat=st1.nextToken();
	    		    		lon=st1.nextToken();
	    		    		ResultSet rs = stmt.executeQuery("select * from VTL_TRIPID where LATITUDE='"+lat+"' and LONGITUDE='"+lon+"'");
	    		    		Boolean result=rs.next();
	    		    		tripid = "TLID"+String.valueOf(rr.nextInt(99999));
	        		        if(result){
	        		        	stmt.executeUpdate("update VTL_TRIPID set TRIPID='"+tripid+"' where LATITUDE='"+lat+"'");
	        		        	stmt.executeUpdate("update VTL_TRIPID set TRIPID='"+tripid+"' where LONGITUDE='"+lon+"'");	
	        		        }	
	        		        else{
	        		        	stmt.executeUpdate("insert into VTL_TRIPID values('"+lat+"','"+lon+"','"+tripid+"')");    
	        		        }
	    		    	}	    		
	    	    	}*/
	    	    	 
	    	    	String keyvalues = rsa.ResponseForwarder();
    		    	System.out.println("keyvalues is......."+keyvalues);
    		    	String devalue = "", puvalue = "";
    		    	StringTokenizer sstt = new StringTokenizer(keyvalues, ":");
    		    	while(sstt.hasMoreTokens()){
    		    		devalue = sstt.nextToken();
    		    		puvalue = sstt.nextToken();
    		    	}    		    	
    		    	//String subject = "Notification from VTL Networks";
    		    	//SendMail sm = new SendMail();
    		    	//sm.send(subject, emailid);
    		    	
	    	    	
	    	    	int rr1 = rr.nextInt(9999);
	    	    	stmt.executeUpdate("update VTL_USERREGISTRATION set DECKEY='"+rr1+"' where USERID='"+luserid+"'"); 
	    	    	
	    	    	String sendvalue = "Hello "+luserid+", Your Decryption Key is:"+rr1+".Enter it in your application and continue.";    		    	
    		    	//SendSMS sms=new SendSMS();
    		    	//sms.SendSMS1(dbmobileno,sendvalue);  
    		    	
    		    	SendMail sme=new SendMail(mailuserid, emailid, "MESSAGE FROM PROJECT VTL", sendvalue, mailuserid, mailpassword);
    		    	
    		    	ffinal = "Success";
    		    	ffinal = ffinal+"/"+String.valueOf(vvin)+"/"+totvalue+"/"+rr1;
	    	    	
	    	    	/*edited*/
	        		//System.out.println("totvalue:"+totvalue);
	            	//PrintWriter pw=response.getWriter();    	
	            	//pw.write("***"+ffinal);
	    	    	/*edited*/
	        		
	        	}
	        	else if(!dbaid.equals(authenid)){
	        		ffinal = "Sorry"+"/"+"Invalid"+"/"+"Authentication";
	        		PrintWriter pw=response.getWriter();    	
	            	pw.write(ffinal); 
	        	}
	        }
	        else if(!result2){
        		ffinal = "Sorry"+"/"+"Invalid"+"/"+"Authentication";
        		PrintWriter pw=response.getWriter();    	
            	pw.write(ffinal); 
        	}con.close();
            stmt.close();
	        //System.out.println("ffinal-------"+ffinal);
	        PrintWriter pw=response.getWriter();    	
        	pw.write(ffinal); 
	        
    		}
    		catch(Exception e){	 
    			e.printStackTrace();
    		}
        return null;
  }
    public String getTimeDiff(Date dateOne, Date dateTwo) {
        String diff = "";
        long timeDiff = Math.abs(dateOne.getTime() - dateTwo.getTime());
        diff = String.format("%d hour(s) %d min(s)", TimeUnit.MILLISECONDS.toHours(timeDiff),
        TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff)));
        return diff;
    }
}