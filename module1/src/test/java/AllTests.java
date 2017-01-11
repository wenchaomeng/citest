
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.vc.go.file.FileSizeTest;
import org.vc.go.file.TruncateTest;

/**
 * @author wenchao.meng
 *
 * Nov 8, 2016
 */
@RunWith(Suite.class)
@SuiteClasses({
	FileSizeTest.class,
	TruncateTest.class
})
public class AllTests {

}
