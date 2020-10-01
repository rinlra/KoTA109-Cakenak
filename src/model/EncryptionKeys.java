package model;

import javax.crypto.SecretKey;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EncryptionKeys {
	
	
	
	private long timestamp;
	private SecretKey aeskey;
	
	
	public EncryptionKeys(long timestamp, SecretKey aeskey) {
		super();
		this.timestamp = timestamp;
		this.aeskey = aeskey;
	}
	
}
