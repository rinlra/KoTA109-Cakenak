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
@WebServlet("/ReduceQuantity")
public class ReduceQuantity extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3848165169286399442L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RepoCart cartModel = new RepoCart();
        RepoKue repoKue = new RepoKue();
        Kue kue = new Kue();
        String id = request.getParameter("id");
        

        boolean addCart = cartModel.reduceQuantity(id);
        
        //get login user id
        Pembeli user = (Pembeli) request.getSession().getAttribute("LoginUser");
        Cart cart = cartModel.getCart(id);
        String idkue = cart.getIdkue();
        kue = repoKue.getKueById(idkue);
        kue.setAvailablestock(kue.getAvailablestock()+1);
        repoKue.updateQuantityKueAvailable(kue);
        response.getWriter().print(cartModel.getNumberOfCartsForUser(user.getIdpembeli()));

    }
}
