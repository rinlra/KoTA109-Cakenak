package controller.penjual;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbrepo.RepoKategori;
import model.Kategori;


@WebServlet(name = "ServletKategoriPenjual", urlPatterns = {"/penjual/ServletKategoriPenjual"})
public class ServletKategoriPenjual extends HttpServlet {
      
            /**
	 * 
	 */
	private static final long serialVersionUID = -2999519000381510033L;
			ArrayList<Kategori> allKategori = new ArrayList<>();
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("servlet");
        RepoKategori categorymodel = new RepoKategori();
        allKategori= categorymodel.getAllKategori();
        //System.out.println(allKategori.get(0).getName());
        //System.out.println(allKategori.size());
        request.setAttribute("allKategorisPenjual", allKategori);

        String nextJSP = "/penjual/category.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
       
    }



    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
