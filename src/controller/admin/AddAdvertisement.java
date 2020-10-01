package controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import Utils.FileUpload;
import dbrepo.RepoAds;
import model.Advertisement;

/**
 * to add Advertisement
 *
 * @KoTA109
 */
@WebServlet("/admin/AddAdvertisement")
@MultipartConfig
public class AddAdvertisement extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4200136777984132187L;
	Advertisement add;
	RepoAds model;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		model = new RepoAds();
		String url = request.getParameter("url");

		Part filePart = request.getPart("image");
		if (filePart.getSize() != 0) { // if photo uploaded
			String path = request.getServletContext().getRealPath("");

			try {
				String uploadedpath = FileUpload.uploadImage(filePart, path);
				model.addAdvertisment(uploadedpath, url);
			} catch (Exception ex) {
				ex.printStackTrace();
				// set alert message
				request.getSession().setAttribute("AlertMessage", "please choose image only");
				// set alert type
				request.getSession().setAttribute("AlertType", "danger");
				response.sendRedirect("ShowAdvertisement");

				return;
			}

		} else { // no photo uploaded
			model.addAdvertisment(request.getParameter("photo"), url);
		}

		// set alert message
		request.getSession().setAttribute("AlertMessage", "ADS Added Successfully");
		// set alert type
		request.getSession().setAttribute("AlertType", "success");
		response.sendRedirect("ShowAdvertisement");

	}

}
