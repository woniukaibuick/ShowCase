sqoop import --connect jdbc:mysql://1:3306/pms?tinyInt1isBit=false --username  --password  --table  pms_purchase_order --columns "" --target-dir /user/hive/warehouse/dw_web_pms.db/pms_purchase_order/date=20171118 --delete-target-dir --num-mappers 1 --hive-drop-import-delims --null-string '' --null-non-string '' --fields-terminated-by "\001" --lines-terminated-by "\n" --where "update_time<='2017-11-18 23:59:59'"

ALTER TABLE	pms_purchase_order	ADD IF NOT EXISTS PARTITION(date=20171118) LOCATION "/user/hive/warehouse/dw_web_pms.db/pms_purchase_order/date=20171118";


INSERT OVERWRITE TABLE pms_purchase_order_full
SELECT
        
FROM
(
	SELECT
		*,
		row_number() over (PARTITION BY purchase_order_id ORDER BY update_time DESC) rank
	FROM
		pms_purchase_order
) tmp
WHERE
	rank=1;
		