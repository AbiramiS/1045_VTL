package com.communication;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.*;
import java.util.Date;
import java.util.Properties;

public class SendSMS{
	public void SendSMS1(String number,String mess) {
		String username="",password="";
	try{
		 Properties prop1=new Properties();
	     prop1.load(new FileInputStream("Site2SMS.properties"));
	     username = prop1.getProperty("userid");
	     password = prop1.getProperty("password");
		
		int c;
		URL url = new URL("http://ubaid.tk/sms/sms.aspx?uid="+username+"&pwd="+password+"&msg="+mess+"&phone="+number+"&provider=site2sms");
		URI uri = new URI(url.getProtocol(), url.getAuthority(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
		URL hp=new URL(uri.toString());
		URLConnection hpCon = hp.openConnection();
		System.out.println("Date: " + new Date(hpCon.getDate()));
		System.out.println("Content-Type: " +
		hpCon.getContentType());
		System.out.println("Expires: " + hpCon.getExpiration());
		System.out.println("Last-Modified: " +
		new Date(hpCon.getLastModified()));
		int len = hpCon.getContentLength();
		System.out.println("Content-Length: " + len);
		if (len > 0)
		{
		System.out.println("=== Content ===");
		InputStream input = hpCon.getInputStream();
		int ii = len;
		while (((c = input.read()) != -1) && (ii > 0))
		{
		System.out.print((char) c);
		}		
		input.close();
		System.out.println("Msg Sent Successfully........");
		}
		else
		{
		System.out.println("No Content Available");
		}
        String data=new String();
  	}
	catch(Exception e1){
		e1.printStackTrace();
   	}
}

}
