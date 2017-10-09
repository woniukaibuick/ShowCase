package showcase.dw.glbg.clone;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

/**
 * 慎用 Object 的 clone 方法来拷贝对象。 
 * 说明： 对象的 clone 方法默认是浅拷贝，若想实现深拷贝需要重写 clone 方法实现属性对象的拷贝。
 * @author gongva
 *
 */
public class CloneTest {
	
	
	@Test
	public void testClone(){
		ArrayList<String> strList = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			strList.add(i+UUID.randomUUID().toString());
		}
		
		List<String> myCloneList =(List<String>) strList.clone();
		myCloneList.remove(0);
		Collections.reverse(myCloneList);
		assert myCloneList.get(0).equals(strList.get(0)) :"deep clone!"; 
		System.err.println(myCloneList.get(0));
		System.err.println(strList.get(0));
		Assert.assertEquals(strList.size(), 9);
	}
	
	@Test
	public void testObjectWithoutOverrideClone() throws CloneNotSupportedException {
		BodyWithoutClone body = new BodyWithoutClone(new HeadWithoutClone(new FaceWithoutClone()));
	    BodyWithoutClone body1 = (BodyWithoutClone) body.clone();
	    System.out.println("body == body1 : " + (body == body1) );
	    System.out.println("body.head == body1.head : " +  (body.head == body1.head));
	    System.out.println("body.head.face == body1.head.face : " +  (body.head.face == body1.head.face));
	    Assert.assertEquals(body.head.face, body1.head.face);
	}
	
	@Test
	public void testObjectClone() throws CloneNotSupportedException{
		Body body = new Body(new Head(new Face()));
	    Body body1 = (Body) body.clone();
	    System.out.println("body == body1 : " + (body == body1) );
	    System.out.println("body.head == body1.head : " +  (body.head == body1.head));
	    System.out.println("body.head.face == body1.head.face : " +  (body.head.face == body1.head.face));
	    Assert.assertEquals(body.head.face, body1.head.face);
	}
	
	
	@Test
	public void testRealDeepClone() throws IOException, ClassNotFoundException{
		BodyWithSerializable body = new BodyWithSerializable(new HeadWithSerializable(new FaceWithSerializable()));
		
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo = new ObjectOutputStream(bo);
		oo.writeObject(body);
		ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
		ObjectInputStream oi = new ObjectInputStream(bi);
		
		BodyWithSerializable body1 = (BodyWithSerializable) oi.readObject();
	    System.out.println("body == body1 : " + (body == body1) );
	    System.out.println("body.head == body1.head : " +  (body.head == body1.head));
	    System.out.println("body.head.face == body1.head.face : " +  (body.head.face == body1.head.face));
	    Assert.assertEquals(body.head.face, body1.head.face);
		

	}

}


class Body implements Cloneable{
    public Head head;
    public Body() {}
    public Body(Head head) {this.head = head;}

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Body newBody =  (Body) super.clone();
        newBody.head = (Head) head.clone();
//        newBody.head.face = (Face)head.face.clone();
        return newBody;
    }

}

class Head implements Cloneable{
    public  Face face;

    public Head() {}
    public Head(Face face){this.face = face;}
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
} 

class Face  {}
   /*implements Cloneable{

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	
}*/


class BodyWithoutClone  implements Cloneable{
    public HeadWithoutClone head;
    public BodyWithoutClone() {}
    public BodyWithoutClone(HeadWithoutClone head) {this.head = head;}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
    
    
}

class HeadWithoutClone {
    public FaceWithoutClone face;
    public HeadWithoutClone() {}
    public HeadWithoutClone(FaceWithoutClone face){this.face = face;}
} 

class FaceWithoutClone  {}


class BodyWithSerializable  implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public HeadWithSerializable head;
    public BodyWithSerializable() {}
    public BodyWithSerializable(HeadWithSerializable head) {this.head = head;} 
}

class HeadWithSerializable implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public FaceWithSerializable face;
    public HeadWithSerializable() {}
    public HeadWithSerializable(FaceWithSerializable face){this.face = face;}
} 

class FaceWithSerializable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;}