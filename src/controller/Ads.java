package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbrepo.RepoAds;
import dbrepo.RepoKue;
import model.Advertisement;

/**
 * Ads slidebar
 * @KoTA109
 */
@WebServlet(name = "Ads", urlPatterns = {"/Ads"})
public class Ads extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2949599848294636904L;


	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        request.setAttribute("maxPrice", (double)(new RepoKue().getHargaKueMaksimum()));
        Advertisement randomAds = new RepoAds().getRandomAds();
        if(randomAds != null )
            request.setAttribute("ads", randomAds);
    }

   
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
