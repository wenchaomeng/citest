package simpleclass;

import java.io.File;

/**
 * @author wenchao.meng
 *
 * Aug 11, 2016
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		print();
	}

	public static void print(){
		System.out.println("hello world");
		System.out.println(Main.class.getClassLoader().getResource("log4j2.xml"));
		System.out.println(Main.class.getClassLoader().getResource("f.out"));
		System.out.println(Main.class.getClassLoader().getResource("f.out"));
		System.out.println(new File("f.out").exists());
		System.out.println(new File("f.out").getAbsolutePath());
		System.out.println(System.getProperty("java.class.path"));
	}

}
