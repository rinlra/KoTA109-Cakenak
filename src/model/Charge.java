package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Charge {

	private String idcharge;
	private String nomorkartu;
    private int value;
    private int used;
    private int taken;
    
	public Charge(String norekening, int value, int used, int taken) {
		super();
		this.nomorkartu = norekening;
		this.value = value;
		this.used = used;
		this.taken = taken;
	}
	
}
