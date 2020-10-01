package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbrepo.RepoKue;
import model.Kue;

/**
 * search by name or price 
 * @KoTA109
 */
public class SearchForProduct extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1030307649554054511L;
	ArrayList<Kue> product = new ArrayList<>();
    RepoKue model = new RepoKue();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("search") != null) {
            String productName = request.getParameter("search");

            System.out.println(productName);

            product = model.getKueByNama(productName);

            System.out.println(product.size());

        } else {
            System.out.println("else");
            double min = Double.parseDouble(request.getParameter("down"));
            System.out.println(min);
            double max = Double.parseDouble(request.getParameter("up"));
            System.out.println(max);

            product = model.getAllKueByHarga(min, max);
            System.out.println("sizeof product" + product.size());

        }

        request.setAttribute("allProducts", product);
        String nextJSP = "/shop.jsp";

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
