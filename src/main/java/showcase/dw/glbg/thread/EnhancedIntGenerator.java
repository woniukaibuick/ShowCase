package showcase.dw.glbg.thread;
/**   
* @Title: EnhancedIntGenerator.java 
* @Package showcase.dw.glbg.thread 
* @Description: TODO(description) 
   
* @date 2017年10月7日 下午8:23:21   
*/
public abstract class EnhancedIntGenerator {
	  private volatile boolean canceled = false;
	  public abstract int next();
	  public abstract int synchronizedNext();
	  public abstract int lockedNext();
	  public abstract int lockedNextAndReturnInAvance();
	  public abstract int atomicNextStepByStep();
	  public abstract int atomicNext();
	  public abstract int threadLocalNext();
	  // Allow this to be canceled:
	  public void cancel() { canceled = true; }
	  public boolean isCanceled() { return canceled; }
	} ///:~