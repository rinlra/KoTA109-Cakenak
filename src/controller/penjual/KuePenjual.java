package controller.penjual;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import Utils.UploadIMG;
import dbrepo.RepoKue;
import model.Kue;
import model.Penjual;

/**
 * to add and view products
 *
 * @author KoTA109
 */
@WebServlet(name = "/KuePenjual", urlPatterns = {"/penjual/KuePenjual"})
@MultipartConfig
public class KuePenjual extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7921646895761209138L;

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

        String id = request.getParameter("id");
        Kue productobject = new RepoKue().getKueById(id);
        if (productobject == null) {
            request.getSession().setAttribute("message", "Kue tidak ditemukan");
            response.sendRedirect("Failed.jsp");
        } else {
            request.setAttribute("product", productobject);
            request.setAttribute("type", "Edit");
            request.getRequestDispatcher("addproduct.jsp").forward(request, response);
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

        //get data from jsp
        String name = request.getParameter("ProductName");
        double price = Double.parseDouble(request.getParameter("ProductPrice"));
        int jenis = Integer.parseInt(request.getParameter("jenis"));
        String date = LocalDate.now().toString();
        String tglproduksi = request.getParameter("tglproduksi");
        String tglbaiksblm = request.getParameter("tglbaiksblm");
        String description = request.getParameter("ProductDescription");
        int quantity = Integer.parseInt(request.getParameter("ProductQuantity"));
        int oldphysicalstock = 0;
        String tmp = request.getParameter("oldphysicalstock");
        if (tmp != null && !tmp.isEmpty()) {
        	oldphysicalstock = Integer.parseInt(request.getParameter("oldphysicalstock"));
        }
        float berat = Float.parseFloat(request.getParameter("berat"));
        int layakjual = 1;
        Penjual penjual = (Penjual) request.getSession().getAttribute("LoginUser");

        //-------------- Set Kue Object  ------------------
        Kue productObj = new Kue();
        productObj.setIdpenjual(penjual.getIdpenjual());
        productObj.setNamakue(name);
        productObj.setHarga(price);
        productObj.setJenis(jenis);
        productObj.setWaktudiinput(date);
        productObj.setTglproduksi(tglproduksi);
        productObj.setTglbaiksblm(tglbaiksblm);
        productObj.setDeskripsi(description);
        productObj.setAvailablestock(quantity);
        productObj.setPhysicalstock(quantity);
        productObj.setBerat(berat);
        productObj.setLayakjual(layakjual);

        //-------------- upload photo ------------------
        Part filePart = request.getPart("gambarkue");
        
        if (filePart.getSize() != 0) {      //if photo uploaded
            UploadIMG up = new UploadIMG();
            try {
            	String namagambar = up.imgKue(request, response, filePart);
                productObj.setGambarkue(namagambar);
            } catch (Exception ex) {
                ex.printStackTrace();
                //set alert message
                request.getSession().setAttribute("AlertMessage", "please choose image only");
                //set alert type
                request.getSession().setAttribute("AlertType", "danger");
                response.sendRedirect("ServletProdukPenjual");

                return;
            }

        } else {                          //no photo uploaded
            productObj.setGambarkue(request.getParameter("photo"));
        }

        //-------------- Update Kue ------------------
        if (request.getParameter("id") != null && !request.getParameter("id").trim().equals("")) {

            String id = request.getParameter("id");
            productObj = new RepoKue().getKueById(id);
            
            if (new RepoKue().editKue(productObj, oldphysicalstock)) {
                //redirect to Success
                //set alert message
                request.getSession().setAttribute("AlertMessage", "Kue Updated Successfully");
                //set alert type
                request.getSession().setAttribute("AlertType", "success");
                response.sendRedirect("ServletProdukPenjual");
                return;
            } else {
                //can't add product
                //set alert message
                request.getSession().setAttribute("AlertMessage", "canot Update product ..An Error occure");
                //set alert type
                request.getSession().setAttribute("AlertType", "danger");
                response.sendRedirect("ServletProdukPenjual");
                return;
            }

            //-------------- Add  new product ------------------
        } else {
            if (new RepoKue().addKue(productObj)) {
                //redirect to Success
                //set alert message
                request.getSession().setAttribute("AlertMessage", "Kue Added Successfully");
                //set alert type
                request.getSession().setAttribute("AlertType", "success");
                response.sendRedirect("ServletProdukPenjual");
                return;
            } else {
                //can't add product
                //set alert message
                request.getSession().setAttribute("AlertMessage", "canot add product ..An Error occure");
                //set alert type
                request.getSession().setAttribute("AlertType", "danger");
                response.sendRedirect("ServletProdukPenjual");
                return;
            }

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
