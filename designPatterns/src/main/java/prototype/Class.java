package prototype;

/**
 * Created by youzhihao on 2017/3/4.
 * 班级类
 */
public class Class implements Cloneable {

    //班级名
    private String className;

    //班级所属年级
    private Integer classGrade;

    private Student[] students;


    @Override
    protected Object clone() {
        Class classCopy = null;
        try {
            classCopy = (Class) super.clone();
            if (this.students != null) {
                //这个是浅层复制，只复制数组的引用
                //classCopy.students=this.students.clone();
                //进行深层复制
                classCopy.students = new Student[this.students.length];
                for (int i = 0; i < this.students.length; i++) {
                    classCopy.students[i] = (Student) this.students[i].clone();
                }
            }
        } catch (CloneNotSupportedException e) {
        }
        return classCopy;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getClassGrade() {
        return classGrade;
    }

    public void setClassGrade(Integer classGrade) {
        this.classGrade = classGrade;
    }

    public Student[] getStudents() {
        return students;
    }

    public void setStudents(Student[] students) {
        this.students = students;
    }
}
