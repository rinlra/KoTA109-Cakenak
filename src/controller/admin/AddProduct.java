package controller.admin;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import Utils.FileUpload;
import dbrepo.RepoKue;
import model.Kue;

/**
 * to add and view products
 *
 * @KoTA109
 */
@WebServlet(name = "AddProduct", urlPatterns = { "/admin/AdminProduct" })
@MultipartConfig
public class AddProduct extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4322751848327393857L;

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
	// + sign on the left to edit the code.">
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
		Kue productobject = new RepoKue().getKueById(id);
		if (productobject == null) {
			request.getSession().setAttribute("message", "Kue not found");
			response.sendRedirect("/admin/Failed.jsp");
		} else {
			request.setAttribute("product", productobject);
			request.setAttribute("type", "Edit");
			request.getRequestDispatcher("/admin/addproduct.jsp").forward(request, response);
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

		// get data from jsp
		String name = request.getParameter("KueName");
		double price = Double.parseDouble(request.getParameter("KuePrice"));
		int jenis = Integer.parseInt(request.getParameter("RepoKue"));
		String date = LocalDate.now().toString();
		String description = request.getParameter("KueDescription");
		int quantity = Integer.parseInt(request.getParameter("KueQuantity"));
		float berat = Float.parseFloat(request.getParameter("berat"));

		// -------------- Set Kue Object ------------------
		Kue productObj = new Kue();
		productObj.setNamakue(name);
		productObj.setHarga(price);
		productObj.setJenis(jenis);
		productObj.setWaktudiinput(date);
		productObj.setDeskripsi(description);
		productObj.setAvailablestock(quantity);
		productObj.setPhysicalstock(quantity);
		productObj.setBerat(berat);

		// -------------- upload photo ------------------
		Part filePart = request.getPart("gambarkue");
		if (filePart.getSize() != 0) { // if photo uploaded
			String path = request.getServletContext().getRealPath("");

			try {
				String uploadedpath = FileUpload.uploadImage(filePart, path);
				productObj.setGambarkue(uploadedpath);
			} catch (Exception ex) {
				ex.printStackTrace();
				// set alert message
				request.getSession().setAttribute("AlertMessage", "please choose image only");
				// set alert type
				request.getSession().setAttribute("AlertType", "danger");
				response.sendRedirect("AdminKueServlet");

				return;
			}

		} else { // no photo uploaded
			productObj.setGambarkue(request.getParameter("photo"));
		}

		// -------------- Update Kue ------------------
		if (request.getParameter("id") != null && !request.getParameter("id").trim().equals("")) {

			String id = request.getParameter("id");
			productObj.setIdkue(id);

//            if (new RepoKue().editKue(productObj)) {
//                //redirect to Success
//                //set alert message
//                request.getSession().setAttribute("AlertMessage", "Kue Updated Successfully");
//                //set alert type
//                request.getSession().setAttribute("AlertType", "success");
//                response.sendRedirect("AdminKueServlet");
//                return;
//            } else {
//                //can't add product
//                //set alert message
//                request.getSession().setAttribute("AlertMessage", "canot Update product ..An Error occure");
//                //set alert type
//                request.getSession().setAttribute("AlertType", "danger");
//                response.sendRedirect("AdminKueServlet");
//                return;
//            }
//
//            //-------------- Add  new product ------------------
//        } else {
//            if (new RepoKue().addKue(productObj)) {
//                //redirect to Success
//                //set alert message
//                request.getSession().setAttribute("AlertMessage", "Kue Added Successfully");
//                //set alert type
//                request.getSession().setAttribute("AlertType", "success");
//                response.sendRedirect("AdminKueServlet");
//                return;
//            } else {
//                //can't add product
//                //set alert message
//                request.getSession().setAttribute("AlertMessage", "canot add product ..An Error occure");
//                //set alert type
//                request.getSession().setAttribute("AlertType", "danger");
//                response.sendRedirect("AdminKueServlet");
//                return;
//            }

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
