package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Pembayaran {

	private String idpembayaran;
	private String tglpembayaran;
	private double totalpembayaran;
	private int statuspembayaran;
	private String idtransaksi;
	
	public Pembayaran(String tglpembayaran, double totalpembayaran, int statuspembayaran, String idtransaksi) {
		super();
		this.tglpembayaran = tglpembayaran;
		this.totalpembayaran = totalpembayaran;
		this.statuspembayaran = statuspembayaran;
		this.idtransaksi = idtransaksi;
	}
	
}
