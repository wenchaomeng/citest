package jdk.gc;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;

public class Test {
	
	
  public static void main(String[] args) throws Exception {
	  
    ImmutableSet<ClassPath.ClassInfo> classes = ClassPath.from(ClassLoader.getSystemClassLoader())
      .getAllClasses();
    int result = 0;
    for (ClassPath.ClassInfo c : classes) {
      try {
        Class<?> cls = c.load();
        result += cls.getName().length();
      } catch (NoClassDefFoundError e) {
      }
    }
    System.out.println(result);
  }
}