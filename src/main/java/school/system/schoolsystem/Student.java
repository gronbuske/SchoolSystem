package school.system.schoolsystem;

import java.util.ArrayList;

public class Student {
	//Identifier for student in the form of a social security number
	private String ssn;
	
	private String firstName;
	private String lastName;
	
	private Class studentClass;
	private ArrayList<StudyGroup> groups;
	
	public Student(String ssn, String firstName, String lastName)	{
		this.ssn = ssn;
		this.firstName = firstName;
		this.lastName = lastName;
		groups = new ArrayList<StudyGroup>();
	}
	
	public void AddToClass(Class c) {
		this.studentClass = c;
	}
	
	public String getClassName() {
		if(studentClass == null)
			return "";
		return studentClass.className;
	}
	
	//Tries to add student to study group, returns false if student already is in a group with the same subject
	public boolean AddToGroup(StudyGroup studyGroup) {
		for (StudyGroup sg : groups) {
			if(sg.subject.equals(studyGroup.subject)) {
				sg.RemoveStudent(this);
				return false;
			}
		}
		if(studentClass != null) {
			for (String s : studentClass.getSubjects()) {
				if(s.equals(studyGroup.subject)) {
					return false;
				}
			}
		}
		groups.add(studyGroup);
		return true;
	}
	
	public void RemoveFromGroup(StudyGroup studyGroup) {
		groups.remove(studyGroup);
	}
	
	//Call from Class when a new subject is added to see that student is not already in a study group with the same subject
	public boolean NewSubjectForClass(String subject) {
		for (StudyGroup studyGroup : groups) {
			if(studyGroup.subject.equals(subject)) {
				studyGroup.RemoveStudent(this);
				return false;
			}
		}
		return true;
	}
	
	public void setSSN(String ssn) {this.ssn = ssn;}
	public String getSSN() {return ssn;}

	public void setName(String firstName, String lastName) {this.firstName = firstName; this.lastName = lastName;}
	public String getFirstName() {return firstName;}
	public String getLastName() {return lastName;}
}
