package system;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import data.DataFile;

import java.util.Timer;
import java.util.TimerTask;

public class SprinklerGroup {
	
	public boolean isON;
	String groupName;
	List<Sprinkler> sList;
	List<Schedule> schedList;
	Timer groupTimer;
//	List<TimerTask> taskList;
	Map<String,TimerTask> schedTaskMap;
	int waterVolume;
	Date activateTime;
		
	private int sprinklerCounter;
	private int schedCounter;
	
	private final static long schedInterval = 1000*60*60*24*7;
	
	public SprinklerGroup(String groupName){
		isON=false;
		this.groupName = groupName;
		sList = new ArrayList<Sprinkler>();
		schedList = new ArrayList<Schedule>();
		groupTimer = new Timer(false);
//		taskList = new ArrayList<>();
		schedTaskMap = new HashMap<>();
		activateTime = null;
	}
	
	public void addSprinkler(){
		sprinklerCounter++;
		String newSprinklerID = sprinklerCounter+ String.valueOf(groupName.charAt(0));
		Sprinkler s = new Sprinkler(newSprinklerID);
		s.setWaterVolume(waterVolume);
		sList.add(s);
	}
	
	public List<Sprinkler> getSprinklerList(){
		return sList;
	}
	
	public String getName(){
		return groupName;
	}
	
	public boolean getStatus(){
		return isON;
	}
	
	public void setStatus(boolean stat){
		for(Sprinkler s: sList){
			if(!stat && s.getWorkStatus()) s.setDisable();
			s.setStatus(stat);
		}
		if(stat && !isON){
			isON = true;
			setEnableGroup();
		}else if(!stat && isON){
			isON = false;
			System.out.println("SprinklerGroup "+ groupName + " is OFF.");
			setDisableGroup();
		}
	}
	
	public void setEnableGroup(){
//		isON = true;
//		this.restartGroupTimer();
//		if(activateTime==null){
		activateTime = new Date();
		groupTimer.schedule(new TimerTask(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("SprinklerGroup "+ groupName + " is working now...");
				
				while(isON){
					try{				
						Thread.sleep(1000);
					}catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}				
		}, 0);
	}
//	}
	
	public void setDisableGroup(){
//		if(isON && activateTime!=null){
//			groupTimer.cancel();
//			groupTimer.purge();
//			isON = false;
		Date currTime = new Date();
		System.out.println("SprinklerGroup "+groupName+" stops.");
		DataFile f = new DataFile();
		try {
			f.writeData(groupName, new Date(), 
					(int)(waterVolume * (currTime.getTime()-activateTime.getTime()) / 1000));
			System.out.println("Data saved to the file.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
//		}
		activateTime = null; 
	}
	
//	public void restartGroupTimer(){
//		groupTimer = new Timer();
//		for(Schedule ss : schedList){
//			groupTimer.schedule(schedTaskMap.get(ss.getID()), ss.toDate(), schedInterval);			
//		}
//	}
//	
//	public void stopGroupTimer(){
//		groupTimer.cancel();
//	}
	
	public void setWaterVolume(int val){
		waterVolume=val;
	}
	
	public int getWaterVolume(){
		return waterVolume;
	}
	
	public List<Schedule> getSchedule(){
		return schedList;
	}
	
	public void addNewSchedule(int day, int startHour, int startMin, int endHour, int endMin){
//		System.out.println(day+" "+startHour+" "+startMin+" "+endHour+" "+endMin);
		String id = generateSchedID();
		Schedule newSchedule = new Schedule(id,day,startHour,startMin,endHour,endMin);
		schedList.add(newSchedule);
		System.out.println("Group "+groupName+" New schedule has been added.");
		addSchedTask(newSchedule);
	}
	
	public void deleteSchedule(String schedID){
		if(schedTaskMap.containsKey(schedID)){
			schedTaskMap.get(schedID).cancel();
			schedTaskMap.remove(schedID);
		}
		for(int i=0;i<schedList.size();i++){
			Schedule s = schedList.get(i);
			if(s.getID().equals(schedID)){
				schedList.remove(i);
			}
		}
	}
	
	public void addTempTask(long duration){
		TimerTask newTask = new SGroupTask(this,duration);
		System.out.println("SprinklerGroup "+ groupName + " is started due to temperature limit.");
		groupTimer.schedule(newTask, 0);
	}
	
	public void addSchedTask(Schedule sched){
		TimerTask newTask = new SGroupTask(this,sched.getDuration());
		schedTaskMap.put(sched.getID(), newTask);
		groupTimer.schedule(newTask, sched.toDate(), schedInterval);
//		System.out.println("To date result: "+sched.toDate());
		System.out.println("New task has been added.");
	}
	
	public String generateSchedID(){
		String newSchedID = String.valueOf(groupName.charAt(0))+ schedCounter;
		schedCounter++;
		return newSchedID;
	}	
}
