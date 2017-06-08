import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JIT on 05.06.2017.
 */
public class AnotherObject {

    private int id;
    private String describe;
    String[] arr;
    private List<NextObject> list;
    private Map<Object, NextObject> map;
    private NextObject nextObject;

    public AnotherObject(int id, String describe) {
        this.id = id;
        this.describe = describe;
        this.arr = new String[]{"dsfdsfsf","sdfdsfdsfsfdsdf"};
        this.list=new ArrayList<NextObject>();
        list.add(new NextObject());

        this.map=new HashMap<>();
        map.put(new Object(),new NextObject());
        this.nextObject = null;


    }
}
