import com.google.gson.Gson;

/**
 * Created by JIT on 05.06.2017.
 */
public class Test {

    public static void main(String args[])  {

        MyObject myObject = new MyObject(1, true );
        JSONConverter jsonConverter = new JSONConverter();
        String resultJSON = jsonConverter.convertIntoJSON(myObject);
        System.out.println("myConv= "+resultJSON);

        Gson gson = new Gson();
        System.out.println("gson=   "+gson.toJson(myObject));

    }

}
