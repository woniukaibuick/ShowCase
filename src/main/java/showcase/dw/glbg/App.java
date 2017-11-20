package showcase.dw.glbg;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
		String s = "ALTER TABLE s_pdm_c_product_catalog_new ADD IF NOT EXISTS PARTITION(date=20170818)  LOCATION '/user/hive/warehouse/dw_web_pdm.db/s_pdm_c_product_catalog_new/date=20170818';";
		
		for (int i = 20170818; i < 20171025; i++) {
			if(i%100 > 31){continue;}
			System.err.println(s.replace("20170818", i+""));
		}
    }
}
