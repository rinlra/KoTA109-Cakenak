package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pembeli implements Cloneable{
	
	private String idpembeli;
	private String nama;
	private String alamat;
	private String kota;
	private String provinsi;
	private String kodepos;
	private String notelp;
	private String username;
	private String password;
	private String email;
	private String idcart;
	private String idtransaksi;
	private String filegbrakun;
	private String tglakundibuat;
	private String tipeakun;
	private String norekening;
	private double cash;
	private int totalkuedibeli;
	
	public Pembeli() {

	}

	public Pembeli(String nama, String alamat, String kota, String provinsi, String kodepos, String notelp,
			String username, String password, String email, String idcart, String idtransaksi, String filegbrakun,
			String tglakundibuat, String tipeakun, String norekening, double cash, int totalkuedibeli) {
		super();
		this.nama = nama;
		this.alamat = alamat;
		this.kota = kota;
		this.provinsi = provinsi;
		this.kodepos = kodepos;
		this.notelp = notelp;
		this.username = username;
		this.password = password;
		this.email = email;
		this.idcart = idcart;
		this.idtransaksi = idtransaksi;
		this.filegbrakun = filegbrakun;
		this.tglakundibuat = tglakundibuat;
		this.tipeakun = tipeakun;
		this.norekening = norekening;
		this.cash = cash;
		this.totalkuedibeli = totalkuedibeli;
	}
	
	@Override
    public Object clone()throws CloneNotSupportedException{  
        return super.clone();  
    }  

    @Override
    public String toString() {
        return "name : " + username  +"\n role" + tipeakun ;
    }
	
}
