package lk.anan.ri.instrument;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.thoughtworks.xstream.XStream;

public class DataDumpHealper {
    public static String root = "";

    public static void createDump(Data data, String uuid) {
  
        try {
            Path filePath = createDumpFile(data, uuid);
            XStream xstream = new XStream();
            String xml = xstream.toXML(data);
            Files.write(filePath, xml.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Path createDumpFile(Data data, String uuid) throws IOException {
        Path dumpDir = Paths.get(root + "target" +"/dump/" + data.getClass().getSimpleName() + "/" + uuid);
        if (!Files.exists(dumpDir)) {
            Files.createDirectories(dumpDir);
        }
        String fileName = data.getClass().getSimpleName() + ".xml";
        Path filePath = Paths.get(dumpDir.toString(), fileName);
        Files.createFile(filePath);
        return filePath;
    }

}