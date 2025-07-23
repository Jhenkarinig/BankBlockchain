/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green.bank;

import com.green.bank.database.JDBC_Connect;
import email.SendMailExample;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author user
 */
@WebServlet(name = "TransferServlet", urlPatterns = {"/TransferServlet"})
public class TransferServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    String account_no, username, target_acc_no, password;
	Connection conn;
	Statement stmt;
	boolean pass_wrong = false;
	int own_amount, transfer_amount, recipient_amount;
        
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
          account_no = request.getParameter("account_no");
		username = request.getParameter("username");
		target_acc_no = request.getParameter("target_acc_no");
		password = request.getParameter("password");
                out.println(target_acc_no);
		transfer_amount = Integer.parseInt(request.getParameter("amount"));

		try {
			JDBC_Connect connect = new JDBC_Connect();
			conn = connect.getConnection();

			stmt = conn.createStatement();

			ResultSet rsOwn = stmt.executeQuery("select * from account where id='" + account_no + "' and username='"
					+ username + "' and password='" + password + "'");
                        
                        boolean iamin=true;
                        if (!rsOwn.next())
                        {
                            request.setAttribute("isPassOK", "No");
				RequestDispatcher rd = request.getRequestDispatcher("transfer.jsp");
				rd.forward(request, response);
                                iamin=false;
                        }
                        
                       
                                Statement stmt1 = conn.createStatement();
			ResultSet rstTarget = stmt1.executeQuery("select * from account where id='" + target_acc_no + "'");
                        String receiver="";
                         if (rstTarget.next())
                        {
                             receiver=rstTarget.getString("username");
                             
                             Statement stmtmail = conn.createStatement();

                            ResultSet rsmail = stmt.executeQuery("select email from account where id='" + account_no +  "'");
                            if (rsmail.next())
                            {
                                 Random r=new Random();
                                String otp=""+r.nextInt(10)+r.nextInt(10)+r.nextInt(10)+r.nextInt(10);
                                 otp.trim();
                                 String msg="OTP for amount transfer is "+otp;
                                 String email=rsmail.getString(1).trim();
                                 
                                  String reply=new SendMailExample().main(email, msg);
                                  if (reply.equals("FAILED"))
                                  {
                                      iamin=false;
                                      request.setAttribute("isPassOK", "No");
                                    RequestDispatcher rd = request.getRequestDispatcher("transfer.jsp");
                                    rd.forward(request, response);
                                  }
                                  else
                                  {
                                      //String otp2=JOptionPane.showInputDialog(null,"Enter OTP sent to "+email);
                                      JOptionPane optionPane = new JOptionPane("Enter OTP sent to "+email);
                                        optionPane.setWantsInput(true); //this is what I added
                                        JDialog dialog = optionPane.createDialog("OTP Validation");
                                        dialog.setAlwaysOnTop(true);
                                        dialog.setVisible(true);
                                        dialog.dispose();
                                        
                                        String otp2=(String)optionPane.getInputValue();
                                        
                                      if (otp.equals(otp2))
                                      {
                                          JOptionPane.showMessageDialog(null,"Valid OTP ");
                                      }
                                      else
                                      {
                                          JOptionPane.showMessageDialog(null,"Invalid OTP entered! Amount Transfer cancelled");
                                          iamin=false;
                                      }
                                          
                                  }
                                 
                            }
                        }
                        else
                        {
                            request.setAttribute("isPassOK", "No");
				RequestDispatcher rd = request.getRequestDispatcher("transfer.jsp");
				rd.forward(request, response);
                                iamin=false;
                        }
                         
                        
                        /*   
                        if (!rsOwn.next() && !rstTarget.next())
                        {
                            request.setAttribute("isPassOK", "No");
				RequestDispatcher rd = request.getRequestDispatcher("transfer.jsp");
				rd.forward(request, response);
                        }
                       
			if (!rsOwn.isBeforeFirst() && !rstTarget.isBeforeFirst()) {
				request.setAttribute("isPassOK", "No");
				RequestDispatcher rd = request.getRequestDispatcher("transfer.jsp");
				rd.forward(request, response);
			} 
                        */
                        if (iamin) 
                         {
				System.out.println("I am in");
				
				//rstTarget.next();
				//String receiver=rstTarget.getString("username");
				
				
				Statement stmt2 = conn.createStatement();
				ResultSet rs1 = stmt2.executeQuery("select * from amount where id ='" + account_no + "'");

				while (rs1.next()) 
                                {
					own_amount = rs1.getInt(2);
				}

				if (own_amount >= transfer_amount) {
					
                                Socket soc=new Socket("localhost",3000);
			           ObjectOutputStream oos=new ObjectOutputStream(soc.getOutputStream());
			           ObjectInputStream oin=new ObjectInputStream(soc.getInputStream());
			           
			           oos.writeObject("ADDBLOCK");
			           oos.writeObject(username);
			           oos.writeObject(receiver);
			           oos.writeObject("Transfer");
			           oos.writeObject(Integer.toString(transfer_amount));
			           oos.writeObject(new java.util.Date().toString());
			           
			           String reply=(String)oin.readObject();
			           
			           if (reply.equals("SUCCESS"))
			           {
					
					own_amount -= transfer_amount;

					ResultSet rs2 = stmt.executeQuery("select * from amount where id ='" + target_acc_no + "'");

					while (rs2.next()) {
						recipient_amount = rs2.getInt(2);
					}

					recipient_amount += transfer_amount;

					PreparedStatement ps = conn.prepareStatement("update amount set amount=? where id= ?");
					ps.setInt(1, own_amount);
					ps.setString(2, account_no);
					ps.executeUpdate();

					PreparedStatement ps1 = conn.prepareStatement("update amount set amount=? where id= ?");
					ps1.setInt(1, recipient_amount);
					ps1.setString(2, target_acc_no);
					ps1.executeUpdate();

					conn.close();

					RequestDispatcher rd = request.getRequestDispatcher("transfer_process.jsp");
					rd.forward(request, response);
					
			           }
			           else
			           {
			        	   
			        	   out.println("<h1>ERROR WHILE CONNECTING TO BLOCKCHAIN SERVER!");
			           }
				}else{
					conn.close();
					request.setAttribute("EnoughMoney", "No");
					RequestDispatcher rd = request.getRequestDispatcher("transfer.jsp");
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
