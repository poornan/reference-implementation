package lk.anan.ri.ejb;

import javax.ejb.Stateless;

@Stateless
public class ExampleBean {

    public String sayHello(String name) {
        return "Hello, " + name + "!";
    }
}