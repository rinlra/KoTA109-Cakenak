package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbrepo.RepoCart;
import dbrepo.RepoKue;
import dbrepo.RepoPembayaran;
import dbrepo.RepoPenjual;
import model.CartItem;
import model.Kue;
import model.Pembeli;
import model.Penjual;

/**
 * to proccess pay operation 
 * @KoTA109
 */
@WebServlet(name = "Pay", urlPatterns = {"/Pay"})
public class Pay extends HttpServlet implements SingleThreadModel{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3521165474467924213L;

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

        RepoCart cartModel = new RepoCart();
        RepoKue repoKue = new RepoKue();
        RepoPenjual repoPenjual = new RepoPenjual();
        RepoPembayaran repoPembayaran = new RepoPembayaran();
        double total = 0;
        String message = "";
        
        //get user form session
        Pembeli user = (Pembeli) request.getSession().getAttribute("LoginUser");

        //get product of user
        ArrayList<CartItem> productCart = cartModel.getProductFromCart(user.getIdpembeli());
        List<Kue> kue = new ArrayList<Kue>();
        List<Penjual> penjual = new ArrayList<Penjual>();
        //-------------- Checkes ------------------
        //check  quantity
        for (CartItem cart : productCart) {

            total = cart.getHarga() * cart.getQuantity();
            
            //number of order greater than avalialbe
            if (cart.getQuantity() > cart.getQuantity_kue()) {
                message += "* anda mengorder " + cart.getQuantity() + " dari produk " + cart.getNama()
                		+ "(ID " + cart.getIdkue() + ")" 
                        + " yang hanya tersisa " + cart.getQuantity_kue() + " buah lagi :( <br/>";
            }
            
            kue.add(repoKue.getKueById(cart.getIdkue()));    
        }
        
//        kue.forEach(kuecekout -> {
//        	int stokfisik = kuecekout.getPhysicalstock();
//        	System.out.println(stokfisik);
//        	Kue newkue = new Kue();
//        	try {
//        		newkue.setAvailablestock(stokfisik);
//        		newkue.setIdkue(kuecekout.getIdkue());
//        		repoKue.updateQuantityKueAvailable(newkue);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        });
        
        //check money 
        if (total > user.getCash()) {
            message += " * Cash anda kurang dari total belanja, tolong isi cash anda terlebih dahulu<br>";
        }

        //an error occure
        if (!message.trim().equals("")) {
            request.getSession().setAttribute("message", message);
            response.sendRedirect("Failed.jsp");
            return;
        }
        
        //-------------- Payment proccess ------------------
        
        //reduce balace in user
        user.setCash(user.getCash() - total);
     
        //call transaction 
        try {
			if(repoPembayaran.startPayment(user, productCart)){
			    //send mail of success
			    message = "Terimakasih sudah berbelanja di Cakenak ^_^ <br/>";
			    
//			    new MailModel(user.getEmail(), "Successfull Payment", message).sendMail();
			    
			    request.getSession().setAttribute("message",message);
			    response.sendRedirect("Success.jsp");
			}else{
			    request.getSession().setAttribute("message", "Error saat memproses data, silahkan coba kembali :( ");
			    response.sendRedirect("Failed.jsp");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "pay money for product of user";
    }// </editor-fold>

}
