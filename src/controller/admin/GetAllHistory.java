package controller.admin;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbrepo.RepoTransaksi;
import model.Transaksi;

/**
 * display all history
 * 
 * @KoTA109
 */
public class GetAllHistory extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9878486775682891L;
	RepoTransaksi h;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		h = new RepoTransaksi();
		ArrayList<Transaksi> allHistory = null;
		try {
			allHistory = h.getAllHistory();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

}
