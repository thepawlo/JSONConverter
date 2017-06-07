/**
 * Created by JIT on 05.06.2017.
 */

public class MyObject {

    private int id;
    private boolean status;
    private String name;
    private AnotherObject anotherObject;



    public MyObject(int id, boolean status) {
        this.id = id;
        this.status = status;
        this.name="object";
        this.anotherObject = new AnotherObject(2,"amazing object");

    }
}
