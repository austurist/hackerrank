package comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @BeforeEach
    void setUp() {
        InputStream resource = SolutionTest.class.getResourceAsStream("SolutionTest_0.txt");
        System.setIn(resource);
    }

    @Test
    void main() {
        Solution.main(null);
    }
}