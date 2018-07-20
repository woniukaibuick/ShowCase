package valar.showcase.canal;
/**   
* @Title: UnitTest.java 
* @Package  
* @Description: TODO(description) 
* @author gongxuesong@globalegrow.com   
* @date 2017年10月7日 上午11:33:21 
* jar exception: https://my.oschina.net/u/134474/blog/778762  
*/
public class UnitTest {
	
	public static void main(String[] args) throws Exception {
		Thread canalClientThread  = new Thread(new CanalClient());
		canalClientThread.start();
		Thread kafkaConsumerThread  = new Thread(new KafkaConsumer_());
		kafkaConsumerThread.start();
		
		
		
//		HbaseHelper helper= new HbaseHelper("test");
////	   helper.testInsertData("arr[0]", "arr[1]");
//		helper.truncateTable();
		

	}

}
