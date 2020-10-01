package controller.penjual;


import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbrepo.RepoKue;
import dbrepo.RepoPenjual;
import dbrepo.RepoTransaksi;
import model.Penjual;
import model.Transaksi;


/**
 * display all history
 * @author OsamaPC
 */
public class GetAllHistory extends HttpServlet {

    
    /**
	 * 
	 */
	private static final long serialVersionUID = 482234103608992594L;
	
   
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    			RepoTransaksi h;
                h=new RepoTransaksi();
                RepoPenjual repoPenjual = new RepoPenjual();
                RepoKue repoKue = new RepoKue();
                ArrayList<Transaksi> allHistory=null;
                Penjual penjual = (Penjual) request.getSession().getAttribute("LoginUser");
                try {
                	
                    allHistory = h.getAllHistory();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
               
    }

  

}
