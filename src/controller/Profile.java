package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import Utils.UploadIMG;
import dbrepo.RepoPembeli;
import model.Pembeli;

/**
 * handle display and edit profile 
 * @KoTA109
 */
@MultipartConfig
@WebServlet(name = "Profile", urlPatterns = {"/Profile"})
public class Profile extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 988566204402086893L;

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        request.getRequestDispatcher("/profile.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	UploadIMG upImg = new UploadIMG();
    	
        try {
            //get id from session
            Pembeli user = (Pembeli) request.getSession().getAttribute("LoginUser");

            Pembeli newUser = (Pembeli) user.clone();
            String path = request.getServletContext().getRealPath("");
            //get request paramater & update object user
            newUser.setNama(request.getParameter("username"));
            newUser.setEmail(request.getParameter("email"));
            newUser.setPassword(request.getParameter("password"));
            newUser.setAlamat(request.getParameter("alamat"));
            newUser.setKota(request.getParameter("kota"));
            newUser.setProvinsi(request.getParameter("provinsi"));
            newUser.setKodepos(request.getParameter("kodepos"));
            newUser.setNotelp(request.getParameter("notelp"));
            newUser.setNorekening(request.getParameter("creditcard"));

            //-------------- upload photo ------------------
            Part filepart = request.getPart("filegbrakun");
            if (filepart.getSize() != 0) {      //if photo uploaded
                
                
                try{
                    upImg.imgPembeli(request, response, filepart);
                    newUser.setFilegbrakun(request.getParameter("filegbrakun"));
                }catch(Exception ex){
                    ex.printStackTrace();
                    request.setAttribute("message", "please choose image only");
                    request.getRequestDispatcher("/Failed.jsp").forward(request, response);
                    return ;
                }
                
            }

            if (new RepoPembeli().updatePembeli(newUser)) {
                //update user successfully
                newUser.setPassword(" ");    //remove password from object
                request.getSession().setAttribute("LoginUser", newUser); //update session user
                //redirect to profile
                request.setAttribute("messageInfo", "update user info Successfully");
                request.getRequestDispatcher("/profile.jsp").forward(request, response);
            } else {
                //can't update user
                request.setAttribute("message", "can't update user now .. :(<br/>"
                        + "email or credit card used before");
                request.getRequestDispatcher("/Failed.jsp").forward(request, response);
            }
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        } 

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
