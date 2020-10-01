package controller.penjual;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbrepo.RepoKue;
import dbrepo.SliderOperation;
import model.Slider;

/**
 * get all slide 
 * @KoTA109
 */
@WebServlet("/penjual/SlidersShow")
public class SlidersShow extends HttpServlet {

    
    /**
	 * 
	 */
	private static final long serialVersionUID = -4239705354743198990L;
	RepoKue pModel;
    ArrayList<Slider> sliderArr=new ArrayList<>();
    SliderOperation sliderOp;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        sliderOp=new SliderOperation();
        sliderArr=sliderOp.getAllSliders();
        request.setAttribute("allslidertsPenjual", sliderArr);
        String nextJSP = "/penjual/offers.jsp";

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }



}
