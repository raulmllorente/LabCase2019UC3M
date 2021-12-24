package phase1;

import java.time.LocalDate;

public class ManageNetworkList implements IManageNetworkList {

	/**
	 * The methods must join two social networks into a single social network. The
	 * method takes two objects of the StudentsList and returns a new list
	 * containing first the students from the first list followed by the students
	 * from the second list.
	 * 
	 * @param lst1
	 * @param lst2
	 * @return
	 */
	public StudentsList union(StudentsList lst1, StudentsList lst2) {
		StudentsList resultList = new StudentsList();

		// Comenzamos recorriendo los elementos de la lista lst1
		for (int i = 0; i < lst1.getSize(); i++) {
			resultList.addLast(lst1.getAt(i));
		}

		// Seguidamente recorremos los elementos de la lista lst2
		for (int i = 0; i < lst2.getSize(); i++) {
			resultList.addLast(lst2.getAt(i));
		}

		return resultList;
	}

	/**
	 * 3. This methods takes a social network as input and an integer parameter opc
	 * so that: - If opc =1: the method must return a StudentsList containing all
	 * the students residing in the same city that the campus where they are
	 * studying. - If opc =2: the method must return a StudentsList containing all
	 * the students residing in cities different that the one where their campus is
	 * located.
	 * 
	 * @param lst1
	 * @param lst2
	 * @param opc
	 * @return
	 */
	public StudentsList getCampusCity(StudentsList lst, int opc) {
		StudentsList l = new StudentsList();
		for (int i = 0; i < lst.getSize(); i++) {
			Student student = lst.getAt(i);
			if (opc == 1 && student.city.equals(student.campus.name())
					|| opc == 2 && !student.city.equals(student.campus.name())) {
				l.addLast(student);
			}
		}

		return l;
	}

	/**
	 * 4. This method takes a social network as input and a integer parameter opc so
	 * that: - If opc=1, the method returns a list of students sorted by ascending
	 * order according to their full name. - If opc=2, the method returns a lit of
	 * students sorted by descending order according to their full name. Note 1. You
	 * must implement your own sort method based on some of the sorted algorithms
	 * (such as bubble, sort). Note 2: Remember that you cannot modify or extend the
	 * StudentsList class. Therefore, if you need an auxiliary method that help you
	 * to sort the list, please include this method into the ManageNetwork class.
	 * Note 3. The input list cannot be modified. The method must return a new list
	 * where the students are sorted.
	 * 
	 * @param lst
	 * @param opc
	 * @return
	 */
	public StudentsList orderBy(StudentsList lst, int opc) {
		StudentsList sortedList = new StudentsList();
		for (int i = 0; i < lst.getSize(); i++) {
			// Recorremos la lista e insertamos en orden
			sortedInsert(sortedList, lst.getAt(i), opc);
		}

		return sortedList;
	}

	/**
	 * auxiliary method to insert student in a sorted way
	 * 
	 * @param lst
	 * @param newStudent
	 * @param opc
	 */
	public static void sortedInsert(StudentsList lst, Student newStudent, int opc) {
		boolean insert = false;
		for (int i = 0; i < lst.size && !insert; i++) {
			Student studenti = lst.getAt(i);

			// Se inserta en orden teniendo en cuenta si el orden es ascendente o
			// descendente
			if (opc == 1 && studenti.email.compareTo(newStudent.email) > 0
					|| opc == 2 && studenti.email.compareTo(newStudent.email) < 0) {
				insert = true;
				lst.insertAt(i, newStudent);
			}
		}

		// si el elemento no se insertó, se hace al final
		if (!insert) {
			lst.addLast(newStudent);
		}
	}

	/**
	 * This methods takes a social network (that is an object of StudentsList class)
	 * and a city name as input and returns a list containing all the students (that
	 * is, an object of the StudentsList class) who live in that city.
	 * 
	 * @param lst
	 * @param city
	 * @return
	 */
	public StudentsList locateByCity(StudentsList lst, String city) {
		StudentsList l = new StudentsList();
		for (int i = 0; i < lst.getSize(); i++) {

			// si la ciudad del estudiante coincide con la pasada por parámetro se incluye
			// en la lista resultado
			if (lst.getAt(i).city.equals(city)) {
				l.addLast(lst.getAt(i));
			}
		}
		return l;
	}

	/**
	 * This takes a social network (an object of the StudentsList class) and two
	 * dates and returns the list of all students from the input list whose
	 * registration dates are in the interval of input dates. Please, take into
	 * account the following comments: - start <= end. - Both dates are included
	 * into the interval. - The order in the resulting list must be the same that in
	 * the input list.
	 * 
	 * @param lst
	 * @param start
	 * @param end
	 * @return
	 */
	public StudentsList getStudentsByDateInterval(StudentsList lst, LocalDate start, LocalDate end) {
		StudentsList resultList = new StudentsList();
		// Si la fecha start es menor que la fecha end
		if (start.isBefore(end)) {
			for (int i = 0; i < lst.getSize(); i++) {
				LocalDate dateStudent = lst.getAt(i).date_sign_in;

				// Si la fecha está en el rango
				if (dateStudent.isAfter(start) && dateStudent.isBefore(end)) {
					resultList.addLast(lst.getAt(i));
				}
			}
		}
		return resultList;

	}

}
