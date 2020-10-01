package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * display leates product in index
 * @author sara metwalli
 */
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbrepo.RepoKue;
import model.Kue;

@WebServlet("/IndexProductServlet")
public class IndexProductServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8857624276266296405L;
	List<Kue> limitedProducts = new ArrayList<Kue>();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RepoKue productModel = new RepoKue();
        limitedProducts = productModel.getAllKue();
         
        request.setAttribute("limitedProducts",limitedProducts );


    }
}
