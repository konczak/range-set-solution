package pl.konczak.rangeset;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class RangeSet
        implements Set<Integer> {

    private static final RangeComparator RANGE_COMPARATOR = new RangeComparator();

    private List<Range> ranges = new ArrayList<>();

    @Override
    public int size() {
        return ranges.size();
    }

    @Override
    public boolean isEmpty() {
        return ranges.isEmpty();
    }

    @Override
    public boolean contains(final Object o) {
        int number = (int) o;

        return this.ranges
                .stream()
                .anyMatch(range -> range.hasInside(number));
    }

    @Override
    public Iterator<Integer> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(final T[] a) {
        return null;
    }

    @Override
    public boolean add(final Integer integer) {
        if (this.contains(integer)) {
            return false;
        }

        this.ranges.add(new Range(integer));

        this.ranges = this.calculateUniqueRanges(this.ranges);

        return true;
    }

    private List<Range> calculateUniqueRanges(final List<Range> ranges) {
        if (ranges.isEmpty() || ranges.size() == 1) {
            return ranges;
        }

        Collections.sort(ranges, RANGE_COMPARATOR);

        List<Range> uniqueRanges = new ArrayList<>();

        Range actualRange = ranges.get(0);

        for (Range range : ranges) {
            if (actualRange.isIntersectWith(range)) {
                actualRange = actualRange.merge(range);
            } else {
                uniqueRanges.add(actualRange);
                actualRange = range;
            }
        }
        uniqueRanges.add(actualRange);
        return uniqueRanges;
    }

    @Override
    public boolean remove(final Object o) {
        if (!this.contains(o)) {
            return false;
        }

        int number = (int) o;

        Range range = this.ranges.stream()
                .filter(r -> r.hasInside(number))
                .findFirst().get();

        this.ranges.remove(range);

        if (range.isSingleElementRange()) {
            //do nothing - removal of single element range is just enough
        } else if (range.isSimpleReduceable(number)) {
            range = range.reduce(number);
            this.ranges.add(range);
        } else {
            int min = range.getMin();
            int max = range.getMax();

            Range a = new Range(min, number - 1);
            Range b = new Range(number + 1, max);

            this.ranges.add(a);
            this.ranges.add(b);
        }

        Collections.sort(this.ranges, RANGE_COMPARATOR);

        return true;
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(final Collection<? extends Integer> c) {
        return false;
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        this.ranges.clear();
    }

    public void addAll(final int min, final int max) {
        Range range = new Range(min, max);

        this.ranges.add(range);

        this.ranges = calculateUniqueRanges(this.ranges);
    }
}
