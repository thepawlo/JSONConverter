import java.lang.reflect.Field;


/**
 * Created by JIT on 05.06.2017.
 */
public class JSONConverter {

    public String convertIntoJSON(Object obj)  {
        StringBuilder sb = new StringBuilder();
        Field[] fields = obj.getClass().getDeclaredFields();
        int fieldsLength = fields.length;
        sb.append("{");
        for (Field f : fields){
            f.setAccessible(true);
            try {
                if (f.getType().isPrimitive()) {
                    sb.append('\"' + f.getName() + "\": " + f.get(obj).toString());
                } else if(f.getType()==String.class || f.getType()==Enum.class) {
                    sb.append('\"' + f.getName() + "\": \"" + f.get(obj).toString()+'\"');
                }else if(f.getType().isArray()){
                    sb.append('\"' + f.getName() + "\": [");
                    //int arrayLength = Array.getLength(f.get(obj));
                    Field[] fieldArray = (Field[]) f.get(obj);
                    for(int i = 0; i<fieldArray.length;i++){
                        sb.append(fieldArray[i]);
                    }
                }else{
                    sb.append('\"' + f.getName() + "\": ");
                    JSONConverter jsonConverter = new JSONConverter();
                    sb.append(jsonConverter.convertIntoJSON(f.get(obj)));
                }
                //add ',' if it isn't the last field of converted object
                if (f != fields[fieldsLength - 1] || !f.equals(fields[fieldsLength - 1])) {
                    sb.append(',');
                }
            }catch (IllegalAccessException e){
                e.printStackTrace();
            }

        }
        sb.append("}");
        return sb.toString();
    }
}
