package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartItem {

   private String idcart;
   private String idpembeli;
   private int quantity;
   private String nama;
   private double harga;
   private String model;
   private String date;
   private String gambar;
   private String deskripsi;
   private int quantity_kue;
   private String idkue;
   private int kategori;
   
   public CartItem(String idpembeli, int quantity, String nama, double harga, String gambar, String deskripsi, String idkue) {
		super();
		this.idpembeli = idpembeli;
		this.quantity = quantity;
		this.nama = nama;
		this.harga = harga;
		this.gambar = gambar;
		this.deskripsi = deskripsi;
		this.idkue = idkue;
   }

	@Override
	public String toString() {
		return "CartItem [idcart=" + idcart + ", idpembeli=" + idpembeli + ", quantity=" + quantity + ", nama=" + nama
				+ ", harga=" + harga + ", model=" + model + ", date=" + date + ", gambar=" + gambar + ", deskripsi="
				+ deskripsi + ", quantity_kue=" + quantity_kue + ", idkue=" + idkue + ", kategori=" + kategori + "]";
	}
   
}
