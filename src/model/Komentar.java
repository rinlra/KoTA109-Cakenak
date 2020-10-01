package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Komentar {
	private String namaKomentar;
	private int jumlah;
	
	public Komentar(String nama, int jumlah) {
		this.namaKomentar = nama;
		this.jumlah = jumlah;
	}
	
	public String getNamaKomentar() {
		return namaKomentar;
	}
	public void setNamaKomentar(String namaKomentar) {
		this.namaKomentar = namaKomentar;
	}

	public int getJumlah() {
		return jumlah;
	}

	public void setJumlah(int jumlah) {
		this.jumlah = jumlah;
	}
}