package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbrepo.RepoCart;
import dbrepo.RepoKue;
import model.Cart;
import model.Kue;
import model.Pembeli;

/**
 *
 * @KoTA109
 */
@WebServlet(name = "increaseQuantity", urlPatterns = {"/increaseQuantity"})
public class increaseQuantity extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2331907696150111806L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RepoCart repoCart = new RepoCart();
        RepoKue repoKue = new RepoKue();
        Kue kue = new Kue();
        String id = request.getParameter("id");

        boolean addCart = repoCart.increaseQuantity(id);
        //get login user id
        Pembeli pembeli = (Pembeli) request.getSession().getAttribute("LoginUser");
        Cart cart = repoCart.getCart(id);
        String idkue = cart.getIdkue();
        kue = repoKue.getKueById(idkue);
        kue.setAvailablestock(kue.getAvailablestock()-1);
        repoKue.updateQuantityKueAvailable(kue);
        response.getWriter().print(repoCart.getNumberOfCartsForUser(pembeli.getIdpembeli()));

    }
}
