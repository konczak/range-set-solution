package pl.konczak.rangeset;

public class Main {
    public static void main(String[] args) {
        RangeSet set = new RangeSet();
        set.add(1);
        set.add(3);
        set.addAll(5, 1000);
        set.remove(3);
        set.remove(300);
        set.addAll(500, 2000);

        set.addAll(5000, 5002);
        set.remove(5001);

        try {
            verify(set);

            System.out.println("Success!");
        } catch (AssertionError e) {
            System.err.println("Failure!");
            e.printStackTrace();
        }
    }

    private static void verify(final RangeSet set) {
        assert set.contains(1);
        assert !set.contains(2);
        assert !set.contains(3);
        assert !set.contains(4);
        assert set.contains(5);
        assert set.contains(250);
        assert set.contains(299);
        assert !set.contains(300);
        assert set.contains(301);
        assert set.contains(1000);
        assert set.contains(1001);
        assert set.contains(5000);
        assert !set.contains(5001);
        assert set.contains(5002);
    }
}
