package showcase.dw.glbg.canal;


import java.util.Properties;

import dto.TransDTO;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class KafkaProducer_{
	String topic;
	public KafkaProducer_(String topics){
		super();
		this.topic=topics;
	}

	public static void sendMsg(String topic, String sendKey, TransDTO data){
		 Producer producer = createProducer();
		 producer.send(new KeyedMessage<Integer, Object>("test",data ));
	}

	  private static Producer createProducer(){
	        Properties prop = new Properties();
	        //声明zk
	        prop.put("zookeeper.connect", "localhost:2181");
	        // 指定message的序列化方法，用户可以通过实现kafka.serializer.Encoder接口自定义该类
	        // 默认情况下message的key和value都用相同的序列化，但是可以使用"key.serializer.class"指定key的序列化
	        prop.put("serializer.class", TransDTO.class);
	        // broker的地址
	        prop.put("metadata.broker.list", "localhost:9092");
	        // 这个参数用于通知broker接收到message后是否向producer发送确认信号
	        //  0 - 表示producer不用等待任何确认信号，会一直发送消息，否则producer进入等待状态
	        // -1 - 表示leader状态的replica需要等待所有in-sync状态的replica都接收到消息后才会向producer发送确认信号，再次之前producer一直处于等待状态
	        prop.put("request.required.acks", "1");
	        
	        prop.put("producer.type", "async");
	        prop.put("batch.num.messages", "5");
	        return new Producer(new ProducerConfig(prop));
	    }
}
