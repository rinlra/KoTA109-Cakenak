package controller;



import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbrepo.RepoCart;


/**
 * get number of product in user cart
 * @KoTA109
 */
@WebServlet("/getCartCount")
public class NumberOFCartsForUser extends HttpServlet {

     /**
	 * 
	 */
	private static final long serialVersionUID = -1215639128707309358L;
	RepoCart cartModel;
   

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        cartModel = new RepoCart();
        String id = request.getParameter("id");
   
         int addCart = cartModel.getNumberOfCartsForUser(id);
         response.getWriter().print(addCart);

    }
  
}
