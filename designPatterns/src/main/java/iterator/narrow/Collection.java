package iterator.narrow;

/**
 * Created by youzhihao on 2017/11/23.
 */
public interface Collection<T> {

    Iterator<T> createIterator();

}
