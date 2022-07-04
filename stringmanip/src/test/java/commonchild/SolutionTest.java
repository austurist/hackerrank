package commonchild;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

class SolutionTest {

    @BeforeEach
    void setUp() {
        InputStream resource = SolutionTest.class.getResourceAsStream("SolutionTest_1.txt");
        System.setIn(resource);
    }

    @Test
    void main() throws IOException {
        Solution.main(null);
    }
}