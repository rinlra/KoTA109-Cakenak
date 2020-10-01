package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Admin {

	private String id;
	private String email;
	private String username;
	private String password;
	private String nama;
	private String tipeakun;
	private String filegbrakun;
	private double revenue;
	
	public Admin() {
		
	}
	
	public Admin(String username, String password, String nama, String tipeakun, double revenue) {
		super();
		this.username = username;
		this.password = password;
		this.nama = nama;
		this.tipeakun = tipeakun;
		this.revenue = revenue;
	}
	
	@Override
    public Object clone()throws CloneNotSupportedException{  
        return super.clone();  
    }
	
}
