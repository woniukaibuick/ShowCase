package valar.showcase.hive.udf.sample;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.Text;

public class AddUDF {
		
	private Text result = new Text();
	public Text evaluate(Text srcText) {
		if (srcText == null) {
			return null;
		}
		result.set(StringUtils.strip(srcText.toString()));
		return result;
	}

}
