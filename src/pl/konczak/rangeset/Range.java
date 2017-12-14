package pl.konczak.rangeset;

public class Range {

    private final int min;

    private final int max;

    public Range(final int singleElementRange) {
        this.min = singleElementRange;
        this.max = singleElementRange;
    }

    public Range(final int min, final int max) {
        assert min <= max;
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public boolean hasInside(final int number) {
        return number == min || number == max || (number > min && number < max);
    }

    public boolean isNextTo(final int number) {
        return this.isNextToMin(number) || this.isNextToMax(number);
    }

    public boolean isNextToMin(final int number) {
        return number - 1 == max;
    }

    public boolean isNextToMax(final int number) {
        return number + 1 == min;
    }

    public Range merge(final int number) {
        if (this.hasInside(number)) {
            return this;
        }

        if (this.isNextToMin(number)) {
            return new Range(number, this.max);
        }

        if (this.isNextToMax(number)) {
            return new Range(this.min, number);
        }

        throw new IllegalArgumentException("It is impossible to merge " + number + " with range " + this.min + " " + this.max);
    }

    public boolean isIntersectWith(final Range range) {
        if (this.min == range.max
                || this.max == range.min) {
            return true;
        }

        return this.min < range.max && this.max > range.min;
    }

    public Range merge(final Range range) {
        assert this.isIntersectWith(range) : "Ranges have to intersect";

        int minFrom = range.getMin();
        int maxFrom = range.getMax();

        if (this.min < minFrom) {
            minFrom = this.min;
        }

        if (this.max > maxFrom) {
            maxFrom = this.max;
        }

        return new Range(minFrom, maxFrom);
    }

    public boolean isBeforeRange(final int number) {
        return number < this.min;
    }

    public boolean isAfterRange(final int number) {
        return number > this.max;
    }

    /**
     * If number is min or max returns reduced Range.
     * If min == max reduced value is null.
     * In other case NULL because simple reduction isn't possible.
     *
     * @param number
     * @return
     */
    public Range reduce(final int number) {
        if (!isSimpleReduceable(number)) {
            return null;
        }

        if (number == this.getMin()) {
            return new Range(number + 1, this.max);
        }
        if (number == this.getMax()) {
            return new Range(this.min, number - 1);
        }

        //never in here?
        return null;
    }

    public boolean isSimpleReduceable(final int number) {
        if (this.isSingleElementRange()) {
            return false;
        }

        return number == this.getMin() || number == this.getMax();
    }

    public boolean isSingleElementRange() {
        return this.min == this.max;
    }

    @Override
    public String toString() {
        return "Range{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }
}
