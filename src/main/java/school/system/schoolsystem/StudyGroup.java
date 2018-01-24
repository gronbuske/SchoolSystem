package school.system.schoolsystem;

import java.util.ArrayList;

public class StudyGroup extends Group {
	public String subject;
	
	public StudyGroup(String subject) {
		studentList = new ArrayList<Student>();
		this.subject = subject;
	}

	@Override
	public void AddStudent(Student student)	{
		if (studentList.size() >= maximumMembers) {
			AddUnplacedStudent(student);
			return;
		}
		if (studentList.size() < minimumMembers - 1) {
			for (Student s : studentList) {
				AddUnplacedStudent(s);
				s.RemoveFromGroup(this);
			}
			AddUnplacedStudent(student);
			studentList.clear();
			return;
		}
		if(!student.AddToGroup(this)) {
			AddUnplacedStudent(student);
			return;
		}
		studentList.add(student);
	}

	@Override
	public void RemoveStudent(Student student) {
		studentList.remove(student);
		student.RemoveFromGroup(this);
		if (studentList.size() < minimumMembers) {
			for (Student s : studentList) {
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
			}
			else {
				studentList.add(student);
				student.AddToGroup(this);
			}
		}
		if (studentList.size() < minimumMembers) {
			for (Student s : studentList) {
				AddUnplacedStudent(s);
				s.RemoveFromGroup(this);
			}
			studentList.clear();
		}
	}
}
