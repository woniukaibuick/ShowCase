package showcase.dw.glbg.canal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import dto.HbaseEntity;
import dto.TransDTO;

public class HbaseHelper {

	private Admin admin;
	private Connection conn;
	private String tableNameStr = "test";

	public HbaseHelper() throws Exception {
		Configuration conf = new Configuration();
		conf.set("hbase.zookeeper.quorum", "10.40.6.177");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		conn = ConnectionFactory.createConnection(conf);
		admin = conn.getAdmin();
	}

	public void close() throws Exception {
		admin = null;
		conn = null;
	}

	/**
	 * add column family failed due to false operation<br>
	 * not useful for enable or disable table
	 * 
	 * @throws IOException
	 */
	public void testCreateTable() throws IOException {
		TableName tableName = TableName.valueOf(tableNameStr);
		if (!admin.tableExists(tableName)) {
			System.out.println("table not exists!");
		} else {
			System.err.println("table  exists!");
			HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
			for (HColumnDescriptor hc : hTableDescriptor.getColumnFamilies()) {
				System.out.println("before column family:" + new String(hc.getName()));
			}
			admin.disableTable(tableName);
			HColumnDescriptor family = new HColumnDescriptor("apple");
			hTableDescriptor.addFamily(family);
			admin.enableTable(tableName);
			for (HColumnDescriptor hc : hTableDescriptor.getColumnFamilies()) {
				System.out.println("after column family:" + new String(hc.getName()));
			}
			System.out.println("table add column famliy completed!");
		}
	}

	public void testQueryTable() throws IOException {
		Table table = conn.getTable(TableName.valueOf(tableNameStr));
		ResultScanner rs = table.getScanner(new Scan());
		for (Result s : rs) {
			byte[] row = s.getRow();
			System.err.println("row key is:" + new String(row));
			List<Cell> cellList = s.listCells();
			for (Cell cell : cellList) {
				byte[] familyArray = cell.getFamilyArray();
				byte[] cellValue = cell.getValueArray();
				System.err.println("FamilyArray:" + new String(familyArray));
				System.err.println("CellValue:" + new String(cellValue));

			}
		}
	}

	public void testInsertData(TransDTO transDTO) throws IOException  {
		if (transDTO == null) {
			System.err.println("entityList is empty!");
			return;
		}
		Table table = conn.getTable(TableName.valueOf(transDTO.getTableName()));
		List<Put> dataList = new ArrayList<Put>();
		Put put = null;
		for(HbaseEntity hbaseEntity : transDTO.getDataList()) {//HashMap<String, String> data
			for(String key : hbaseEntity.getRowData().keySet()) {
				String value =  hbaseEntity.getRowData().get(key);
				put = new Put(Bytes.toBytes(hbaseEntity.getRowKey()));
				put.addColumn(hbaseEntity.getColumnFamily().getBytes(), Bytes.toBytes(key),
							Bytes.toBytes(value));
				dataList.add(put);
			}
		}
		table.put(dataList);
	}

	public void testUpdateData(TransDTO transDTO) throws IOException {
		testInsertData(transDTO);
	}

	public void truncateTable() throws IOException {
		// 取得目标数据表的表名对象
		TableName tableName = TableName.valueOf(tableNameStr);
		// 设置表状态为无效
		admin.disableTable(tableName);
		// 清空指定表的数据
		admin.truncateTable(tableName, true);
	}

	/**
	 * 删除行
	 */
	public void deleteByRowKey(List<String> rowKeyList) throws IOException {
		if (rowKeyList == null || rowKeyList.isEmpty()) {
			System.err.println("rowKeyList is empty!");
			return;
		}
		// 取得待操作的数据表对象
		Table table = conn.getTable(TableName.valueOf(tableNameStr));
		for (String rowKey : rowKeyList) {
			// 创建删除条件对象
			Delete delete = new Delete(Bytes.toBytes(rowKey));
			// 执行删除操作
			table.delete(delete);
		}

	}
	public void testDeleteData(TransDTO transDTO) throws IOException {
		if (transDTO == null) {
			System.err.println("transDTO is empty!");
			return;
		}
		// 取得待操作的数据表对象
		Table table = conn.getTable(TableName.valueOf(transDTO.getTableName()));
		for (HbaseEntity hbaseEntity  : transDTO.getDataList()) {
			// 创建删除条件对象
			Delete delete = new Delete(Bytes.toBytes(hbaseEntity.getRowKey()));
			// 执行删除操作
			table.delete(delete);
		}

	}

	public void getResultByColumn(String rowKey, String familyName, String columnName) throws IOException {
		Table table = conn.getTable(TableName.valueOf(tableNameStr));
		Get get = new Get(Bytes.toBytes(rowKey));
		get.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName)); // 获取指定列族和列修饰符对应的列
		Result result = table.get(get);
		for (Cell kv : result.listCells()) {
			System.out.println("family:" + Bytes.toString(CellUtil.cloneFamily(kv)));
			System.out.println("qualifier:" + Bytes.toString(CellUtil.cloneQualifier(kv)));
			System.out.println("value:" + Bytes.toString(CellUtil.cloneValue(kv)));
			System.out.println("Timestamp:" + kv.getTimestamp());
			System.out.println("-------------------------------------------");
		}
	}

	public void updateTable(String rowKey, String familyName, String columnName, String value) throws IOException {
		Table table = conn.getTable(TableName.valueOf(tableNameStr));
		Put put = new Put(Bytes.toBytes(rowKey));
		put.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName), Bytes.toBytes(value));
		table.put(put);
		System.out.println("update table Success!");
	}

	public void deleteColumn(String rowKey, String familyName, String columnName) throws IOException {
		Table table = conn.getTable(TableName.valueOf(tableNameStr));
		Delete deleteColumn = new Delete(Bytes.toBytes(rowKey));
		deleteColumn.addColumns(Bytes.toBytes(familyName), Bytes.toBytes(columnName));
		table.delete(deleteColumn);
		System.out.println(familyName + ":" + columnName + "is deleted!");
	}
	
	public void dispatchData(TransDTO transDTO) throws IOException {
		if(transDTO == null) {return ;}
		switch (transDTO.getEventType()) {
		case INSERT:
			testInsertData(transDTO);
			break;
		case UPDATE:
			testUpdateData(transDTO);
			break;
		case DELETE:
			testDeleteData(transDTO);
			break;
		default:
			System.err.println("EventType:"+transDTO.getEventType()+" was not handled!");
			break;
		}
	}

}
