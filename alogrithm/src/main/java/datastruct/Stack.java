package datastruct;

/**
 *
 * @author youzhihao
 * 逻辑数据结构: 栈
 * 数组实现
 */
public class Stack {
   private int[] array;
   private int   size;

   public Stack(int capacity) {
      this.array = new int[capacity];
   }

   /**
    * 入栈
    */
   public void push(int element) {
      array[size++] = element;
      if (size > array.length) {
         resize();
      }
   }

   /**
    * 出栈
    */
   public int pop() {
      if (size <= 0) {
         throw new RuntimeException("栈空");
      }
      return array[--size];
   }

   /**
    *扩容
    */
   private void resize() {
      int[] arrayNew = new int[array.length * 2];
      System.arraycopy(array, 0, arrayNew, 0, array.length);
      array = arrayNew;
   }

   //测试
   public static void main(String[] args) {
      Stack stack = new Stack(10);
      for (int i = 0; i < 10; i++) {
         stack.push(i);
      }
      for (int i = 0; i < 10; i++) {
         System.out.println(stack.pop());
      }
   }
}
