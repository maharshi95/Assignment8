package pkg;

import java.lang.instrument.Instrumentation;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by maharshigor on 20/07/16.
 */

public class PreMain {

	private static final Logger log = Logger.getLogger (PreMain.class.getName ());

	public static void premain(String agentArgument, Instrumentation instrumentation){
		log.setLevel (Level.INFO);
		log.info ("Hey, I am a JavaAgent...");
		SimpleClassTransformer transformer = new SimpleClassTransformer();
		instrumentation.addTransformer(transformer);
		log.info ("I just added a SimpleClassTransformer to the instrumentation object");
	}

}
