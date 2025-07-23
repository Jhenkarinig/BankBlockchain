<%@page import="com.green.bank.model.AccountModel"%>
<%@page import="com.green.bank.database.JDBC_Connect"%>
<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Transfer</title>
<link rel="shortcut icon" type="image/png" href="image/favicon.png" />
<link rel="stylesheet" type="text/css" href="css/deposit.css">
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
</head>
<%@page import="java.net.*,java.io.*,java.util.*" %>

<body>
	<div class="row">
		<jsp:include page="header.jsp" />
	</div>
	<div class="container-fullwidth">
		<%
			AccountModel ac = null;
		%>
		<%
			ac = (AccountModel) session.getAttribute("userDetails");
			if (ac != null) {
		%>
		<div class="row" style="margin-top: 50px;">
			<div class="col-md-4 col-md-offset-4">
				
					<h2>Registered Users</h2>
					<div class="col-md-12">
						<hr class="colorgraph">
					</div>
					<%
					try
					{
						JDBC_Connect connect = new JDBC_Connect();
                                                Connection con = connect.getConnection();
                                                
                                                PreparedStatement pst=con.prepareStatement("select * from account where username<>?");
                                                pst.setString(1,ac.getUsername());
                                                ResultSet rs=pst.executeQuery();
				           
				          
				           {
				        	   %>
				        	   <table border=1 width=800>
				        	   <tr>
				        	   <th>ID</th>
				        	   <th>FNAME</th>
				        	   <th>LNAME</th>
				        	   <th>ADDRESS</th>
                                                   <th>CITY</th>
                                                                                                      
				        	   <th>USERNAME</th>
                                                    
                                                   <th>PHONE NO.</th>
                                                    <th>EMAIL ID</th>
                                                     <th>ACCOUNT TYPE</th>
                                                      <th>REG. DATE</th>
				        	   </tr>
				        	   
				        	   <%
				        	   while (rs.next())
                                                   {
                                                       %>
                                                       <tr>
                                                           <td><%=rs.getString(1)%></td>
                                                           <td><%=rs.getString(2)%></td>
                                                           <td><%=rs.getString(3)%></td>
                                                           <td><%=rs.getString(4)%></td>
                                                           <td><%=rs.getString(5)%></td>
                                                           <td><%=rs.getString(8)%></td>
                                                           <td><%=rs.getString(10)%></td>
                                                           <td><%=rs.getString(11)%></td>
                                                           <td><%=rs.getString(12)%></td>
                                                           <td><%=rs.getString(13)%></td>
                                                          
                                                       </tr>
                                                       <%
                                                   }
                                                       
				        	  
				        	   
				        	   %>
				        	   
				        	   
				        	   </table>
				        	   <%
				           }
						
						
						
					}
					catch(Exception e)
					{
						System.out.println(e);
						e.printStackTrace();
					}
					
					%>
					<div class="col-md-12">
						<hr class="colorgraph">
					</div>
					
					<%
						String isPassOK = (String) request.getAttribute("isPassOK");
							if (isPassOK != null && isPassOK.equals("No")) {
					%>
					<div class="col-md-12">
						<div class="alert alert-danger" role="alert">
							<strong>Sorry!</strong> Transfer account no/Password incorrect.
						</div>
					</div>
					<%
						}
					%>
					
					</div>
				</form>
			</div>
		</div>
		<%
			} else {
		%>
		<div class="row" style="margin-top: 150px;">
			<div class="alert alert-warning col-md-4 col-md-offset-4"
				role="alert">
				<strong>Warning!</strong> You have to login first.
			</div>
		</div>

		<%
			}
		%>

		
	</div>
</body>
</html>