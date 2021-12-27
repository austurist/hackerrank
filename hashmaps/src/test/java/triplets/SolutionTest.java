package triplets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @BeforeEach
    void setUp() {
        InputStream resource = SolutionTest.class.getResourceAsStream("SolutionTest_6.txt");
        System.setIn(resource);
    }

    @Test
    void main() throws IOException {
        Solution.main(null);
    }
}