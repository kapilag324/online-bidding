/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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
@WebServlet(urlPatterns = {"/myorders"})
public class myorders extends HttpServlet {

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
                PrintWriter out=response.getWriter();
                response.setContentType("text/html");
                out.write("<html><body>");
                out.write("<img src='banner.png' width='100%'><hr>");
                out.write("<table width='100%'><tr><td><a href='index'>Home</a></td><td><a href='myprofile'>My Profile</a></td><td><a href='uploadproduct'>Upload Product</a></td><td><a href='previous'>Previous Biddings</a></td><td><a href='upcoming'>Upcoming Bids</a></td><td><form method='post' action='search'><input type='text' name='t1'><input type='submit' value='Search'></form></td>"+sss+"<td><a href='myorders'>My Orders</a></td></tr></table>");
                stmt=con.prepareStatement("Select biditems.*,biddings.* from biditems,biddings where biditems.itemid=biddings.itemid and biddings.email=? and edate<?");
                stmt.setString(1,email);
                stmt.setString(2,myp1.MyUtility.getDate());
                rs=stmt.executeQuery();
                out.write("<table align='center'>");
                out.write("<tr><th>Product Details</th><th>Min. Price</th><th>My Price</th><th>Bid Date</th></tr>");
                while(rs.next())
                {
                    String s1=rs.getString(3);
                    String s2=rs.getString(4);
                    String s3=rs.getString(12);
                    String s4=rs.getString(13);
                    out.write("<tr>");
                    out.write("<td>"+s1+"</td>");
                    out.write("<td>"+s2+"</td>");
                    out.write("<td>"+s3+"</td>");
                    out.write("<td>"+s4+"</td>");
                    out.write("</tr>");
                }
                out.write("</table>");
                out.write("<center><h3>Successful Bids</h3></center>");
                //Select biddings.* from biddings,v1 where biddings.itemid=v1.itemid and biddings.price=v1.MaxPrice
                stmt=con.prepareStatement("Select biddings.* from biddings,v2 where biddings.itemid=v2.itemid and biddings.price=v2.MaxPrice and email=? and  v2.itemid in(select itemid from biditems where edate<?)");
                stmt.setString(1,email);
                stmt.setString(2,myp1.MyUtility.getDate());
                rs=stmt.executeQuery();
                out.write("<table align='center'>");
                while(rs.next())
                {
                    String s1=rs.getString(3);
                    String s2=rs.getString(4);
                    String s3=rs.getString(12);
                    String s4=rs.getString(13);
                    out.write("<tr>");
                    out.write("<td>"+s1+"</td>");
                    out.write("<td>"+s2+"</td>");
                    out.write("<td>"+s3+"</td>");
                    out.write("<td>"+s4+"</td>");
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
