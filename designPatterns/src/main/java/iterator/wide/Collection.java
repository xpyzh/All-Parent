package iterator.wide;

/**
 * Created by youzhihao on 2017/11/23.
 */
public interface Collection<T> {

    Iterator<T> createIterator();

    T get(int index);

    int size();
}
