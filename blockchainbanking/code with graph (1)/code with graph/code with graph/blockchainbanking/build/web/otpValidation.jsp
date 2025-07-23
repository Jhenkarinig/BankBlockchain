<%-- 
    Document   : otpValidation
    Created on : Dec 12, 2024, 3:54:39 PM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>OTP Validation</title>
    </head>
    <%@page import="java.util.Random"%>
    <%@page import="email.SendMailExample"%>
    <%@page import="com.green.bank.database.JDBC_Connect,java.sql.*"%>
    <body>
        <h1>OTP Validation</h1>
        <%
        try
        {
            String account_no = request.getParameter("account_no");
		String username = request.getParameter("username");
		String target_acc_no = request.getParameter("target_acc_no");
		String password = request.getParameter("password");
                String amount = request.getParameter("amount");
                
            Random r=new Random();
            String otp=""+r.nextInt(10)+r.nextInt(10)+r.nextInt(10)+r.nextInt(10);
            otp.trim();
            String msg="OTP for amount transfer is "+otp;
            JDBC_Connect connect = new JDBC_Connect();
            Connection conn = connect.getConnection();

		Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("select email from account where id='" + account_no + "'");
                        
                rs.next();        
            new SendMailExample().main(rs.getString(1).trim(), msg);
            
            request.setAttribute("account_no", account_no);
            request.setAttribute("account_no", account_no);
            request.setAttribute("account_no", account_no);
            request.setAttribute("account_no", account_no);
            RequestDispatcher rd=request.getRequestDispatcher("TransferServlet");
            
            
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
            %>
    </body>
</html>
