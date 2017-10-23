package showcase.dw.glbg.sunkai;

public class MainTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		for (int i = 0; i < 100000; i++) {

			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					TimeClient.request();
				}
			}).start();
		}
	}

}
