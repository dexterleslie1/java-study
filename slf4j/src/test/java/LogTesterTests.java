import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dexterleslie.Chan
 */
public class LogTesterTests {
    private final static Logger logger = LoggerFactory.getLogger(LogTesterTests.class);

    @Test
    public void test(){
        LogTester logTester=new LogTester();
        logTester.logThisClass();
        logTester.logBusinessError();
        logTester.logUnexpectedException();
    }

    @Test
    public void test1() {
        Exception exception = new Exception("错误1");
        logger.error(exception.getMessage(), exception);
        exception = new Exception("错误2");
        logger.info(exception.getMessage(), exception);
    }
}
