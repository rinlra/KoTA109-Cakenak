package controller.admin;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import Utils.UploadIMG;
import model.Kue;
import model.Penjual;

@WebServlet(name = "/ActionController", value = "/admin/ActionController")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
		maxFileSize = 1024 * 1024 * 50, // 50 MB
		maxRequestSize = 1024 * 1024 * 100) // 100 MB
public class ActionController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String penjualid;
	public UploadIMG upimg = new UploadIMG();

	public ActionController() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		System.out.println("ACTION = " + action);

//		request = (HttpServletRequest) request.getContextPath() + "/admin/";

		HBaseUtils hbaseUtils = new HBaseUtils();
		System.out.println("request : " + request.getContextPath() + " response : " + response);
		if ("retrieve_penjual".equals(action)) {
			showAllData(request, response, hbaseUtils);
		} else if ("to_input_penjual".equals(action)) {
			RequestDispatcher rd = request.getRequestDispatcher("InputDataPenjual.jsp");
			rd.forward(request, response);
		} else if ("insert_penjual".equals(action)) {
			// TODO
			String namapenjual = request.getParameter("namapenjual");
			String notelp = request.getParameter("notelp");
			String alamat = request.getParameter("alamat");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String filegbrakun = request.getParameter("filegbrakun");
			// Part filepart = request.getPart("filegbrakun");
			// upimg.imgPenjual(request, response, filepart);
			// System.out.println(request.getParameter("filegbrakun"));
			String tglakundibuat = request.getParameter("tglakundibuat");
			String tipeakun = request.getParameter("tipeakun");
			String norekening = request.getParameter("norekening");
			String newcf = null;
			String newc = null;
			String newv = null;
			if (request.getParameter("newcf") != null) {
				newcf = request.getParameter("newcf");
				newc = request.getParameter("newc");
				newv = request.getParameter("newv");
				System.out.println(newcf + " " + newc + " " + newv);
			}
			int kuetotal = Integer.parseInt("0");
			double pendapatan = 0;
			int statusverifikasi = (int) 1;
			boolean result = hbaseUtils.insertDataPenjual(namapenjual, notelp, alamat, username, password, filegbrakun,
					tglakundibuat, tipeakun, norekening, kuetotal, pendapatan, statusverifikasi, newcf, newc, newv);
			if (result) {
				showAllData(request, response, hbaseUtils);
			} else {
				RequestDispatcher rd = request.getRequestDispatcher("/admin/error.jsp");
				rd.forward(request, response);
			}
		} else if ("delete_penjual".equals(action)) {
			String row = request.getParameter("idpenjual");
			List<Kue> kueList = hbaseUtils.getKueById(row);

			System.out.println("ROW DELETED = " + row);

			boolean result = hbaseUtils.deletepenjual(row);
			if (result) {
				showAllData(request, response, hbaseUtils);
			} else {
				RequestDispatcher rd = request.getRequestDispatcher("/admin/error.jsp");
				rd.forward(request, response);
			}
		} else if ("update_penjual".equals(action)) {
			String row = request.getParameter("idpenjual");
			String name = request.getParameter("namapenjual");
			String contact = request.getParameter("notelp");
			String address = request.getParameter("alamat");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String filegbrakun = request.getParameter("filegbrakun");
			String tglakundibuat = request.getParameter("tglakundibuat");
			String tipeakun = request.getParameter("tipeakun");
			String norekening = request.getParameter("norekening");
			double pendapatan = Double.parseDouble(request.getParameter("pendapatan"));
			int totalkue = Integer.parseInt("0");
			int statusverifikasi = Integer.parseInt(request.getParameter("statusverifikasi"));
			Penjual penjual = new Penjual(name, contact, address, username, password, filegbrakun, tglakundibuat,
					tipeakun, norekening, totalkue, pendapatan, statusverifikasi);
			penjual.setIdpenjual(row);
			request.setAttribute("info", penjual);
			request.getRequestDispatcher("/admin/editDataPenjual.jsp").forward(request, response);
		} else if ("edit_penjual".equals(action)) {
			String row = request.getParameter("idpenjual");
			String name = request.getParameter("namapenjual");
			String contact = request.getParameter("notelp");
			String address = request.getParameter("alamat");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String filegbrakun = request.getParameter("filegbrakun");
			String tglakundibuat = request.getParameter("tglakundibuat");
			String tipeakun = request.getParameter("tipeakun");
			String norekening = request.getParameter("norekening");
			int totalkue = Integer.parseInt("0");
			boolean result = hbaseUtils.updatePenjual(row, name, contact, address, username, password, filegbrakun,
					tglakundibuat, tipeakun, norekening, totalkue);
			if (result) {
				showAllData(request, response, hbaseUtils);
			} else {
				RequestDispatcher rd = request.getRequestDispatcher("/admin/error.jsp");
				rd.forward(request, response);
			}
		} else if ("kueInfo".equals(action)) {
			// TODO
			String penjualId = request.getParameter("idpenjual");
			List<Kue> kueList = hbaseUtils.getKueById(penjualId);
			request.setAttribute("kueList", kueList);
			request.setAttribute("idpenjual", penjualId);
			RequestDispatcher rd = request.getRequestDispatcher("/admin/daftarKue.jsp");
			rd.forward(request, response);
		} else if ("retrieve_all_kue".equals(action)) {
			showAllkue(request, response, hbaseUtils);
		} else if ("to_input_kue".equals(action)) {
			String penjualId = request.getParameter("idpenjual");
			request.setAttribute("idpenjual", penjualId);
			RequestDispatcher rd = request.getRequestDispatcher("/admin/InputDataKue.jsp");
			rd.forward(request, response);
		} else if ("insert_kue".equals(action)) {
			// TODO
			String idpenjual = request.getParameter("idpenjual");
			String namakue = request.getParameter("namakue");
			int jenis = Integer.parseInt(request.getParameter("jenis"));
			double harga = Double.parseDouble(request.getParameter("harga"));
			String gambarkue = upimg.getFileName(request.getPart("gambarkue"));
			Part filepart = request.getPart("gambarkue");
			upimg.imgKue(request, response, filepart);
			int physicalstock = Integer.parseInt(request.getParameter("physicalstock"));
			int availablestock = Integer.parseInt(request.getParameter("physicalstock"));
			String date = LocalDate.now().toString();
			String tglproduksi = request.getParameter("tglproduksi");
			String tglbaiksblm = request.getParameter("tglbaiksblm");
			String description = request.getParameter("deskripsi");
			int quantity = Integer.parseInt(request.getParameter("physicalstock"));
			int oldphysicalstock = 0;
			String tmp = request.getParameter("oldphysicalstock");
			if (tmp != null && !tmp.isEmpty()) {
				oldphysicalstock = Integer.parseInt(request.getParameter("oldphysicalstock"));
			}
			float berat = Float.parseFloat(request.getParameter("berat"));
			String deskripsi = request.getParameter("deskripsi");
			int layakjual = 1;
			// List<String> komentar = null;
			Kue kue = new Kue();
			kue.setIdpenjual(idpenjual);
			kue.setNamakue(namakue);
			kue.setHarga(harga);
			kue.setJenis(jenis);
			kue.setWaktudiinput(date);
			kue.setTglproduksi(tglproduksi);
			kue.setTglbaiksblm(tglbaiksblm);
			kue.setDeskripsi(description);
			kue.setAvailablestock(quantity);
			kue.setPhysicalstock(quantity);
			kue.setBerat(berat);
			kue.setGambarkue(upimg.getFileName(request.getPart("gambarkue")));
			kue.setLayakjual(layakjual);
			boolean result = hbaseUtils.insertDataKue(kue);
			if (result) {
				showAllData(request, response, hbaseUtils);
			} else {
				RequestDispatcher rd = request.getRequestDispatcher("/admin/error.jsp");
				rd.forward(request, response);
			}
		} else if ("delete_kue".equals(action)) {
			String id = request.getParameter("idkue");
			System.out.println("ROW DELETED = " + id);

			boolean result = hbaseUtils.deletekue(id);
			if (result) {
				showAllData(request, response, hbaseUtils);
			} else {
				RequestDispatcher rd = request.getRequestDispatcher("/admin/error.jsp");
				rd.forward(request, response);
			}
		} else if ("update_kue".equals(action)) {
			String idkue = request.getParameter("idkue");
			String idpenjual = request.getParameter("idpenjual");
			String namakue = request.getParameter("namakue");
			int jenis = Integer.parseInt(request.getParameter("jenis"));
			double harga = Double.parseDouble(request.getParameter("harga"));
			String gambarkue = request.getParameter("gambarkue");

			if (gambarkue != null && !gambarkue.isEmpty()) {
				Part filepart = request.getPart("gambarkue");
				if (filepart.getSize() != 0) {
					gambarkue = upimg.getFileName(request.getPart("gambarkue"));
					filepart = request.getPart("gambarkue");
					upimg.imgKue(request, response, filepart);
				} else {
					gambarkue = request.getParameter("photo");
				}
			}

			int physicalstock = Integer.parseInt(request.getParameter("physicalstock"));
			int availablestock = Integer.parseInt(request.getParameter("availablestock"));
			String date = LocalDate.now().toString();
			String tglproduksi = request.getParameter("tglproduksi");
			String tglbaiksblm = request.getParameter("tglbaiksblm");
			String description = request.getParameter("deskripsi");
			int quantity = Integer.parseInt(request.getParameter("physicalstock"));
			int oldphysicalstock = 0;
			String tmp = request.getParameter("oldphysicalstock");
			if (tmp != null && !tmp.isEmpty()) {
				oldphysicalstock = Integer.parseInt(request.getParameter("oldphysicalstock"));
			}
			float berat = Float.parseFloat(request.getParameter("berat"));
			String deskripsi = request.getParameter("deskripsi");
			int layakjual = Integer.parseInt(request.getParameter("layakjual"));
			// List<String> komentar = null;
			Kue kue = new Kue();
			kue.setIdpenjual(idpenjual);
			kue.setNamakue(namakue);
			kue.setHarga(harga);
			kue.setJenis(jenis);
			kue.setWaktudiinput(date);
			kue.setTglproduksi(tglproduksi);
			kue.setTglbaiksblm(tglbaiksblm);
			kue.setDeskripsi(description);
			kue.setAvailablestock(availablestock);
			kue.setPhysicalstock(quantity);
			kue.setBerat(berat);
			kue.setGambarkue(gambarkue);
			kue.setLayakjual(layakjual);
			request.setAttribute("idkue", idkue);
			request.setAttribute("kue", kue);
			request.setAttribute("jenisKue", jenis);
			request.getRequestDispatcher("/admin/editKueDetail.jsp").forward(request, response);
		} else if ("edit_kue".equals(action)) {
			String idkue = request.getParameter("idkue");
			String idpenjual = request.getParameter("idpenjual");
			String namakue = request.getParameter("namakue");
			int jenis = Integer.parseInt(request.getParameter("jenis"));
			double harga = Double.parseDouble(request.getParameter("harga"));
			String gambarkue = request.getParameter("gambarkue");

			if (gambarkue != null && !gambarkue.isEmpty()) {
				Part filepart = request.getPart("gambarkue");
				if (filepart.getSize() != 0) {
					gambarkue = upimg.getFileName(request.getPart("gambarkue"));
					filepart = request.getPart("gambarkue");
					upimg.imgKue(request, response, filepart);
				} else {
					gambarkue = request.getParameter("photo");
				}
			}
			int physicalstock = Integer.parseInt(request.getParameter("physicalstock"));
			int availablestock = Integer.parseInt(request.getParameter("availablestock"));
			String date = LocalDate.now().toString();
			String tglproduksi = request.getParameter("tglproduksi");
			String tglbaiksblm = request.getParameter("tglbaiksblm");
			String description = request.getParameter("deskripsi");
			int quantity = Integer.parseInt(request.getParameter("physicalstock"));
			int oldphysicalstock = 0;
			String tmp = request.getParameter("oldphysicalstock");
			if (tmp != null && !tmp.isEmpty()) {
				oldphysicalstock = Integer.parseInt(request.getParameter("oldphysicalstock"));
			}
			float berat = Float.parseFloat(request.getParameter("berat"));
			String deskripsi = request.getParameter("deskripsi");
			int layakjual = Integer.parseInt(request.getParameter("layakjual"));
			// List<String> komentar = null;
			Kue kue = new Kue();
			kue.setIdkue(idkue);
			kue.setIdpenjual(idpenjual);
			kue.setNamakue(namakue);
			kue.setHarga(harga);
			kue.setJenis(jenis);
			kue.setWaktudiinput(date);
			kue.setTglproduksi(tglproduksi);
			kue.setTglbaiksblm(tglbaiksblm);
			kue.setDeskripsi(description);
			kue.setAvailablestock(availablestock);
			kue.setPhysicalstock(quantity);
			kue.setBerat(berat);
			kue.setGambarkue(gambarkue);
			kue.setLayakjual(layakjual);
			boolean result = hbaseUtils.updatekue(kue, oldphysicalstock);
			if (result) {
				RequestDispatcher rd = request.getRequestDispatcher("/admin/daftarPenjual.jsp");
				rd.forward(request, response);
			} else {
				RequestDispatcher rd = request.getRequestDispatcher("/admin/error.jsp");
				rd.forward(request, response);
			}
		} else if ("tambah_komentar".equals(action)) {
			String kueid = request.getParameter("idkue");
			request.setAttribute("idkue", kueid);
			RequestDispatcher rd = request.getRequestDispatcher("/admin/InputDataKomentar.jsp");
			rd.forward(request, response);
		} else if ("insert_komentar".equals(action)) {
			String kueid = request.getParameter("idKue");
			String namaKomentar = request.getParameter("namaKomentar");
			int jumlah = Integer.parseInt(request.getParameter("jumlah"));
			System.out.println(kueid);
			System.out.println(jumlah);
			boolean result = hbaseUtils.insertKomentar(kueid, namaKomentar, jumlah);
			if (result) {
				showAllData(request, response, hbaseUtils);
			} else {
				RequestDispatcher rd = request.getRequestDispatcher("/admin/error.jsp");
				rd.forward(request, response);
			}
		} else if ("to_filter_penjual".equals(action)) {
			RequestDispatcher rd = request.getRequestDispatcher("/admin/FilterPenjual.jsp");
			rd.forward(request, response);
		} else if ("filter_jumlah_kue".equals(action)) {
			int jumlah = Integer.parseInt(request.getParameter("jumlahKue"));
			try {
				ArrayList<Penjual> listPenjual = hbaseUtils.getPenjualByJumlah(jumlah);
				request.setAttribute("info_penjual", listPenjual);
				request.getRequestDispatcher("/admin/daftarPenjual.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ("search_kue_menu".equals(action)) {
			RequestDispatcher rd = request.getRequestDispatcher("/admin/CariKue.jsp");
			rd.forward(request, response);
		} else if ("kue_by_berat".equals(action)) {
			RequestDispatcher rd = request.getRequestDispatcher("/admin/kueByBerat.jsp");
			rd.forward(request, response);
		} else if ("kue_by_harga".equals(action)) {
			RequestDispatcher rd = request.getRequestDispatcher("/admin/kueByHarga.jsp");
			rd.forward(request, response);
		} else if ("kue_by_komentar".equals(action)) {
			RequestDispatcher rd = request.getRequestDispatcher("/admin/kueByKomentar.jsp");
			rd.forward(request, response);
		} else if ("kue_by_jenis".equals(action)) {
			RequestDispatcher rd = request.getRequestDispatcher("/admin/kueByJenis.jsp");
			rd.forward(request, response);
		} else if ("kueByBerat".equals(action)) {
			float berat = Float.parseFloat(request.getParameter("berat"));
			try {
				List<Kue> kueList = hbaseUtils.getKueByBerat(berat);
				request.setAttribute("kueList", kueList);
				request.getRequestDispatcher("/admin/daftarKue.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ("kueByHarga".equals(action)) {
			int min = Integer.parseInt(request.getParameter("minHarga"));
			int max = Integer.parseInt(request.getParameter("maxHarga"));
			try {
				List<Kue> kueList = hbaseUtils.getKueByHarga(min, max);
				request.setAttribute("kueList", kueList);
				request.getRequestDispatcher("/admin/daftarKue.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ("kueByStuff".equals(action)) {
			String stuff = request.getParameter("stuff");
			String[] stuffList = stuff.split(",");
			System.out.println(stuff);
			try {
				List<Kue> kueList = hbaseUtils.getKueByKomentar(stuffList);
				request.setAttribute("kueList", kueList);
				request.getRequestDispatcher("/admin/daftarKue.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ("kueByJenis".equals(action)) {
			int jeniskue = Integer.parseInt(request.getParameter("jenis"));
			try {
				List<Kue> kueList = hbaseUtils.getKueByJenis(jeniskue);
				request.setAttribute("kueList", kueList);
				request.getRequestDispatcher("/admin/daftarKue.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void showAllData(HttpServletRequest request, HttpServletResponse response, HBaseUtils hbaseUtils) {
		try {
			ArrayList<Penjual> listPenjual = hbaseUtils.getPenjual();
			request.setAttribute("info_penjual", listPenjual);
			request.getRequestDispatcher("/admin/daftarPenjual.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showAllkue(HttpServletRequest request, HttpServletResponse response, HBaseUtils hbaseUtils) {
		try {
			ArrayList<Kue> listkue = hbaseUtils.getKue();
			request.setAttribute("kueList", listkue);
			request.getRequestDispatcher("/admin/daftarKue.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}