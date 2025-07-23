/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green.bank;

import com.green.bank.database.JDBC_Connect;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author user
 */
@WebServlet(name = "WithdrawServlet", urlPatterns = {"/WithdrawServlet"})
public class WithdrawServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    String account_no, username, password;
	Connection conn;
	Statement stmt;
	boolean pass_wrong = false;
	int current_amount, withdraw_amount;
        
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
          account_no = request.getParameter("account_no");
		username = request.getParameter("username");
		password = request.getParameter("password");
		withdraw_amount = Integer.parseInt(request.getParameter("amount"));

		try {
			JDBC_Connect connect = new JDBC_Connect();
			conn = connect.getConnection();

			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("select * from account where id='" + account_no + "' and username='"
					+ username + "' and password='" + password + "'");

			if (!rs.isBeforeFirst()) {
				request.setAttribute("isPassOK", "No");
				RequestDispatcher rd = request.getRequestDispatcher("withdraw.jsp");
				rd.forward(request, response);
			} else {
				System.out.println("I am in");
				ResultSet rs1 = stmt.executeQuery("select * from amount where id ='" + account_no + "'");

				while (rs1.next()) {
					current_amount = rs1.getInt(2);

					System.out.println(current_amount);
				}

				if (current_amount >= withdraw_amount) {
					
					
					Socket soc=new Socket("localhost",3000);
			           ObjectOutputStream oos=new ObjectOutputStream(soc.getOutputStream());
			           ObjectInputStream oin=new ObjectInputStream(soc.getInputStream());
			           
			           oos.writeObject("ADDBLOCK");
			           oos.writeObject(username);
			           oos.writeObject("self");
			           oos.writeObject("withdraw");
			           oos.writeObject(Integer.toString(withdraw_amount));
			           oos.writeObject(new java.util.Date().toString());
			           
			           String reply=(String)oin.readObject();
			           
			           if (reply.equals("SUCCESS"))
			           {
					current_amount -= withdraw_amount;

					PreparedStatement ps = conn.prepareStatement("update amount set amount=? where id= ?");
					ps.setInt(1, current_amount);
					ps.setString(2, account_no);
					ps.executeUpdate();

					conn.close();

					RequestDispatcher rd = request.getRequestDispatcher("Withdraw_process.jsp");
					rd.forward(request, response);
			           }
			           else
			           {
			        	  
			        	   out.println("<h1>ERROR WHILE CONNECTING TO BLOCKCHAIN SERVER!");
			           }
			           
				} else {
					conn.close();
					request.setAttribute("EnoughMoney", "No");
					RequestDispatcher rd = request.getRequestDispatcher("withdraw.jsp");
					rd.forward(request, response);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
