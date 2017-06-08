import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by JIT on 05.06.2017.
 */
public class Test {

    public static void main(String args[])  {

        MyObject myObject = new MyObject(1, true );
        JSONConverter jsonConverter = new JSONConverter();
        String resultJSON = jsonConverter.toJson(true);
        System.out.println("myConv= "+resultJSON);

        MyObject[] objects = {new MyObject(3,true),new MyObject(4, false)};
        int[] numbers = {1,2,3};
        List<MyObject> list = new ArrayList<>();
        list.add(new MyObject(4,true));
        list.add(new MyObject(546, false));
        List<String> list2 = new ArrayList<>();
        list2.add("dfsdf");
        list2.add("sad");
        Gson gson = new Gson();
        System.out.println("gson=   "+gson.toJson(false));

        String resultdsfdfJSON = jsonConverter.toJson(myObject);
        System.out.println(resultdsfdfJSON);
    }

}
