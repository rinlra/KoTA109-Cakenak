
package controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbrepo.RepoKategori;
import model.Kategori;

@WebServlet("/admin/AdminKategori")
public class AdminKategori extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8313482291362183302L;

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

		// int id = Integer.parseInt(request.getParameter("KategoriId"));
		String name = request.getParameter("KategoriName");
		/// object from category

		Kategori categoryobj = new Kategori();

		categoryobj.setNama(name);
		///// set data in database
		System.out.println("---> " + categoryobj.getNama());
		if (new RepoKategori().addKategori(categoryobj)) {

			// set alert message
			request.getSession().setAttribute("AlertMessage", "Kategori Added Successfully");
			// set alert type
			request.getSession().setAttribute("AlertType", "success");
			response.sendRedirect("AdminKategoriServlet");
		} else {
			// set alert message
			request.getSession().setAttribute("AlertMessage", "canot add Kategori ..An Error occure");
			// set alert type
			request.getSession().setAttribute("AlertType", "danger");
			response.sendRedirect("AdminKategoriServlet");
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
