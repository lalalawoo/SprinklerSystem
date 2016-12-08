package system;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import data.DataFile;

public class Sprinkler {
	
	private String sID;
	private boolean isOn;
	private boolean isFunctional;
	private Timer timer;
	private Date activateTime;
	private int waterVolume;
	
	
	public Sprinkler(String sID){
		this.sID=sID;
		isOn = false;
		isFunctional = true;
		activateTime = null;
	}
	
	public String getID(){
		return sID;
	}
	
	public void setStatus(boolean stat){
		if(stat) isOn = true && isFunctional;
		else isOn=false;
	}
	
	public void setEnable(){
		isOn = true && isFunctional;
		if(isOn && activateTime==null){
			timer = new Timer();
			activateTime = new Date();
			timer.schedule(new TimerTask(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					System.out.println("Sprinkler "+ sID + " is working now...");
					System.out.println(activateTime.getTime());
					
					while(isOn){
						try{				
							Thread.sleep(1000);
						}catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}				
			}, 0);
		}else{
			System.out.println("Sprinkler " + sID + " is not activated due to malfuctional status");
		}
	}
	
	public void setDisable(){
//		System.out.println("Call " + sID + ".setDisable()");
		isOn = false;
		System.out.println("Sprinkler "+sID+" stops.");
		if(activateTime!=null){
			timer.cancel();
			timer.purge();			
			Date currTime = new Date();
			
			DataFile f = new DataFile();
			String groupName = this.getGroupName();
			try {
				f.writeData(groupName, new Date(), 
						(int)(waterVolume * (currTime.getTime()-activateTime.getTime())));
				System.out.println("Data saved to the file.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		activateTime = null; 
	}
	
	public void setFunction(boolean funcStat){
		isFunctional = funcStat;
	}
	
	public boolean getWorkStatus(){
		return isOn;
	}
	
	public boolean getFuncStatus(){
		return isFunctional;
	}
	
	public void setWaterVolume(int val){
		waterVolume = val;
	}
	
	public String getGroupName(){
		String s = this.sID.substring(sID.length()-1);
		String groupName = "";
		switch(s.toUpperCase()){
		case "N":
			groupName = "NORTH";
			break;
		case "S":
			groupName = "SOUTH";
			break;
		case "W":
			groupName = "WEST";
			break;
		case "E":
			groupName = "EAST";
			break;
		}
		return groupName;
	}
	
//	public static void main(String[] args){
//		Sprinkler s = new Sprinkler("2W");
//		s.setWaterVolume(30);
//		s.setEnable();
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		s.setDisable();
//	} 
}
