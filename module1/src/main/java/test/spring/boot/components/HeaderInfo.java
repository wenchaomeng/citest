package test.spring.boot.components;


/**
 * @author wenchao.meng
 *
 * Aug 3, 2016
 */
public class HeaderInfo {
	
	private HeaderType type;
	private int  from;

	public HeaderInfo() {
		System.out.println("HeaderInfo....");
		
	}
	public HeaderInfo(HeaderType type, int from) {
	}

	
	public HeaderType getType() {
		return type;
	}

	public void setType(HeaderType type) {
		this.type = type;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}
	
}
