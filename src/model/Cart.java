package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cart {
	
    private String idcart;
    private String idpembeli;
    private String idkue;
    private int quantity;

    public Cart() {
    }
    
     public Cart(String cartId)
    {
        this.idcart = cartId;
    }

	public Cart(String idpembeli, String idkue, int quantity) {
		super();
		this.idpembeli = idpembeli;
		this.idkue = idkue;
		this.quantity = quantity;
	}
     
}
