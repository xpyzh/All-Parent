import java.util.NoSuchElementException;

/**
 * 链表
 * Created by youzhihao on 2019/2/19.
 */
public class Test1 {

    public static void main(String[] args) {
        CustomLinkedList<String> linkedList=new CustomLinkedList<>();
        linkedList.add("A");
        linkedList.add("B");
        linkedList.add("C");
        linkedList.add("D");
        linkedList.remove();

    }


    static class CustomLinkedList<E> {

        private Node<E> first;

        private Node<E> last;

        private int size;

        public CustomLinkedList() {
        }

        public boolean add(E e) {
            final Node<E> l = last;
            final Node<E> newNode = new Node<>(l, e, null);
            last = newNode;
            if (l == null)
                first = newNode;
            else
                l.next = newNode;
            size++;
            return true;
        }

        public E remove() {
            final Node<E> f = first;
            if (f == null)
                throw new NoSuchElementException();
            final E element = f.item;
            final Node<E> next = f.next;
            first = next;
            if (next == null)
                last = null;
            else
                next.prev = null;
            size--;
            return element;
        }


       static class Node<E> {

            private E item;

            private Node<E> next;

            private Node<E> prev;

            Node(Node<E> prev, E element, Node<E> next) {
                this.item = element;
                this.next = next;
                this.prev = prev;
            }
        }
    }
}
