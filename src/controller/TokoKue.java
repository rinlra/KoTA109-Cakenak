package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbrepo.RepoKue;
import model.Kue;

@WebServlet("/TokoKue")
public class TokoKue extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4635176455279920130L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Kue> allProducts = new ArrayList<Kue>();
        RepoKue productModel = new RepoKue();
        String id = request.getParameter("idpenjual");
        //-------------- handle paging ------------------
        int pageid = 1;
        int totalPerPage = 9;
        int start;

        if (request.getParameter("page") != null) {
            pageid = Integer.parseInt(request.getParameter("page"));
        }

        //end & start for paging
        start = (pageid - 1 )*totalPerPage;
        

        if (request.getParameter("cate") != null) {
            int cate = Integer.parseInt(request.getParameter("cate"));
            //allProducts = productModel.getAllProductByCategoryId(cate,start,totalPerPage);
        } else {
            allProducts = productModel.getKueByIdPenjualRange(id, start, totalPerPage);
        }
        
        //int noOfRecords = productModel.getNoOfRecords();
        //int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / totalPerPage);
       
        request.setAttribute("allProducts", allProducts);
        //request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", pageid);
        request.setAttribute("query" , request.getParameter("cate"));
        
        String nextJSP = "/shop.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);

    }

}
