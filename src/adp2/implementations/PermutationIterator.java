package adp2.implementations;

import static java.util.Collections.sort;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class PermutationIterator<E extends Comparable<? super E>> implements Iterator<List<E>> {
    List<E> permutation;
    List<E> lastPermutation;
    boolean isFirstPermutation;
    
    public PermutationIterator(List<E> l) {
        permutation = first_permutation(l);
        lastPermutation = last_permutation(l);
        isFirstPermutation = true;
    }

    @Override
    public boolean hasNext() {
        return !permutation.equals(lastPermutation);
    }

    @Override
    public List<E> next() {
        if (isFirstPermutation) {
            isFirstPermutation = false;
        } else {
            next_permutation_(permutation);
        }
        return new ArrayList<E>(permutation);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
    
    
    private static <E extends Comparable<? super E>> List<E> first_permutation(List<E> l) {
        if (l == null) return new ArrayList<E>();
        List<E> res = new ArrayList<E>(l);
        first_permutation_(res);
        return res;
    }
    
    private static <E extends Comparable<? super E>> List<E> last_permutation(List<E> l) {
        if (l == null) return new ArrayList<E>();
        List<E> res = new ArrayList<E>(l);
        sort(res, java.util.Collections.reverseOrder());
        return res;
    }
    
    private static <E extends Comparable<? super E>> List<E> next_permutation(List<E> l) {
        if (l == null) return new ArrayList<E>();
        List<E> res = new ArrayList<E>(l);
        next_permutation_(res);
        return res;
    }
        
    private static <E extends Comparable<? super E>> void first_permutation_(List<E> l) {
        sort(l);
    }
    
    
    // http://en.wikipedia.org/wiki/Permutation#Generation_in_lexicographic_order
    private static <E extends Comparable<? super E>> void next_permutation_(List<E> list) {
        if (list == null) {
            return;
        }

        // Find the largest index k such that a[k] < a[k + 1]. If no such index exists, the permutation is the last permutation.
        int k = -1;
        for (int i = list.size()-2; i >= 0; --i) {
            if (list.get(i) != null && list.get(i+1) != null && list.get(i).compareTo(list.get(i+1)) < 0) {
                k = i;
                break;
            }
        }
        if (k == -1) return; // last permutation
        
        // Find the largest index l such that a[k] < a[l]. Since k + 1 is such an index, l is well defined and satisfies k < l.
        int l = k+1;
        for (int i = list.size()-1; i > k+1; --i) {
            if (list.get(i) != null && list.get(k).compareTo(list.get(i)) < 0) {
                l = i;
                break;
            }
        }
        
        // Swap a[k] with a[l].
        swap(k, l, list);
        
        // Reverse the sequence from a[k + 1] up to and including the final element a[n].
        for (int i = k+1, j = list.size()-1; i < j; ++i, --j)
            swap(i, j, list);
    }
    
    private static <E> void swap(int i, int j, List<E> l) {
        if (l != null) {
            E tmp = l.get(i);
            l.set(i, l.get(j));
            l.set(j, tmp);
        }
    }

}