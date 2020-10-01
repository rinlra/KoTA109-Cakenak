package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Penjual {
	
	private String idpenjual;
	private String namapenjual;
	private String notelp;
	private String alamat;
	private String email;
	private String username;
	private String password;
	private String filegbrakun;
	private String tglakundibuat;
	private String tipeakun;
	private String norekening;
	private double pendapatan;
	private int totalkue;
	private int statusverifikasi;
	
	public Penjual(String namapenjual, String notelp, String alamat, String username, String password,
			String filegbrakun, String tglakundibuat, String tipeakun, String norekening, int totalkue, double pendapatan,
			int statusverifikasi) {
		super();
		this.namapenjual = namapenjual;
		this.notelp = notelp;
		this.alamat = alamat;
		this.username = username;
		this.password = password;
		this.filegbrakun = filegbrakun;
		this.tglakundibuat = tglakundibuat;
		this.tipeakun = tipeakun;
		this.norekening = norekening;
		this.totalkue = totalkue;
		this.statusverifikasi = statusverifikasi;
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
