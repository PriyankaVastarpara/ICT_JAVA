/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author priya
 */
public class InsertServlet extends HttpServlet {
Connection con;
    
     public InsertServlet(){
    
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/product","root","");
            System.out.println("connected");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
        }
        
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet InsertServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet InsertServlet at " + request.getContextPath() + "</h1>");
            out.println("<form method=\"get\" action=\"/product_assign/InsertServlet\">\n" +
"            Enter product name : <input type=\"text\" name=\"pname\"><br>\n" +
"            Enter product price : <input type=\"text\" name=\"pprice\"><br>\n" +
"            Enter product unit : <input type=\"text\" name=\"punit\"><br>\n" +
"            Enter product stock : <input type=\"text\" name=\"pstock\"><br>\n" +
"            <input type=\"submit\" name=\"Save\" value=\"Save\"><br>\n" +
"            \n" +
"        </form>");
            
            String name=request.getParameter("pname");
            int price=Integer.parseInt(request.getParameter("pprice"));
            int unit=Integer.parseInt(request.getParameter("punit"));
            int stock=Integer.parseInt(request.getParameter("pstock"));
            
            
                PreparedStatement pst=con.prepareCall("insert into productmaster(productname,productprice,productunit,productstock) values(?,?,?,?)");
                pst.setString(1,name);
                pst.setInt(2,price);
                pst.setInt(3,unit);
                pst.setInt(4,stock);
                pst.execute();

                response.sendRedirect("/product_assign/FirstServlet");
            
            out.println("</body>");
            out.println("</html>");
            
        } catch (SQLException ex) {
            Logger.getLogger(InsertServlet.class.getName()).log(Level.SEVERE, null, ex);
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
