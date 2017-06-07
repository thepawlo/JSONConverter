import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;


/**
 * Created by JIT on 05.06.2017.
 */
public class JSONConverter {

    private StringBuilder sb = new StringBuilder();

    public String convertIntoJSON(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        int fieldsLength = fields.length;
        sb.append("{");
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                if (f.getType().isPrimitive()) {
                    sb.append('\"' + f.getName() + "\":" + f.get(obj).toString());
                } else if (f.getType() == String.class || f.getType() == Enum.class) {
                    sb.append('\"' + f.getName() + "\":\"" + f.get(obj).toString() + '\"');
                } else if (f.getType().isArray()) {
                    sb.append('\"' + f.getName() + "\":[");
                    int arrayLength = Array.getLength(f.get(obj));
                    for (int i = 0; i < arrayLength; i++) {
                        sb.append(Array.get(f.get(obj), i));
                        if (i != arrayLength - 1) {
                            sb.append(',');
                        }
                    }
                    sb.append(']');
                } else if (Collection.class.isAssignableFrom(f.getType())) {
                    sb.append('\"' + f.getName() + "\":[");
                    Collection collection = (Collection) f.get(obj);
                    int collectionSize = Array.getLength(collection.toArray());
                    for (int i = 0; i < collectionSize; i++) {
                        sb.append("\""+Array.get(collection.toArray(), i)+"\"");
                        if (i != collectionSize - 1) {
                            sb.append(',');
                        }
                    }
                    sb.append(']');
                } else {
                    sb.append('\"' + f.getName() + "\":");
                    JSONConverter jsonConverter = new JSONConverter();
                    sb.append(jsonConverter.convertIntoJSON(f.get(obj)));
                }
                //add ',' if it isn't the last field of converted object
                if (f != fields[fieldsLength - 1] || !f.equals(fields[fieldsLength - 1])) {
                    sb.append(',');
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        sb.append("}");
        return sb.toString();
    }
}


//    private void convertArrayToJSON(Field field, Object convertedObject){
//        try {
//        if(Collection.class.isAssignableFrom(field.getType())){
//            Collection collection = (Collection) field.get(convertedObject);
//
//        }else {
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }

