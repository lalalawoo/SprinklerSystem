package system;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/* The schedule class has scheduled DayOfWeek, start time and end time.
 * By using toDate() function, the schedule would be converted to the
 * date of next DayOfWeek with the start hour and start minutes. 
 */

public class Schedule {
	
	private String schedID;
	private int schedDay;
	private int startHour;
	private int startMin;
	private int endHour;
	private int endMin;
	
	public Schedule(String id, int day, int startHour, int startMin, int endHour, int endMin){
		schedID=id;
		schedDay = day;
		this.startHour = startHour;
		this.startMin = startMin;
		this.endHour = endHour;
		this.endMin = endMin;
	}
	
	public String getID(){
		return schedID;
	}
	
	public int getDay(){
		return schedDay;
	}
	
	public int getStartHour(){
		return startHour;
	}
	
	public int getStartMin(){
		return startMin;
	}
	
	public int getEndHour(){
		return endHour;
	}
	
	public int getEndMin(){
		return endMin;
	}
	
	public Date toDate(){
//		TimeZone.setDefault(TimeZone.getTimeZone("PST"));
		Calendar c = Calendar.getInstance();
		int currDay = c.get(Calendar.DAY_OF_WEEK);
		
		if(schedDay>=currDay){
			c.add(Calendar.DATE,schedDay-currDay);
//			System.out.println("After adjust date, " + c.getTime());
		}else{
			c.add(Calendar.DATE, schedDay+7-currDay);
		}
//		System.out.println("StartHour is "+ startHour);
		c.set(Calendar.HOUR_OF_DAY, startHour);
//		System.out.println("After set startHour, " + c.getTime());
		c.set(Calendar.MINUTE, startMin);
//		System.out.println("After set startMin, " + c.getTime());

		c.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
//		System.out.println(c.get(Calendar.DAY_OF_WEEK));
		
		return c.getTime();
	}
	
	public long getDuration(){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MINUTE, startMin);
		c.set(Calendar.HOUR_OF_DAY, startHour);
		Date start = c.getTime();
		c.set(Calendar.MINUTE, endMin);
		c.set(Calendar.HOUR_OF_DAY, endHour);
		Date end = c.getTime(); 
//		System.out.println(end.getTime()-start.getTime());
		return end.getTime()-start.getTime();
	}
	
//	public static void main(String[] args){
//		Schedule ss = new Schedule("0",3,20,0,20,15);
//		System.out.println(ss.toDate());
//		System.out.println(ss.getDuration());
//		DateFormat df = DateFormat.getDateInstance(DateFormat.FULL);
//		System.out.println(df.format(new Date()));
//	}
}
