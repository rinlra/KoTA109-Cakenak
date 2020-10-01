package controller.penjual;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbrepo.RepoPenjual;
import model.Penjual;

/**
 * handle sign up 
 * @KoTA109
 */
@WebServlet(name = "SignupPenjual", urlPatterns = {"/penjual/SignupPenjual"})
public class SignupPenjual extends HttpServlet {

	private static final long serialVersionUID = -2057150423963510712L;

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
        PrintWriter out = response.getWriter();
        
        String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        
        //-------------- get request paramater ------------------
        String namapenjual = request.getParameter("namapenjual");
		String notelp = request.getParameter("notelp");
		String alamat = request.getParameter("alamat");
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String filegbrakun = request.getParameter("filegbrakun");
		//Part filepart = request.getPart("filegbrakun");
		//upimg.imgPenjual(request, response, filepart);
		//System.out.println(request.getParameter("filegbrakun"));
		String tglakundibuat = request.getParameter("tglakundibuat");
		String tipeakun = request.getParameter("tipeakun");
		String norekening = request.getParameter("norekening");
		int kuetotal = 0;
		double pendapatan = 0;
		int statusverifikasi = 0;
        
        //-------------- create penjual object ------------------
        Penjual penjual = new Penjual();
        penjual.setNamapenjual(namapenjual);
        penjual.setAlamat(alamat);
        penjual.setNotelp(notelp);
        penjual.setUsername(username);
        penjual.setPassword(password);
        penjual.setEmail(email);
        penjual.setFilegbrakun("akunpenjualdefault.jpg");
        penjual.setTglakundibuat(date);
        penjual.setNorekening(norekening);
        penjual.setTipeakun("ROLE_SELLER");
        penjual.setPendapatan(pendapatan);
        penjual.setTotalkue(kuetotal);
        penjual.setStatusverifikasi(statusverifikasi);
   
        if(new RepoPenjual().insertDataPenjual(penjual)){
            request.setAttribute("message", "Register berhasil");
            getServletContext().getRequestDispatcher("Success.jsp").forward(request, response);
        }else {
            request.setAttribute("message", "Register gagal <br/> Email atau username sudah digunakan .. ");
            getServletContext().getRequestDispatcher("/penjual/Failed.jsp").forward(request, response);
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
