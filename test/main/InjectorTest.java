package main;

import com.sayit.di.Autowired;
import com.sayit.di.Injector;
import org.junit.jupiter.api.Test;

public class InjectorTest {

    static class Te{
        @Autowired
        private String a;

        private int b;

        public void p(){
            System.out.println(a);
        }
    }


    @Test
    public void test(){
        Injector.registerProvider(String.class, "asc");
        Te te = new Te();
        try {
            Injector.inject(te);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        te.p();

    }

}
