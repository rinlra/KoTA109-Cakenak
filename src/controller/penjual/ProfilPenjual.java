package controller.penjual;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import Utils.UploadIMG;
import dbrepo.RepoPenjual;
import model.Penjual;

/**
 * edit profile
 *
 * @KoTA109
 */
@MultipartConfig
@WebServlet(name = "ProfilPenjual", urlPatterns = {"/penjual/ProfilPenjual"})
public class ProfilPenjual extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4874728003532190310L;

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

        String id = request.getParameter("id");
        Penjual user = new RepoPenjual().getPenjualById(id);

        if (user == null) {
            request.getSession().setAttribute("message", "user not found");
            response.sendRedirect("/penjual/Failed.jsp");
        } else {
            request.setAttribute("userInfo", user);
            request.getRequestDispatcher("/penjual/profile.jsp").forward(request, response);
        }

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
            Penjual user = (Penjual) request.getSession().getAttribute("LoginUser");

            Penjual newUser = (Penjual) user.clone();
            String path = request.getServletContext().getRealPath("");
            //get request paramater & update object user
            newUser.setNamapenjual(request.getParameter("namapenjual"));
            newUser.setUsername(request.getParameter("username"));
            newUser.setEmail(request.getParameter("email"));
            newUser.setPassword(request.getParameter("password"));
            newUser.setAlamat(request.getParameter("alamat"));
            newUser.setNotelp(request.getParameter("notelp"));
            newUser.setNorekening(request.getParameter("creditcard"));
            newUser.setFilegbrakun("akunpenjualdefault.jpg");
            newUser.setTglakundibuat(request.getParameter("tglakundibuat"));
            newUser.setTipeakun("ROLE_SELLER");

            //-------------- upload photo ------------------
            Part filepart = request.getPart("filegbrakun");
            if (filepart.getSize() != 0) {      //if photo uploaded
                
                
                try{
                    String namagambar = upImg.imgPenjual(request, response, filepart);
                    newUser.setFilegbrakun(namagambar);
                }catch(Exception ex){
                    ex.printStackTrace();
                    request.setAttribute("message", "please choose image only");
                    request.getRequestDispatcher("/penjual/Failed.jsp").forward(request, response);
                    return ;
                }
                
            } else {                          //no photo uploaded
                newUser.setFilegbrakun(request.getParameter("photo"));
            }

            if (new RepoPenjual().updatePenjual(newUser)) {
                //update user successfully
                newUser.setPassword(" ");    //remove password from object
                request.getSession().setAttribute("LoginUser", newUser); //update session user
                //redirect to profile
                request.setAttribute("messageInfo", "update user info Successfully");
                request.getRequestDispatcher("/penjual/profile.jsp").forward(request, response);
            } else {
                //can't update user
                request.setAttribute("message", "can't update user now .. :(<br/>"
                        + "email or credit card used before");
                request.getRequestDispatcher("/penjual/Failed.jsp").forward(request, response);
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
