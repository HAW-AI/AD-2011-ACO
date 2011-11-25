package adp2.implementations;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    PermutationIteratorTest.class,
    MatrixImplTest.class,
    BruteForceTSPTest.class,
    PathImplTest.class
})
public final class TestSuite {}
