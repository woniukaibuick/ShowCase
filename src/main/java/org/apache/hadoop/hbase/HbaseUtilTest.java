package org.apache.hadoop.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.jdo.annotations.Transactional;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HbaseUtilTest {
	
	private Admin admin;
    private Connection conn;
    private String tableNameStr = "hbase_util_test";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "192.168.199.128");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		 conn = ConnectionFactory.createConnection(conf);
		 admin = conn.getAdmin();
	}

	@After
	public void tearDown() throws Exception {
		admin =null;
		conn = null;
	}
	
	

	/**
	 * add column family failed due to false operation<br>
	 * not useful for enable or disable table
	 * @throws IOException
	 */
	@Test
	@Transactional()
	public void testCreateTable() throws IOException {
		TableName tableName = TableName.valueOf(tableNameStr);
		if(!admin.tableExists(tableName)) {
			System.out.println("table not exists!");
		}else {
			System.err.println("table  exists!");
			HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
			for(HColumnDescriptor hc : hTableDescriptor.getColumnFamilies()) {
				System.out.println("before column family:"+new String(hc.getName()));
			}
			admin.disableTable(tableName);
			HColumnDescriptor family = new HColumnDescriptor("apple"); 
			hTableDescriptor.addFamily(family);
			admin.enableTable(tableName);
			for(HColumnDescriptor hc : hTableDescriptor.getColumnFamilies()) {
				System.out.println("after column family:"+new String(hc.getName()));
			}
			System.out.println("table add column famliy completed!");
		}
	}
	@Test
	public void testQueryTable() throws IOException {
		Table table = conn.getTable(TableName.valueOf("test"));
		ResultScanner rs = table.getScanner(new Scan());
		for(Result s : rs) {
			byte[] row = s.getRow();
			System.err.println("row key is:"+new String(row));
			List<Cell> cellList = s.listCells();
			for(Cell cell : cellList) {
				byte[] familyArray = cell.getFamilyArray();
				byte[] cellValue =  cell.getValueArray();
				System.err.println("FamilyArray:"+new String(familyArray));
				System.err.println("CellValue:"+new String(cellValue));
				
			}
		}
	}
	
	@Test
	public void testInsertData() throws IOException {
//		Get get = new Get(Bytes.toBytes("row1"));
		Table table = conn.getTable(TableName.valueOf(tableNameStr));
//		org.junit.Assert.assertTrue(!table.exists(get));
		
		List<Put> putList = new ArrayList<Put>();
		Put put = null;
		for(int i = 0; i<10;i++) {
			put = new Put(Bytes.toBytes(UUID.randomUUID().toString()));
			put.addColumn("cf".getBytes(),  Bytes.toBytes("name"), Bytes.toBytes("iwatch" + i));
			putList.add(put);
		}
		table.put(putList);
		
		
		
	}
	
	
	@Test
	public void testDefaultCharset() {
		byte[] bytes=  "hi".getBytes();
		System.err.println(bytes.length);
	}

}
