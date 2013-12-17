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
vc.clear();
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
    		values=lat+"***"+lon;
			vc.add(values);
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
									<li><a href="adminlogin.jsp"><span>Back</span></a></li>
									<li class=""><a href="updation.jsp"><center>Trip Line Updation</center></a></li>
									<li class=""><a href="calculation.jsp">Mean Calculation</a></li>
									<li class="last"><a href="">Logout</a></li>
								</ul>
								
								<br class="clear" />
							</div>
							
						</div>
					</div>
				</div>
				
			</div>
			
			<!--header end-->
							<br/><table width=490px cellpadding="0" cellspacing="0" border="0">
					        	<tr><td><iframe id="frame1" src="Mapindex.jsp" width=700px height=450px frameborder=5> </iframe></td></tr>      	
					        </table><br/><br/>
						<div class="padding2">
							<img alt="" src="images/2-t2.gif" /><br />
							<div class="content">
								
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