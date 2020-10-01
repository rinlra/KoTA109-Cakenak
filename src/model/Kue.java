package model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Kue {
	String idkue;
	String idpenjual;
	String namakue;
	int jenis;
	double harga;
	String gambarkue;
	int physicalstock;
	int availablestock;
	float berat;
	String deskripsi;
	String tglproduksi;
	String tglbaiksblm;
	String waktudiinput;
	int layakjual;
	//List<Komentar> komentar;
	
	public Kue(String idpenjual, String namakue, int jenis, double harga, String gambarkue,
			int physicalstock, int availablestock, float berat, String deskripsi, int layakjual) {
		super();
		this.idpenjual = idpenjual;
		this.namakue = namakue;
		this.jenis = jenis;
		this.harga = harga;
		this.gambarkue = gambarkue;
		this.physicalstock = physicalstock;
		this.availablestock = availablestock;
		this.berat = berat;
		this.deskripsi = deskripsi;
		this.layakjual = layakjual;
		//this.komentar = komentar;
	}

	public String getIdkue() {
		return idkue;
	}

	public void setIdkue(String idkue) {
		this.idkue = idkue;
	}

	public String getIdpenjual() {
		return idpenjual;
	}

	public void setIdpenjual(String idpenjual) {
		this.idpenjual = idpenjual;
	}

	public String getNamakue() {
		return namakue;
	}

	public void setNamakue(String namakue) {
		this.namakue = namakue;
	}

	public int getJenis() {
		return jenis;
	}

	public void setJenis(int jenis) {
		this.jenis = jenis;
	}

	public double getHarga() {
		return harga;
	}

	public void setHarga(double harga) {
		this.harga = harga;
	}

	public String getGambarkue() {
		return gambarkue;
	}

	public void setGambarkue(String gambarkue) {
		this.gambarkue = gambarkue;
	}

	public int getPhysicalstock() {
		synchronized(this) {
		return physicalstock;
		}
	}

	public void setPhysicalstock(int physicalstock) {
		synchronized(this) {
		this.physicalstock = physicalstock;
		}
	}

	public int getAvailablestock() {
		synchronized(this) {
		return availablestock;
		}
	}

	public void setAvailablestock(int availablestock) {
		synchronized(this) {
		this.availablestock = availablestock;
		}
	}

	public Float getBerat() {
		return berat;
	}

	public void setBerat(Float berat) {
		this.berat = berat;
	}

	public String getDeskripsi() {
		return deskripsi;
	}

	public void setDeskripsi(String deskripsi) {
		this.deskripsi = deskripsi;
	}

	public String getTglproduksi() {
		return tglproduksi;
	}

	public void setTglproduksi(String tglproduksi) {
		this.tglproduksi = tglproduksi;
	}

	public String getTglbaiksblm() {
		return tglbaiksblm;
	}

	public void setTglbaiksblm(String tglbaiksblm) {
		this.tglbaiksblm = tglbaiksblm;
	}

	public String getWaktudiinput() {
		return waktudiinput;
	}

	public void setWaktudiinput(String waktudiinput) {
		this.waktudiinput = waktudiinput;
	}

	public int getLayakjual() {
		return layakjual;
	}

	public void setLayakjual(int layakjual) {
		this.layakjual = layakjual;
	}

	public void setBerat(float berat) {
		this.berat = berat;
	}
	
}