package simpleclass;

/**
 * @author wenchao.meng
 *
 * Aug 11, 2016
 */
public class Message<T extends Enum<T>>{
	
	T  state;
	
	
	public T getState() {
		return state;
	}
	
	
	

}
