package phase2;

import phase1.ManageNetworkList;
import phase1.Student;
import phase1.StudentsList;

public class ManageNetworkTree implements IManageNetworkTree {

	/**
	 * It takes an object of the StudentsTree class and an object of the
	 * StudentsList class (phase 1), and inserts each student from the list into the
	 * tree. The method does not return anything.
	 * 
	 * @param tree
	 * @param list
	 */
	public void copySocialNetwork(StudentsTree tree, StudentsList list) {
		for (int i = 0; i < list.getSize(); i++) {
			Student student = list.getAt(i);

			// Incluimos el estudiante en el árbol
			tree.insertStudent(student);
		}

	}

	/**
	 * This takes an object of the StudentsTree class and returns an object of the
	 * StudentsList class containing all the students in the tree ordered by their
	 * email. Hint: You can develop an auxiliary and recursive method which takes a
	 * BSTNode object and a StudentsList object. You cannot use any sort algorithm
	 * to sort the resulting list. To obtain the list, you must traverse the tree
	 * (or subtree) in a recursive way.
	 * 
	 * @return
	 */
	public StudentsList getOrderedList(StudentsTree tree) {
		StudentsList sL = new StudentsList();
		getOrderedListAux(tree.root, sL);
		return sL;
	}

	public void getOrderedListAux(BSTNode treeNode, StudentsList list) {
		if (treeNode != null) {
			Student student = treeNode.oStudent;
			ManageNetworkList.sortedInsert(list, student, 1); // Insertamos en orden la lista que siempre estará
																// ordenada
			getOrderedListAux(treeNode.left, list); // Recorre el sub-arbol izquierdo
			getOrderedListAux(treeNode.right, list); // Recorre el sub-arbol derecho
		}

	}

	/**
	 * This class has a parameter n as input and removes all students having a
	 * number of blocks equal or greater than n.
	 * 
	 * @param num
	 */

	public void deleteByNumberOfBlocks(StudentsTree tree, int num) {
		deleteByNumberOfBlocks(tree, tree.root, num);
	}

	/**
	 * Metodo auxiliar para deleteByNumberOfBlocks
	 * 
	 * @param tree
	 * @param currentNode
	 * @param num
	 */
	public void deleteByNumberOfBlocks(StudentsTree tree, BSTNode currentNode, int num) {
		if (currentNode != null) {
			if (num <= currentNode.oStudent.blocks) {
				tree.removeStudent(currentNode.oStudent.email);
			}
			deleteByNumberOfBlocks(tree, currentNode.left, num);
			deleteByNumberOfBlocks(tree, currentNode.right, num);

		}
	}

	/*
	 * La StudentsTree no sería eficiente ya que además de buscar por nombre habría
	 * que buscar también por fecha. Una posible solución que fuera más eficiente
	 * sería usar un árbol de árboles en el que la primera clave para restringir la
	 * búsqueda sería la fecha y cuando se hubiera encontrado la fecha de inicio de
	 * sesión se buscarían los usuarios en el subarbol.
	 */

}
