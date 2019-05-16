/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Administrator
 */
@WebServlet(urlPatterns = {"/register"})
public class register extends HttpServlet {

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
    {
        try{
                String msg="";
                if(request.getParameter("b1")!=null)
                {
                    String cap=request.getParameter("t10");
                    HttpSession hs=request.getSession(false);
                    String id=hs.getId();
                    String text=id.substring(0,6).toUpperCase();
                    if(cap.equals(text))
                    {
                    Class.forName("org.gjt.mm.mysql.Driver");
                    Connection con=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/OnlineBid","root","");
                    PreparedStatement stmt=con.prepareStatement("Insert into Members values(?,?,?,?,?,?,?)");
                    stmt.setString(1,request.getParameter("t1"));
                    stmt.setString(2,request.getParameter("t4"));
                    stmt.setString(3,request.getParameter("t5"));
                    stmt.setString(4,request.getParameter("t6"));
                    stmt.setString(5,request.getParameter("t7"));
                    stmt.setString(6,request.getParameter("t8"));
                    stmt.setString(7,request.getParameter("t9"));
                    stmt.executeUpdate();
                    stmt=con.prepareStatement("Insert into users values(?,?,'member')");
                    stmt.setString(1,request.getParameter("t1"));
                    stmt.setString(2,request.getParameter("t2"));
                    stmt.executeUpdate();      
                    con.close();
                    response.sendRedirect("login");
                    }
                    else
                    {
                        msg="Invalid Captcha!!!";
                    }
                }
                PrintWriter out=response.getWriter();
                response.setContentType("text/html");
                out.write("<html><body>");
                out.write("<img src='banner.png' width='100%'><hr>");
                out.write("<table width='100%'><tr><td><a href='index'>Home</a></td><td><a href='register'>Register</a></td><td><a href='biddingrules'>Bidding Rules</a></td><td><a href='previous'>Previous Biddings</a></td><td><a href='upcoming'>Upcoming Bids</a></td><td><form method='post' action='search'><input type='text' name='t1'><input type='submit' value='Search'></form></td><td><a href='login'>Login</a></td><td><a href='resolution'>Resolution Center</a></td></tr></table>");
                out.write("<form method='post'>");
                out.write("<table align='center'>");
                out.write("<tr><td>Email:</td><td><input type='email' name='t1'></td></tr>");
                out.write("<tr><td>Password:</td><td><input type='password' name='t2'></td></tr>");
                out.write("<tr><td>Re Type:</td><td><input type='password' name='t3'></td></tr>");
                out.write("<tr><td>Name:</td><td><input type='text' name='t4'></td></tr>");
                out.write("<tr><td>Mobile:</td><td><input type='text' name='t5'></td></tr>");
                out.write("<tr><td>Address:</td><td><textarea name='t6'></textarea></td></tr>");
                out.write("<tr><td>State:</td><td><select name='t7'><option>Uttar Pradesh</option><option>Himachal Pradesh</option><option>Punjab</option></select></td></tr>");
                out.write("<tr><td>City:</td><td><select name='t8'><option>Lucknow</option><option>Amritsar</option><option>Meerut</option><option>Dharamshala</option></select></td></tr>");
                out.write("<tr><td>Pin code:</td><td><input type='text' name='t9'></td></tr>");
                out.write("<tr><td>Enter Captcha:</td><td><input type='text' name='t10' size='5'><img src='getCaptcha' height='50' width='150'></td></tr>");
                out.write("<tr><td></td><td>"+msg+"</td></tr>");
                out.write("<tr><td></td><td><input type='submit' name='b1' value='Register'></td></tr>");
                out.write("</table></form>");
                out.write("<hr><img src='footer.png' width='100%'>");
                out.write("</body></html>");
        }catch(Exception ee){System.out.println("Error "+ee);}
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
