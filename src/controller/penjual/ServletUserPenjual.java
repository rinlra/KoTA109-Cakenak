/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.penjual;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * display all users
 * @author sara metwalli
 */
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbrepo.RepoPembeli;
import model.Pembeli;

@WebServlet("/penjual/ServletUserPenjual")
public class ServletUserPenjual extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6640198281396330683L;
	List<Pembeli> allUsers = new ArrayList<Pembeli>();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RepoPembeli userDbModel = new RepoPembeli();
        allUsers = userDbModel.getPembeli();
        request.setAttribute("allUsersPenjual", allUsers);
        String nextJSP = "/penjual/users.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }
}
