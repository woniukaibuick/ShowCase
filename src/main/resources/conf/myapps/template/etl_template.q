--@author GongXuesong
--@desc dw_web_pms.pms_house_storage_child_sales 全量

SET mapred.job.queue.name=root.users; 

SET hive.fetch.task.conversion=more;
SET hive.cli.print.header=true;
SET hive.exec.reducers.max=60;
SET hive.exec.compress.output=false;
SET hive.exec.compress.intermediate=true;
SET hive.auto.convert.join = false; 
SET hive.groupby.skewindata=true;
 
set hive.map.aggr=true;
SET mapred.max.split.size= 100000000 ;
SET mapred.min.split.size.per.node= 100000000 ;
SET mapred.min.split.size.per.rack= 100000000;
SET hive.input.format=org.apache.hadoop.hive.ql.io.CombineHiveInputFormat;

SET mapred.job.name='operation_sys_phrase2_template.q';

USE dw_web_pms; --need to change

CREATE TABLE IF NOT EXISTS  (

)
COMMENT ''
PARTITIONED BY(date string)
ROW FORMAT DELIMITED FIELDS TERMINATED BY "\001" LINES TERMINATED BY '\n'
STORED AS textfile  
LOCATION '/user/hive/warehouse/.db/';

CREATE TABLE IF NOT EXISTS  _full(

)
COMMENT ''
ROW FORMAT DELIMITED FIELDS TERMINATED BY "\001" LINES TERMINATED BY '\n'
STORED AS textfile  
LOCATION '/user/hive/warehouse/.db/_full';


	
INSERT OVERWRITE TABLE _full
SELECT
	             
FROM
(
	SELECT
		*,
		row_number() over (PARTITION BY queue_id 
		                       ORDER BY update_time DESC) rank
	FROM
		(
		SELECT
			
		FROM  WHERE date = ${DATE}
		UNION ALL
		SELECT
			
		FROM _full
		 ) tp
) tmp
WHERE
	rank=1;	
	