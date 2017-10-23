package showcase.dw.glbg.canal;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import showcase.dw.glbg.canal.dto.TransDTO;
import showcase.dw.glbg.canal.util.BeanUtils;

/**   
* @Title: cc.java 
* @Package  
* @Description: TODO(description) 
* @author gongxuesong@globalegrow.com   
* @date 2017年10月7日 上午11:22:44   
*/
public class KafkaConsumer_ implements Runnable {

	public void run() {
		// TODO Auto-generated method stub
		try{
        //构建consumer对象
        ConsumerConnector consumer =createConsumer();
        //构建一个map对象，代表topic-------String：topic名称，Integer：从这个topic中获取多少条记录
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        //每次获取1条记录
        topicCountMap.put("test", 1);
        //构造一个messageStreams：输入流      --String：topic名称，List获取的数据
        Map<String, List<KafkaStream<byte[], byte[]>>> messageStreams = consumer.createMessageStreams(topicCountMap);
        //获取每次接收到的具体数据
        KafkaStream<byte[], byte[]> stream = messageStreams.get("test").get(0);
        ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
        while(iterator.hasNext()){
            byte[] data = iterator.next().message();
            ByteArrayInputStream ba = new ByteArrayInputStream(data);
            ObjectInputStream os = new ObjectInputStream(ba);
            TransDTO entity = (TransDTO) BeanUtils.byte2Obj(iterator.next().message());
            
            consumer.commitOffsets();
            System.out.println("ConsumerA接收到的数据：" + entity.getTableName());
        }
	   }catch(Exception e){
           System.out.println(e.getMessage());
       }
       
	}
	 private ConsumerConnector createConsumer(){
	        Properties prop = new Properties();
	        //声明zk
	        prop.put("zookeeper.connect", "localhost:2181");
	        //指定这个consumer的消费组,每个组只能获取一次消息
	        prop.put("group.id", "group1");
	        //smallest和largest(默认)
	        //此配置参数表示当此groupId下的消费者,在ZK中没有offset值时(比如新的groupId,或者是zk数据被清空),
	        //consumer应该从哪个offset开始消费.
	        //largest表示接受接收最大的offset(即最新消息),
	        //smallest表示最小offset,即从topic的开始位置消费所有消息.
	        prop.put("auto.offset.reset", "smallest");
	        return Consumer.createJavaConsumerConnector(new ConsumerConfig(prop));
	    }
	    
}
