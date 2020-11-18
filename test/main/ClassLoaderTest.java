package main;

import org.junit.jupiter.api.Test;

public class ClassLoaderTest {


    @Test
    public void test1() throws ClassNotFoundException {
        Class<?> aClass = ClassLoader.getSystemClassLoader().loadClass("com.sayit.ui.control.frame.SearchContactController");
        System.out.println(aClass);
    }
}
