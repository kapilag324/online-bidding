/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
@WebServlet(urlPatterns = {"/DB"})
public class DB extends HttpServlet {

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
                Class.forName("org.gjt.mm.mysql.Driver");
                Connection con=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306","root","");
                PreparedStatement stmt;
                ResultSet rs;
                stmt=con.prepareStatement("Create database OnlineBid");
                stmt.executeUpdate();
                stmt=con.prepareStatement("use OnlineBid");
                stmt.executeUpdate();
                stmt=con.prepareStatement("Create Table Members(Email varchar(40) Primary Key,Name varchar(30),Mobile varchar(10),Address varchar(40),State varchar(30),city varchar(30),PinCode varchar(6))");
                stmt.executeUpdate();
                stmt=con.prepareStatement("Create Table Users(Email varchar(40) Primary Key,password varchar(30),utype varchar(30))");
                stmt.executeUpdate();
                stmt=con.prepareStatement("Create Table BidItems(ItemId int Primary Key,Email varchar(40),Details varchar(100),Price int,UDate date,SDate date,EDate date,Photo MediumBlob)");
                stmt.executeUpdate();
                stmt=con.prepareStatement("Create Table Biddings(bid int,ItemId int,Email varchar(40),Price int,BidDate Date)");
                stmt.executeUpdate();
                stmt=con.prepareStatement("Create Table Orders(OId int,Email varchar(40),Odate date,TotalAmt int)");
                stmt.executeUpdate();
                stmt=con.prepareStatement("Create Table OrderDetails(OId int,Itemid int,Price int)");
                stmt.executeUpdate();
                stmt=con.prepareStatement("Insert into users values('admin','secret','admin')");
                stmt.executeUpdate();
                stmt=con.prepareStatement("Create View v1 as select Itemid,Max(Price) as MaxPrice from biddings group by itemid");
                stmt.executeUpdate();
                stmt=con.prepareStatement("Create View V2 as select max(price) MaxPrice,itemid from biddings group by itemid");
                stmt.executeUpdate();
                PrintWriter out=response.getWriter();
                response.setContentType("text/html");
                out.write("Done");
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
