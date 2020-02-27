/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author HP
 */
public class categorymatch extends HttpServlet {
    
    Connection con,con1;
    Statement st,st1;
    ResultSet rs = null,rs1=null;
    int bookcount = 0;
    static int counter = 0;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
    super.init();      
    try{
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost/catdb", "root", "");
        st = con.createStatement();
        rs = st.executeQuery("SELECT * FROM books;");          
    }catch(SQLException | ClassNotFoundException er){
        System.out.println(er);
    }
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        
        bookcount = 0;
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) 
        {                
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Book Display</title>"); 
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"newcss.css\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<section class=\"container-fluid\">\n" +
                        "<section class=\"row justify-content-center\">\n" +
                        "<section class=\"col-12 col-sm-6 col-md-3\">  ");
            out.println("<div class=\"form-group text-danger text-center\"> Available Books Under Your Search</div>");
            
            try{           
            rs.beforeFirst();
            String value = request.getParameter("Categories");                
            for(int i = 0; rs.next(); i++) 
            {
                System.out.println("Processing: " + rs.getString(6));
                if(value.equals(rs.getString("category")))
                {
                    bookcount ++;
                        out.println("<form class=\"form-container\" name=\"display"+bookcount+"\" action=\"cartmanager\">");
                        out.println("<div class=\"form-group text-center\">");

                            out.println("<details>");
                            out.println("<summary>" + rs.getString(4) + "</summary>");
                            out.println("<br /><p id=\"addtocart\"> "+  rs.getString(1) + "</p>");
                            //out.println("<p> "+  rs.getString(2) + "</p>");
                            out.println("<p> "+  rs.getString(3) + "</p>");
                            out.println("<p> "+  rs.getString(5) + "</p>");
                            out.println("<p> "+  rs.getString(6) + "</p>");

                            out.println("</details>");

                        out.println("</div>");
                    out.println("<br /></div>");                    
                    out.println("</form>");     
                }
            }
            } catch(SQLException ex) {
                    System.out.println(ex);
            }
            out.println("<form class=\"form-container\" name=\"display"+bookcount+"\" action=\"cartmanager\">");
            out.println("<button class=\"btn btn-primary btn-block\" id=\"book"+bookcount+"\" data-toggle=\"tooltip\" title=\"Click to Check\" data-placement=\"bottom\">Add Some Books to Cart</button>");
            out.println("</form>");     

            out.println("<form class=\"form-container\"  name=\"display\" action=\"index_1.html\">");
            out.println("<br />");
            if(bookcount == 0)
                out.println("<div class=\"form-group text-danger text-center\"> No Books Avaibale at the Moment Check Back Later!</div>");
            else
                out.println("<div class=\"form-group text-danger text-center\"> " + bookcount + " Search Results Found</div>");
            out.println("<button class=\"btn btn-warning btn-block\" data-toggle=\"tooltip\" title=\"Click to Check\" data-placement=\"bottom\">Click to View Catalog</button>");
            out.println("</form>");
           
            out.println("</section>\n" +
                        "</section>\n" +
                        "</section>");
            
            out.println("</body>");
            out.println("</html>");                
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(categorymatch.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(categorymatch.class.getName()).log(Level.SEVERE, null, ex);
        }
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
