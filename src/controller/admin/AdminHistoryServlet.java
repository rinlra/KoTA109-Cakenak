package controller.admin;

/**
 * display history 
 * @author sara metwalli
 */
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbrepo.RepoTransaksi;
import model.Transaksi;

@WebServlet("/admin/AdminHistoryServlet")
public class AdminHistoryServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7691281632964129889L;
	ArrayList<Transaksi> allHistory = null;
	RepoTransaksi h;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		h = new RepoTransaksi();
		try {

			allHistory = h.getAllHistory();
			request.setAttribute("allHistoryAdmin", allHistory);

			String nextJSP = "/admin/history.jsp";
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			dispatcher.forward(request, response);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
