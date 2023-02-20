import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This application will keep track of things like what classes are offered by
 * the school, and which students are registered for those classes and provide
 * basic reporting. This application interacts with a database to store and
 * retrieve data.
 */
public class SchoolManagementSystem {

    public static void getAllClassesByInstructor(String first_name, String last_name) {
        Connection connection = null;
        Statement sqlStatement = null;

        try {
             /* Your logic goes here */
            throw new SQLException(); // REMOVE THIS (this is just to force it to compile)
            
        } catch (SQLException sqlException) {
            System.out.println("Failed to get class sections");
            System.out.println(sqlException.getMessage());

        } finally {
            try {
                if (sqlStatement != null)
                    sqlStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

    }

    public static void submitGrade(String studentId, String classSectionID, String grade) {
        Connection connection = null;
        Statement sqlStatement = null;

        try {
        	connection = Database.getDatabaseConnection();
        	sqlStatement = connection.createStatement();
        	
        	switch(grade) {
        	case "A":
        		grade = "1";
        	case "B":
        		grade = "2";
        	case "C":
        		grade = "3";
        	case "D":
        		grade = "4";
        	case "F":
        		grade = "5";
        	}

        	String sql = "UPDATE class_registrations " +
        			"SET grade_id = %s " +
        			"WHERE student_id = %s AND class_section_id = %s;";
        	
        	sql = String.format(sql, grade, studentId, classSectionID);
        	
        	sqlStatement.executeUpdate(sql);
        	
        	System.out.println("Grade has been submitted!");

        } catch (SQLException sqlException) {
            System.out.println("Failed to submit grade");
            System.out.println(sqlException.getMessage());

        } finally {
            try {
                if (sqlStatement != null)
                    sqlStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    
    public static void registerStudent(String studentId, String classSectionID) {
        Connection connection = null;
        Statement sqlStatement = null;

        try {
        	
        	connection = Database.getDatabaseConnection();
        	sqlStatement = connection.createStatement();
        	
        	String sql = "INSERT INTO class_registrations (student_id, class_section_id) \n"
        			+ "VALUES ('%s', '%s'); \n";
        	sql = String.format(sql, studentId, classSectionID);
        	
        	sqlStatement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        	
        	ResultSet rs = sqlStatement.getGeneratedKeys();
        	
        	while(rs.next()) {
        		int classRegID = rs.getInt(1);
        		System.out.println("Class Registration ID | Student ID | Class Section ID");
        		System.out.println("--------------------------------------------------------");
        		System.out.println(classRegID + " | " + studentId + " | " + classSectionID);
        	}
//        	
            //throw new SQLException(); // REMOVE THIS (this is just to force it to compile)
        } catch (SQLException sqlException) {
            System.out.println("Failed to register student");
            System.out.println(sqlException.getMessage());

        } finally {
            try {
                if (sqlStatement != null)
                    sqlStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
    

    public static void deleteStudent(String studentId) {
        Connection connection = null;
        Statement sqlStatement = null;

        try {
        	connection = Database.getDatabaseConnection();
        	sqlStatement = connection.createStatement();
        	
        	String sql = "DELETE FROM `cs_hu_310_final_project`.`students`\r\n"
        			+ "WHERE student_id = %s;";
        	sql = String.format(sql, studentId);
        	
        	sqlStatement.executeUpdate(sql);
        	
//        		System.out.println("--------------------------------------------------------");
    		System.out.println("Student with id: " + studentId + " was deleted");
        } catch (SQLException sqlException) {
            System.out.println("Failed to delete student");
            System.out.println(sqlException.getMessage());

        } finally {
            try {
                if (sqlStatement != null)
                    sqlStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }


    public static void createNewStudent(String firstName, String lastName, String birthdate) {
        Connection connection = null;
        Statement sqlStatement = null;

        try {
        	connection = Database.getDatabaseConnection();
        	sqlStatement = connection.createStatement();
        	
        	String sql = "INSERT INTO `students` (`first_name`, `last_name`, `birthdate`) "
        			+ "VALUES ('%s', '%s', '%s'); \n";
        	sql = String.format(sql, firstName, lastName, birthdate);
        	
        	sqlStatement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        	ResultSet rs = sqlStatement.getGeneratedKeys();
        	
        	while(rs.next()) {
        		int studentID = rs.getInt(1);
        		System.out.println("Student ID | First Name | Last Name | Birthday");
        		System.out.println("--------------------------------------------------------");
        		System.out.println(studentID + " | " + firstName + " | " + lastName + " | " + birthdate);
        	}
        } catch (SQLException sqlException) {
            System.out.println("Failed to create student");
            System.out.println(sqlException.getMessage());

        } finally {
            try {
                if (sqlStatement != null)
                    sqlStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

    }

    
    public static void instructorClasses() {
        Connection connection = null;
        Statement sqlStatement = null;

        try {
        	
        	connection = Database.getDatabaseConnection();
        	sqlStatement = connection.createStatement();
        	
        	String sql = "SELECT * FROM instructors i "
        			+ "JOIN academic_titles at ON at.academic_title_id = i.academic_title_id "
        			+ "JOIN class_sections cs ON cs.class_section_id = i.class_section_id  "
        			+ "JOIN terms t ON t.term_id = cs.term_id "
        			+ "JOIN classes c ON c.class_id = cs.class_id ";
        	
        	ResultSet rs = sqlStatement.executeQuery(sql);
        	
        	System.out.println("First Name | Last Name | Title | Code | Name | Term");
        	
        	System.out.println("----------------------------------------------------");
        	while(rs.next()) {
        		String fName = rs.getString("i.firstName");
        		String lName = rs.getString("i.last_name");
        		String title = rs.getString("at.title");
        		String code = rs.getString("c.code");
        		String className = rs.getString("c.name");
        		String term = rs.getString("t.name");
        		
        		System.out.println(fName + " | " + lName + " | " + title +
        				" | " + code + " | " + className + " | " + term);
        	}
        	
        } catch (SQLException sqlException) {
            System.out.println("Failed to get class sections");
            System.out.println(sqlException.getMessage());

        } finally {
            try {
                if (sqlStatement != null)
                    sqlStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }    
    
    
    
    public static void listAllClassRegistrations() {
        Connection connection = null;
        Statement sqlStatement = null;

        try {
        	
        	connection = Database.getDatabaseConnection();
        	sqlStatement = connection.createStatement();
        	
        	String sql = "SELECT * FROM class_registrations cr "
        			+ "JOIN class_sections cs ON cs.class_section_id = cr.class_section_id "
        			+ "JOIN terms t ON t.term_id = cs.term_id "
        			+ "JOIN students s ON s.student_id = cr.student_id "
        			+ "JOIN classes c ON c.class_id = cs.class_id "
        			+ "JOIN grades g ON g.grade_id = cr.grade_id";
        	
        	ResultSet rs = sqlStatement.executeQuery(sql);
        	
        	System.out.println("Student ID | Class Section ID | First Name | Last Name" +
        				"| Code | Name | term | Letter Grade");
        	
        	System.out.println("----------------------------------------------------");
        	while(rs.next()) {
        		String sID = rs.getString("s.student_id");
        		String csID = rs.getString("cs.class_section_id");
        		String Fname = rs.getString("s.first_name");
        		String Lname = rs.getString("s.last_name");
        		String code = rs.getString("c.code");
        		String name = rs.getString("c.name");
        		String term = rs.getString("t.name");
        		String lG = rs.getString("g.letter_grade");
        		
        		System.out.println(sID + " | " + csID + " | " + Fname +
        				" | " + Lname + " | " + code + " | " + name +
        				" | " + term + " | " + lG);
        		
        	}
        	
        } catch (SQLException sqlException) {
            System.out.println("Failed to get class sections");
            System.out.println(sqlException.getMessage());

        } finally {
            try {
                if (sqlStatement != null)
                    sqlStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    
    public static void listAllClassSections() {
        Connection connection = null;
        Statement sqlStatement = null;

        try {
        	
        	connection = Database.getDatabaseConnection();
        	sqlStatement = connection.createStatement();
        	
        	String sql = "SELECT * FROM class_sections cs "
        			+ "JOIN classes c ON c.class_id = cs.class_id "
        			+ "JOIN terms t ON t.term_id = cs.term_id";
        	
        	ResultSet rs = sqlStatement.executeQuery(sql);
        	
        	System.out.println("Class Section ID | Code | Name | term");
        	System.out.println("----------------------------------------------------");
        	while(rs.next()) {
        		String csID = rs.getString(1);
        		String code = rs.getString("c.code");
        		String name = rs.getString("c.name");
        		String term = rs.getString("t.name");
        		
        		System.out.println(csID + " | " + code + " | " + name + " | " + term);
        		
        	}
        	
        } catch (SQLException sqlException) {
            System.out.println("Failed to get class sections");
            System.out.println(sqlException.getMessage());

        } finally {
            try {
                if (sqlStatement != null)
                    sqlStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void listAllClasses() {
        Connection connection = null;
        Statement sqlStatement = null;

        try {
        	connection = Database.getDatabaseConnection();
        	sqlStatement = connection.createStatement();
        	
        	String sql = "SELECT * FROM classes";
        	
        	ResultSet rs = sqlStatement.executeQuery(sql);
        	
        	System.out.println("class ID | Code | Name | Description");
        	System.out.println("----------------------------------------------------");
        	while(rs.next()) {
        		String classID = rs.getString(1);
        		String code = rs.getString(2);
        		String name = rs.getString(3);
        		String description = rs.getString(4);
        		
        		System.out.println(classID + " | " + description + " | " + code + " | " + name);
        		
        	}
        } catch (SQLException sqlException) {
            System.out.println("Failed to get students");
            System.out.println(sqlException.getMessage());

        } finally {
            try {
                if (sqlStatement != null)
                    sqlStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }


    public static void listAllStudents() {
        Connection connection = null;
        Statement sqlStatement = null;

        try {
        	connection = Database.getDatabaseConnection();
        	sqlStatement = connection.createStatement();
        	
        	String sql = "SELECT * FROM students";
        	
        	ResultSet rs = sqlStatement.executeQuery(sql);
        	
        	System.out.println("student ID | First Name | Last Name | Birthdate");
        	System.out.println("----------------------------------------------------");
        	while(rs.next()) {
        		String studentID = rs.getString(1);
        		String Fname = rs.getString(2);
        		String Lname = rs.getString(3);
        		String birthday = rs.getString(4);
        		
        		System.out.println(studentID + " | " + Fname + " | " + Lname + " | " + birthday);
        		
        	}
        	
        } catch (SQLException sqlException) {
            System.out.println("Failed to get students");
            System.out.println(sqlException.getMessage());

        } finally {
            try {
                if (sqlStatement != null)
                    sqlStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    /***
     * Splits a string up by spaces. Spaces are ignored when wrapped in quotes.
     *
     * @param command - School Management System cli command
     * @return splits a string by spaces.
     */
    public static List<String> parseArguments(String command) {
        List<String> commandArguments = new ArrayList<String>();
        Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(command);
        while (m.find()) commandArguments.add(m.group(1).replace("\"", ""));
        return commandArguments;
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the School Management System");
        System.out.println("-".repeat(80));

        Scanner scan = new Scanner(System.in);
        String command = "";

        do {
            System.out.print("Command: ");
            command = scan.nextLine();
            ;
            List<String> commandArguments = parseArguments(command);
            command = commandArguments.get(0);
            commandArguments.remove(0);

            if (command.equals("help")) {
                System.out.println("-".repeat(38) + "Help" + "-".repeat(38));
                System.out.println("test connection \n\tTests the database connection");

                System.out.println("list students \n\tlists all the students");
                System.out.println("list classes \n\tlists all the classes");
                System.out.println("list class_sections \n\tlists all the class_sections");
                System.out.println("list class_registrations \n\tlists all the class_registrations");
                System.out.println("list instructor <first_name> <last_name>\n\tlists all the classes taught by that instructor");


                System.out.println("delete student <studentId> \n\tdeletes the student");
                System.out.println("create student <first_name> <last_name> <birthdate> \n\tcreates a student");
                System.out.println("register student <student_id> <class_section_id>\n\tregisters the student to the class section");

                System.out.println("submit grade <studentId> <class_section_id> <letter_grade> \n\tcreates a student");
                System.out.println("help \n\tlists help information");
                System.out.println("quit \n\tExits the program");
            } else if (command.equals("test") && commandArguments.get(0).equals("connection")) {
                Database.testConnection();
            } else if (command.equals("list")) {
                if (commandArguments.get(0).equals("students")) listAllStudents();
                if (commandArguments.get(0).equals("classes")) listAllClasses();
                if (commandArguments.get(0).equals("class_sections")) listAllClassSections();
                if (commandArguments.get(0).equals("class_registrations")) listAllClassRegistrations();

                if (commandArguments.get(0).equals("instructor")) {
                    getAllClassesByInstructor(commandArguments.get(1), commandArguments.get(2));
                }
            } else if (command.equals("create")) {
                if (commandArguments.get(0).equals("student")) {
                    createNewStudent(commandArguments.get(1), commandArguments.get(2), commandArguments.get(3));
                }
            } else if (command.equals("register")) {
                if (commandArguments.get(0).equals("student")) {
                    registerStudent(commandArguments.get(1), commandArguments.get(2));
                }
            } else if (command.equals("submit")) {
                if (commandArguments.get(0).equals("grade")) {
                    submitGrade(commandArguments.get(1), commandArguments.get(2), commandArguments.get(3));
                }
            } else if (command.equals("delete")) {
                if (commandArguments.get(0).equals("student")) {
                    deleteStudent(commandArguments.get(1));
                }
            } else if (!(command.equals("quit") || command.equals("exit"))) {
                System.out.println(command);
                System.out.println("Command not found. Enter 'help' for list of commands");
            }
            System.out.println("-".repeat(80));
        } while (!(command.equals("quit") || command.equals("exit")));
        System.out.println("Bye!");
    }
}

