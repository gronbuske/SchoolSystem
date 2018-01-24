package school.system.schoolsystem;

import java.util.ArrayList;

import junit.framework.TestCase;

public class SchoolTests extends TestCase {

	public void tearDown() {
		Group.ClearUnplacedStudent();
	}
	
    public void testRemoveStudentToUnderMinimumLevel() {
    	Class c = new Class("A1");
    	c.SetMinimumMembers(5);
    	ArrayList<Student> students = new ArrayList<Student>();
    	for (int i = 0; i < 5; i++)
			students.add(new Student("000000-123" + Integer.toString(i), "Test", "Testsson"));
    	
    	c.AddStudents(students);
    	for (Student student : students) {
			assertEquals(student.getClassName(), c.className);
		}
    	c.RemoveStudent(students.get(0));
    	for (Student student : students)
			assertEquals(student.getClassName(), "");
    }
    
    public void testAddStudentToOverMaximumLimit() {
    	Class c = new Class("A2");
    	c.SetMaximumMembers(2);
    	Student s1 = new Student("000000-1231", "student", "A");
    	Student s2 = new Student("000000-1232", "student", "B");
    	Student s3 = new Student("000000-1233", "student", "C");
    	c.AddStudent(s1);
    	c.AddStudent(s2);
    	c.AddStudent(s3);
		assertEquals(s1.getClassName(), c.className);
		assertEquals(s2.getClassName(), c.className);
		assertEquals(s3.getClassName(), "");
    }
    
    public void testGradeLevels() {
    	Class c = new Class("A3");
    	c.SetGradeLevel(4, 6);
    	int[] grades = new int[3];
    	grades[0] = 4;
    	grades[1] = 5;
    	grades[2] = 6;
    	for (int i = 0; i < c.GetGradeLevels().length; i++)
			assertEquals(c.GetGradeLevels()[i], grades[i]);
    	
    	c.SetGradeLevel(6, 4);
    	for (int i = 0; i < c.GetGradeLevels().length; i++)
			assertEquals(c.GetGradeLevels()[i], grades[i]);
    	
    	assertEquals(c.GetGradeLevel(), 4);
    }
    
    public void testTooSmallClass() {
    	Class c = new Class("A4");
    	c.SetMinimumMembers(3);
    	ArrayList<Student> students = new ArrayList<Student>();
    	for (int i = 0; i < 2; i++)
			students.add(new Student("000000-123" + Integer.toString(i), "Test", "Testsson"));
    	c.AddStudents(students);
    	
    	Student s1 = new Student("999999-1233", "Efter", "Sladdare");
    	students.add(s1);
    	c.AddStudent(s1);
    	
    	for (Student student : students)
    		assertTrue(Group.GetUnplacedStudents().contains(student));
        //Should be an empty class first time since the amount of pupils are too small when adding the first batch
    	assertTrue(c.studentList.isEmpty());
    	
    	c.AddStudents(students);
    	assertEquals(c.studentList.size(), 3);
    }
    
    public void testUnplaceStudentForMultipleOfSameSubject() {
    	Class c = new Class("A5");
    	c.AddSubject("Math");
    	Student s1 = new Student("999999-9999", "Matte", "Matsson");
    	c.AddStudent(s1);
    	
    	c.AddSubject("Math");
    	assertTrue(Group.RemoveUnplacedStudent(s1));
    	
    	c.AddSubject("Math");
    	assertFalse(Group.RemoveUnplacedStudent(s1));
    	
    	StudyGroup sgmath = new StudyGroup("Math");
    	StudyGroup sgEng = new StudyGroup("English");
    	
    	sgmath.AddStudent(s1);
    	assertTrue(Group.RemoveUnplacedStudent(s1));
    	
    	sgEng.AddStudent(s1);
    	assertFalse(Group.RemoveUnplacedStudent(s1));
    	
    	c.RemoveSubject("Math");
    	sgmath.AddStudent(s1);
    	assertFalse(Group.RemoveUnplacedStudent(s1));
    	
    	assertEquals(s1.getClassName(), "A5");
    }

    public void testRemoveStudentToUnderMinimumLevelFromStudyGroup() {
    	StudyGroup c = new StudyGroup("Math");
    	c.SetMinimumMembers(5);
    	ArrayList<Student> students = new ArrayList<Student>();
    	for (int i = 0; i < 5; i++)
			students.add(new Student("000000-123" + Integer.toString(i), "Test", "Testsson"));
    	
    	c.AddStudents(students);
    	for (Student student : students) {
			assertTrue(c.studentList.contains(student));
		}
    	c.RemoveStudent(students.get(0));
    	for (Student student : students)
			assertFalse(c.studentList.contains(student));
    }
    
    public void testAddStudentToOverMaximumLimitFromStudyGroup() {
    	StudyGroup c = new StudyGroup("Math");
    	c.SetMaximumMembers(2);
    	Student s1 = new Student("000000-1231", "student", "A");
    	Student s2 = new Student("000000-1232", "student", "B");
    	Student s3 = new Student("000000-1233", "student", "C");
    	c.AddStudent(s1);
    	c.AddStudent(s2);
    	c.AddStudent(s3);
		assertTrue(c.studentList.contains(s1));
		assertTrue(c.studentList.contains(s2));
		assertFalse(c.studentList.contains(s3));
    }

    public void testTooSmallStudyGroup() {
    	StudyGroup c = new StudyGroup("Math");
    	c.SetMinimumMembers(3);
    	ArrayList<Student> students = new ArrayList<Student>();
    	for (int i = 0; i < 2; i++)
			students.add(new Student("000000-123" + Integer.toString(i), "Test", "Testsson"));
    	c.AddStudents(students);
    	
    	Student s1 = new Student("999999-1233", "Efter", "Sladdare");
    	students.add(s1);
    	c.AddStudent(s1);
    	
    	for (Student student : students)
    		assertTrue(Group.GetUnplacedStudents().contains(student));
        //Should be an empty class first time since the amount of pupils are too small when adding the first batch
    	assertTrue(c.studentList.isEmpty());
    	
    	c.AddStudents(students);
    	assertEquals(c.studentList.size(), 3);
    }
    
    public void testAddStudentToMultipleStudyGroupsWithSameSubject() {
    	StudyGroup sg1 = new StudyGroup("Math");
    	StudyGroup sg2 = new StudyGroup("Math");
    	StudyGroup sg3 = new StudyGroup("Swedish");
    	
    	Student s = new Student("000000-0000", "Test", "Testsson");
    	
    	sg1.AddStudent(s);
    	sg2.AddStudent(s);
    	sg3.AddStudent(s);
    	
    	assertFalse(sg1.studentList.contains(s));
    	assertFalse(sg2.studentList.contains(s));
    	assertTrue(sg3.studentList.contains(s));
    }
}
