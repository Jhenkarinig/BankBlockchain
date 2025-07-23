<%-- 
    Document   : viewadmintransactions
    Created on : Dec 10, 2024, 7:43:49 PM
    Author     : user
--%>


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
				
					<h2>Transactions</h2>
					<div class="col-md-12">
						<hr class="colorgraph">
					</div>
					<%
					try
					{
                                            JDBC_Connect connect = new JDBC_Connect();
                                                Connection con = connect.getConnection();
                                                
                                                PreparedStatement pst=con.prepareStatement("select username from account where username<>?");
                                               pst.setString(1,"admin");
                                                ResultSet rs=pst.executeQuery();
                                                %>
                                                <form name="viewtrans" method="get" action="">
                                                <center>
                                                <p>
                                                    Select User: &nbsp;
                                                    <select name="user">
                                                    <%
                                                    while(rs.next())
                                                        out.println("<option>"+rs.getString(1)+"</option>");
                                                    %>
                                                    </select>
                                                    &nbsp;
                                                    <input type="submit" name="submit" />
                                                    
                                                        </p>
                                                </center>
                                                </form>
                                            <%
                                                    if (request.getParameter("user")!=null)
                                                    {
                                                        
                                                    
                                            
						Socket soc=new Socket("localhost",3000);
				           ObjectOutputStream oos=new ObjectOutputStream(soc.getOutputStream());
				           ObjectInputStream oin=new ObjectInputStream(soc.getInputStream());
				           
				           oos.writeObject("VIEWLOG");
                                           oos.writeObject(request.getParameter("user"));
				           Vector v=(Vector)oin.readObject();
				           
				           if (v.size()==0)
				           {
				        	   out.println("<h1>NO TRANSACTIONS FOUND!</h1>");
				           }
				           else
				           {
				        	   %>
				        	   <table border=1 width=800>
				        	   <tr>
				        	   <th>FROM</th>
				        	   <th>TO</th>
				        	   <th>OPERATION</th>
				        	   <th>AMOUNT</th>
				        	   <th>DATE/TIME</th>
				        	   </tr>
				        	   
				        	   <%
				        	   for (int i=0;i<v.size();i++)
				        	   {
				        		   StringTokenizer st=new StringTokenizer(v.get(i).toString(),"$");
				        		   out.println("<tr>");
				        		   while (st.hasMoreTokens())
				        		   {
				        			   %>
				        			   <td><%=st.nextToken() %></td>
				        			   <%
				        		   }
				        		   out.println("</tr>");
				        	   }
				        	  
				        	   
				        	   %>
				        	   
				        	   
				        	   </table>
				        	   <%
				           }
						
						
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

		<!-- Footer start here -->
		<div class="row" style="margin-top: 50px;">
			<jsp:include page="footer.jsp"></jsp:include>
		</div>
	</div>
</body>
</html>
