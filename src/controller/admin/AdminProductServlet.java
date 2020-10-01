package controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * diplay products
 * @author sara metwalli
 */
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbrepo.RepoKue;
import model.Kue;

@WebServlet("/admin/AdminProductServlet")
public class AdminProductServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3554979144681271132L;
	List<Kue> allKues = new ArrayList<Kue>();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RepoKue productModel = new RepoKue();
		allKues = productModel.getAllKue();
		request.setAttribute("allKuesAdmin", allKues);

		String nextJSP = "/admin/products.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request, response);

	}

}
