import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.sql.Timestamp;

public class Main {


    static String dbAddress = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/group35";
    static String dbUsername = "Group35";
    static String dbPassword = "3170group35";
    static Connection conn;
    static int debug =1;
	static Scanner sc = new Scanner(System.in);
    //static int debug =1;

	public static int GetInputInt(int max){
		int res = -1;
		boolean valid = false;
		try{
			String s = sc.next();
			String killEnter = sc.nextLine();
			if(isInteger(s)){
				res = Integer.parseInt(s);
				if(res >= 1 && res <= max) 
					valid = true;
			}
			if(!valid){
				System.out.println("[ERROR]: Invalid input!");
				System.out.println("Please enter [1-" + max + "].");
				res = GetInputInt(max);
			}
		}
		catch(java.util.NoSuchElementException e){
			System.out.println("[ERROR] No input! Please check!");
		}
		return res;
	}
    public static void Choice() throws IOException, FileNotFoundException, SQLException{
    	// boolean tocatch= true;
    	// do{
			System.out.println("Welcome! Who are you?");
			System.out.println("1. An administrator");
			System.out.println("2. A passenger");
			System.out.println("3. A driver");
			System.out.println("4. A manager");
			System.out.println("5. None of the above");

			System.out.println("Please enter [1-5]."); //1-4?? not 1-5??
				
			int choice = GetInputInt(5);

            switch(choice){
                case 1:                
                    adminChoice();                
                    break;
                case 2: 
                    passengerChoice();
                    break;
                case 3:
                    driverChoice();
                    break;
                case 4:
                    managerChoice();
                    break;
                case 5:
                	//tocatch = false;
                    conn.close();
                    System.exit(0);
            }   
    	// }
		// while (tocatch == true);
    }

    public static void adminChoice() throws FileNotFoundException, IOException, SQLException{
        
        String dbAddress = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/group35";
        String dbUsername = "Group35";
        String dbPassword = "3170group35";
        System.out.println("Administrator, what would you like to do?");
        System.out.println("1. Create all tables");
        System.out.println("2. Delete all tables");
        System.out.println("3. Load data");
        System.out.println("4. Check data");
        System.out.println("5. Go back");        
        System.out.println("Please enter [1-5].");
        
        Statement stmt1 = null;
        Statement stmt2 = null;
        Statement stmt3 = null;
        Statement stmt4 = null;
        Statement stmt5 = null;
        Statement stmt6 = null;


        int choice = GetInputInt(5);

        switch(choice){
            case 1:                
                try{
                    //Class.forName("com.mysql.jdbc.Driver");
                    //if(debug==0)
                    stmt1 = conn.createStatement();
                    String sql1 = "CREATE TABLE Driver(" + 
                                "id INTEGER not NULL check(id > 0)," +
                                "name VARCHAR(30) not NULL," +
                                "vehicle_id VARCHAR(6) not NULL," +
                                "driving_years INTEGER not NULL check(driving_years >= 0)," +
                                "PRIMARY KEY ( id ));";
                    String sql2 = "CREATE TABLE Vehicle(" + 
                                "id VARCHAR(6) not NULL," +
                                "model VARCHAR(30) not NULL," +
                                "seats INTEGER not NULL check(seats > 0)," +
                                "PRIMARY KEY ( id ));";
                    String sql3 = "CREATE TABLE Passenger(" + 
                                "id INTEGER not NULL check(id > 0)," +
                                "name VARCHAR(30) not NULL," +
                                "PRIMARY KEY ( id ));";
                    String sql4 = "CREATE TABLE Trip(" + 
                                "id INTEGER not NULL AUTO_INCREMENT," +
                                "driver_id INTEGER not NULL check(driver_id > 0)," +
                                "passenger_id INTEGER not NULL check(passenger_id > 0)," +
                                "start_time TIMESTAMP NULL DEFAULT NULL," +
                                "end_time TIMESTAMP NULL DEFAULT NULL," +
                                "start_location VARCHAR(20) not NULL," +
                                "destination VARCHAR(20) not NULL," +
                                "fee INTEGER check(fee >= 0)," +
                                "PRIMARY KEY ( id ));";
                    String sql5 = "CREATE TABLE Request(" + 
                                "id INTEGER not NULL check(id > 0)," +
                                "passenger_id INTEGER not NULL check(passenger_id > 0)," +
                                "start_location VARCHAR(20) not NULL," +
                                "destination VARCHAR(20) not NULL," +
                                "model VARCHAR(30)," +
                                "passengers INTEGER not NULL check(passengers > 0)," +
                                "taken INTEGER," +
                                "driving_years INTEGER," +   //need to debug
                                "PRIMARY KEY ( id ));";
                    String sql6 = "CREATE TABLE Taxi_stop(" + 
                                "name VARCHAR(20) not NULL," +
                                "location_x INTEGER not NULL," +
                                "location_y INTEGER not NULL," +
                                "PRIMARY KEY ( name ));";
                    stmt1.executeUpdate(sql1);
                    stmt1.executeUpdate(sql2);
                    stmt1.executeUpdate(sql3);
                    stmt1.executeUpdate(sql4);
                    stmt1.executeUpdate(sql5);
                    stmt1.executeUpdate(sql6);
                    System.out.println("Processing...Done! Tables are created!"); 
                }
                // catch(ClassNotFoundException e){
                //     System.out.println("[ERROR]: Java MySQL DB Driver not found!");
                //     //System.exit(0);
                // }
                catch(SQLException e){
                    System.out.println(e);
                    System.out.println("[ERROR]: Tables have been created before!");
                    //System.exit(0);
                }               
                adminChoice();                
                break;
            case 2:    
                try{
                    Class.forName("com.mysql.jdbc.Driver");               
                    stmt1 = conn.createStatement();
                    String sql1 = "DROP TABLE Driver;";
                    String sql2 = "DROP TABLE Vehicle;";
                    String sql3 = "DROP TABLE Passenger;";
                    String sql4 = "DROP TABLE Trip;";
                    String sql5 = "DROP TABLE Request;";
                    String sql6 = "DROP TABLE Taxi_stop;";
                    stmt1.executeUpdate(sql1);
                    stmt1.executeUpdate(sql2);
                    stmt1.executeUpdate(sql3);
                    stmt1.executeUpdate(sql4);
                    stmt1.executeUpdate(sql5);
                    stmt1.executeUpdate(sql6);
                    System.out.println("Processing...Done! Tables are deleted!"); 
                }
                catch(ClassNotFoundException e){
                    System.out.println("[ERROR]: Java MySQL DB Driver not found!");
                    //System.exit(0);
                }
                catch(SQLException e){
                    System.out.println("[ERROR]: Tables are not created yet!");
                    //System.exit(0);
                }
                adminChoice();
                break;
            case 3:
                try{
                    System.out.println("Please enter the folder path.");
                    //Class.forName("com.mysql.jdbc.Driver");
                    String folder_path = sc.next();               
                    String Driver = folder_path + "/" + "drivers.csv";
                    String Passenger = folder_path + "/" + "passengers.csv";
                    String Trip = folder_path + "/" + "trips.csv";
                    String Vehicle = folder_path + "/" + "vehicles.csv";                  
                    String Taxi_stop = folder_path + "/" + "taxi_stops.csv";                  
                    BufferedReader dReader = new BufferedReader(new FileReader(Driver));
                    BufferedReader pReader = new BufferedReader(new FileReader(Passenger));
                    BufferedReader tReader = new BufferedReader(new FileReader(Trip));
                    BufferedReader vReader = new BufferedReader(new FileReader(Vehicle));                    
                    BufferedReader sReader = new BufferedReader(new FileReader(Taxi_stop));                    
                    String sql1 = "INSERT INTO Driver(id, name, vehicle_id, driving_years) VALUES(?,?,?,?);";
                    String sql2 = "INSERT INTO Passenger(id, name) VALUES(?,?);";
                    String sql3 = "INSERT INTO Trip(id, driver_id, passenger_id, start_time, end_time, start_location, destination, fee) VALUES(?,?,?,?,?,?,?,?);";
                    String sql4 = "INSERT INTO Vehicle(id, model, seats) VALUES(?,?,?);";
                    String sql5 = "INSERT INTO Taxi_stop(name, location_x, location_y) VALUES(?,?,?);";
                    String line = "";
                    while ((line = dReader.readLine()) != null) {
                        String[] array = line.split(",");
                        PreparedStatement pstmt = conn.prepareStatement(sql1);
                        pstmt.setInt(1, Integer.parseInt(array[0]));
                        pstmt.setString(2, array[1]);
                        pstmt.setString(3, array[2]);
                        pstmt.setInt(4, Integer.parseInt(array[3]));
                        pstmt.executeUpdate();
                    }           
                    while ((line = pReader.readLine()) != null) {
                        String[] array = line.split(",");
                        PreparedStatement pstmt = conn.prepareStatement(sql2);
                        pstmt.setInt(1, Integer.parseInt(array[0]));
                        pstmt.setString(2, array[1]);
                        pstmt.executeUpdate();
                    }
                    while ((line = vReader.readLine()) != null) {
                        String[] array = line.split(",");
                        PreparedStatement pstmt = conn.prepareStatement(sql4);
                        pstmt.setString(1, array[0]);
                        pstmt.setString(2, array[1]);
                        pstmt.setInt(3, Integer.parseInt(array[2]));
                        pstmt.executeUpdate();
                    }
                    while ((line = tReader.readLine()) != null) {
                        String[] array = line.split(",");
                        PreparedStatement pstmt = conn.prepareStatement(sql3);
                        pstmt.setInt(1, Integer.parseInt(array[0]));
                        pstmt.setInt(2, Integer.parseInt(array[1]));
                        pstmt.setInt(3, Integer.parseInt(array[2]));
                        pstmt.setString(4, array[3]);
                        pstmt.setString(5, array[4]);
                        pstmt.setString(6, array[5]);
                        pstmt.setString(7, array[6]);
                        pstmt.setInt(8, Integer.parseInt(array[7]));
                        pstmt.executeUpdate();
                    }
                    while ((line = sReader.readLine()) != null) {
                        String[] array = line.split(",");
                        PreparedStatement pstmt = conn.prepareStatement(sql5);
                        pstmt.setString(1, array[0]);
                        pstmt.setInt(2, Integer.parseInt(array[1]));
                        pstmt.setInt(3, Integer.parseInt(array[2]));
                        pstmt.executeUpdate();
                    }
                    System.out.println("Processing...Done! Data is loaded!");
                }
                // catch(ClassNotFoundException e){
                //     System.out.println("[ERROR]: Java MySQL DB Driver not found!");
                //     //System.exit(0);
                // }
                catch (FileNotFoundException e) {
                    System.out.println("[ERROR] Invalid folder path."); 
                }               
                catch (SQLException ex){
                    System.out.println("[ERROR]: Tables are not created yet!");
                // System.exit(0);
                }
                adminChoice();                
                break;
            case 4:
                System.out.println("Number of records in each table:"); 
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    stmt1 = conn.createStatement();
                    stmt2 = conn.createStatement();
                    stmt3 = conn.createStatement();
                    stmt4 = conn.createStatement();
                    stmt5 = conn.createStatement();
                    stmt6 = conn.createStatement();
                    String sql1 = "SELECT count(*) FROM Driver";
                    String sql2 = "SELECT count(*) FROM Vehicle";
                    String sql3 = "SELECT count(*) FROM Passenger";
                    String sql4 = "SELECT count(*) FROM Trip";
                    String sql5 = "SELECT count(*) FROM Request";
                    String sql6 = "SELECT count(*) FROM Taxi_stop";
                    ResultSet rs1 = stmt1.executeQuery(sql1);
                    ResultSet rs2 = stmt2.executeQuery(sql2);
                    ResultSet rs3 = stmt3.executeQuery(sql3);
                    ResultSet rs4 = stmt4.executeQuery(sql4);
                    ResultSet rs5 = stmt5.executeQuery(sql5);
                    ResultSet rs6 = stmt6.executeQuery(sql6);
                    rs1.next();
                    rs2.next();
                    rs3.next();
                    rs4.next();
                    rs5.next();
                    rs6.next();
                    System.out.println("Driver: " + rs1.getInt(1));
                    System.out.println("Vehicle: " + rs2.getInt(1));
                    System.out.println("Passenger: " + rs3.getInt(1));
                    System.out.println("Trip: " + rs4.getInt(1));
                    System.out.println("Request: " + rs5.getInt(1));
                    System.out.println("Taxi_stop: " + rs6.getInt(1));
                }
                catch(ClassNotFoundException e){
                    System.out.println("[ERROR]: Java MySQL DB Driver not found!");
                    //System.exit(0);
                }
                catch (SQLException ex) {
                    System.out.println("[ERROR]: Data hasn't been loaded yet!");
                }
                adminChoice();
                break;
            case 5:
                // try{
                //     Class.forName("com.mysql.jdbc.Driver");               
                //     //if(debug==0) conn = DriverManager.getConnection(dbAddress, dbUsername, dbPassword);
                //     stmt1 = conn.createStatement();
                //     String sql = "DROP TABLE Request;";
                //     stmt1.executeUpdate(sql);
                //     System.out.println("Processing...Done! Request tables is deleted!"); 
                //     String sql2 = "CREATE TABLE Request(" + 
                //                 "id INTEGER not NULL check(id > 0)," +
                //                 "passenger_id INTEGER not NULL check(passenger_id > 0)," +
                //                 "start_location VARCHAR(20) not NULL," +
                //                 "destination VARCHAR(20) not NULL," +
                //                 "model VARCHAR(30)," +
                //                 "passengers INTEGER not NULL check(passengers > 0)," +
                //                 "taken INTEGER," +
                //                 "driving_years INTEGER," +   //need to debug
                //                 "PRIMARY KEY ( id ));";
                //     stmt1.executeUpdate(sql2);
                //     System.out.println("Processing...Done! Request Table is created!"); 
                // }
                // catch(ClassNotFoundException e){
                //     System.out.println("[ERROR]: Java MySQL DB Driver not found!");
                //     //System.exit(0);
                // }
                // catch(SQLException e){
                //     System.out.println("[ERROR]: Tables are not created yet!");
                //     //System.exit(0);
                // }
                // adminChoice();
                // break;
                Choice();                
                break;      
        }   
    }

    public static Connection getConnection(){   //throws Exception
		String driver = "com.mysql.jdbc.Driver";
		String dbAddress = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/group35";
		String dbUsername = "Group35";
		String dbPassword = "3170group35";
		Connection con = null;
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(dbAddress,  dbUsername,  dbPassword);
			 return con;
		}
		catch(ClassNotFoundException e){
			 System.out.println("[ERROR] Java MySQL DB is not found!!");
			 System.exit(0);
		}
		catch(SQLException e){
			 System.out.println(e);
		}
		return null;
	}
	 
	//Get function number
	 public static int Finput() {
		boolean tocatch= true;
		int input = 0;
		do {
			try {
				System.out.println("Please enter [1-4]");
				input = GetInputInt(4);
				// input = sc.nextLine();
				// if(!isInteger(input))
				// 	continue
				// input = Integer.parseInt(input); // get input number -- (1-4)
			} 	
			catch (InputMismatchException e) {
				sc.next();
				System.out.println("[ERROR] Invalid Input.");
				continue;
			}
			if ((input == 1 || input == 2 || input == 3 || input == 4)){
		    	tocatch = false;
		    }
		     else  {
		        System.out.println("[ERROR] Invalid Input.");
		    }
		}while (tocatch == true);
		return input;	
		}
	 
	 // Check whether the driver id is valid or not.
	 public static boolean existDID( String input ) {
			try{
				int DriverId = Integer.parseInt( input );
				String query = "SELECT COUNT(*) FROM Driver D WHERE D.id=?;"; 
				PreparedStatement statement = conn.prepareStatement(query);
				statement.setInt(1, DriverId);
				ResultSet resultset = statement.executeQuery();
				if(resultset.next()){
					if (resultset.getInt(1)>= 1){
						return true;
					}
				}
			}
			catch(SQLException e){
				 System.out.println(e);
				 return false;
			}
			return false;
		}

	public static boolean isInteger( String input ) {
		    try {
		        Integer.parseInt( input );
		        return true;
		    }
		    catch( Exception e ) {
		        return false;
		    }
		}
	 
	//Get driver id
	 public static int Fdriverid() {
			boolean tocatch= true;
			int driverid = 0;
			String input;
			String input1;
			do {
				try {
				System.out.println("Please enter your ID.");
				input = sc.next(); // get driver id
				input1 = sc.nextLine(); 
				} 	
				catch (InputMismatchException e) {
					sc.next();
					System.out.println("[ERROR] Invalid ID.");
					continue;
				}
				if (isInteger(input) && input1.isEmpty()== true && existDID(input)){
					driverid = Integer.parseInt( input );
					tocatch = false;
			    }
			     else  {
			        System.out.println("[ERROR] Invalid ID.");
			    }
			}while (tocatch == true);
			return driverid;	
		}
	 
	 //Get coordinates of the driver
	 public static int[] Fcoordinate() {
			boolean tocatch;
			String input1;
			String input2;
			String input3 = null;
			int[] xy = new int[2];
			do {
				tocatch = true;
				try {
				System.out.println("Please enter the coordinates of your location.");
				input1 = sc.next(); 
				input2 = sc.next(); 
				input3 = sc.nextLine(); 
				} 	
				catch (InputMismatchException e) {
					sc.next();
					System.out.println("[ERROR] Invalid coordinates.");
					continue;
				}
				catch (NullPointerException e) {
					System.out.println("[ERROR] Invalid coordinates.");
					continue;
				}
				if (isInteger(input1) && isInteger(input2) && input3.isEmpty()==true){
					int x = Integer.parseInt(input1);
					int y = Integer.parseInt(input2);
					xy[0]= x;
					xy[1]= y;
			    	tocatch = false;
			    }
			     else  {
			         System.out.println("[ERROR] Invalid coordinates.");
			    }
			}while (tocatch == true);
			return xy;	
		}
	 
	 //Get the maximum distance
	 public static int Fmaxdistance() {
			boolean tocatch;
			int input = 0;
			do {
				tocatch = true;
				try {
					System.out.println("Please enter the maximum distance from you to the passenger.");
					input = Integer.parseInt(sc.nextLine()); // get maximum distance -- nonnegative
				}
				catch (InputMismatchException e) {
					sc.next();
					System.out.println("[ERROR] Invalid maximum distance.");
					continue;
				}
				if ((input >= 0)){
			    	tocatch = false;
			    }
			     else  {
			        System.out.println("[ERROR] Invalid maximum distance.");
			     }
			}while (tocatch == true);
			return input;	
		}
	 
	 // Check whether the driver id is valid or not.
	 public static boolean existRID( String input ) {
			try{
				int RequestId = Integer.parseInt( input );
				String query = "SELECT COUNT(*) FROM Request R WHERE R.id=?"; 
				PreparedStatement statement = conn.prepareStatement(query);
				statement.setInt(1, RequestId);
				ResultSet resultset = statement.executeQuery();
				if(resultset.next()){
					if (resultset.getInt(1)>= 1){
						return true;
					}
					}
			}
			catch(SQLException e){
				 System.out.println(e);
				 return false;
			}
			return false;
		}
	 
	//Get request id
	 public static int Frequestid() {
		boolean tocatch= true;
		int requestid = 0;
		String input;
		String input1;
		do {
			try {
			System.out.println("Please enter the request ID.");
			input = sc.next(); // get driver id
			input1 = sc.nextLine(); 
			} 	
			catch (InputMismatchException e) {
				sc.next();
				System.out.println("[ERROR] Invalid request ID.");
				continue;
			}
			if (isInteger(input) && input1.isEmpty()==true && existRID(input)){
				requestid = Integer.parseInt( input );
				tocatch = false;
				  }
			else  {
				System.out.println("[ERROR] Invalid request ID.");
			}
		}while (tocatch == true);
		return requestid;	
	}
		 
	//Choose whether to finish a trip or not
	public static boolean WantToFinish() {
		boolean tocatch = true;
		String input = null;
		String input1 = null;
		boolean choice;
		boolean finish;
		do {
			try {
				System.out.println("Do you wish to finish the trip? [y/n]");
				input = sc.next();
				input1 = sc.nextLine(); 	
			}
			catch (InputMismatchException e) {
			sc.next();
			System.out.println("[ERROR] Invalid input.");
			continue;
			}
			choice = input.equals("y") || input.equals("n");
			if (choice == true && input1.isEmpty()==true){
			tocatch = false;
			}
			else  {
			System.out.println("[ERROR] Invalid input.");
			}
		}while (tocatch == true);
		finish = input.equals("y");
		return finish;	
	}
	
	//Search a Request
	public static void SearchRequest() {
		try{

			//Input information
			int DriverId = Fdriverid();
			int[] xy = Fcoordinate();
			int maxdistance= Fmaxdistance();
			
			String query = "SELECT DISTINCT R.id, P.name, R.passengers, R.start_location, R.destination FROM Driver D, Vehicle V, Request R, Passenger P, Taxi_stop Ts WHERE D.id=? AND R.taken=0 AND D.vehicle_id=V.id AND D.driving_years>=(IFNULL(R.driving_years,0)) AND V.seats>=R.passengers AND ( (LOWER(V.model) LIKE CONCAT('%',R.model,'%')) OR (R.model IS NULL) ) AND R.start_location=Ts.name AND (ABS(Ts.location_x-?)+ABS(Ts.location_y-?)<=?) AND R.passenger_id=P.id;"; 
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, DriverId);
			statement.setInt(2, xy[0]);
			statement.setInt(3, xy[1]);
			statement.setInt(4, maxdistance);
			ResultSet resultset = statement.executeQuery();
			ResultSetMetaData metadata = resultset.getMetaData();
			int numcols = metadata.getColumnCount();
			List<List<String>> result = new ArrayList<List<String>>();  // List of list, one per row
		
			System.out.println("request ID, passenger name, num of passengers, start location, destination");
			while(resultset.next()){
				List<String> row = new ArrayList<String>(numcols); // new list per row
			    int i = 1;
			    while (i <= numcols) {  // don't skip the last column, use <=
			    	if (i==numcols)
			        System.out.print(resultset.getString(i));
			    	else
			    		System.out.print(resultset.getString(i)+ ", ");
			    	row.add(resultset.getString(i++));
			    }
			    result.add(row); // add it to the result
			    System.out.print("\n");
			}
			return;
		}
		catch(SQLException e){
			 System.out.println(e);
		}
		catch(Exception e){
			 System.out.println(e);
		}
	}
	
	public static void TakeRequest() {
		try{
			
			// Input Driver ID
			int DriverId = Fdriverid();
			
			// Check whether the driver has unfinished trips
			String query0 = "SELECT COUNT(*) FROM Trip T WHERE T.driver_id=? AND T.fee IS NULL;"; 
			PreparedStatement statement0 = conn.prepareStatement(query0);
			statement0.setInt(1, DriverId);
			ResultSet resultset0 = statement0.executeQuery();
		    if(resultset0.next()){
				if (resultset0.getInt(1)>= 1){
					System.out.println("[ERROR] You still have an unfinished trip.");
					return;
			}
			}
			
			//Input the request Id until it satisfies
			int RequestId = 0;
			boolean tocatch= true;
			do {
				RequestId = Frequestid();
				String q_rt = "SELECT R.taken FROM Request R WHERE R.id=?;"; 
				PreparedStatement stmt_rt = conn.prepareStatement(q_rt);
				stmt_rt.setInt(1, RequestId);
				ResultSet rs_rt = stmt_rt.executeQuery();
				if(rs_rt.next()){
					if (rs_rt.getInt(1)>= 1){
						System.out.println("[ERROR] The request has been taken.");
						continue;
					}
				}
			
				//Check whether the driver satisfies the request
				String query5 = "SELECT COUNT(*) FROM Driver D, Vehicle V, Request R WHERE R.id=? AND D.id=? AND D.vehicle_id = V.id AND D.driving_years >= (IFNULL(R.driving_years,0)) AND V.seats >= R.passengers AND ( (LOWER(V.model) LIKE CONCAT('%',R.model,'%')) OR (R.model IS NULL) ) ;"; 
				PreparedStatement checkr = conn.prepareStatement(query5);
				checkr.setInt(1, RequestId);
				checkr.setInt(2, DriverId);
				ResultSet rs_r = checkr.executeQuery();
				if(rs_r.next()){
					if (rs_r.getInt(1)>= 1){
						tocatch = false;
					}
					else 
						System.out.println("[ERROR] You do not satisfy all the criteria of the request.");
				}
			}while (tocatch == true);

			// Retrieve the information with start_time	
			String query2 = "SELECT P.name, R.start_location, R.destination, R.passenger_id, (SELECT NOW())AS Time FROM Request R, Passenger P WHERE R.id=? AND R.passenger_id = P.id;"; 
			PreparedStatement statement2 = conn.prepareStatement(query2);
			statement2.setInt(1, RequestId);
			ResultSet resultset2 = statement2.executeQuery();
			String PassengerName = "";
			String StartLocation = "";
			String Destination = "";
			int PassengerId = 0;
			String StartTime = "";
			if(resultset2.next()){
			PassengerName = resultset2.getString(1);
			StartLocation = resultset2.getString(2);
			Destination = resultset2.getString(3);
			PassengerId = resultset2.getInt(4);
			StartTime = resultset2.getString(5);
			}
			
			//Update Request Mark taken
			String query1 = "UPDATE Request R SET R.taken=1 WHERE R.id =?;";
			PreparedStatement update = conn.prepareStatement(query1);
			update.setInt(1, RequestId);
			update.executeUpdate();  
			
			//Update Trip information
			String query3 = "INSERT INTO Trip (driver_id, passenger_id, start_location, destination, start_time) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement create = conn.prepareStatement(query3);
			create.setInt(1, DriverId);
			create.setInt(2, PassengerId);
			create.setString(3, StartLocation);
			create.setString(4, Destination);
			create.setString(5, StartTime.replaceAll("\\D+",""));
			create.executeUpdate();
			
			//Get the new Trip Id
			String query4 = "SELECT T.id FROM Trip T WHERE T.driver_id=? AND T.fee IS NULL;"; 
			PreparedStatement statement4 = conn.prepareStatement(query4);
			statement4.setInt(1, DriverId);
			ResultSet resultset4 = statement4.executeQuery();  
			int TripId = 0;
			if(resultset4.next()){
			TripId = resultset4.getInt(1);
			}
			
			//Display the information
			System.out.println("Trip ID, Passenger name, Start");
			System.out.println(TripId +", "+ PassengerName+", "+ StartTime);			
		}
		catch(SQLException e){
			 System.out.println(e);
		}
		catch(Exception e){
			 System.out.println(e);
		}
		return;
	}
	
	public static void FinishTrip() {
		try{
			int DriverId = 0;
			boolean tocatch= true;
			
			//Input driver ID until has an unfinished trip
			do{
				DriverId = Fdriverid();
				String query0 = "SELECT COUNT(*) FROM Trip T WHERE T.driver_id=? AND T.fee IS NULL;"; 
				
				PreparedStatement statement0 = conn.prepareStatement(query0);
				statement0.setInt(1, DriverId);
				ResultSet resultset0 = statement0.executeQuery();
				if(resultset0.next()){
					if (resultset0.getInt(1) == 1){
						tocatch = false;
					}else {
						System.out.println("[ERROR] You don't have an unfinished trip.");
					}
				}
			}while (tocatch == true);
			
			String query = "SELECT T.id, T.passenger_id, T.start_time, P.name FROM Trip T, Passenger P WHERE T.driver_id=? AND T.fee IS NULL AND T.passenger_id=P.id;";
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, DriverId);
			ResultSet resultset = statement.executeQuery();		
			int TripId = 0;
			int PassengerId = 0;
			String StartTime = "";
			String PassengerName = "";
			if(resultset.next()){
			TripId = resultset.getInt(1);
			PassengerId = resultset.getInt(2);
			StartTime = resultset.getString(3);
			PassengerName = resultset.getString(4);
			}
			
			//Display the unfinished trip information 
			System.out.println("Trip ID, Passenger ID, Start");
			System.out.println(TripId +", "+ PassengerId+", "+ StartTime);
			
			//Input whether the driver want to finish the trip or not
			Boolean YorN = WantToFinish();
			if (YorN == false){
				return;
			} 
		
			//Update the trip end time
			String query1 = "UPDATE Trip T SET T.end_time=(SELECT NOW()) WHERE T.id =?;";
			PreparedStatement update = conn.prepareStatement(query1);
			update.setInt(1, TripId);
			update.executeUpdate(); 

			//Calculate the fee and retrieve the information
			String query4 = "SELECT T.end_time, TIMESTAMPDIFF(minute,T.start_time, T.end_time) AS fee FROM Trip T WHERE T.id=?;"; 
			PreparedStatement statement4 = conn.prepareStatement(query4);
			statement4.setInt(1, TripId);
			ResultSet resultset4 = statement4.executeQuery();  
			String EndTime= "";
			int Fee= 0;
			if (resultset4.next()){
				EndTime = resultset4.getString(1);
				Fee = resultset4.getInt(2);
			}
			
			//Update the trip fee
			String query2 = "UPDATE Trip T SET T.fee=? WHERE T.id =?;";
			PreparedStatement update2 = conn.prepareStatement(query2);
			update2.setInt(1, Fee);
			update2.setInt(2, TripId);
			update2.executeUpdate(); 
			
			//Display the finished trip information 
			System.out.println("Trip ID, Passenger Name, Start, End, Fee");
			System.out.println(TripId +", "+ PassengerName+", "+ StartTime+", "+ EndTime+", "+ Fee);
			
		}
		catch(SQLException e){
			 System.out.println(e);
		}
		catch(Exception e){
			 System.out.println(e);
		}
		return;
	}
	
	public static void driverChoice() throws FileNotFoundException, IOException, SQLException{
	    
		System.out.print("Driver, what would you like to do?\n"+"1. Search requests\n"
	    +"2. Take a request\n"+"3. Finish a trip\n"+"4. Go back\n");  
	    
		int input = 0;
		input = Finput();
		 switch (input) {
		    case 1:
		        SearchRequest();
		        driverChoice();
		        break;
		    case 2:
		    	TakeRequest();
		    	driverChoice();
		        break;
		    case 3:
		    	FinishTrip();
		    	driverChoice();
		        break;
		    case 4:
		    	Choice();
		        break;
		    default:
		        System.out.println("Invalid Input");
		        break;
		    }
	}

    public static void requestARide() throws IOException, SQLException{
        
        String dbAddress = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/group35";
        String dbUsername = "Group35";
        String dbPassword = "3170group35";

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Statement stmt = conn.createStatement();


            System.out.println("Please enter your ID.");
            String pid = sc.nextLine(); // get the passenger id.
            while(pid.isEmpty()) {
            	System.out.println("[ERROR] No Input!\n" + "Please enter your ID.");
                pid = sc.nextLine();
            }
            
			while(true){
            // check whether the passenger id is valid
				String sql1 = "SELECT id FROM Passenger WHERE id = " + pid + ";"; 
				sql1 = String.format(sql1, pid);
				ResultSet rs1 = stmt.executeQuery(sql1);
				if(!rs1.isBeforeFirst()){    
					System.out.println("[ERROR] Invalid Passenger id!");
					System.out.println("Please enter your ID.");
					pid = sc.nextLine();
					//System.out.println(sql1);
					rs1= stmt.executeQuery(sql1);
				} 
				else
					break;
			}
           
           
           //check whether there is an unfinished tip or request
		   while(true){
				String sql2 = "SELECT r.Passenger_id FROM Request r, Trip t " + "WHERE t.passenger_id = r.passenger_id " +
						"AND r.Passenger_id = %s AND (end_time = null OR taken = 0);";
				sql2 = String.format(sql2, pid);
				ResultSet rs2 = stmt.executeQuery(sql2);
				//System.out.println("111\n");
				if(rs2.isBeforeFirst()){
					System.out.println("[ERROR] You still have an unfinished request or trip!");
					System.out.println("Please enter your ID.");
					pid = sc.nextLine();
					sql2 = "SELECT r.Passenger_id FROM Request r, Trip t " + "WHERE t.passenger_id = r.passenger_id " +
						"AND r.Passenger_id = %s AND (end_time = null OR taken = 0);";
					sql2 = String.format(sql2, pid);
					//System.out.println(sql2);
					rs2 = stmt.executeQuery(sql2);
				}
				else
					break;
		   }
            
            // get the number of passengers
            System.out.println("Please enter your the number of passengers."); 
            //if the input is a string???
            int no = Integer.parseInt(sc.nextLine());

            while(no < 1 || no > 8){
                System.out.println("[ERROR] Input is out of range!\n"+
                        "Please enter your the number of passengers."); //better description
                no = Integer.parseInt(sc.nextLine());
            }


			String start = "", dest = "";
			while(true){
				//get correct start location
				System.out.println("Please enter the start location.");
				if(sc.hasNextLine()){
					start = sc.nextLine();
					int startLength = start.length();
					//start = start.toLowerCase();

					String sql4 = "SELECT name FROM Taxi_stop WHERE LOWER(name) = '%s';";
					sql4 = String.format(sql4,start.toLowerCase());
					ResultSet rs4 = stmt.executeQuery(sql4);

					if(start.isEmpty() || startLength > 20 || !rs4.isBeforeFirst()) {
						System.out.println("[ERROR] Invalid Input!");
						continue;
					}
				}
				else{
					System.out.println("[ERROR] No input! Please check!");
				}

				//get correct destination
				while(true){
					System.out.println("Please enter the destination.");
					if(sc.hasNextLine()){
						dest = sc.nextLine();
						int destLength = dest.length();
						//dest = dest.toLowerCase();

						String sql4 = "SELECT name FROM Taxi_stop WHERE LOWER(name) = '%s';";
						sql4 = String.format(sql4,dest.toLowerCase());
						ResultSet rs4 = stmt.executeQuery(sql4);

						if(dest.isEmpty() || destLength > 20 || !rs4.isBeforeFirst()) {
							System.out.println("[ERROR] Invalid Input!");
							continue;
						}
						else
							break;
					}
					else{
						System.out.println("[ERROR] No input! Please check!");
						break;
					}
				}

				if(start.equalsIgnoreCase(dest)){
					System.out.println("[ERROR] The start location and destination cannot be the same!");
				}
				else
					break;
			}

            //construct the main sql for counting the # of qualified of Drivers
            String mainSql = "SELECT COUNT(d.id) AS noOfDriver FROM Driver d, Vehicle v " + "WHERE d.vehicle_id = v.id " +
                    "AND v.seats >= %d" ; 
            mainSql = String.format(mainSql, no);

            System.out.println("Please enter model. (Press enter to skip)");
            String modelInput = sc.nextLine();
            String model, model1;
            int modelLength = modelInput.length();
            // get the model of the car
            
            while (true) {
            	if(modelInput.isEmpty()) {
	                model = null;
	                break;
            	}
	            else if(modelLength <= 30 ){
                    model = modelInput;	                	               
	                model1 =  "%" + modelInput + "%";
	                //add model criteria into main SQL query
	                mainSql += " AND LOWER(v.model) LIKE LOWER('%s')"; 
	                mainSql = String.format(mainSql, model1);
	                break;
	            }
	            else if(modelLength > 30) { 
	            	 System.out.println("[ERROR] Invalid model type!\n"
	                         + "Please enter model. (Press enter to skip)");
	                 modelInput = sc.nextLine(); 
	            }	        
            }
            
            System.out.println("Please enter the minimum driving years of the Driver. (Press enter to skip)");
            String driYrInput = sc.nextLine();
            //System.out.println("driYrInput: " + driYrInput);
            // get the minimum driving year
            
            //need to be updated, while input 0 return error, print enter do not add to the mainsql
            if(!driYrInput.isEmpty()){
                mainSql += " AND d.driving_years >= " + driYrInput + ";";
                //System.out.println(mainSql);
                // mainSql = String.format(mainSql, driYrInput);
            }
            else {
            	mainSql += ";";
            	driYrInput = null;
            }
            
            //System.out.println(mainSql);
          //execute final query
            int count = -1;
            ResultSet mainRs = stmt.executeQuery(mainSql);// no matching count is null or 0? 
            if(!mainRs.isBeforeFirst()){
                System.out.println("[ERROR] No matching Driver! Adjust the criteria!");
                requestARide();
            }
            else{
                
                while(mainRs.next()){
                    count = mainRs.getInt("noOfDriver");
                }
                

               if( count == 0 ){
                    System.out.println("[ERROR] No matching Driver! Adjust the criteria!");
                    requestARide();
               }
               else{
                    String put = "INSERT INTO Request VALUES (?,?,?,?,?,?,?,?);";
                    PreparedStatement pstmt = conn.prepareStatement(put);
                        
                        // set the rid as the maximum rid+1
                    String sql3 = "SELECT Max(r.id) FROM Request r;";
                    ResultSet rs3 = stmt.executeQuery(sql3);
                    int maxId = 0;
                    if(!rs3.isBeforeFirst()){
                        maxId = 1;
                    }
                    else{
                        while(rs3.next()){
                        maxId = rs3.getInt(1);
                        }
                            maxId++; 
                    }
                    
                    pstmt.setInt(1,maxId); //insert rid  0                 
                    pstmt.setInt(2, Integer.parseInt(pid));
                    pstmt.setString(3, start);
                    pstmt.setString(4, dest);
                    if(model != null) {
                        pstmt.setString(5, model);  //change inport value!!!!!
                    }
                    else {
                        pstmt.setNull(5, Types.VARCHAR);//no input, set null
                    }
                    pstmt.setInt(6, no);
                        
                    // insert 0 as the request is not taken
                    pstmt.setInt(7, 0); 
                    if(driYrInput != null) {
                        pstmt.setInt(8, Integer.parseInt(driYrInput));
                    }
                    else {
                        pstmt.setNull(8, Types.INTEGER);
                    }
                    pstmt.executeUpdate();

                    System.out.println("Your request is placed. " + count + " Drivers are able to take the request.\n");                  
                    }
                    passengerChoice();
            }
   
        }           
        catch(ClassNotFoundException e){
                        System.out.println("[ERROR]: Java MySQL DB Driver not found!");
                        requestARide();

                    }
        catch (SQLException ex) {
                        System.out.println(ex);
                        System.out.println("[ERROR]: SQL is incorrect!");
                        requestARide();                     
            }   

    }
    
    
    
    public static void checkTripRecord() throws IOException, SQLException{
        String dbAddress = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/group35";
        String dbUsername = "Group35";
        String dbPassword = "3170group35";

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Statement stmt = conn.createStatement();

            System.out.println("Please enter your ID.");
            int pid = Integer.parseInt(sc.nextLine());

            String sql1 = "SELECT id FROM Passenger WHERE id=%d;"; // check whether the passenger id is valid
            sql1 = String.format(sql1, pid);
            ResultSet rs1 = stmt.executeQuery(sql1); 
            while(!rs1.isBeforeFirst()){    
                System.out.println("[ERROR] Invalid Passenger id!\n");
                System.out.println("Please enter your ID.");
                pid = Integer.parseInt(sc.nextLine());;

                sql1 = String.format(sql1, pid);
                rs1= stmt.executeQuery(sql1);
            } 

            System.out.println("Please enter the start date.");
            String startDate = sc.nextLine();
            startDate += " 00:00:00";

            System.out.println("Please enter the end date.");
            String endDate = sc.nextLine();
            endDate += " 23:59:59";

            System.out.println("Please enter the destination.");
            String dest = sc.nextLine();
            
            //Execute 
            String mainSql = "SELECT t.id tid, d.name dname, v.id vid, v.model vmodel, t.start_time stime, t.end_time etime, t.fee fee, t.start_location slocation, t.destination destination"+
            		" FROM Driver d, Vehicle v, Trip t"+
            		" WHERE d.Vehicle_id = v.id AND d.id = t.Driver_id" + 
            		" AND t.Passenger_id = %d AND LOWER(t.destination) = LOWER('%s')"+
            		" AND t.start_time > '%s' AND t.end_time < '%s'"+
            		" ORDER BY t.start_time DESC;";

            mainSql = String.format(mainSql, pid, dest, startDate, endDate);
            
            ResultSet finalRs = stmt.executeQuery(mainSql);
            if(!finalRs.isBeforeFirst()) {
            	System.out.println("[ERROR] No Proper Record!");
                passengerChoice();
            }
            else {
            	System.out.println("Trip_id, Driver Name, Vehicle ID, Veicle Model, Start, End, Fee, Start Location, Destination\n");
            	while(finalRs.next()) {
            		int tid = finalRs.getInt("tid");
            		String dname = finalRs.getString("dname");
            		String vid = finalRs.getString("vid");
            		String model = finalRs.getString("vmodel");
            		java.sql.Timestamp starttime = finalRs.getTimestamp("stime");
            		java.sql.Timestamp endtime = finalRs.getTimestamp("etime");
            		int fee = finalRs.getInt("fee");
            		String sLocation = finalRs.getString("slocation");
            		String dLocation = finalRs.getString("destination");
            		
            		System.out.println(tid + ", " + dname + ", " + vid + ", " + model 
            				+ "," + starttime + ", " + endtime + ", " + fee + ", " + sLocation + ", " + dLocation + "\n");
            	}
                passengerChoice();

            }
                        
        }
        catch(ClassNotFoundException e){
                        System.out.println("[ERROR]: Java MySQL DB Driver not found!");
                        checkTripRecord();
                    }
        catch (SQLException ex) {
                        System.out.println(ex);
                        System.out.println("[ERROR]: SQL is incorrect!");
                        checkTripRecord();
            }
}
    
    public static void passengerChoice() throws IOException, SQLException{
        System.out.println("Passenger, what would you like to do?\n" + 
                "1. Request a ride\n" + "2. Check trip records\n" + "3. Go back\n" + "Please enter [1-3].");
        
        int input = GetInputInt(3);
        
        switch(input){
            case 1 :
                requestARide();
                break;
            case 2 :
                checkTripRecord();
                break;
            case 3 :
                Choice();
                break;
        }

    }

    public static int Minput() {
			boolean tocatch= true;
			int input = 0;
			do {
				try {
				System.out.println("Please enter [1-2]");
				input = GetInputInt(2);
				//input = Integer.parseInt(sc.nextLine()); // get input number -- (1-2)
				} 	
				catch (InputMismatchException e) {
					sc.next();
					System.out.println("[ERROR] Invalid Input.");
					continue;
				}
				if ((input == 1 || input == 2 )){
			    	tocatch = false;
			    }
			     else  {
			        System.out.println("[ERROR] Invalid Input.");
			    }
			}while (tocatch == true);
			return input;	
	}	
	
	 //Get the minimum distance
	 public static int Mmindistance() {
			boolean tocatch;
			int input = 0;
			do {
				tocatch = true;
				try {
					System.out.println("Please enter the minimum distance.");
					input = Integer.parseInt(sc.nextLine());; // get minimum distance -- nonnegative 	
				}
				catch (InputMismatchException e) {
					sc.next();
					System.out.println("[ERROR] Invalid minimum distance.");
					continue;
				}
				if ((input >= 0)){
			    	tocatch = false;
			    }
			     else  {
			        System.out.println("[ERROR] Invalid minimum distance.");
			     }
			}while (tocatch == true);
			return input;	
		}
	 
	 //Get the maximum distance
	 public static int Mmaxdistance( int min) {
			boolean tocatch;
			int input = 0;
			do {
				tocatch = true;
				try {
					System.out.println("Please enter the maximum distance.");
					input = Integer.parseInt(sc.nextLine()); // get maximum distance -- nonnegative
				}
				catch (InputMismatchException e) {
					sc.next();
					System.out.println("[ERROR] Invalid maximum distance.");
					continue;
				}
				if ((input >= 0) && (input>= min)){
			    	tocatch = false;
			    }
			     else  {
			        System.out.println("[ERROR] Invalid maximum distance.");
			     }
			}while (tocatch == true);
			return input;	
		}
	 
	public static void FindTrip() {
		try{

			//Input information
			int mind= Mmindistance();
			int maxd= Mmaxdistance(mind);
			
			String query = "SELECT DISTINCT T.id, D.name, P.name, T.start_location, T.destination, T.fee FROM Trip T, Driver D, Passenger P, Taxi_stop Ts, Taxi_stop Td WHERE T.fee IS NOT NULL AND T.driver_id=D.id AND T.passenger_id=P.id AND LOWER(T.start_location)=LOWER(Ts.name) AND LOWER(T.destination)=LOWER(Td.name) AND (ABS(Ts.location_x-Td.location_x)+ABS(Ts.location_y-Td.location_y))>=? AND (ABS(Ts.location_x-Td.location_x)+ABS(Ts.location_y-Td.location_y))<=?;"; 
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, mind);
			statement.setInt(2, maxd);
			
			ResultSet resultset = statement.executeQuery();
			ResultSetMetaData metadata = resultset.getMetaData();
			int numcols = metadata.getColumnCount();
			List<List<String>> result = new ArrayList<List<String>>();  // List of list, one per row
		
			System.out.println("trip id, driver name, passenger name, start location, destination, duration");
			while(resultset.next()){
				List<String> row = new ArrayList<String>(numcols); // new list per row
			    int i = 1;
			    while (i <= numcols) {  // don't skip the last column, use <=
			    	if (i==numcols)
			        System.out.print(resultset.getString(i));
			    	else
			    		System.out.print(resultset.getString(i)+ ", ");
			    	row.add(resultset.getString(i++));
			    }
			    result.add(row); // add it to the result
			    System.out.print("\n");
			}
			return;
		}
		catch(SQLException e){
			 System.out.println(e);
		}
		catch(Exception e){
			 System.out.println(e);
		}
	}
	
	public static void managerChoice() throws FileNotFoundException, IOException, SQLException{
	    System.out.print("Manager, what would you like to do?\n"+"1. Find trips\n"+"2. Go back\n");  
	    
		int input = 0;
		input = Minput();
		 switch (input) {
		    case 1:
		        FindTrip();
		        managerChoice();
		        break;
		    case 2:
		    	Choice();
		        break;
		    default:
		        System.out.println("Invalid Input");
		        break;
		    }
	}

    public static void main(String[] args) throws IOException, FileNotFoundException, SQLException {
        conn = DriverManager.getConnection(dbAddress, dbUsername, dbPassword);
        Choice();
    }
}
