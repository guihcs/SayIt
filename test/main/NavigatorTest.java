package main;

import com.sayit.ui.navigator.Navigator;
import org.junit.jupiter.api.Test;

public class NavigatorTest {


    @Test
    public void t1(){
        Navigator.loadFrom("routes/routes.json");
    }

}
