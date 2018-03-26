package servlets;

import dbService.DBException;
import dbService.DBService;
import dbService.dao.UsersDAO;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import static dbService.dao.UsersDAO.getHash;
import static org.eclipse.jetty.http.HttpMethod.POST;

public class SignUpServlet extends HttpServlet {

    DBService dbService = new DBService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        try {

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Registration</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<p>Best Quality Builders</p>");
        out.println("<form action='signup' method='POST'>");
        out.println("Login: <input type='text' name='login'/>");
        out.println("email: <input type='text' name='email'/>");
        out.println("Password: <input type='password' name='password'/>");
        out.println("<input type='submit' value='Sign up'>");
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

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
        } catch (AddressException e) {
            resp.getWriter().println("Incorrect email");
            return;
        }
        try {

            dbService.addUser(login,email,password);
            resp.sendRedirect("/signin");

        } catch (DBException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
