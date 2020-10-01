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
import dbrepo.RepoKue;
import dbrepo.SliderOperation;
import model.Kue;
import model.Slider;

/**
 * to add slider
 *
 * @KoTA109
 */
@WebServlet("/admin/AddSlider")
@MultipartConfig
public class AddSlider extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7069935000262268819L;
	static String productIdForSlider;
	static Kue productForSlider;
	RepoKue pModel;
	Slider slider;
	SliderOperation sliderOp;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("addslider");
		productIdForSlider = request.getParameter("id");
		pModel = new RepoKue();
		productForSlider = pModel.getKueById(productIdForSlider);

		if (productForSlider == null) {
			request.getSession().setAttribute("message", "Kue not found");
			response.sendRedirect("../Failed.jsp");
		} else {
			request.setAttribute("product", productForSlider);
			request.getRequestDispatcher("/admin/addSlider.jsp").forward(request, response);
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		sliderOp = new SliderOperation();
//        String name = request.getParameter("KueName");
//        String price = request.getParameter("KuePrice");
		String tittle = request.getParameter("tittle");
		String subtittle = request.getParameter("subTittle");
		String desc = request.getParameter("KueDescription");

		slider = new Slider();
		slider.setDeskripsi(desc);
		slider.setTitle(tittle);
		slider.setSubtitle(subtittle);
		slider.setIdkue(productIdForSlider);

		Part filePart = request.getPart("image");
		if (filePart.getSize() != 0) { // if photo uploaded
			String path = request.getServletContext().getRealPath("");

			try {
				String uploadedpath = FileUpload.uploadImage(filePart, path);
				slider.setGambar(uploadedpath);
			} catch (Exception ex) {
				ex.printStackTrace();

				// set alert message
				request.getSession().setAttribute("AlertMessage", "please choose image only");
				// set alert type
				request.getSession().setAttribute("AlertType", "danger");
				response.sendRedirect("SlidersShow");
				return;
			}

		} else { // no photo uploaded
			slider.setGambar(request.getParameter("photo"));
		}

		System.out.println(slider);
		boolean addSlider = sliderOp.addSlider(slider);
		if (addSlider) {
			// set alert message
			request.getSession().setAttribute("AlertMessage", "Slide Added Successfully");
			// set alert type
			request.getSession().setAttribute("AlertType", "success");
			response.sendRedirect("SlidersShow");
		} else {
			// set alert message
			request.getSession().setAttribute("AlertMessage", "canot add slide ..An Error occure");
			// set alert type
			request.getSession().setAttribute("AlertType", "danger");
			response.sendRedirect("SlidersShow");

		}

	}

}
