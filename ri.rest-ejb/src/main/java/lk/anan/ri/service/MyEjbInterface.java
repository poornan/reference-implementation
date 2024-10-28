package lk.anan.ri.service;

import javax.ejb.Local;

@Local
public interface MyEjbInterface {
    String hello(String name);
}
