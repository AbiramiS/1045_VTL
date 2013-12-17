<%@page import="java.util.*,java.io.*,java.sql.*" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>----Index----</title>
<link href="style.css" rel="stylesheet" type="text/css" />
<link href="layout.css" rel="stylesheet" type="text/css" />
<script src="maxheight.js" type="text/javascript"></script>
</head>
<%!
Connection con;
Statement stmt;
ResultSet rs;
PreparedStatement ps;
Vector vc=new Vector();
Vector vc1=new Vector();
Vector vc2=new Vector();
%>
<body id="index_1" onload="new ElementMaxHeight();">

<%
int ch;
String totvalue="",tripidvalue="",lat="",lon="",values="";
FileInputStream fis = null;
try{
vc1.clear();
vc2.clear();
fis = new FileInputStream("VTL Positions.txt");
   	byte b[]=new byte[1024];
   	while((ch=fis.read(b))!=-1){
   		String tempvalue = new String(b);
   		totvalue+=tempvalue.trim();
   	}
   	StringTokenizer st = new StringTokenizer(totvalue, "\n");
   	while(st.hasMoreTokens()){
   		tripidvalue=st.nextToken();
   		StringTokenizer st1 = new StringTokenizer(tripidvalue, ",");
    	while(st1.hasMoreTokens()){
    		lat=st1.nextToken();
    		lon=st1.nextToken();
			vc1.add(lat);
			vc2.add(lon);
    		}
    }
}
catch(Exception e){
}
%>

	<div id="header_tall">
		<div id="main">
			<!--header -->
			<div id="header">
				<div class="h_logo">
						<div class="left">
						<img alt="" src="images/vtllogonew.png" weight="120" height="120"/><br />
					</div>
					<div class="right">
						<a href="#"><strong>Virtual Trip Lines</strong></a></div>
					<div class="clear"></div>
				</div>
				<div id="menu">
					<div class="rightbg">
						<div class="leftbg">
							<div class="padding">
								<ul>
									<li><a href="adminsucces.jsp"><span>Back</span></a></li>
									<li><a href=""></a></li>
									<li class=""><a href="updation.jsp"><center>Trip Line Updation</center></a></li>
									<li><a href=""></a></li>
									<!--<li class=""><a href="calculation.jsp">Mean Calculation</a></li>
									--><li class="last"><a href="">Logout</a></li>
								</ul>
								<br class="clear" />
							</div>
						</div>
					</div>
				</div>
				<div class="content">
					<img alt="" src="images/header_t1.jpg" /><br />
					<img alt="" src="images/header_t2.jpg" /><br />
					<div class="text">
						<b><strong><font color="white">Enhancing Privacy and Accuracy in Probe Vehicle-Based Traffic Monitoring via Virtual Trip Lines.</font></strong></b><br />
					</div>   
					<a href="#"><img alt="" src="images/header_click_here.jpg" /></a><div class="clear"></div>
				</div>
			</div>
			<!--header end-->
			<div id="middle">
				<div class="indent">
					<div class="column1">
						<div class="padding1">
							<table width=490px cellpadding="0" cellspacing="0" border="0">
					        	<tr><td></td></tr>      	
					        </table><br/><br/><br/>
<center>			        
<h1><font color="black" size="5"><strong><center>TRIP LINE DETAILS</center></strong></font></h1><br/>
<br/><br/><hr/>
<html:form action="/AdminUpdation">
					  
Choose Latitude:
<html:select name="AdminUpdationForm" property="latitude">
<html:option value=""></html:option>
<%
for(int ro=0;ro<vc1.size();ro++)
{ 
		String dd=vc1.get(ro).toString();
		dd=dd.replace("[","");
		dd=dd.replace("]","");
		StringTokenizer token1=new StringTokenizer(dd,",");
		String s1=token1.nextToken();
%>
			
			<html:option value="<%=s1%>"><%=s1%></html:option>
			
<% } %>
</html:select>		
<br/><br/>
Choose Latitude:
<html:select name="AdminUpdationForm" property="longitude">
<html:option value=""></html:option>
<%
for(int ro=0;ro<vc2.size();ro++)
{ 
		String dd=vc2.get(ro).toString();
		dd=dd.replace("[","");
		dd=dd.replace("]","");
		StringTokenizer token1=new StringTokenizer(dd,",");
		String s1=token1.nextToken();
%>
			
			<html:option value="<%=s1%>"><%=s1%></html:option>
			
<% } %>
</html:select>	<br/><br/>
<br/><html:submit value="Upload" style="background-color:black; color:white;"/>
      <html:reset value="Reset" style="background-color:black; color:white;"/>
      <br class="spacer" />
      <div style="color:red">
 	  <html:errors />
</div>  
</html:form>
</center> 
						</div>
						<div class="padding2">
							<img alt="" src="images/2-t2.gif" /><br />
							<div class="content">
								
							</div>
						</div>
					</div>
					<div class="column2">
						<div class="border">
							<div class="btall">
								<div class="ltall">
									<div class="rtall">
										<div class="tleft">
											<div class="tright">
												
											</div>
										</div>
									</div>
								</div>
							</div> 
						</div>
					</div>
					<div class="clear"></div>
				</div>
			</div>
			<!--footer -->
			<div id="footer">
				<div class="indent">
					<center>Copyright &copy; 2012 Solutions Online &bull; <a href="index-6.html">Privacy Policy</a></center></div>
			</div>
			<!--footer end-->
		</div>
	</div>
</body>
</html>