package iterator.wide;

/**
 * Created by youzhihao on 2017/11/23.
 */
public class List<T> implements Collection<T> {

    private Object[] object;

    public List(Object[] object) {
        this.object = object;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index > object.length) {
            throw new IllegalArgumentException();
        }
        return (T) object[index];
    }

    public int size() {
        return this.object.length;
    }

    @Override
    public Iterator<T> createIterator() {
        return new ListIterator<T>(this);
    }
}
