package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Transaksi {

	private String idtransaksi;
    private String norekening;
    private String idpembeli;
    private String idkue;
    private String idpengiriman;
    private String tglpembelian;
    private String foto;
    private double totaltransaksi;
    private int Quantity;
    private int statussellerpaid;
    private int statustransaksi;

    
	public Transaksi(String norekening, String idpengiriman, String tglpembelian, String foto, double totaltransaksi,
			int statussellerpaid, int statustransaction) {
		super();
		this.norekening = norekening;
		this.idpengiriman = idpengiriman;
		this.tglpembelian = tglpembelian;
		this.foto = foto;
		this.totaltransaksi = totaltransaksi;
		this.statussellerpaid = statussellerpaid;
		this.statustransaksi = statustransaction;
	}
	 
}
