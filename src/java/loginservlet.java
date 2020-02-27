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
public class loginservlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    String emailname, pass,safename;
    Boolean logged;
    
    Connection con;
    Statement st;
    ResultSet rs = null;
    
    public void init(ServletConfig config) throws ServletException {
    super.init();      
    try{
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost/mydata", "root", "");
        st = con.createStatement();
        rs = st.executeQuery("SELECT * FROM cattable;");          
    }catch(SQLException | ClassNotFoundException er){
        System.out.println(er);
    }
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                
        logged = false;
        try {           
            rs.beforeFirst();
                emailname = request.getParameter("Uemail");
                pass = request.getParameter("Upwd");
            for(int i=0; rs.next(); i++) {
                System.out.println("Processing: " + rs.getString(1));
                if(emailname.equals(rs.getString(1)))
                {
                    logged = true;
                    safename = rs.getString(1);
                    
                }
            }
        } catch(SQLException ex) {
                System.out.println(ex);
        }
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out=response.getWriter();  

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Profile Viewer</title>"); 
        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"newcss.css\">");
        out.println("</head>");
        out.println("<body>");  
        out.println("<section class=\"container-fluid\">\n" +
                            "<section class=\"row justify-content-center\">\n" +
                            "<section class=\"col-12 col-sm-6 col-md-3\">  ");

        if(logged)
        {  
            out.println("<form class=\"form-container\"  name=\"display\" action=\"index_1.html\">  ");
            out.println("<div class=\"form-group text-success text-center\">");
            out.print("You are successfully logged in!");  
            out.print("<br>Welcome, " + safename);  
            out.println("</div>");
            out.println("<button class=\"btn btn-dark btn-block\" data-toggle=\"tooltip\" title=\"Click to Check\" data-placement=\"bottom\">Search For Books</button>");
            out.println("</form>");
            Cookie ck = new Cookie("name",safename);  //Set Cookie
            response.addCookie(ck);  
        }else{  
            out.println("<form class=\"form-container\"  name=\"display\" action=\"login.html\">  ");            
            out.println("<div class=\"form-group text-center\">");            
            out.println("Trying to Login as " + safename);
            out.println("</div>");            
            
            out.println("<div class=\"form-group text-danger text-center\">");
            out.println("<br>Sorry, username or password error!");
            out.println("<br>Please Double Check The Login Crendetials");
            out.println("</div>");
            out.println("<button class=\"btn btn-dark btn-block\" data-toggle=\"tooltip\" title=\"Click to Check\" data-placement=\"bottom\">Login Again</button>");            
            out.println("</form>");       
        }  
        out.println("</section>" + "</section>" +"</section>");
 
        out.println("</body>");
        out.println("</html>");
          
        out.close(); 
        
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
