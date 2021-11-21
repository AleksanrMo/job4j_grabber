import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.is;

class TestCodeCovTest {

    @Test
    void testSum() {
        TestCodeCov cov = new TestCodeCov(6, 4);
        int rsl = 10;
        MatcherAssert.assertThat(rsl, is(cov.sum()));
    }
}