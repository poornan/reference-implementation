package lk.anan.ri.instrument;

import java.util.HashMap;
import java.util.Map;

public class DataImpl implements Data {
    Map<String, String> data = new HashMap<>();

    public DataImpl() {
        data.put("key1", "value1");
        data.put("key2", "value2");
        data.put("key3", "value3");

    }
}
