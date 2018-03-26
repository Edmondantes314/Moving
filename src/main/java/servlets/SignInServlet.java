package servlets;

import dbService.DBException;
import dbService.DBService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import static dbService.dao.UsersDAO.getHash;


public class SignInServlet extends HttpServlet {

    DBService dbService = new DBService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        try {

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Log in</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p>Best Quality Builders</p>");
            out.println("<form action='signin' method='POST'>");
            out.println("Login: <input type='text' name='login'/>");
            out.println("Password: <input type='password' name='password'/>");
            out.println("<input type='submit' value='Sign in'>");
            out.println("</form>");
            out.println("<a href=" +
                    "\"/data_recovery\"" +
                    ">Forgot password or login?</a>");
            out.println("</body>");
            out.println("</html>");
        }
        finally {
            out.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String password = req.getParameter("password");
        String login= req.getParameter("login");


        try {
            if(dbService.doLogin(login,password).getPassword().equals(getHash(password)) ||
                  dbService.doLogin(login,password).getName().equals(login)){
               resp.getWriter().println("Welcome, " + login);
                RequestDispatcher view = req.getRequestDispatcher("user.html");
                view.forward(req, resp);

            }

        } catch (DBException e) {
            resp.getWriter().println("User not found");
            e.printStackTrace();
       } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
