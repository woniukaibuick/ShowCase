package showcase.dw.glbg.canal;


import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.alibaba.otter.canal.protocol.Message;

import dto.HbaseEntity;
import dto.TransDTO;


/**
 * Created by Michael on 2016/8/17.
 */
public class CanalClient implements Runnable{

    private static void printEntry(List<Entry> entrys) {
        for (Entry entry : entrys) {
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }

            RowChange rowChage = null;
            try {
                rowChage = RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }

            EventType eventType = rowChage.getEventType();
            System.out.println(String.format("Canal Listener:================> binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));
/*            "================> binlog[mysql-bin.000005:442] , name[test,user] , eventType : INSERT\n" +
                    "name : success    update=false\n" +
                    "age : 23    update=false"*/
            System.out.println("Canal Listener Row Change SQL:"+rowChage.getSql());
            System.out.println("Canal Listener rowChage.getRowDatasCount():"+rowChage.getRowDatasCount());
            System.out.println("Canal Listener RowDatasList isEmpty:"+rowChage.getRowDatasList().isEmpty());
            
            TransDTO transDTO = new TransDTO();
            transDTO.setEventType(eventType);
            transDTO.setSchemaName(entry.getHeader().getSchemaName());
            transDTO.setTableName(entry.getHeader().getTableName());
            for (RowData rowData : rowChage.getRowDatasList()) {
                if (eventType == EventType.DELETE) {
                	transDTO.getDataList().add(dataMassage(rowData.getBeforeColumnsList()));
                } else if (eventType == EventType.INSERT) {
                	transDTO.getDataList().add(dataMassage(rowData.getAfterColumnsList()));
                } else {
//                    transDTO.getDataList().add(dataMassage(rowData.getBeforeColumnsList()));
                    transDTO.getDataList().add(dataMassage(rowData.getAfterColumnsList()));
                }
            }
            sendMsg(transDTO);
        }
    }

    /**
     * 
     * @param columns
     * @return
     */
    private static HbaseEntity dataMassage(List<Column> columns) {
       System.out.println("column name and values:");
       HbaseEntity hbaseEntity = new HbaseEntity();
       HashMap<String,String> data = new HashMap<String,String>();
        for (Column column : columns) {
        	data.put(column.getName(), column.getValue());
        	if(column.getIsKey()) {
        		 hbaseEntity.setRowKey(column.getName());
        	}
        }
        hbaseEntity.setRowData(data);
        hbaseEntity.setColumnFamily("cf");
        return hbaseEntity;
    }
    
    private static void sendMsg(TransDTO src) {
    	KafkaProducer_.sendMsg("test", UUID.randomUUID().toString() ,src);
    }
    
//    private static void sendMsg(TransDTO src) {
//    	HbaseHelper hh = null;
//		try {
//			hh = new HbaseHelper();
//			hh.dispatchData(src);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }

	public void run() {
		// TODO Auto-generated method stub
		 // 创建链接     AddressUtils.getHostIp()    "192.168.61.132"    hadoop
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("127.0.0.1",
                11111), "example", "", "");

        int batchSize = 1000;
        int emptyCount = 0;
        try {
            connector.connect();
//            connector.subscribe(".*\\..*");
            connector.subscribe("");
            connector.rollback();
            int totalEmtryCount = 120;
            while (emptyCount < totalEmtryCount) {
                Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    emptyCount++;
//                    System.out.println("empty count : " + emptyCount);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                } else {
                    emptyCount = 0;
                    // System.out.printf("message[batchId=%s,size=%s] \n", batchId, size);
                    printEntry(message.getEntries());
                }

                connector.ack(batchId); // 提交确认
                // connector.rollback(batchId); // 处理失败, 回滚数据
            }

            System.out.println("Kafka Producer empty too many times, exit");
        } finally {
            connector.disconnect();
        }
	}
}
