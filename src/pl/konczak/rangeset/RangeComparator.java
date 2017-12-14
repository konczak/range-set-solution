package pl.konczak.rangeset;

import java.util.Comparator;

public class RangeComparator
        implements Comparator<Range> {


    @Override
    public int compare(final Range range1, final Range range2) {
        if (range1.getMin() == range2.getMin()
                && range1.getMax() == range2.getMax()) {
            return 0;
        }

        if (range1.getMin() < range2.getMin()) {
            return -1;
        }

        if (range1.getMin() == range2.getMin()
                && range1.getMax() < range2.getMax()) {
            return -1;
        }

        return 1;
    }
}
