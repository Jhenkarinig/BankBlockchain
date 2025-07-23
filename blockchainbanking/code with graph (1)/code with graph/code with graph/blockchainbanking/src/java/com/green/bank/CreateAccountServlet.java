/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green.bank;

import com.green.bank.model.AccountModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.RequestDispatcher;

/**
 *
 * @author user
 */
@WebServlet(name = "CreateAccountServlet", urlPatterns = {"/CreateAccountServlet"})
public class CreateAccountServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    String account_no, first_name, last_name, address, city, branch, zip, username, password, re_password,
			phone_number, email, account_type;
	int amount;
        
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
           first_name = request.getParameter("first_name");
		last_name = request.getParameter("last_name");
		address = request.getParameter("address");
		city = request.getParameter("city");
		branch = request.getParameter("branch");
		zip = request.getParameter("zip");
		username = request.getParameter("username");
		password = request.getParameter("password");
		re_password = request.getParameter("re_password");
		phone_number = request.getParameter("phone");
		email = request.getParameter("email");
		account_type = request.getParameter("account_type");
		amount = Integer.parseInt(request.getParameter("amount"));

		// Generating account number
		Random rand = new Random();
		int random_num = 100000 + rand.nextInt(999999);
		account_no = first_name.substring(0, 2) + last_name.substring(0, 2) + random_num;
		System.out.println(account_no);
		
		//Getting Current date
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String reg_date = df.format(new Date());

		// Setting all variables to model class
		AccountModel am = new AccountModel();
		am.setAccount_no(account_no);
		am.setFirst_name(first_name);
		am.setLast_name(last_name);
		am.setAddress(address);
		am.setCity(city);
		am.setBranch(branch);
		am.setZip(zip);
		am.setUsername(username);
		am.setPassword(password);
		am.setPhone_number(phone_number);
		am.setEmail(email);
		am.setAccount_type(account_type);
		am.setAmount(amount);
		am.setReg_date(reg_date);

		if (password.equals(re_password)) {
			request.setAttribute("Account_details", am);
			RequestDispatcher rd = request.getRequestDispatcher("create_account_progress.jsp");
			rd.forward(request, response);

		} else {
			request.setAttribute("not_match", "yes");
			RequestDispatcher rd = request.getRequestDispatcher("create_account.jsp");
			rd.forward(request, response);
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
