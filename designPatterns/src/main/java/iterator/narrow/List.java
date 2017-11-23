package iterator.narrow;

/**
 * Created by youzhihao on 2017/11/23.
 */
public class List<T> implements Collection<T> {

    private Object[] object;

    public List(Object[] object) {
        this.object = object;
    }

    @Override
    public Iterator<T> createIterator() {
        return new ListIterator<T>(this);
    }

    private class ListIterator<T> implements Iterator<T> {

        private int index = 0;

        private Collection collection;

        public ListIterator(Collection collection) {
            this.collection = collection;
        }

        @Override
        public boolean hasNext() {
            return index < object.length;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T next() {
            return (T) object[index++];
        }
    }
}
