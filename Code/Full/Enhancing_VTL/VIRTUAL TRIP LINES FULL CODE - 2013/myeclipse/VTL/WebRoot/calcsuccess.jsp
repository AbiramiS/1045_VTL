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

<body id="index_1" onload="new ElementMaxHeight();">

<%
String fromlatitude=session.getAttribute("fromlatitude").toString();
String fromlongitude=session.getAttribute("fromlongitude").toString();
String tolatitude=session.getAttribute("tolatitude").toString();
String tolongitude=session.getAttribute("tolongitude").toString();
String avgspeed=session.getAttribute("avgspeed").toString();
String tottime=session.getAttribute("tottime").toString();
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
									<li class=""><a href="calculation.jsp">Mean Calculation</a></li>
									<li class="last"><a href="">Logout</a></li>
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
							<br/><br/>
							
							
<center>			        
<h1><font color="black" size="5"><strong><center>SPEED INFORMATION BASED ON TWO GPS POSITIONS</center></strong></font></h1><br/>
<table bgcolor="white" align="center" border="5" cellpadding="5">
<font color="blue"><tr style="color:white">
<th><font color="white"><strong>FROM LATITUDE</strong></font></th>
<th><font color="white"><strong>FROM LONGITUDE</strong></font></th>
<th><font color="white"><strong>TO LATITUDE</strong></font></th>
<th><font color="white"><strong>TO LONGITUDE</strong></font></th>
<th><font color="white"><strong>AVG. SPEED<br/>(mph)</strong></font></th>
<th><font color="white"><strong>TIME TAKEN<br/>(HH:MM:SS)</strong></font></th>
<br/></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr></font>
		<tr>   
		<td><center><font color="white"><strong><%=fromlatitude%></strong></font></center></td>
		<td><center><font color="white"><strong><%=fromlongitude%></strong></font></center></td>
		<td><center><font color="white"><strong><%=tolatitude%></strong></font></center></td>
		<td><center><font color="white"><strong><%=tolongitude%></strong></font></center></td>
		<td><center><font color="white"><strong><%=avgspeed%></strong></font></center></td>
		<td><center><font color="white"><strong><%=tottime%></strong></font></center></td>
		</tr>
</table>
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