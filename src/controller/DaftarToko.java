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

import dbrepo.RepoPenjual;
import model.Penjual;

@WebServlet("/DaftarToko")
public class DaftarToko extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 341764252081088380L;
	List<Penjual> allstore = new ArrayList<Penjual>();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RepoPenjual repoPenjual = new RepoPenjual();

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
            allstore = repoPenjual.pembeliGetAllPenjual(start, totalPerPage);
        }
        
        //int noOfRecords = productModel.getNoOfRecords();
        //int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / totalPerPage);
       
        request.setAttribute("allStore", allstore);
        //request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", pageid);
        request.setAttribute("query" , request.getParameter("cate"));
        
        String nextJSP = "/daftartokopenjual.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);

    }
}
