package huiswerknakijken.hu.domein;

import java.util.ArrayList;

//hier moeten waarschijnlijk ook nog Courses aan gekoppeld worden wat dus de vakken zijn.
//zodat je per vak huiswerk kan hebben enzo...

public class Class {

	private ArrayList<Student> students;
	private Teacher teacher;
	
	public ArrayList<Student> getStudents() {
		return students;
	}
	public void setStudents(ArrayList<Student> students) {
		this.students = students;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	
}
