
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * Created by JIT on 05.06.2017.
 */
public class JSONConverter {


    private static final Class[] PRIMITIVES = new Class[]{Integer.class, Double.class, Boolean.class,
                                                        Byte.class,Character.class, Short.class, Long.class, Float.class};

    private StringBuilder sb;
    private StringBuilder result;
    private Object objectToConvert;
    private Field[] fields;

    public String toJson(Object convertedObject) {
        if (convertedObject == null) {
            return "null";
        }
        sb = new StringBuilder();
        result = new StringBuilder();
        objectToConvert = convertedObject;
        String resultJson;
        fields = objectToConvert.getClass().getDeclaredFields();
        if (Map.class.isAssignableFrom(objectToConvert.getClass())) {
            resultJson = convertMapToJson();
        } else if (Collection.class.isAssignableFrom(objectToConvert.getClass())) {
            resultJson = convertCollectionToJson();
        } else if (objectToConvert.getClass().isArray()) {
            resultJson = convertArrayToJson();
        } else if (String.class.isAssignableFrom(objectToConvert.getClass())) {
            resultJson = convertStringToJson();
        } else if (checkPrimitive(objectToConvert.getClass())) {
            resultJson = convertPrimitiveToJson();
        } else {
            resultJson = convertObjectToJson();
        }
        return resultJson;
    }

    private boolean checkPrimitive(Class<?> cls){
        for(Class cs : PRIMITIVES){
            if(cs==cls){
                return true;
            }
        }
        return false;
    }


    private String convertPrimitiveToJson() {
        result.append(objectToConvert.toString());
        return result.toString();
    }

    private String convertStringToJson() {
        result.append("\"" + objectToConvert.toString() + "\"");
        return result.toString();
    }

    private String convertObjectToJson() {
        result.append("{" + convertToJson() + "}");
        return result.toString();
    }

    private String convertArrayToJson() {
        result.append("[" + convertToJson() + "]");
        return result.toString();
    }

    private String convertCollectionToJson() {
        result.append("[" + convertToJson() + "]");
        return result.toString();
    }

    private String convertMapToJson() {
        result.append("{" + convertToJson() + "}");
        return result.toString();
    }

    private String convertToJson() {
        int fieldsLength = fields.length;
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                if (f.get(objectToConvert) != null) {
                    if (f.getType().isPrimitive()) {
                        primitiveConverter(f);
                    } else if (f.getType() == String.class || f.getType() == Enum.class) {
                        stringOrEnumConverter(f);
                    } else if (f.getType().isArray()) {
                        arrayConverter(f);
                    } else if (Collection.class.isAssignableFrom(f.getType())) {
                        collectionConverter(f);
                    } else if (Map.class.isAssignableFrom(f.getType())) {
                        mapConverter(f);
                    } else {
                        objectConverter(f);
                    }
                }else{
                    sb.deleteCharAt(sb.length()-1);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            //add ',' if it isn't the last field of converted object
            if (f != fields[fieldsLength - 1] || !f.equals(fields[fieldsLength - 1])) {
                sb.append(',');
            }
        }
        return sb.toString();
    }

    private void primitiveConverter(Field field) {
        try {
            sb.append('\"' + field.getName() + "\":" + field.get(objectToConvert).toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void stringOrEnumConverter(Field field) {
        try {
            sb.append('\"' + field.getName() + "\":\"" + field.get(objectToConvert).toString() + '\"');
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void arrayConverter(Field field) {
        try {
            sb.append('\"' + field.getName() + "\":[");
            int arrayLength = Array.getLength(field.get(objectToConvert));
            for (int i = 0; i < arrayLength; i++) {
                if (Array.get(field.get(objectToConvert), i).getClass().isPrimitive()) {
                    sb.append(Array.get(field.get(objectToConvert), i));
                } else {
                    JSONConverter jsonConverter = new JSONConverter();
                    sb.append(jsonConverter.toJson(Array.get(field.get(objectToConvert), i)));
                }
                if (i != arrayLength - 1) {
                    sb.append(',');
                }
            }
            sb.append(']');
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void collectionConverter(Field field) {
        try {
            sb.append('\"' + field.getName() + "\":[");
            Collection collection = (Collection) field.get(objectToConvert);
            int collectionSize = Array.getLength(collection.toArray());
            for (int i = 0; i < collectionSize; i++) {
                JSONConverter jsonConverter = new JSONConverter();
                sb.append(jsonConverter.toJson(Array.get(collection.toArray(), i)));
                if (i != collectionSize - 1) {
                    sb.append(',');
                }
            }
            sb.append(']');
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void mapConverter(Field field) {
        try {
            sb.append('\"' + field.getName() + "\":{");
            Map<?, ?> map = (Map) field.get(objectToConvert);
            JSONConverter jsonConverter = new JSONConverter();
            for (Map.Entry pair : map.entrySet()) {
                sb.append("\"" + pair.getKey().toString() + "\":");
                if (pair.getValue().getClass().isPrimitive() || pair.getValue().getClass() == String.class) {
                    sb.append("\"" + pair.getValue() + "\",");
                } else {
                    sb.append(jsonConverter.toJson(pair.getValue()) + ",");
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append('}');
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void objectConverter(Field field) {
        try {
            sb.append('\"' + field.getName() + "\":");
            JSONConverter jsonConverter = new JSONConverter();
            sb.append(jsonConverter.toJson(field.get(objectToConvert)));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}




