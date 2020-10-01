package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.admin.HBaseUtils;
import dbrepo.RepoKue;
import model.Kue;

/**
 * Servlet implementation class HomeController
 */
@WebServlet(name = "/HomeController")
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 3L;
    
	public RepoKue repoKue = new RepoKue();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
//    public HomeController() {
//        super();
//        // TODO Auto-generated constructor stub
//    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		ArrayList<Kue> listkue = repoKue.getAllKue();
		request.setAttribute("kueList", listkue);
		//request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public void showAllkue(HttpServletRequest request, HttpServletResponse response, HBaseUtils hbaseUtils) {
		try {
			ArrayList<Kue> listkue = hbaseUtils.getKue();
			request.setAttribute("kueList", listkue);
			request.getRequestDispatcher("/StoreHome.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
