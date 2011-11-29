package adp2.implementations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;


public class PermutationIteratorTest {
    
    private List<Integer> initialInts;
    private List<String>  initialStrs;
    private List<List<List<Integer>>> intPerms;
    private List<List<List<String>>>  strPerms;
    
    @SuppressWarnings("unchecked")
    @Before
    public void setup() {
        initialInts = asList(3, 0, 4, 1, 2);
        initialStrs = asList("hans", "peter", "egon", "emma", "erna");
        
        List<List<Integer>> emptyIntListList = asList();
        intPerms = asList(
            emptyIntListList,
            asList(asList(0)),
            asList(asList(0, 1), asList(1, 0)),
            asList(asList(0, 1, 2), asList(0, 2, 1), asList(1, 0, 2), asList(1, 2, 0), asList(2, 0, 1), asList(2, 1, 0)),
            asList(asList(0, 1, 2, 3), asList(0, 1, 3, 2), asList(0, 2, 1, 3), asList(0, 2, 3, 1), asList(0, 3, 1, 2), asList(0, 3, 2, 1), asList(1, 0, 2, 3), asList(1, 0, 3, 2), asList(1, 2, 0, 3), asList(1, 2, 3, 0), asList(1, 3, 0, 2), asList(1, 3, 2, 0), asList(2, 0, 1, 3), asList(2, 0, 3, 1), asList(2, 1, 0, 3), asList(2, 1, 3, 0), asList(2, 3, 0, 1), asList(2, 3, 1, 0), asList(3, 0, 1, 2), asList(3, 0, 2, 1), asList(3, 1, 0, 2), asList(3, 1, 2, 0), asList(3, 2, 0, 1), asList(3, 2, 1, 0)));

        List<List<String>> emptyStrListList = asList();
        strPerms = asList(
            emptyStrListList,
            asList(asList("egon")),
            asList(asList("egon", "emma"), asList("emma", "egon")),
            asList(asList("egon", "emma", "erna"), asList("egon", "erna", "emma"), asList("emma", "egon", "erna"), asList("emma", "erna", "egon"), asList("erna", "egon", "emma"), asList("erna", "emma", "egon")),
            asList(asList("egon", "emma", "erna", "hans"), asList("egon", "emma", "hans", "erna"), asList("egon", "erna", "emma", "hans"), asList("egon", "erna", "hans", "emma"), asList("egon", "hans", "emma", "erna"), asList("egon", "hans", "erna", "emma"), asList("emma", "egon", "erna", "hans"), asList("emma", "egon", "hans", "erna"), asList("emma", "erna", "egon", "hans"), asList("emma", "erna", "hans", "egon"), asList("emma", "hans", "egon", "erna"), asList("emma", "hans", "erna", "egon"), asList("erna", "egon", "emma", "hans"), asList("erna", "egon", "hans", "emma"), asList("erna", "emma", "egon", "hans"), asList("erna", "emma", "hans", "egon"), asList("erna", "hans", "egon", "emma"), asList("erna", "hans", "emma", "egon"), asList("hans", "egon", "emma", "erna"), asList("hans", "egon", "erna", "emma"), asList("hans", "emma", "egon", "erna"), asList("hans", "emma", "erna", "egon"), asList("hans", "erna", "egon", "emma"), asList("hans", "erna", "emma", "egon")),
            asList(asList("egon", "emma", "erna", "hans", "peter"), asList("egon", "emma", "erna", "peter", "hans"), asList("egon", "emma", "hans", "erna", "peter"), asList("egon", "emma", "hans", "peter", "erna"), asList("egon", "emma", "peter", "erna", "hans"), asList("egon", "emma", "peter", "hans", "erna"), asList("egon", "erna", "emma", "hans", "peter"), asList("egon", "erna", "emma", "peter", "hans"), asList("egon", "erna", "hans", "emma", "peter"), asList("egon", "erna", "hans", "peter", "emma"), asList("egon", "erna", "peter", "emma", "hans"), asList("egon", "erna", "peter", "hans", "emma"), asList("egon", "hans", "emma", "erna", "peter"), asList("egon", "hans", "emma", "peter", "erna"), asList("egon", "hans", "erna", "emma", "peter"), asList("egon", "hans", "erna", "peter", "emma"), asList("egon", "hans", "peter", "emma", "erna"), asList("egon", "hans", "peter", "erna", "emma"), asList("egon", "peter", "emma", "erna", "hans"), asList("egon", "peter", "emma", "hans", "erna"), asList("egon", "peter", "erna", "emma", "hans"), asList("egon", "peter", "erna", "hans", "emma"), asList("egon", "peter", "hans", "emma", "erna"), asList("egon", "peter", "hans", "erna", "emma"), asList("emma", "egon", "erna", "hans", "peter"), asList("emma", "egon", "erna", "peter", "hans"), asList("emma", "egon", "hans", "erna", "peter"), asList("emma", "egon", "hans", "peter", "erna"), asList("emma", "egon", "peter", "erna", "hans"), asList("emma", "egon", "peter", "hans", "erna"), asList("emma", "erna", "egon", "hans", "peter"), asList("emma", "erna", "egon", "peter", "hans"), asList("emma", "erna", "hans", "egon", "peter"), asList("emma", "erna", "hans", "peter", "egon"), asList("emma", "erna", "peter", "egon", "hans"), asList("emma", "erna", "peter", "hans", "egon"), asList("emma", "hans", "egon", "erna", "peter"), asList("emma", "hans", "egon", "peter", "erna"), asList("emma", "hans", "erna", "egon", "peter"), asList("emma", "hans", "erna", "peter", "egon"), asList("emma", "hans", "peter", "egon", "erna"), asList("emma", "hans", "peter", "erna", "egon"), asList("emma", "peter", "egon", "erna", "hans"), asList("emma", "peter", "egon", "hans", "erna"), asList("emma", "peter", "erna", "egon", "hans"), asList("emma", "peter", "erna", "hans", "egon"), asList("emma", "peter", "hans", "egon", "erna"), asList("emma", "peter", "hans", "erna", "egon"), asList("erna", "egon", "emma", "hans", "peter"), asList("erna", "egon", "emma", "peter", "hans"), asList("erna", "egon", "hans", "emma", "peter"), asList("erna", "egon", "hans", "peter", "emma"), asList("erna", "egon", "peter", "emma", "hans"), asList("erna", "egon", "peter", "hans", "emma"), asList("erna", "emma", "egon", "hans", "peter"), asList("erna", "emma", "egon", "peter", "hans"), asList("erna", "emma", "hans", "egon", "peter"), asList("erna", "emma", "hans", "peter", "egon"), asList("erna", "emma", "peter", "egon", "hans"), asList("erna", "emma", "peter", "hans", "egon"), asList("erna", "hans", "egon", "emma", "peter"), asList("erna", "hans", "egon", "peter", "emma"), asList("erna", "hans", "emma", "egon", "peter"), asList("erna", "hans", "emma", "peter", "egon"), asList("erna", "hans", "peter", "egon", "emma"), asList("erna", "hans", "peter", "emma", "egon"), asList("erna", "peter", "egon", "emma", "hans"), asList("erna", "peter", "egon", "hans", "emma"), asList("erna", "peter", "emma", "egon", "hans"), asList("erna", "peter", "emma", "hans", "egon"), asList("erna", "peter", "hans", "egon", "emma"), asList("erna", "peter", "hans", "emma", "egon"), asList("hans", "egon", "emma", "erna", "peter"), asList("hans", "egon", "emma", "peter", "erna"), asList("hans", "egon", "erna", "emma", "peter"), asList("hans", "egon", "erna", "peter", "emma"), asList("hans", "egon", "peter", "emma", "erna"), asList("hans", "egon", "peter", "erna", "emma"), asList("hans", "emma", "egon", "erna", "peter"), asList("hans", "emma", "egon", "peter", "erna"), asList("hans", "emma", "erna", "egon", "peter"), asList("hans", "emma", "erna", "peter", "egon"), asList("hans", "emma", "peter", "egon", "erna"), asList("hans", "emma", "peter", "erna", "egon"), asList("hans", "erna", "egon", "emma", "peter"), asList("hans", "erna", "egon", "peter", "emma"), asList("hans", "erna", "emma", "egon", "peter"), asList("hans", "erna", "emma", "peter", "egon"), asList("hans", "erna", "peter", "egon", "emma"), asList("hans", "erna", "peter", "emma", "egon"), asList("hans", "peter", "egon", "emma", "erna"), asList("hans", "peter", "egon", "erna", "emma"), asList("hans", "peter", "emma", "egon", "erna"), asList("hans", "peter", "emma", "erna", "egon"), asList("hans", "peter", "erna", "egon", "emma"), asList("hans", "peter", "erna", "emma", "egon"), asList("peter", "egon", "emma", "erna", "hans"), asList("peter", "egon", "emma", "hans", "erna"), asList("peter", "egon", "erna", "emma", "hans"), asList("peter", "egon", "erna", "hans", "emma"), asList("peter", "egon", "hans", "emma", "erna"), asList("peter", "egon", "hans", "erna", "emma"), asList("peter", "emma", "egon", "erna", "hans"), asList("peter", "emma", "egon", "hans", "erna"), asList("peter", "emma", "erna", "egon", "hans"), asList("peter", "emma", "erna", "hans", "egon"), asList("peter", "emma", "hans", "egon", "erna"), asList("peter", "emma", "hans", "erna", "egon"), asList("peter", "erna", "egon", "emma", "hans"), asList("peter", "erna", "egon", "hans", "emma"), asList("peter", "erna", "emma", "egon", "hans"), asList("peter", "erna", "emma", "hans", "egon"), asList("peter", "erna", "hans", "egon", "emma"), asList("peter", "erna", "hans", "emma", "egon"), asList("peter", "hans", "egon", "emma", "erna"), asList("peter", "hans", "egon", "erna", "emma"), asList("peter", "hans", "emma", "egon", "erna"), asList("peter", "hans", "emma", "erna", "egon"), asList("peter", "hans", "erna", "egon", "emma"), asList("peter", "hans", "erna", "emma", "egon")));
    }

    @Test
    public void testIntPermutations() {
        testTPermutations(initialInts, intPerms);
    }

    @Test
    public void testStrPermutations() {
        testTPermutations(initialStrs, strPerms);
    }
    
    private <T extends Comparable<? super T>> List<List<T>> permutationsList(List<T> input) {
        List<List<T>> res = new LinkedList<List<T>>();
        Iterator<List<T>> iter = Values.permutationIterator(input);
        while (iter.hasNext()) res.add(iter.next());
        return res;
    }
    
    private <T extends Comparable<? super T>> void testTPermutations(List<T> initials, List<List<List<T>>> perms) {
        List<T> sortedInitials = new ArrayList<T>(initials);
        Collections.sort(sortedInitials);
        
        for (int i = 0; i < sortedInitials.size(); ++i) {
            List<T> input = new LinkedList<T>();
            for (int j = 0; j < i; ++j) input.add(sortedInitials.get(j));
            
            List<List<T>> actual = permutationsList(input);
            
            assertEquals(perms.get(i), actual);
        }
    }
}
