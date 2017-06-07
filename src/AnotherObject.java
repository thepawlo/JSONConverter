import java.util.ArrayList;
import java.util.List;

/**
 * Created by JIT on 05.06.2017.
 */
public class AnotherObject {

    private int id;
    private String describe;
    int[] arr;
    private List<String> list;

    public AnotherObject(int id, String describe) {
        this.id = id;
        this.describe = describe;
        this.arr = new int[]{12,3};
        this.list=new ArrayList<String>();
        list.add("raz");
        list.add("dwa");

    }
}
