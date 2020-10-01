package controller.penjual;

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
import model.Penjual;

@WebServlet(name = "/ServletProdukPenjual", urlPatterns = {"/penjual/ServletProdukPenjual"})
public class ServletProdukPenjual extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4750824362588916998L;
	List<Kue> allKues = new ArrayList<Kue>();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RepoKue productModel = new RepoKue();
        Penjual penjual = (Penjual) request.getSession().getAttribute("LoginUser");
        allKues = productModel.getKueByIdPenjual(penjual.getIdpenjual());
        request.setAttribute("daftar_kue_penjual", allKues);

        String nextJSP = "/penjual/products.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);

    }

}
