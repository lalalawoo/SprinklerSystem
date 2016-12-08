package system;

import java.io.IOException;
import java.util.Date;
import java.util.TimerTask;

import data.DataFile;

public class SGroupTask extends TimerTask {
	
	SprinklerGroup group;
	long duration;
	
	public SGroupTask(SprinklerGroup group, long duration){
		this.group = group;
		this.duration = duration;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
//		System.out.println("GroupTimer now running...");
		Date startTime = new Date();
		group.setStatus(true);
		while(group.getStatus()){
			try {
				System.out.println("SprinklerGroup "+ group.getName() + " is now working...");
				Thread.sleep(duration);
				System.out.println("SprinklerGroup "+ group.getName() + " finished working.");
				break;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		group.setStatus(false);
		System.out.println("SprinklerGroup "+ group.getName() + " is OFF.");
		Date stopTime = new Date();
		System.out.println("Stop time: " + stopTime.getTime());
		DataFile f = new DataFile();
		try {
			f.writeData(group.getName(), stopTime, 
					(int)(group.getSprinklerList().size() * group.getWaterVolume()
							* (stopTime.getTime()-startTime.getTime())/1000));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
