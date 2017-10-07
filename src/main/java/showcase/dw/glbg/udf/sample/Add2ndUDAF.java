package showcase.dw.glbg.udf.sample;

import org.apache.hadoop.hive.ql.exec.UDAF;
import org.apache.hadoop.hive.ql.exec.UDAFEvaluator;
import org.apache.hadoop.io.DoubleWritable;

public class Add2ndUDAF  extends UDAF {
	
	public static class PersonalEvaluater implements UDAFEvaluator{

		public static class PartialResult {
			private double sumValue;
			private int    count;
			public double getSumValue() {
				return sumValue;
			}
			public void setSumValue(double sumValue) {
				this.sumValue = sumValue;
			}
			public int getCount() {
				return count;
			}
			public void setCount(int count) {
				this.count = count;
			}
				
		}
		private PartialResult partialResult;
		@Override
		public void init() {
			// TODO Auto-generated method stub
			partialResult = new PartialResult();	
		}
		
		public boolean  iterate(DoubleWritable value) {
			if (value == null) {
				return true;
			}
			if (partialResult == null) {
				partialResult = new PartialResult();
			}
			partialResult.setSumValue(partialResult.getSumValue()+value.get());
			partialResult.setCount(partialResult.getCount()+1);
			return true;
		}
		
		public DoubleWritable terminate() {
			if (partialResult == null) {
				return null;
			}
			return new DoubleWritable(partialResult.getSumValue()/partialResult.getCount());
		}                    
		public PartialResult terminatePartial() {
			return partialResult;
		}
		public boolean merge(PartialResult other) {
			if (other == null) {
				return true;
			}
			if (partialResult == null) {
				partialResult = new PartialResult();
			}
			partialResult.setSumValue(partialResult.getSumValue()+other.getSumValue());
			partialResult.setCount(partialResult.getCount()+other.getCount());
			return true;
		}
		
		
	}
	

}
