package showcase.dw.glbg.canal.util;
import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

  
public class DecodeingKafka implements Deserializer<Object> {  
  
    @Override  
    public void configure(Map<String, ?> configs, boolean isKey) {  
    }  
  
    @Override  
    public Object deserialize(String topic, byte[] data) {  
        return BeanUtils.byte2Obj(data);  
    }  
  
    @Override  
    public void close() {  
          
    }  
}  
