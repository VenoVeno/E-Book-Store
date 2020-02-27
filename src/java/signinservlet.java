/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
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
public class signinservlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    String name, mail, pass;
    Boolean logged;
    
    Connection con,con1;
    Statement st,st1;
    ResultSet rs = null,rs1 = null;
        
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        String name, email, pass, safename = null;
        Integer flag;
        
        flag = 0;
        name = request.getParameter("Uname");
        email = request.getParameter("Uemail");
        pass = request.getParameter("Upwd");  
        
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/mydata", "root", "");
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM cattable;");          
            logged = false;
                try {           
                    rs.beforeFirst();

                    for(int i=0; rs.next(); i++)
                    {
                        System.out.println("Processing: " + rs.getString(1));
                        if(!(name.equals(rs.getString(1)) || email.equals(rs.getString(2))))
                        {
                            logged = true;
                            safename = name;
                            break;
                        }
                    }
                } catch(SQLException ex) {
                        System.out.println(ex);
                }      
        }catch(SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Signin Procedure</title>");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"newcss.css\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<section class=\"container-fluid\">\n" +
                    "<section class=\"row justify-content-center\">\n" +
                    "<section class=\"col-12 col-sm-6 col-md-3\">  ");
            
            if!logged){
                try
                {
                    Class.forName("com.mysql.jdbc.Driver");
                    con1 = DriverManager.getConnection("jdbc:mysql://localhost/mydata", "root", "");
                    st1 = con1.createStatement();
                    st1.executeUpdate("INSERT INTO cattable VALUES('"+name+"','"+email+"','"+pass+"');");
                }catch(SQLException | ClassNotFoundException e) {
                    System.out.println(e);
                    flag = 1;
                }
                out.println("<form class=\"form-container\"  name=\"display\" action=\"login.html\">");
                out.println("<div class=\"form-group text-success text-center\">");
                out.print("You are Successfully Signed in!");
                out.print("<br>Welcome, " + name);
                out.println("</div>");
                out.println("<button class=\"btn btn-dark btn-block\" data-toggle=\"tooltip\" title=\"Click to Check\" data-placement=\"bottom\">Login to Proceed</button>");
                out.println("</form>");
            }
            else{
                out.println("<form class=\"form-container\"  name=\"display\" action=\"signup.html\">  ");
                out.println("<div class=\"form-group text-center\">");
                out.println("Trying to Signin as " + safename);
                out.println("</div>");
                
                out.println("<div class=\"form-group text-danger text-center\">");
                out.println("<br>Sorry, User already exist");
                out.println("<br>Please Double Check The Crendetials");
                out.println("</div>");
                out.println("<button class=\"btn btn-dark btn-block\" data-toggle=\"tooltip\" title=\"Click to Check\" data-placement=\"bottom\">Signin Again</button>");
                out.println("</form>");
            }            
            out.println("</section>" + "</section>" +"</section>");
            
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
        } catch (SQLException ex) {
            Logger.getLogger(signinservlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            Logger.getLogger(signinservlet.class.getName()).log(Level.SEVERE, null, ex);
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
