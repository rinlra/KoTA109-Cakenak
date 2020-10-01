package controller;

import java.io.IOException;

/**
 * handle get card number and send via mail
 * @author sara metwalli
 */
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Utils.MailModel;
import dbrepo.RepoCharge;
import model.Pembeli;

@WebServlet("/ScratchCardServlet")
public class ScratchCardServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 766838225117623042L;
	RequestDispatcher dispatcher;
    String nextJSP;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int charge = Integer.parseInt(request.getParameter("charge"));
        RepoCharge chModel = new RepoCharge();

        int count;
        switch (charge) {
            case 50:
            case 100:
            case 200:
            case 500:
                count = chModel.getSumCardNumber(charge);

                if (count > 0) {
                    request.setAttribute("message", " Mohon cek email anda untuk mendapatkan kode charge");
                    request.setAttribute("showGoToCharge", true);
                    String card_number = chModel.getCard(charge);
                    chModel.setCardTaken(card_number);
                    //-------------- Send mail ------------------
                    String message = "Terimakasi sudah menggunakan layanan charge Cakenak ^_^ <br/>"
                    + "your cardNumber is : "+card_number + "<br/> Dengan nilai sebesar : "+charge +"$"
                    +"<br/> untuk mengisi card anda <a href='http://localhost:8080/Cakenak/ConfirmScratchCard.jsp'> click here </a>";
                    Pembeli user =(Pembeli) request.getSession().getAttribute("LoginUser");
                    new MailModel(user.getEmail(), "Successfull Payment", message).sendMail();
                    
                    nextJSP = "/Success.jsp";
                    dispatcher = getServletContext().getRequestDispatcher(nextJSP);
                    dispatcher.forward(request, response);
                    break;
                }

            default:
                request.setAttribute("message", "Sorry this Scratch Card is not available right now, Please try again later ");
                nextJSP = "/Failed.jsp";
                dispatcher = getServletContext().getRequestDispatcher(nextJSP);
                dispatcher.forward(request, response);

        }

    }
}
