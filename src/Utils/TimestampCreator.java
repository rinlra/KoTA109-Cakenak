package Utils;

public class TimestampCreator {
	
	private long unixTime = System.currentTimeMillis() / 1000L;
	
	public long getCurTmstmp () {
		return unixTime;
	}
	
}
