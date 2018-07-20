package valar.showcase.java.thread; //: concurrency/EvenGenerator.java

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


// When threads collide.

public class EvenGenerator extends EnhancedIntGenerator {
	public static final int NEXT = 0;
	public static final int SYNCHRONIZED_NEXT = 1;
	public static final int LOCLED_NEXT = 2;
	public static final int LOCKED_NEXT_AND_RETURN_IN_AVANCE = 3;
	public static final int ATOMIC_NEXT = 4;
	private int currentEvenValue = 0;
	private AtomicInteger atomicCurrentEvenValue = new AtomicInteger(0);
	private Lock lock = new ReentrantLock();
	private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
	private ThreadLocal<Integer> threadLocalValue = new ThreadLocal<Integer>() {

		@Override
		protected Integer initialValue() {
			// TODO Auto-generated method stub
			return new Integer(0);
		}
		
	};
	@Override
	public int next() {
		++currentEvenValue; 
		++currentEvenValue;
		return currentEvenValue;
	}

	@Override
	public synchronized int synchronizedNext() {
		++currentEvenValue; 
		++currentEvenValue;
		return currentEvenValue;
	}

	@Override
	public int lockedNext() {
		lock.lock();
		try {
			++currentEvenValue; 
			++currentEvenValue;
		} finally {
			lock.unlock();
		}
		return currentEvenValue;
	}

	@Override
	public int lockedNextAndReturnInAvance() {
		// TODO Auto-generated method stub
		lock.lock();
		try {
			++currentEvenValue; 
			++currentEvenValue;
			return currentEvenValue;
		} finally {
			lock.unlock();
		}
	}
	
	@Override
	public int atomicNextStepByStep() {
		// TODO Auto-generated method stub
		atomicCurrentEvenValue.addAndGet(1);
		atomicCurrentEvenValue.addAndGet(1);
		return atomicCurrentEvenValue.get();
	}


	@Override
	public int atomicNext() {
		// TODO Auto-generated method stub
		atomicCurrentEvenValue.addAndGet(2);
		return atomicCurrentEvenValue.get();
	}


	@Override
	public int threadLocalNext() {
		// TODO Auto-generated method stub
		threadLocalValue.set(threadLocalValue.get()+1);
		threadLocalValue.set(threadLocalValue.get()+1);
		return threadLocalValue.get();
	}


}




/**
 * next
 * 22901 not even! 22903 not even!
 */

/**
 * runs good
 */


/**
 * lockedNext
 * 11259 not even! 11263 not even! 11261 not even! 11259 not even! 11259 not
 * even! 11259 not even!
 */

// you have to make sure the unlock will not happen too early!