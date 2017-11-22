package composite;

/**
 * Created by youzhihao on 2017/11/22.
 * 组合模式：将对象组合成树形结构以表示"部分-整体"的层次结构。
 * 组合模式使得用户对单个对象和组合对象的使用据用一致性
 *
 * demo:文件系统，
 * 文件：相当于叶子节点
 * 目录：相当于枝干节点
 */
public class Run {

    public static void main(String[] args) {
        System.out.println("~~~~~~~~~~~~~~~~~");
        createSingleFile("目录");
        System.out.println("~~~~~~~~~~~~~~~~~");
        createSingleDir("文件");
        System.out.println("~~~~~~~~~~~~~~~~~");
        createAll();

    }

    //文件可以单独创建
    public static void createSingleFile(String dirName) {
        DirComponent component = new DirComponent(dirName);
        component.create();
    }

    //目录也可以单独创建
    public static void createSingleDir(String fileName) {
        FileComponent component = new FileComponent(fileName);
        component.create();
    }

    //组合起来同样可以创建，符合:组合模式使得用户对单个对象和组合对象的使用据用一致性的原则
    public static void createAll() {
        DirComponent root = new DirComponent("dir");
        FileComponent file1 = new FileComponent("dir.file1");
        DirComponent dir1 = new DirComponent("dir.dir1");
        DirComponent dir2 = new DirComponent("dir.dir2");
        FileComponent file2 = new FileComponent("dir.dir2.file");
        root.add(dir1);
        root.add(dir2);
        dir2.add(file2);
        root.add(file1);
        root.create();

    }

}
