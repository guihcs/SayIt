package com.sayit.di;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Injector {

    private static final Map<Class<?>, Object> providerMap = new HashMap<>();


    public static void registerProvider(Class<?> type, Object provider) {
        providerMap.put(type, provider);
    }

    public static Object getProvider(Class<?> type) {
        return providerMap.get(type);
    }

    public static void inject(Object object) throws IllegalAccessException {

        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            Autowired annotation = field.getAnnotation(Autowired.class);
            if (annotation != null) {
                field.setAccessible(true);
                field.set(object, getProvider(field.getType()));
            }
        }
    }
}
