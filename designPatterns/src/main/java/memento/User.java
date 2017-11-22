package memento;

/**
 * Created by youzhihao on 2017/11/22.
 */
public class User {
  private int age;

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
  public void show()
  {
    System.out.println(String.format("当前年龄为:%d",this.age));
  }

  public MementoIF createMemento() {
    return new UserMemento(age);
  }

  public void loadMemento(MementoIF mementoIF) {
    if (mementoIF instanceof UserMemento) {
      UserMemento memento = (UserMemento) mementoIF;
      this.age = memento.getAge();
    } else {
      throw new IllegalArgumentException("参数不对");
    }
  }

  //用私有内部类，来防止外部对象修改存储的备忘录内部状态
  private class UserMemento implements MementoIF {
    private int age;

    public UserMemento(int age) {
      this.age = age;
    }

    public int getAge() {
      return age;
    }

    public void setAge(int age) {
      this.age = age;
    }
  }
}
