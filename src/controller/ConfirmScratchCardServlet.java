package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * check card number to get cash
 * @author sara metwalli
 */
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbrepo.RepoCharge;
import dbrepo.RepoPembeli;
import model.Pembeli;

@WebServlet("/ConfirmScratchCardServlet")
public class ConfirmScratchCardServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5365287889613668867L;
	RequestDispatcher dispatcher;
    String nextJSP;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Pembeli user =(Pembeli) request.getSession().getAttribute("LoginUser");
        String usrId = user.getIdpembeli();
        double cash = user.getCash();
        
        String CardStr = request.getParameter("CardStr");
        System.out.println("cardStr"+CardStr);
        RepoCharge chModel = new RepoCharge();
        boolean exist = chModel.checkCardExistForUser(CardStr);
       // boolean notused = chModel.checkCardValidation(CardStr);
        System.out.println("exx "+ exist );
        if (exist ) {

            try {
                int value = chModel.getValuefromNumber(CardStr);
                chModel.setCardUsed(CardStr);
                cash = cash + value;
                RepoPembeli usrDbModel = new RepoPembeli();
                user.setCash(cash);
                boolean cashAdded = usrDbModel.updateBalancePembeli(user);
                if (cashAdded) {
                    request.setAttribute("message", "The Cash added to your balance successfully");
                    
                    nextJSP = "/Success.jsp";
                    dispatcher = getServletContext().getRequestDispatcher(nextJSP);
                    dispatcher.forward(request, response);
                    
                } else {
                    request.setAttribute("message", "Error , cash is not added please check your balance and try again ");
                    nextJSP = "/Failed.jsp";
                    dispatcher = getServletContext().getRequestDispatcher(nextJSP);
                    dispatcher.forward(request, response);
                    
                }
            } catch (IOException ex) {
                Logger.getLogger(ConfirmScratchCardServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        else{
         request.setAttribute("message", "Error , This Card is invalid please try again later ");
                nextJSP = "/Failed.jsp";
                dispatcher = getServletContext().getRequestDispatcher(nextJSP);
                dispatcher.forward(request, response);
        }

    }
}
