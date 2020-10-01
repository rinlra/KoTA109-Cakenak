package controller;

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
import model.Pembeli;
import model.Transaksi;

@WebServlet("/ServletHistoriPembeli")
public class ServletHistoriPembeli extends HttpServlet {
	RepoTransaksi h;
    /**
	 * 
	 */
	private static final long serialVersionUID = -3889404350720020100L;
	

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	ArrayList<Transaksi> allHistory = new ArrayList<Transaksi>();
        
        h=new RepoTransaksi();
        Pembeli pembeli = (Pembeli) request.getSession().getAttribute("LoginUser");
        try {
        	if (!h.getUserHistory(pembeli.getIdpembeli()).isEmpty()) {
        		allHistory = h.getUserHistory(pembeli.getIdpembeli());
        	}
			
			
            

        
        } catch (IOException ex) {
           ex.printStackTrace();
        }
        
        request.setAttribute("allHistoryPembeli",allHistory );
        String nextJSP = "/history.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }
}
