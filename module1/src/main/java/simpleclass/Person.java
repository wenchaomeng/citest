package simpleclass;

/**
 * @author wenchao.meng
 *
 * Jul 29, 2016
 */
public class Person implements PersonInterface{
	
	private String name;
	private int age;

	public Person(){}
	
	public Person(String name, int age){
		this.name = name;
		this.age = age;
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	

}
