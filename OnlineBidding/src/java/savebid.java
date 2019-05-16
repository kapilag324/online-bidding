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
@WebServlet(urlPatterns = {"/savebid"})
public class savebid extends HttpServlet {

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
                Connection con=null;
                PreparedStatement stmt=null;
                ResultSet rs=null;
                String utype=null;
                String email=null;
                HttpSession hs=request.getSession(false);
                if(hs!=null)
                {
                    con=(Connection)hs.getAttribute("CON");
                    email=hs.getAttribute("EMAIL").toString();
                    utype=hs.getAttribute("UTYPE").toString();
                    stmt=con.prepareStatement("Select count(*)+1 from biddings");
                    rs=stmt.executeQuery();
                    int bid=0;
                    if(rs.next()) bid=rs.getInt(1);
                    stmt=con.prepareStatement("Insert into biddings values(?,?,?,?,?)");
                    stmt.setInt(1,bid);
                    stmt.setString(2,request.getParameter("t2"));
                    stmt.setString(3,email);
                    stmt.setString(4,request.getParameter("t1"));
                    stmt.setString(5,myp1.MyUtility.getDate());
                    stmt.executeUpdate();
                    response.sendRedirect("index");
                }
                PrintWriter out=response.getWriter();
                response.setContentType("text/html");
                out.write("<html><body>");
                if(hs==null)
                {
                    out.write("<center><h3><a href='login'>Login</a> to Place bid</h3></center>");
                }
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
