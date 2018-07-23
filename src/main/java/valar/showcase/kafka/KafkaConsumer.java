package valar.showcase.kafka;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

/**   
* @Title: cc.java 
* @Package  
* @Description: TODO(description) 
* @author gong_xuesong@126.com
* @date 2017年10月8日 上午11:22:44   
*/
public class KafkaConsumer implements Runnable {

	public void run() {
		// TODO Auto-generated method stub
		Properties props = new Properties();
        props.put("auto.offset.reset", "smallest"); //必须要加，如果要读旧数据
         props.put("zookeeper.connect", "localhost:2181");
        props.put("group.id", "pv");
        props.put("zookeeper.session.timeout.ms", "400");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");
        
        ConsumerConfig conf = new ConsumerConfig(props);
        ConsumerConnector consumer = kafka.consumer.Consumer.createJavaConsumerConnector(conf);
        String topic = "test";
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, new Integer(1));
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);
        
        KafkaStream<byte[], byte[]> stream = streams.get(0); 
        ConsumerIterator<byte[], byte[]> it = stream.iterator();
        while (it.hasNext()){
        	String msg = new String(it.next().message());
            System.out.println("Kafka Consumer receiveed message: " + msg);
        }
        
        if (consumer != null) consumer.shutdown();   //其实执行不到，因为上面的hasNext会block
	}
}
