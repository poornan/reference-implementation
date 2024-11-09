package lk.anan.ri.instrument;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

public class ExampleTest {
    DataDumpHealper dataDumpHealper = new DataDumpHealper();

    @Test
    public void testDataDumpHealperClass() {
        assertEquals("DataDumpHealper", dataDumpHealper.getClass().getSimpleName(),
                "DataDumpHealper class should be available");
    }

    @Test
    public void testDataDumpHealperHasMethodCreateDump() {
        try {
            dataDumpHealper.getClass().getMethod("createDump");
        } catch (NoSuchMethodException e) {
            throw new AssertionError("Method createDump should be available in DataDumpHealper class");
        }
    }


    @Test
    public void testDataDumpHealperHasMethodCreateDump1() {
        assertThrows(UnsupportedOperationException.class, ()->{
            dataDumpHealper.createDump(new DataImpl(), UUID.randomUUID().toString());
        });
    }
}
