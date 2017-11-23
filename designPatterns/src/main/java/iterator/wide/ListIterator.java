package iterator.wide;

/**
 * Created by youzhihao on 2017/11/23.
 */
public class ListIterator<T> implements Iterator<T> {

    private int index = 0;

    private Collection collection;

    public ListIterator(Collection collection) {
        this.collection = collection;
    }

    @Override
    public boolean hasNext() {
        return index < collection.size();
    }

    @Override
    public T next() {
        return (T) collection.get(index++);
    }
}
