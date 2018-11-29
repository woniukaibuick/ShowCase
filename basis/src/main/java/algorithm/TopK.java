package algorithm;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * 最大堆 最小堆  PriorityQueue搞定
 * @author gongva
 *
 */
public class TopK {
	
	private Random random;
	private List<Integer> data;
	
	@Before
	public void init(){
		random = new Random();
		data = Arrays.asList(1,3,7,9,41,21,50,7,11,9,2);
	}

	@Test
	public void testMinHeap(){
		PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();
		for (int i = 0; i < data.size() ; i++) {
			minHeap.add(data.get(i));
		}
		for (int i = 0; i < 11 ; i++) {
			System.out.println(minHeap.poll());
		}
	}
	
	@Test
	public void testMaxHeap(){
		PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(11,new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				// TODO Auto-generated method stub
				return o2.compareTo(o1);//不能使用o2-o1
			}
		});
		for (int i = 0; i <  data.size() ; i++) {
			maxHeap.add(data.get(i));
		}
		for (int i = 0; i <  data.size() ; i++) {
			System.out.println(maxHeap.poll());
		}
		
		
	}
	
	@Test
	public void testArraySort(){
//		Arrays.sort(a, c);
	}
	
	@Test
	public void testAddMore(){
		PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();
		for (int i = 0; i < data.size() ; i++) {
			minHeap.add(data.get(i));
		}
		minHeap.add(0);
		Assert.assertTrue(minHeap.size() == data.size() + 1);
		for (int i = 0; i <  data.size() ; i++) {
			System.out.println(minHeap.poll());
		}
	}
	

}
