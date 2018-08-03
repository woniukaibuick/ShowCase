package org.apache.hadoop.canal.dto;
/**   
* @Title: HbaseEntity.java 
* @Package dto 
* @Description: TODO(description) 
* @author gong_xuesong@126.com
*/

import java.io.Serializable;
import java.util.HashMap;

public class HbaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String rowKey;
	private String columnFamily;
	private HashMap<String,String> rowData;
	public String getRowKey() {
		return rowKey;
	}
	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}
	public String getColumnFamily() {
		return columnFamily;
	}
	public void setColumnFamily(String columnFamily) {
		this.columnFamily = columnFamily;
	}
	public HashMap<String, String> getRowData() {
		return rowData;
	}
	public void setRowData(HashMap<String, String> rowData) {
		this.rowData = rowData;
	}
	
}
