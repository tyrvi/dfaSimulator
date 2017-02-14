import java.util.Arrays;

final public class Tuple<T> {
    private final T[] contents;

    public Tuple (T[] contents) {
        if (contents.length != 2)
            throw new IllegalArgumentException();
        this.contents = contents;
    }

    public T[] getContents () {
        return this.contents.clone();
    }

    @Override
    public int hashCode () {
        return Arrays.deepHashCode(this.contents);
    }

	@Override
    public boolean equals (Tuple other) {
        return Arrays.deepEquals(this.contents, other.getContents());
    }

    @Override
    public String toString () {
        return Arrays.deepToString(this.contents);
    }
}
