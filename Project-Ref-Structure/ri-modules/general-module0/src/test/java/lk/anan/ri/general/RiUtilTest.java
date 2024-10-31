package lk.anan.ri.general;


import org.junit.jupiter.api.Test;
import lombok.extern.slf4j.Slf4j;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class RiUtilTest {

    @Test
    public void sayHello() {
        
        String result = RiUtil.sayHello("Ananth");
        assertEquals("Hello, Ananth!", result);
        log.info(result);
    }
}
