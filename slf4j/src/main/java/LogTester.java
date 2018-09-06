import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dexterleslie.Chan
 */
public class LogTester {
    private final static Logger loggerThisClass= LoggerFactory.getLogger(LogTester.class);
    private final static Logger loggerBusinessError=LoggerFactory.getLogger("logBusinessError");
    private final static Logger loggerUnexpectedException=LoggerFactory.getLogger("logUnexpectedException");

    public void logThisClass(){
        loggerThisClass.debug("Debug message from loggerThisClass");
    }

    public void logBusinessError(){
        loggerBusinessError.error("Error message from loggerBusinessError");
    }

    public void logUnexpectedException(){
        this.doLogUnexpectedException();
    }
    public void doLogUnexpectedException(){
        loggerUnexpectedException.error("Error message from loggerUnexpectedException");
    }
}
