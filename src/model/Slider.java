package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Slider {

    private String id;
    private String gambar;
    private String title;
    private String subtitle;
    private String deskripsi;
    private String idkue;
    
	public Slider(String gambar, String title, String subtitle, String deskripsi, String idkue) {
		super();
		this.gambar = gambar;
		this.title = title;
		this.subtitle = subtitle;
		this.deskripsi = deskripsi;
		this.idkue = idkue;
	}  
    
}
