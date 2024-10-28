package lk.anan.ri.service;

import javax.ejb.Stateless;

@Stateless
public class MyEjb implements MyEjbInterface {

    @Override
    public String hello(String name) {
        return "Hello " + name + " from EJB!";
    }
}
