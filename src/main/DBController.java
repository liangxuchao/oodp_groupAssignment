package main;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import model.AdminModel;
import model.AllocatedListingModel;
import model.CourseModel;
import model.StudentModel;
import model.TimeTableModel;
import model.UserModel;
import model.WaitListingModel;

public class DBController {
	public static ArrayList<StudentModel> student;
	public static ArrayList<AdminModel> admin;
	public static ArrayList<WaitListingModel> WaitListing;
	public static ArrayList<AllocatedListingModel> AllocatedListing;
	public static ArrayList<CourseModel> CourseModelListing;
	public static ArrayList<TimeTableModel> TimeTableListing;
	
	public DBController() {
		try {
			
			WaitListing = readWaitListing("FileDB/WaitingListing.txt");
			AllocatedListing = readAllocateListing("FileDB/AllocatedListing.txt");

			TimeTableListing = readTimeTableListing("FileDB/TimeTable.txt");
			CourseModelListing =  readCourseListing("FileDB/Course.txt");
			
			ArrayList readuser = new ArrayList();
			readuser = readUsers("FileDB/User.txt");
			student = (ArrayList<StudentModel>) readuser.get(1);
			admin = (ArrayList<AdminModel>) readuser.get(0);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.print("Error while loading files");
		}
	}
	
	
	public static final String SEPARATOR = "|";

    // an example of reading
	public static ArrayList readUsers(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList)read(filename);
		ArrayList<AdminModel> admin = new ArrayList<AdminModel>() ;// to store Professors data
		ArrayList<StudentModel> student = new ArrayList<StudentModel>() ;// to store Professors data
		ArrayList alr = new ArrayList();
        for (int i = 1 ; i < stringArray.size() ; i++) {
        	
				String st = (String)stringArray.get(i);
				// get individual 'fields' of the string separated by SEPARATOR
				StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","

				String  UserID = star.nextToken().trim();	// first token
				String  Password = star.nextToken().trim();	// second token
				String  FirstName = star.nextToken().trim();
				String  LastName = star.nextToken().trim();
				String  Gender = star.nextToken().trim();
				String  Nationality = star.nextToken().trim();
				char  UserType = star.nextToken().trim().charAt(0);

				Date AccessTimeStart;
				Date  AccessTimeEnd;
				try {
					AccessTimeStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(star.nextToken().trim());
					AccessTimeEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(star.nextToken().trim());
				} catch (ParseException e) {
					AccessTimeStart = null;
					AccessTimeEnd = null;
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				if(UserType == 'S') {
					StudentModel Student = new StudentModel(UserID, Password,FirstName,LastName,Gender,Nationality,UserType,AccessTimeStart,AccessTimeEnd);
					Student.AllocateListing = 	readAllocateListingByStudentID(Student.getUserID());
					Student.WaitListing = 	readAllWaitListingByStudentID(Student.getUserID());
					student.add(Student) ;
				}else if(UserType == 'A') {
					AdminModel Admin = new AdminModel(UserID, Password,FirstName,LastName,Gender,Nationality,UserType,AccessTimeStart,AccessTimeEnd);
					admin.add(Admin) ;
					
				}
				
				
				// add to UserModel list
				
			}
	        alr.add(admin);
	        alr.add(student);
			return alr ;
	}

	public static ArrayList<WaitListingModel> readWaitListing(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList)read(filename);
		ArrayList<WaitListingModel> WaitList = new ArrayList<WaitListingModel>() ;
        for (int i = 1 ; i < stringArray.size() ; i++) {
        	
				String st = (String)stringArray.get(i);
				// get individual 'fields' of the string separated by SEPARATOR
				StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","

				int CourseIndex = Integer.parseInt(star.nextToken().trim());	// first token
				String  UserID = star.nextToken().trim();	// second token
				Date  ApplyTime;
				try {
					ApplyTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(star.nextToken().trim());
				} catch (ParseException e) {
					ApplyTime = null;
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				WaitListingModel WaitItem = new WaitListingModel(CourseIndex,UserID,ApplyTime); 
				
				// add to UserModel list

		        WaitList.add(WaitItem);
		}
		return WaitList ;
	}

	public static ArrayList<AllocatedListingModel> readAllocateListing(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList)read(filename);
		ArrayList<AllocatedListingModel> AllocateListing = new ArrayList<AllocatedListingModel>() ;
        for (int i = 1 ; i < stringArray.size() ; i++) {
        	
				String st = (String)stringArray.get(i);
				// get individual 'fields' of the string separated by SEPARATOR
				StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","

				int CourseIndex = Integer.parseInt(star.nextToken().trim());	// first token
				String  UserID = star.nextToken().trim();	// second token
				Date  RegisterTime;
				try {
					RegisterTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(star.nextToken().trim());
				} catch (ParseException e) {
					RegisterTime = null;
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				AllocatedListingModel AllocateItem = new AllocatedListingModel(CourseIndex,UserID,RegisterTime); 
				
				// add to UserModel list

				AllocateListing.add(AllocateItem);
		}
		return AllocateListing ;
	}
	
	public static ArrayList<CourseModel> readCourseListing(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList)read(filename);
		ArrayList<CourseModel> CourseListing = new ArrayList<CourseModel>() ;
        for (int i = 1 ; i < stringArray.size() ; i++) {
        	
				String st = (String)stringArray.get(i);
				// get individual 'fields' of the string separated by SEPARATOR
				StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","

				int IndexNumber = Integer.parseInt(star.nextToken().trim());	// first token
				String  CourseCode = star.nextToken().trim();	
				String  CourseName = star.nextToken().trim();
				String  CourseType = star.nextToken().trim();	
				int  Vacancy = Integer.parseInt(star.nextToken().trim());
				String  School = star.nextToken().trim();	
				int  AU = Integer.parseInt(star.nextToken().trim());	
				
				CourseModel AllocateItem = new CourseModel(IndexNumber,CourseCode,CourseName,CourseType,Vacancy,School,AU); 
				AllocateItem.TimeTableList = readTimeTableByCourseIndex(AllocateItem.getIndexNumber());
				
				
				// add to UserModel list

				CourseListing.add(AllocateItem);
		}
		return CourseListing ;
	}
	
	public static ArrayList<TimeTableModel> readTimeTableListing(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList)read(filename);
		ArrayList<TimeTableModel> TimeTableListing = new ArrayList<TimeTableModel>() ;
        for (int i = 1 ; i < stringArray.size() ; i++) {
        	
				String st = (String)stringArray.get(i);
				// get individual 'fields' of the string separated by SEPARATOR
				StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","

				int IndexNumber = Integer.parseInt(star.nextToken().trim());	// first token
				String  Type = star.nextToken().trim();	
				int  Group = Integer.parseInt(star.nextToken().trim());
				String  Day = star.nextToken().trim();	
				String  Time = star.nextToken().trim();	
				String  Venue = star.nextToken().trim();
				
				TimeTableModel TimeTableItem = new TimeTableModel(IndexNumber,Type,Group,Day,Time,Venue); 
				
				// add to UserModel list

				TimeTableListing.add(TimeTableItem);
		}
		return TimeTableListing ;
	}
	
	
	
	// -----------------------------------------
	
	public static ArrayList<AllocatedListingModel> readAllocateListingByStudentID(String StudentID){
		ArrayList<AllocatedListingModel> AList = new ArrayList<AllocatedListingModel>();
		for(int i=0; i< AllocatedListing.size(); i++) {
			
			if(AllocatedListing.get(i).getUserID() == StudentID) {
				AList.add(AllocatedListing.get(i));
			}
		
		}
		return AList;
	}
	public static ArrayList<AllocatedListingModel> readAllocateListingByCourseIndex(int CourseIndex){
		ArrayList<AllocatedListingModel> AList = new ArrayList<AllocatedListingModel>();
		for(int i=0; i< AllocatedListing.size(); i++) {
			
			if(AllocatedListing.get(i).getCourseIndex() == CourseIndex) {
				AList.add(AllocatedListing.get(i));
			}
		
		}
		return AList;
	}
	
	public static ArrayList<WaitListingModel> readAllWaitListingByStudentID(String StudentID){
		ArrayList<WaitListingModel> wList = new ArrayList<WaitListingModel>();
		for(int i=0; i< WaitListing.size(); i++) {
			
			if(WaitListing.get(i).getUserID() == StudentID) {
				wList.add(WaitListing.get(i));
			}
		
		}
		return wList;
	}
	
	
	
	public static ArrayList<TimeTableModel> readTimeTableByCourseIndex(int CourseID){
		ArrayList<TimeTableModel> tList = new ArrayList<TimeTableModel>();
		for(int i=0; i< TimeTableListing.size(); i++) {
			
			if(TimeTableListing.get(i).getIndexNumber() == CourseID) {
				tList.add(TimeTableListing.get(i));
			}
		
		}
		return tList;
	}
	
	
	
	
	
	
	
	
	

	// -----------------------------------------
	public static void saveNewStudents(String filename, List al) throws IOException {
		List alw = new ArrayList() ;// to store Professors data

        for (int i = 0 ; i < al.size() ; i++) {
				StudentModel stud = (StudentModel)al.get(i);
				StringBuilder st =  new StringBuilder() ;
				st.append(stud.getUserID().trim());
				st.append(SEPARATOR);
				st.append(stud.getPassword().trim());
				st.append(SEPARATOR);
				st.append(stud.getFirstName());
				st.append(SEPARATOR);
				st.append(stud.getLastName());
				st.append(SEPARATOR);
				st.append(stud.getGender());
				st.append(SEPARATOR);
				st.append(stud.getNationality());
				st.append(SEPARATOR);
				st.append(stud.getUserType());
				st.append(SEPARATOR);
				st.append(stud.getAccessTimeStart());
				st.append(SEPARATOR);
				st.append(stud.getAccessTimeEnd());
				st.append(SEPARATOR);
				st.append(stud.getUserType());
				alw.add(st.toString()) ;
			}
			write(filename,alw);
	}

	
	
  /** Write fixed content to the given file. */
  public static void write(String fileName, List data) throws IOException  {
    PrintWriter out = new PrintWriter(new FileWriter(fileName));

    try {
		for (int i =0; i < data.size() ; i++) {
      		out.println((String)data.get(i));
		}
    }
    finally {
      out.close();
    }
  }

  /** Read the contents of the given file. */
  public static List read(String fileName) throws IOException {
	List data = new ArrayList() ;
    Scanner scanner = new Scanner(new FileInputStream(fileName));
    try {
      while (scanner.hasNextLine()){
        data.add(scanner.nextLine());
      }
    }
    finally{
      scanner.close();
    }
    return data;
  }



}
	
