//package controller.admin;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import dbrepo.RepoPembeli;
//import model.Pembeli;
//
///**
// * handle sign up 
// * @KoTA109
// */
//@WebServlet(name = "/admin/Signup", urlPatterns = {"/admin/Signup"})
//public class Signup extends HttpServlet {
//
//	private static final long serialVersionUID = -2057150423963510712L;
//
//	/**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        PrintWriter out = response.getWriter();
//        
//        String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
//        
//        //-------------- get request paramater ------------------
//        String nama = request.getParameter("nama");
//        String alamat = request.getParameter("alamat");
//        //String kota = request.getParameter("kota");
//        //String provinsi = request.getParameter("provinsi");
//        //String kodepos = request.getParameter("kodepos");
//        String notelp = request.getParameter("notelp");
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        String email = request.getParameter("email");
//        System.out.println(nama + alamat + notelp + username + password + email);
//        
//        //-------------- create pembeli object ------------------
//        Pembeli pembeli = new Pembeli();
//        pembeli.setNama(nama);
//        pembeli.setAlamat(alamat);
//        pembeli.setKota(" ");
//        pembeli.setProvinsi(" ");
//        pembeli.setKodepos(" ");
//        pembeli.setNotelp(notelp);
//        pembeli.setUsername(username);
//        pembeli.setPassword(password);
//        pembeli.setEmail(email);
//        pembeli.setFilegbrakun("akunpembelidefault.jpg");
//        pembeli.setTglakundibuat(date);
//        pembeli.setIdcart(" ");
//        pembeli.setIdtransaksi(" ");
//        pembeli.setNorekening(" ");
//        pembeli.setTipeakun("ROLE_COSTUMER");
//        pembeli.setCash(0);
//        pembeli.setTotalkuedibeli(0);
//   
//        if(new RepoPembeli().insertDataPembeli(pembeli)){
//            request.setAttribute("message", "You signup successfully");
//            getServletContext().getRequestDispatcher("/Success.jsp").forward(request, response);
//        }else {
//            request.setAttribute("message", "Cant't Signup <br/> Email or Cridt Card used before .. ");
//            getServletContext().getRequestDispatcher("/Failed.jsp").forward(request, response);
//        }
//       
//        
//        
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
//
//}
