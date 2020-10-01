package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransaksiDetail {

	private String idtransaksi;
	private String namakue;
	private int quantity;
	private double harga;
	private double subtotal;
	
	public TransaksiDetail(String idtransaksi, String namakue, int quantity, double harga, double subtotal) {
		super();
		this.idtransaksi = idtransaksi;
		this.namakue = namakue;
		this.quantity = quantity;
		this.harga = harga;
		this.subtotal = subtotal;
	}
	
}
