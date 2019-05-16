
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
@WebServlet(urlPatterns = {"/login"})
public class login extends HttpServlet {

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
                    Class.forName("org.gjt.mm.mysql.Driver");
                    Connection con=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/onlinebid","root","");
                    PreparedStatement stmt=con.prepareStatement("Select * from users where Email=? and password=?");
                    stmt.setString(1,request.getParameter("t1"));
                    stmt.setString(2,request.getParameter("t2"));
                    ResultSet rs=stmt.executeQuery();
                    if(rs.next())
                    {
                        String utype=rs.getString(3);
                        HttpSession hs=request.getSession(true);
                        hs.setAttribute("CON",con);
                        hs.setAttribute("UTYPE",utype);
                        hs.setAttribute("EMAIL",request.getParameter("t1"));
                        if(utype.equals("admin"))
                        {
                            response.sendRedirect("admin");
                        }
                        else
                        {
                            response.sendRedirect("index");
                        }
                    }
                    else
                    {
                        msg="Invalid Login/Password!!!";
                    }
                }
                PrintWriter out=response.getWriter();
                response.setContentType("text/html");
                out.write("<html><body>");
                out.write("<img src='banner.png' width='100%'><hr>");
                out.write("<table width='100%'><tr><td><a href='index'>Home</a></td><td><a href='register'>Register</a></td><td><a href='biddingrules'>Bidding Rules</a></td><td><a href='previous'>Previous Biddings</a></td><td><a href='upcoming'>Upcoming Bids</a></td><td><form method='post' action='search'><input type='text' name='t1'><input type='submit' value='Search'></form></td><td><a href='login'>Login</a></td><td><a href='resolution'>Resolution Center</a></td></tr></table>");
                out.write("<br><br><br><hr>");
                out.write("<form method='post'><table align='center'>");
                out.write("<tr><td>Login:</td><td><input type='text' name='t1'></td></tr>");
                out.write("<tr><td>Password:</td><td><input type='password' name='t2'></td></tr>");
                out.write("<tr><td></td><td><input type='submit' name='b1' value='Login'></td></tr>");
                out.write("<tr><td></td><td>"+msg+"</td></tr>");
                out.write("<tr><td></td><td><a href='register'>Register</a><tr>");
                out.write("</table></form>");
                out.write("<hr><br><br>");
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
