package servlets;

import dbService.DBException;
import dbService.DBService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;

public class DataRecoveryServlet extends HttpServlet{

    DBService dbService = new DBService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        try {

            out.println("<html>");
            out.println("<head>");
            out.println("<title>DataRecovery</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<form action='data_recovery' method='POST'>");
            out.println("What is your email address?: <input type='text' name='email'/>");
            out.println("<input type='submit' value='Send'&lt;br /> <br />");
            out.println("We will send you users password and login.");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
        finally {
            out.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getParameter("email");
        try {
            if(dbService.findUserByEmail(email).getEmail().equals(email)){
                resp.getWriter().println("Here massage supposed to be send to email ");
            }
        } catch (DBException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
