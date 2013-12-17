package com.source;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
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

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.text.SimpleDateFormat;

import com.database.DBConnection;

public class UpdateVTL extends org.apache.struts.action.Action 
{
	ResultSet rs, rs1;
	PreparedStatement ps,ps1;
	Statement stmt = null;
	Connection con = null;
	Random rr = new Random();
    @SuppressWarnings("unchecked")
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		    	
		Boolean status=true;
		FileInputStream fis = null;
		int ch;
    	Date date1 = new Date();
        int day, month, year;
        int second, minute, hour;
        Calendar currentcal = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        GregorianCalendar date = new GregorianCalendar();
		String totvalue = "", exptime = "";
		int altercal = 0;
		int totalmon = 0, nextmonthdays = 0;
		int finalexpdate = 0;
		String dd = "", mm = "", yy = "";
		int totalyear = 0;
		String exdate = "";
		
		try {			
			DBConnection dbconn=new DBConnection();	
			Random rr=new Random();
	    	con=dbconn.getConnect();		
    		stmt=con.createStatement(); 
	    	DynaValidatorForm dvf=(DynaValidatorForm)form;
	    	String userid2=dvf.get("userid2").toString();
	        rs1 = stmt.executeQuery("select * from VTL_USERUPDATION where USERID='"+userid2+"'");
	        Boolean result1=rs1.next();	        
	        if(result1) {
	        	DateFormat dateFormat11 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	            Date date11 = new Date();
	            System.out.println(dateFormat11.format(date11));
	        	
	        	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	     	   //get current date time with Calendar()
	     	   Calendar cal1 = Calendar.getInstance();
	     	   System.out.println("Current Date Time : " + dateFormat.format(cal1.getTime()));
	     	   
	     	  cal1 = Calendar.getInstance();
	     	  cal1.add(Calendar.MONTH, 1);
	     	  System.out.println("Add one month to current date : " + dateFormat.format(cal1.getTime()));
	     	  String newexpdate=dateFormat.format(cal1.getTime());
	     	   
	        	/*int total = Integer.parseInt(dd);
				int lastmonth = 12;
				int upgrade = 10;
				int nmv = 0;
				int finalval = 0;
				int monthdays31 = 31;
				int monthdays30 = 30;
				int monthdays29 = 29;
				int monthdays28 = 28;
				int monthdays = 0;
				int monthvalue = 0;
				int currentmonthdays = Integer.parseInt(mm);
				int leapyear = Integer.parseInt(yy)%4;
	       
	        	
				if(currentmonthdays>0 && currentmonthdays<13 && total>0 && total<32){
					if(currentmonthdays==02 && total<29){
						System.out.println("INVALID DATE");
					}
					if(currentmonthdays==02 || currentmonthdays==2){
						monthvalue=2;
					}
					if(monthvalue==1 && currentmonthdays==1 || currentmonthdays==3 || currentmonthdays==5 || currentmonthdays==7 || currentmonthdays==8 || currentmonthdays==10){
						monthdays = 31;
						currentmonthdays = 31;
					}
					if(currentmonthdays==2){
						if(leapyear==0){
							monthdays = 28;
							currentmonthdays = 28;
						}
						if(leapyear==1){
							monthdays = 29;
							currentmonthdays = 29;
						}
					}
					if(currentmonthdays==12){
						nmv = 1;
						monthdays = 31;
						currentmonthdays = 31;
					}
					if(monthvalue==0 && currentmonthdays==4 || currentmonthdays==6 || currentmonthdays==9 || currentmonthdays==11){
						monthdays = 30;
						currentmonthdays = 30;
					}
					if(total>18 && total<=28 && monthdays==28){
							int remainingdays = monthdays - total;
							finalval = upgrade - remainingdays;
							nmv = Integer.parseInt(mm)+1;
					}
					if(total>=1 && total<=18 && monthdays==28){
							nmv = Integer.parseInt(mm)+1;
							total = total+10;
							finalval = total;
					}
					if(total>18 && total<=28 && monthdays==29){
							System.out.println(total+"()-"+monthdays);
							int remainingdays = monthdays - total;
							finalval = upgrade - remainingdays;
							nmv = Integer.parseInt(mm)+1;
					}
					if(total>=1 && total<=18 && monthdays==29){
							nmv = Integer.parseInt(mm)+1;
							total = total+10;
							finalval = total;
					}

				    if(total>=1 && total<=20 && monthvalue==0){
						if(Integer.parseInt(mm)==lastmonth){
							nmv = 1;
							total = total+10;
							finalval = total;
						}
						else{
							nmv = Integer.parseInt(mm)+1;
							total = total+10;
							finalval = total;
						}
		        	}
		        	else if(total>=1 && total<=20 && monthvalue==1){
						if(Integer.parseInt(mm)==lastmonth){
							total = total+10;
							nmv = 1;
							finalval = total;
						}
						else{
						    total = total+10;
						    nmv = Integer.parseInt(mm)+1;
						    finalval = total;
						}
		        	}
		        	else if(total>=20 && monthdays==30){
							int remainingdays = currentmonthdays - total;
							finalval = upgrade - remainingdays;
							nmv = Integer.parseInt(mm)+1;
		        	}

		        	else if(total>=20 && monthdays==31){
							if(nmv==1){
								nmv = 1;
								int remainingdays = currentmonthdays - total;
								finalval = upgrade - remainingdays;
							}
							else{
								int remainingdays = currentmonthdays - total;
								finalval = upgrade - remainingdays;
								nmv = Integer.parseInt(mm)+1;
									if(finalval==0 && currentmonthdays==31){
										finalval = 31;
									}
									if(finalval==0 && currentmonthdays==30){
										finalval = 30;
									}
							 }
		        	  }
				}
        	System.out.println("NEXT MONTH-"+nmv);
			System.out.println("expiration date-"+finalval);			
			exdate= String.valueOf(finalval)+"/"+String.valueOf(nmv)+"/"+yy;*/		
			stmt.executeUpdate("update VTL_USERUPDATION set EXPIRATIONDATE='"+newexpdate+"' where USERID='"+userid2+"'");
	        }
	        con.close();
            stmt.close();
	        exdate = "UPDATED SUCCESSFULLY";
        	PrintWriter pw=response.getWriter();
        	pw.write(exdate); 
    		}
    		catch(Exception e){	 
    			e.printStackTrace();
    		}
        return null;
  }
}