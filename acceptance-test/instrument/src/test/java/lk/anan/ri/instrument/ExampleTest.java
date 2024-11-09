package lk.anan.ri.instrument;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class ExampleTest {

    @Test
    public void testDataDumpHealperClass() {
        assertEquals("DataDumpHealper", DataDumpHealper.class.getSimpleName(),
                "DataDumpHealper class should be available");
    }

    // @Test
    // public void testDataDumpHealperHasMethodCreateDump() {
    // try {
    // dataDumpHealper.getClass().getMethod("createDump");
    // } catch (NoSuchMethodException e) {
    // throw new AssertionError("Method createDump should be available in
    // DataDumpHealper class");
    // }
    // }

    @Test
    public void testDataDumpHealperHasMethodCreateDump1() {
        DataDumpHealper.createDump(new DataImpl(), UUID.randomUUID().toString());
    }

    @Test
    public void testDataDumpHealperHasMethodCreateDumpParentFolder() {
        DataDumpHealper.createDump(new DataImpl(), UUID.randomUUID().toString());
        assertTrue(Files.exists(Paths.get("target" + "/dump")), "Dump folder should exist");
    }

    @Test
    public void testDataDumpHealperHasMethodCreateDumpRootFolder() {
        DataDumpHealper.root = "target/";
        DataDumpHealper.createDump(new DataImpl(), UUID.randomUUID().toString());
        assertTrue(Files.exists(Paths.get("target/" + "target" + "/dump")), "root folder should exist");
    }

    @Test
    public void testDataDumpHealperHasMethodCreateDumpXmlFile() throws Exception {
        DataDumpHealper.createDump(new DataImpl(), UUID.randomUUID().toString());
        assertTrue(Files.walk(Paths.get("target" + "/dump"))
                .filter(Files::isRegularFile)
                .anyMatch(path -> path.toString().endsWith(".xml")),
                "There should be a file with .xml extension in the dump folder");
    }

    @Test
    public void testDataDumpHealperCreatesXmlFileWithContent() throws Exception {
        String uuid = UUID.randomUUID().toString();
        DataDumpHealper.createDump(new DataImpl(), uuid);
        assertTrue(Files.walk(Paths.get("target" + "/dump/" + DataImpl.class.getSimpleName() + "/" + uuid))
                .filter(Files::isRegularFile)
                .anyMatch(path -> {
                    if (path.toString().endsWith(".xml")) {
                        try {
                            String content = new String(Files.readAllBytes(path));
                            return content.contains("lk.anan.ri.instrument.DataImpl");
                        } catch (Exception e) {
                            return false;
                        }
                    }
                    return false;
                }),
                "There should be a file with .xml extension in the dump folder containing XML content");
    }

}
