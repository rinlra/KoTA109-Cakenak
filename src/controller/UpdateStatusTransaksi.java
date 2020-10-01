package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbrepo.RepoTransaksi;
import model.Pembeli;
import model.Transaksi;

@WebServlet(value="/UpdateStatusTransaksi")
public class UpdateStatusTransaksi extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6115986503849738771L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		Pembeli pembeli = (Pembeli) request.getSession().getAttribute("LoginUser");
		Transaksi transaksi = new Transaksi();
        RepoTransaksi repoTransaksi = new RepoTransaksi();
        
        int statuspaid = 1;
        
        transaksi.setIdpembeli(pembeli.getIdpembeli());
        transaksi.setIdtransaksi(request.getParameter("idtransaksi"));
        transaksi.setStatussellerpaid(statuspaid);
        
        repoTransaksi.updateTransaksiPembeli(transaksi);
        
        String nextJSP = "/ServletHistoriPembeli";
        
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.include(request, response);
    }
	
}
