package school.system.schoolsystem;

import java.util.ArrayList;

public abstract class Group {
	private static ArrayList<Student> unplacedStudents;
	protected ArrayList<Student> studentList;
	protected int minimumMembers = 0;
	protected int maximumMembers = Integer.MAX_VALUE;

	public abstract void AddStudent(Student student);
	public abstract void AddStudents(ArrayList<Student> students);
	public abstract void RemoveStudent(Student student);
	
	private static ArrayList<Student> UnplacedStudentsInstance(){
		if (unplacedStudents == null) {
			unplacedStudents = new ArrayList<Student>();
		}
		return unplacedStudents;
	}
	
	protected static void AddUnplacedStudent(Student student) {
		if(!UnplacedStudentsInstance().contains(student)) {
			UnplacedStudentsInstance().add(student);
		}
	}
	
	protected static boolean RemoveUnplacedStudent(Student student) {
		return UnplacedStudentsInstance().remove(student);
	}

	protected static void ClearUnplacedStudent() {
		UnplacedStudentsInstance().clear();
	}
	
	public static ArrayList<Student> GetUnplacedStudents(){
		return new ArrayList<Student>(UnplacedStudentsInstance());
	}
	
	public void SetMinimumMembers(int minimum) {
		minimumMembers = minimum;
	}
	
	public void SetMaximumMembers(int maximum) {
		maximumMembers = maximum;
	}
}
