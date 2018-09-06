import org.junit.Test;

/**
 * @author Dexterleslie.Chan
 */
public class LogTesterTests {
    @Test
    public void test(){
        LogTester logTester=new LogTester();
        logTester.logThisClass();
        logTester.logBusinessError();
        logTester.logUnexpectedException();
    }
}
