package system;

import java.util.TimerTask;

public class SprinklerTask extends TimerTask{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(2000000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
