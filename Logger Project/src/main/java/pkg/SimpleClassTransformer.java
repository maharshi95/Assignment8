package pkg;

import javassist.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by maharshigor on 20/07/16.
 */
public class SimpleClassTransformer implements ClassFileTransformer {

	private static final Logger log = Logger.getLogger (PreMain.class.getName ());
	static {
		log.setLevel (Level.INFO);
		log.info ("JavaAgent again, logging from SimpleClassTransformer");
	}

	private static String getSysOutStmt(Object o) {
		return "System.out.println(\"" + o.toString () + " \");";
	}

	public byte[] transform(ClassLoader loader,
							String className,
							Class<?> classBeingRedefined,
							ProtectionDomain protectionDomain,
							byte[] classfileBuffer) throws IllegalClassFormatException {
		ClassPool classPool = ClassPool.getDefault ();
//		log.info ("\n\nLoading class "+className);
		byte[] byteCode = classfileBuffer.clone ();
		if(className.contains("SortBox")) {
			log.info ("Loaded...... class "+className + "\n\n");

			try {
				CtClass ctClass = classPool.makeClass (new ByteArrayInputStream (classfileBuffer));
				CtMethod[] ctMethods = ctClass.getDeclaredMethods ( );
				CtField[] ctMembers = ctClass.getFields ( );
				for (CtMethod method : ctMethods) {
					log.info ("\n method call: " + method.getLongName ( ));
					log.info ("info: " + method.getMethodInfo ( ));

					String entryLog = getSysOutStmt ("Method entry: " + method.getLongName ());
					entryLog += "\n{for (int i=0; i < $args.length; i++) {System.out.print(\"\" + $args[i] + \" \");}}";
					entryLog += getSysOutStmt ("");

					method.insertBefore (entryLog);

					String exitLog = getSysOutStmt ("Method returns " + method.getReturnType ());

					method.insertAfter (exitLog);

					log.info ("exit Log:\n" + exitLog);
				}
				log.info ("All good here :D");
				byteCode = ctClass.toBytecode ();
				ctClass.detach ();
			} catch (IOException e) {
				e.printStackTrace ( );
			} catch (NotFoundException e) {
				e.printStackTrace ( );
			} catch (CannotCompileException e) {
				e.printStackTrace ( );
			}
		}
		return byteCode;
	}
}
