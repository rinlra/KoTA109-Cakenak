package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Pengiriman {

	private String idpengiriman;
	private String jenispengiriman;
	private double biayapengiriman;
	private String idregion;
	private String idtransaksi;
	
	public Pengiriman(String jenispengiriman, double biayapengiriman, String idregion, String idtransaksi) {
		super();
		this.jenispengiriman = jenispengiriman;
		this.biayapengiriman = biayapengiriman;
		this.idregion = idregion;
		this.idtransaksi = idtransaksi;
	}
	
}
