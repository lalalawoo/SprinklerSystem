package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataFile {
	
	private int[] sysWC;
	private int[] gNorthWC;
	private int[] gSouthWC;
	private int[] gWestWC;
	private int[] gEastWC;
	
	private final static String defaultFileName = "WaterConsumption.txt";
	private final static int numOfData = 10;
	
	public DataFile(){
		sysWC = new int[numOfData];
		gNorthWC = new int[numOfData];
		gSouthWC = new int[numOfData];
		gEastWC = new int[numOfData];
		gWestWC = new int[numOfData];
	}
	
	public void loadData(){
		BufferedReader br = null;
		String line="";
		String split=",";
		
		try{
			br = new BufferedReader(new FileReader(defaultFileName));
			System.out.println("Loading data from "+defaultFileName);
			while((line = br.readLine())!=null){
				String[] data = line.split(split);
				int week = getWeek(data[1]);
				if(week>9) continue;
				switch(data[0].toUpperCase()){
				case "NORTH":
					gNorthWC[week]+=Integer.valueOf(data[2]);
					break;
				case "SOUTH":
					gSouthWC[week]+=Integer.valueOf(data[2]);
					break;
				case "EAST":
					gEastWC[week]+=Integer.valueOf(data[2]);
					break;
				case "WEST":
					gWestWC[week]+=Integer.valueOf(data[2]);
					break;
				}
			}
			for(int i=0;i<numOfData;i++){
				sysWC[i]=gNorthWC[i]+gSouthWC[i]+gEastWC[i]+gWestWC[i];
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Done loading water consumption data!");
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static int getWeek(String s) throws ParseException{
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date date = df.parse(s);
		Calendar c = Calendar.getInstance();
		int currWeek = c.get(Calendar.WEEK_OF_YEAR);
		c.setTime(date);
		return currWeek - c.get(Calendar.WEEK_OF_YEAR);
	}
	
	public void releaseData(){
		sysWC=new int[numOfData];;
		gNorthWC=new int[numOfData];;
		gEastWC=new int[numOfData];;
		gWestWC=new int[numOfData];;
		gSouthWC=new int[numOfData];;				
	}
	
	public int[] getSysWC(){
		return reverseArray(sysWC);
	}
	
	public int[] getGroupWC(String s){
		switch(s.toUpperCase()){
		case "NORTH":
			return reverseArray(gNorthWC);
		case "SOUTH":
			return reverseArray(gSouthWC);
		case "EAST":
			return reverseArray(gEastWC);
		case "WEST":
			return reverseArray(gWestWC);
		}
		return new int[10];
	}
	
	public void writeData(String groupName, Date date, int waterConsumption) throws IOException{
		BufferedWriter bw = new BufferedWriter(new FileWriter(defaultFileName,true));
		PrintWriter out = new PrintWriter(bw);
		String record = groupName.trim().toUpperCase()+","+convertDate(date)+","+String.valueOf(waterConsumption);
        out.println(record);
        out.close(); 
        bw.close();
	}
	
	public static String convertDate(Date date){
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		return df.format(date);
		
	}
	
	public int[] reverseArray(int[] arry){
		int n=arry.length;
		int[] res= new int[n];
		for(int i=0;i<n;i++){
			res[n-1-i]=arry[i];
		}
		return res;
	}
	
//	public static void main(String[] args) throws IOException{
//		FileIO f = new FileIO();
//		DateFormat df = DateFormat.getDateInstance(DateFormat.FULL);
////		String content = "NORTH"+","+convertDate(new Date())+","+String.valueOf(30);
//		f.writeData("NORTH",new Date(),30);
//		f.loadData();
//		int[] temp = f.getSysWC();
//		for(int i=0;i<temp.length;i++){
//			System.out.print(temp[i]+" ");
//		}
//	}

}
