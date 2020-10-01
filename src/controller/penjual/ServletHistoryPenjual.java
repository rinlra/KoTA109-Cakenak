package controller.penjual;

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

@WebServlet("/penjual/ServletHistoryPenjual")
public class ServletHistoryPenjual extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3889404350720020100L;
	ArrayList<Transaksi> allHistory = null;
    RepoTransaksi h;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         h=new RepoTransaksi();
        try {
            
            allHistory = h.getAllHistory();
            request.setAttribute("allHistoryPenjual",allHistory );

        String nextJSP = "/penjual/history.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
        } catch (IOException ex) {
           ex.printStackTrace();
        }
    }
}
