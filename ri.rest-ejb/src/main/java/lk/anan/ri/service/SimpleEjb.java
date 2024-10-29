package lk.anan.ri.service;

import lk.anan.ri.util.Stackscanner;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local
public class SimpleEjb {
    public void calUtil(){
        Stackscanner.getCaller();
    }
}
