/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.InputStream;
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
@WebServlet(urlPatterns = {"/upload"})
public class upload extends HttpServlet {

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
                PrintWriter out=response.getWriter();
                int size=request.getContentLength();
                InputStream inr=request.getInputStream();
                byte[] data=new byte[size];
                int n=inr.read(data);
                while(n<size)
                {
                    int m=inr.read(data,n,size-n);
                    n=n+m;
                }
                String str=new String(data);
                int p=str.indexOf("\r\n");
                String boundary=str.substring(0,p);
                p=str.indexOf("name=\"t1\"")+13;
                int p1=str.indexOf(boundary,p-13)-2;
                String t1=str.substring(p,p1);
                
                
                p=str.indexOf("name=\"t2\"")+13;
                p1=str.indexOf(boundary,p-13)-2;
                String t2=str.substring(p,p1);
                
                p=str.indexOf("name=\"t3\"")+13;
                p1=str.indexOf(boundary,p-13)-2;
                String t3=str.substring(p,p1);
                
                p=str.indexOf("name=\"t4\"")+13;
                p1=str.indexOf(boundary,p-13)-2;
                String t4=str.substring(p,p1);
                
                p=str.indexOf("Content-Type:")+14;
                p1=str.indexOf("\r\n\r\n",p);
                String contentype=str.substring(p,p1);
                int startdata=p1+4;
                int enddata=str.indexOf(boundary,startdata)-3;
                int datasize=enddata-startdata+1;
                byte[] filedata=new byte[datasize];
                for(int i=0;i<filedata.length;i++)
                {
                    filedata[i]=data[startdata+i];
                }
                Connection con=null;
                PreparedStatement stmt=null;
                ResultSet rs=null;
                String email=null;
                HttpSession hs=request.getSession(false);
                con=(Connection)hs.getAttribute("CON");
                email=hs.getAttribute("EMAIL").toString();
                int id=0;
                stmt=con.prepareStatement("Select count(*)+1 from biditems");
                rs=stmt.executeQuery();
                if(rs.next())
                {
                    id=rs.getInt(1);
                }
                String udate=myp1.MyUtility.getDate();
                stmt=con.prepareStatement("Insert into biditems values(?,?,?,?,?,?,?,?)");
                stmt.setInt(1,id);
                stmt.setString(2,email);
                stmt.setString(3,t1);
                stmt.setString(4,t2);
                stmt.setString(5,udate);
                stmt.setString(6,t3);
                stmt.setString(7,t4);
                stmt.setBytes(8,filedata);
                stmt.executeUpdate();
                response.sendRedirect("uploadproduct");
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
