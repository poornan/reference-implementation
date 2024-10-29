package lk.anan.ri.service;

import lk.anan.ri.util.Stackscanner;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class MyEjb implements MyEjbInterface {
    @EJB
    SimpleEjb ejb;
    @Override
    public String hello(String name) {
        Stackscanner.getCaller();
        ejb.calUtil();
        return "Hello " + name + " from EJB!";
    }
}
