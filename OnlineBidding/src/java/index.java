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
@WebServlet(urlPatterns = {"/index"})
public class index extends HttpServlet {

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
                String sss="<td><a href='login'>Login</a></td>";
                Connection con=null;
                PreparedStatement stmt=null;
                ResultSet rs=null;
                String utype=null;
                String email=null;
                HttpSession hs=request.getSession(false);
                if(hs!=null)
                {
                    sss="<td><a href='myaccount'>My Account</a></td><td><a href='logout'>Logout</a></td>";
                    con=(Connection)hs.getAttribute("CON");
                    email=hs.getAttribute("EMAIL").toString();
                    utype=hs.getAttribute("UTYPE").toString();
                }
                else
                {
                    Class.forName("org.gjt.mm.mysql.Driver");
                    con=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/onlinebid","root","");
                }
                PrintWriter out=response.getWriter();
                response.setContentType("text/html");
                out.write("<html><body>");
                out.write("<img src='banner.png' width='100%'><hr>");
                out.write("<table width='100%'><tr><td><a href='index'>Home</a></td><td><a href='register'>Register</a></td><td><a href='biddingrules'>Bidding Rules</a></td><td><a href='previous'>Previous Biddings</a></td><td><a href='upcoming'>Upcoming Bids</a></td><td><form method='post' action='search'><input type='text' name='t1'><input type='submit' value='Search'></form></td>"+sss+"<td><a href='resolution'>Resolution Center</a></td></tr></table>");
                stmt=con.prepareStatement("Select biditems.*,v1.* from biditems Left outer join v1 on biditems.itemid=v1.itemid where sdate<=? and edate>=?");
                stmt.setString(1,myp1.MyUtility.getDate());
                stmt.setString(2,myp1.MyUtility.getDate());
                rs=stmt.executeQuery();
                out.write("<table align=center>");
                while(rs.next())
                {
                    String s1=rs.getString(1);
                    String s2=rs.getString(3);
                    String s3=rs.getString(4);
                    String s4=rs.getString(6);
                    String s5=rs.getString(7);
                    String s6=rs.getString("MaxPrice");
                    if(s6==null) s6=s3;
                    out.write("<tr>");
                    out.write("<td><img src='getPhoto?id="+s1+"' width='300'></td>");
                    out.write("<td>"+s2+"<br>Minimum Price:"+s3+"<br>End Date:"+s5+"<br><font color='red'>Max Bid Price:"+s6+"</font></td>");
                    out.write("<td><form method='post' action='savebid'><br>My Bid Price:<input type='text' name='t1'><input type='hidden' name='t2' value='"+s1+"'><br><input type='submit' value='Save Bid'></form></td>");
                    out.write("</tr>");
                }
                out.write("</table>");
                out.write("<hr><img src='footer.png' width='100%'>");
                out.write("</body></html>");
        }catch(Exception ee){}
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
