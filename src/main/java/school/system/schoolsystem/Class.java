package school.system.schoolsystem;

import java.util.ArrayList;

public class Class extends Group {
	public String className;
	//For classes spanning more than one grade, grade levels in class is between lowest and highest
	private int lowestGradeLevel;
	private int highestGradeLevel;
	private ArrayList<String> subjects;
	
	public Class(String className) {
		studentList = new ArrayList<Student>();
		this.className = className;
		lowestGradeLevel = 0;
		highestGradeLevel = 0;
		subjects = new ArrayList<String>();
	}
	
	//Return a list of subjects the class is taking
	public ArrayList<String> getSubjects(){
		return new ArrayList<String>(subjects);
	}
	
	public boolean AddSubject(String subject) {
		if(subjects.contains(subject)) {
			subjects.remove(subject);
			for (Student student : studentList) {
				AddUnplacedStudent(student);
			}
			return false;
		}
		subjects.add(subject);
		for (Student student : studentList) {
			student.NewSubjectForClass(subject);
		}
		return true;
	}
	
	public boolean RemoveSubject(String subject) {
		if(subjects.contains(subject)) {
			subjects.remove(subject);
			return true;
		}
		return false;
	}
	
	//Sets the grade level of the class from grade level fromGradeLevel to grade level toGradeLevel
	public void SetGradeLevel(int fromGradeLevel, int toGradeLevel) {
		if (fromGradeLevel <= toGradeLevel) {
			lowestGradeLevel = fromGradeLevel;
			highestGradeLevel = toGradeLevel;
		}
		else {
			lowestGradeLevel = toGradeLevel;
			highestGradeLevel = fromGradeLevel;
		}
	}
	
	//Returns an array with all grade levels in the class
	public int[] GetGradeLevels() {
		int length = highestGradeLevel - lowestGradeLevel + 1;
		int[] returnArray = new int[length];
		for (int i = 0; i < length; i++) {
			returnArray[i] = i + lowestGradeLevel;
		}
		return returnArray;
	}
	
	//Returns grade level, if multiple grade levels in class, returns the lowest
	public int GetGradeLevel() {
		return lowestGradeLevel;
	}
	
	@Override
	public void AddStudent(Student student)	{
		if (studentList.size() >= maximumMembers) {
			AddUnplacedStudent(student);
			student.AddToClass(null);
			return;
		}
		studentList.add(student);
		if (studentList.size() < minimumMembers) {
			for (Student s : studentList) {
				s.AddToClass(null);
				AddUnplacedStudent(s);
			}
			studentList.clear();
		}
		else {
			student.AddToClass(this);
		}
	}

	@Override
	public void RemoveStudent(Student student) {
		studentList.remove(student);
		student.AddToClass(null);
		if (studentList.size() < minimumMembers) {
			for (Student s : studentList) {
				s.AddToClass(null);
				AddUnplacedStudent(s);
			}
			studentList.clear();
		}
	}

	@Override
	public void AddStudents(ArrayList<Student> students) {
		for (Student student : students) {
			if (studentList.size() >= maximumMembers) {
				AddUnplacedStudent(student);
				student.AddToClass(null);
			}
			else {
				studentList.add(student);
				student.AddToClass(this);
			}
		}
		if (studentList.size() < minimumMembers) {
			for (Student s : studentList) {
				s.AddToClass(null);
				AddUnplacedStudent(s);
			}
			studentList.clear();
		}
	}
}
