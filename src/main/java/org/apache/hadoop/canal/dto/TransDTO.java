package org.apache.hadoop.canal.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.otter.canal.protocol.CanalEntry.EventType;

/**   
* @Title: TransDTO.java 
* @Package pojo 
* @Description: TODO(description) 
* @author gong_xuesong@126.com
* @date 2017年10月13日 上午11:55:00   
*/
public class TransDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String schemaName;
	private String tableName;
	private EventType eventType;
	private List<HbaseEntity> dataList = new ArrayList<HbaseEntity>();
	public String getSchemaName() {
		return schemaName;
	}
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public EventType getEventType() {
		return eventType;
	}
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
	public List<HbaseEntity> getDataList() {
		return dataList;
	}
	public void setDataList(List<HbaseEntity> dataList) {
		this.dataList = dataList;
	}
	
	

}
