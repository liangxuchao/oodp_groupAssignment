package dbrepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import file.FileHandle;
import model.DBContext;
import model.TimeTableModel;

public class TimeTableRepo {


	public static ArrayList<TimeTableModel> readTimeTableByCourseIndex(int CourseID){
		ArrayList<TimeTableModel> tList = new ArrayList<TimeTableModel>();
		for(int i=0; i< DBContext.TimeTableListing.size(); i++) {
			
			if(DBContext.TimeTableListing.get(i).getIndexNumber() == CourseID) {
				tList.add(DBContext.TimeTableListing.get(i));
			}
		
		}
		return tList;
	}
	
	
	public static void add(ArrayList<TimeTableModel> TimeTableList){
		for(int i = 0; i <TimeTableList.size(); i++){
			DBContext.TimeTableListing.add(TimeTableList.get(i));
		}
		save(DBContext.TimeTableListing);	
	}
	
	public static void update(ArrayList<TimeTableModel> OldTimeTableList,ArrayList<TimeTableModel> NewTimeTableList ){
		for(int i = 0; i <OldTimeTableList.size(); i++){
			DBContext.TimeTableListing.remove(OldTimeTableList.get(i));
		}
		for(int i = 0; i <NewTimeTableList.size(); i++){
			DBContext.TimeTableListing.add(NewTimeTableList.get(i));
		}
		save(DBContext.TimeTableListing);	
	}
	
	public static void save(List<TimeTableModel> al)  {
		
		List<String> alw = new ArrayList<String>() ;
		alw.add("CourseIndex|Type|Group|Day|Time|Venue");
		String SEPARATOR = DBContext.SEPARATOR;
        for (int i = 0 ; i < al.size() ; i++) {
        	TimeTableModel timeTable = (TimeTableModel)al.get(i);
				StringBuilder st =  new StringBuilder() ;
				st.append(timeTable.getIndexNumber());
				st.append(SEPARATOR);
				st.append(timeTable.getType());
				st.append(SEPARATOR);
				st.append(timeTable.getGroup());
				st.append(SEPARATOR);
				st.append(timeTable.getDay());
				st.append(SEPARATOR);
				st.append(timeTable.getTimeStart());
				st.append(SEPARATOR);
				st.append(timeTable.getTimeEnd());
				st.append(SEPARATOR);
				st.append(timeTable.getVenue());
				
				alw.add(st.toString()) ;
			}

		try {
			FileHandle.write(DBContext.TBFILENAME,alw);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
