package valar.showcase.hadoop.mr.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**   
* @Title: FindFriends.java 
* @Package showcase.dw.glbg.mr 
* @Description: TODO(description) 
* @author gong_xuesong@126.com
* @date 2017年12月23日 上午10:12:39   
*/
public class FindFriends {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		Job job = new Job(conf);
		job.setJarByClass(FindFriends.class);
		job.setJobName("DM_SAL_PROFIT_TARGET");
		
//		job.setOutputKeyClass(Text.class);
//		job.setOutputValueClass(Text.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(TextArrayWritable.class);
		
		
		job.setMapperClass(FindFriendsMapper.class);
		job.setCombinerClass(FindFriendsReducer.class);
		job.setReducerClass(FindFriendsReducer.class);
		
		FileInputFormat.addInputPath(job, new Path("/user/gongxuesong/data/fb.txt"));
		FileOutputFormat.setOutputPath(job, new Path("/user/gongxuesong/data/output10"));
		
		job.setNumReduceTasks(1);
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}

class FindFriendsMapper extends Mapper<LongWritable, Text, Text, TextArrayWritable>{

	@Override
	protected void cleanup(Mapper<LongWritable, Text, Text, TextArrayWritable>.Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		super.cleanup(context);
	}

	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, TextArrayWritable>.Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
//		super.map(key, value, context);
		String users = value.toString();
		String uuid = UUID.randomUUID().toString();
		users =( users == null ? "" :users);
		String [] userArr = users.trim().split(" ");
		System.err.println("UUID:"+uuid+",Item:"+Arrays.toString(userArr));
		context.write(new Text(uuid), new TextArrayWritable(userArr));
		
	}

	@Override
	public void run(Mapper<LongWritable, Text, Text, TextArrayWritable>.Context context) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		super.run(context);
	}

	@Override
	protected void setup(Mapper<LongWritable, Text, Text, TextArrayWritable>.Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		super.setup(context);
	}


}
class FindFriendsReducer extends Reducer<Text, TextArrayWritable, Text, TextArrayWritable>{

	@Override
	protected void cleanup(Reducer<Text, TextArrayWritable, Text, TextArrayWritable>.Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		super.cleanup(context);
	}

	@Override
	protected void reduce(Text arg0, Iterable<TextArrayWritable> arg1, Reducer<Text, TextArrayWritable, Text, TextArrayWritable>.Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
//		super.reduce(arg0, arg1, arg2);
		List<HashSet<String>> setList = new ArrayList<HashSet<String>>();
		Iterator<TextArrayWritable>   iterator = arg1.iterator();
		while (iterator.hasNext()) {
			setList.add(parseTextArr(iterator.next()));
		}
		//求交集：
		HashSet<String> tmpSet = new HashSet<String>();
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < setList.size(); i++) {
			for (int j = i+1; j < setList.size()-1; j++) {
				HashSet<String> groupA = setList.get(i);
				HashSet<String> groupB = setList.get(j);
				tmpSet.clear();
				tmpSet.addAll(groupA);
				tmpSet.retainAll(groupB);
				
				list.clear();
				list.addAll(tmpSet);
				if (!tmpSet.isEmpty() && tmpSet.size() >= 2 ) {
					for (int k = 0; k < list.size(); k++) {
						for (int m = k+1; m < list.size()-1; m++) {
							String userA = list.get(k)+","+list.get(m);
							context.write(new Text(userA), getRemainingUsers(list, k, m));
						}
					}
				}
			}
		}

		
	}

	@Override
	public void run(Reducer<Text, TextArrayWritable, Text, TextArrayWritable>.Context arg0) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		super.run(arg0);
	}

	@Override
	protected void setup(Reducer<Text, TextArrayWritable, Text, TextArrayWritable>.Context context) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		super.setup(context);
	}
	
	
	private HashSet<String> parseTextArr(TextArrayWritable src) {
		HashSet<String> group = new HashSet<String>();
		if (src != null && src.get().length > 0) {
			for (int i = 0; i < src.get().length; i++) {
				Text text = (Text) (src.get())[i];
				group.add(text.toString());
			}
		}
		return group;
	}

	private TextArrayWritable getRemainingUsers(List<String> list, int removeIndexI, int removeIndexJ) {
		
		if (list != null && list.size() > 2) {
			String[] arr = new String[list.size()-2];
			int index = 0;
			for (int i = 0; i < list.size(); i++) {
				if (i != removeIndexI && i != removeIndexJ) {
					arr[index] = list.get(i);
					index++;
				}
			}
			return new TextArrayWritable(arr);
		}
		return null;
	}

	
}
class NonSplitTextFormat extends TextInputFormat{

	@Override
	protected boolean isSplitable(FileSystem fs, Path file) {
		// TODO Auto-generated method stub
		return false;
	}
	
}

class TextArrayWritable extends ArrayWritable {
	public TextArrayWritable() {
		super(Text.class);
	}

	public TextArrayWritable(String[] strings) {
		super(Text.class);
		Text[] texts = new Text[strings.length];
		for (int i = 0; i < strings.length; i++) {
			texts[i] = new Text(strings[i]);
		}
		set(texts);
	}
}