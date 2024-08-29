/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author priya
 */
@WebServlet(urlPatterns = {"/UpdateServlet"})
public class UpdateServlet extends HttpServlet {
    Connection con;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    public UpdateServlet(){
     try {
            
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/product","root","");
            System.out.println("connected");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
        }
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
           
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateServlet at " + request.getContextPath() + "</h1>");
            
             int id=Integer.parseInt(request.getParameter("id"));
          String action = request.getParameter("Update");

            if (action == null) { 
              
                try {
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery("SELECT * FROM productmaster WHERE productid=" + id);
                    if (rs.next()) {
                        out.println("<form method='POST' action='/product_assign/UpdateServlet'>");
                        out.println("Enter product name : <input type='text' name='pname' value='" + rs.getString(2) + "'><br>");
                        out.println("Enter product price : <input type='text' name='pprice' value='" + rs.getInt(3) + "'><br>");
                        out.println("Enter product unit : <input type='text' name='punit' value='" + rs.getInt(4) + "'><br>");
                        out.println("Enter product stock : <input type='text' name='pstock' value='" + rs.getInt(5) + "'><br>");
                        out.println("<input type='hidden' name='id' value='" + id + "'>");
                        out.println("<input type='submit' name='Update' value='Update'><br>");
                        out.println("</form>");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else { 
               
                String name = request.getParameter("pname");
                int price = Integer.parseInt(request.getParameter("pprice"));
                int unit = Integer.parseInt(request.getParameter("punit"));
                int stock = Integer.parseInt(request.getParameter("pstock"));

                try {
                    PreparedStatement pst = con.prepareStatement("UPDATE productmaster SET productname=?, productprice=?, productunit=?, productstock=? WHERE productid=?");
                    pst.setString(1, name);
                    pst.setInt(2, price);
                    pst.setInt(3, unit);
                    pst.setInt(4, stock);
                    pst.setInt(5, id);
                    pst.executeUpdate();
                    response.sendRedirect("/product_assign/FirstServlet");
                } catch (SQLException ex) {
                    Logger.getLogger(UpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            
            out.println("</body>");
            out.println("</html>");
        }   catch (Exception ex) {
                Logger.getLogger(UpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
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
