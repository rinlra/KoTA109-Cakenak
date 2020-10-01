package controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import Utils.UploadIMG;
import dbrepo.RepoPembeli;
import model.Pembeli;

/**
 * edit profile
 *
 * @KoTA109
 */
@MultipartConfig
@WebServlet(name = "AdminProfile", urlPatterns = { "/admin/AdminProfile" })
public class AdminProfile extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2089806604306348147L;

	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("id");
		Pembeli user = new RepoPembeli().getPembeliById(id);

		if (user == null) {
			request.getSession().setAttribute("message", "user not found");
			response.sendRedirect("/admin/Failed.jsp");
		} else {
			request.setAttribute("userInfo", user);
			request.getRequestDispatcher("/admin/profile.jsp").forward(request, response);
		}

	}

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
		System.out.println("Test0-----------------------------");
		Pembeli user = new Pembeli();
		String path = request.getServletContext().getRealPath("");
		// get request paramater & update object user
		user.setNama(request.getParameter("username"));
		user.setEmail(request.getParameter("email"));
		user.setPassword(request.getParameter("password"));
		user.setAlamat(request.getParameter("alamat"));
		user.setKota(request.getParameter("kota"));
		user.setProvinsi(request.getParameter("provinsi"));
		user.setKodepos(request.getParameter("kodepos"));
		user.setNotelp(request.getParameter("notelp"));
		user.setNorekening(request.getParameter("creditcard"));
//        user.setJob(request.getParameter("job"));
//        user.setCreditCard(request.getParameter("creditcard"));
//        user.setUserId(Integer.parseInt(request.getParameter("id")));
//        user.setRole("user");
		System.out.println("Test1-----------------------------");
		// -------------- upload photo ------------------
		Part filePart = request.getPart("image");
		if (filePart.getSize() != 0) { // if photo uploaded
			UploadIMG up = new UploadIMG();
			try {
				String uploadedpath = up.imgPembeli(request, response, filePart);
				user.setFilegbrakun(uploadedpath);
			} catch (Exception ex) {
				ex.printStackTrace();
				// set alert message
				request.getSession().setAttribute("AlertMessage", "please choose image only");
				// set alert type
				request.getSession().setAttribute("AlertType", "danger");
				response.sendRedirect("AdminUserServlet");

				return;
			}

		} else { // no photo uploaded
			user.setFilegbrakun(request.getParameter("photo"));
		}

		System.out.println("Test3-----------------------------");
		System.out.println("Username :-: " + user.getUsername());
		if (new RepoPembeli().updateUser(user, path)) {
			// update user successfully
			// set alert message
			request.getSession().setAttribute("AlertMessage", "update user info Successfully");
			// set alert type
			request.getSession().setAttribute("AlertType", "success");
			response.sendRedirect("AdminUserServlet");
		} else {
			// can't update user
			// set alert message
			request.getSession().setAttribute("AlertMessage", "Canot update user ..an error Occuer");
			// set alert type
			request.getSession().setAttribute("AlertType", "danger");
			response.sendRedirect("AdminUserServlet");

			return;

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
