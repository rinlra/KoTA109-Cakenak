
package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbrepo.RepoKue;
import dbrepo.RepoPenjual;
import model.Kue;
import model.Penjual;

/**
 * handle display product and recomand products
 * @KoTA109
 */
@WebServlet(name = "Product", urlPatterns = {"/Product"})
public class Product extends HttpServlet {

   

    
    /**
	 * 
	 */
	private static final long serialVersionUID = 5365341270484248437L;



	/**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String idkue = request.getParameter("id");
        RepoKue repoKue = new RepoKue();
        RepoPenjual repoPenjual = new RepoPenjual();
        Kue kue = repoKue.getKueById(idkue);
        Penjual penjual = null;
        
        //no product with this id 
        if(kue==null){
            response.sendRedirect("404.jsp");
        }else{
        	penjual = repoPenjual.getPenjualById(kue.getIdpenjual());
             //assigne it on request
            request.setAttribute("product", kue);
            request.setAttribute("namapenjualkue", penjual.getNamapenjual());
            //get recommnded product
            //ArrayList<Kue> recommendeditems = repoKue.getRecommendedItem(kue.getJenis(), productID);
            //request.setAttribute("recomed", recommendeditems);
            
            request.getRequestDispatcher("/product-details.jsp").forward(request, response);
        }
        
       
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
