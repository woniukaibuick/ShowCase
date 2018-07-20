package valar.showcase.hadoop.mr;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

/**   
* @Title: ReporterTest.java 
* @Package showcase.dw.glbg.mr 
* @Description: TODO(description) 
   
* @date 2017年10月6日 下午2:04:46   
*/
public class ReporterTest extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable>{

	@Override
	public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter)
			throws IOException {
		// TODO Auto-generated method stub
		
		
	}

}
