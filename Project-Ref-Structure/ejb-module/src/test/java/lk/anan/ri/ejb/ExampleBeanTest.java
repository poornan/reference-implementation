package lk.anan.ri.ejb;

import org.apache.openejb.testing.Module;
import org.junit.jupiter.api.Test;

import jakarta.ejb.EJB;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.openejb.jee.EjbJar;
import org.apache.openejb.jee.StatelessBean;
import org.apache.openejb.junit5.RunWithApplicationComposer;

@RunWithApplicationComposer
public class ExampleBeanTest {

    @EJB
    private ExampleBean exampleBean;

    @Module
    public EjbJar beans() {
        EjbJar ejbJar = new EjbJar("Example-Bean");
        ejbJar.addEnterpriseBean(new StatelessBean(ExampleBean.class));
        return ejbJar;
    }

    @Test
    public void sayHello() {
        String result = exampleBean.sayHello("Ananth");
        assertEquals("Hello, Ananth!", result);
    }
}