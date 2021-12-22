package twostrings;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

class SolutionTest {

    @BeforeEach
    void setUp() {
        InputStream resource = SolutionTest.class.getResourceAsStream("SolutionTest_0.txt");
        System.setIn(resource);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void main() throws IOException {
        Solution.main(null);
    }
}