package com.source;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;

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

public class AdminCalculation extends org.apache.struts.action.Action 
{
	ResultSet rs;
	double dbvaluelat,dbvaluelon;
	PreparedStatement ps,ps1;
	Statement stmt = null;
	String speed="",dblat="",dblon="",dateandtime="";
	Connection con = null;
	double value=0.0;
	double value1; 
	double final1;
	BigDecimal num1,num2;
	int speed1=0,vv=0;
	int count=0;
	int hr,mn,sec;
	HashMap hs=new HashMap();

    @SuppressWarnings("unchecked")
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		    	
		Boolean status=true;
		try{
		HttpSession sess=request.getSession();
		DBConnection dbconn=new DBConnection();	
		Random rr=new Random();
		con=dbconn.getConnect();		
		stmt=con.createStatement();
        DateFormat df = DateFormat.getInstance();
    	DynaValidatorForm dvf=(DynaValidatorForm)form;
    	String fromlatitude=dvf.get("fromlatitude").toString();
    	String fromlongitude=dvf.get("fromlongitude").toString();
    	String tolatitude=dvf.get("tolatitude").toString();
    	String tolongitude=dvf.get("tolongitude").toString();
    	
    	   double a=Double.valueOf(fromlatitude);
		   double b=Double.valueOf(fromlongitude);
		   double c=Double.valueOf(tolatitude);
		   double d=Double.valueOf(tolongitude);
		   
    		System.out.println("fromlatitude:"+fromlatitude);
    		System.out.println("fromlongitude:"+fromlongitude);
    		System.out.println("tolatitude:"+tolatitude);
    		System.out.println("tolongitude.:"+tolongitude);
    		
    		if(fromlatitude.equals(tolatitude) && fromlongitude.equals(tolongitude)){
    			System.out.println("#########same positions#########");
    			return mapping.findForward("samepositions");
    		}
    		else{
    			rs = stmt.executeQuery("select * from VTL_SPEEDUPDATIONS");
                while(rs.next()){
                	dblat=rs.getString(3);
                	dblon=rs.getString(4);
                	System.out.println("&&&&&&&&&&");
                	dbvaluelat=Double.valueOf(dblat);
                	dbvaluelon=Double.valueOf(dblon);              
                
	                if((a>=dbvaluelat && c<=dbvaluelat) || (a<=dbvaluelat && c>=dbvaluelat)){
	                	speed=rs.getString("AVGSPEED");
	                	dateandtime=rs.getString("DATEandTIME");
	                	if(!speed.equals(null))
	                		count++;
	                		hs.put(count, dateandtime);
	                		System.out.println("count--"+count);
	                		value1=Float.parseFloat(speed);
	                		value=value+value1;
					}
                }
                System.out.println("final count--"+count);
                String string1=hs.get(1).toString();
                String string2=hs.get(count).toString();
                String st11="",st12="",st21="",st22 = "",st23="";
                StringTokenizer st1=new StringTokenizer(string1, " ");
                while(st1.hasMoreTokens()){
                	st11=st1.nextToken();
                	st12=st1.nextToken();
                	 StringTokenizer st2=new StringTokenizer(st12, ":");
                     while(st2.hasMoreTokens()){
                    	 st21=st2.nextToken();
                    	 st22=st2.nextToken();
                    	 st23=st2.nextToken();
                     }
                }
                
                String st111="",st121="",st211="",st221="",st231="";
                StringTokenizer st1111=new StringTokenizer(string2, " ");
                while(st1111.hasMoreTokens()){
                	st111=st1111.nextToken();
                	st121=st1111.nextToken();
                	 StringTokenizer st2111=new StringTokenizer(st121, ":");
                     while(st2111.hasMoreTokens()){
                    	 st211=st2111.nextToken();
                    	 st221=st2111.nextToken();
                    	 st231=st2111.nextToken();
                     }
                }                
                if(Integer.parseInt(st21)>Integer.parseInt(st211)){
                	hr=Integer.parseInt(st21)-Integer.parseInt(st211);
                }
                else{
                	hr=Integer.parseInt(st211)-Integer.parseInt(st21);
                }
                if(Integer.parseInt(st22)>Integer.parseInt(st221)){
                	mn=Integer.parseInt(st22)-Integer.parseInt(st221);
                }
                else{
                	mn=Integer.parseInt(st221)-Integer.parseInt(st22);
                }
                if(Integer.parseInt(st23)>Integer.parseInt(st231)){
                	sec=Integer.parseInt(st23)-Integer.parseInt(st231);
                }
                else{
                	sec=Integer.parseInt(st231)-Integer.parseInt(st23);
                }
                final1=value/count;
                System.out.println("TOTAL SPEED:::"+value);
                System.out.println("AVERAGE SPEED:::"+final1);
                System.out.println("TIME DIFFERENCE:::"+hr+":"+mn+":"+sec);
                String tottime=hr+":"+mn+":"+sec;
                
                float round = Round(final1,2);
                
                stmt.executeUpdate("insert into VTL_ESTIMATION values('"+fromlatitude+"','"+fromlongitude+"','"+tolatitude+"','"+tolongitude+"','"+round+"','"+tottime+"')");      	
                
                con.close();
                stmt.close();
                sess.setAttribute("fromlatitude", fromlatitude);
                sess.setAttribute("fromlongitude", fromlongitude);
                sess.setAttribute("tolatitude", tolatitude);
                sess.setAttribute("tolongitude", tolongitude);
                sess.setAttribute("avgspeed", round);
                sess.setAttribute("tottime", tottime);
                return mapping.findForward("calcsuccess");	
    			}                	
		    }
    		catch(Exception e){	 
    			e.printStackTrace();
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