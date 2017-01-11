package test.log4j.annotation;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;

/**
 * @author wenchao.meng
 *
 * Nov 1, 2016
 */
@SupportedAnnotationTypes("apache.logging.log4j.core.config.plugins.*")
public class MyAnnotationProcessor extends AbstractProcessor{
	
	public MyAnnotationProcessor() {

//        processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "process construct");
//
//		System.out.println("MyAnnotationProcessor constructor");
//		try(FileOutputStream fous = new FileOutputStream(new File("/opt/logs/testprocess.log"))){
//			fous.write("nihaoma".getBytes());
//			fous.write(new Date().toString().getBytes());
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		
//		System.out.println("process:" + annotations + "," + roundEnv);
		return true;
	}

}
