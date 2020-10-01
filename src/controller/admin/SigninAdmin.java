package controller.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbrepo.RepoAdmin;
import model.Admin;

@WebServlet(name = "/SigninAdmin", urlPatterns = { "/admin/SigninAdmin" })
public class SigninAdmin extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6662676825324184570L;

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		Admin user = new RepoAdmin().signInAdmin(username, password);
		if (user == null) {
			// TODO : MAKE forget password
			request.setAttribute("message", "Cant't Login <br/> Wrong username or password .. ");
			getServletContext().getRequestDispatcher("/admin/Failed.jsp").forward(request, response);
		} else {

			// set session for login user
			HttpSession session = request.getSession(true);
			session.setAttribute("LoginUser", user);
			session.setMaxInactiveInterval(60 * 15);

			if (user.getTipeakun().equalsIgnoreCase("ROLE_ADMIN")) {
				System.out.println("ADMIN");
				response.sendRedirect("index.jsp"); // admin
			}
		}

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
