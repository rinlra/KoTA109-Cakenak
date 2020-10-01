package controller.penjual;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbrepo.RepoKue;
import dbrepo.RepoPenjual;
import dbrepo.RepoTransaksi;
import model.Kue;
import model.Penjual;
import model.Transaksi;

@WebServlet("/penjual/LaporanJual")
public class LaporanJual extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3015585775878413910L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	ArrayList<Transaksi> allHistory = new ArrayList<Transaksi>();
        RepoTransaksi h;
        h=new RepoTransaksi();
        RepoPenjual repoPenjual = new RepoPenjual();
        RepoKue repoKue = new RepoKue();
        ArrayList<Kue> kuepenjual = null;
        Penjual penjual = (Penjual) request.getSession().getAttribute("LoginUser");
        try {
            kuepenjual = repoKue.getKueByIdPenjual(penjual.getIdpenjual());
            kuepenjual.forEach(produk -> {
            	try {
					allHistory.add(h.getSellerHistory(produk.getIdkue()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            });
            request.setAttribute("allHistoryPenjual",allHistory );

        String nextJSP = "/penjual/laporanjual.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
        } catch (IOException ex) {
           ex.printStackTrace();
        }
    }
	
}
