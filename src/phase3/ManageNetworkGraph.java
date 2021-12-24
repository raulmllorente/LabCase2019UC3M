package phase3;

import java.util.LinkedList;
import java.util.List;

public class ManageNetworkGraph implements IManageNetworkGraph {

	public LinkedList<String> students;
	LinkedList<LinkedList<Integer>> lst_of_lstAdjacents;

	/*
	 * El grafo basado en listas es más adecuado cuando el numero de nodos del grafo
	 * es muy alto(en nuestro caso mas de 20 mil).Ya que es mas facil ubicar 20 mil
	 * nodos en memoria de forma dispersa que reservar memoria(de forma consecutiva)
	 * para una tabla que además tiene la problematica que hay que crearla con un
	 * tamaño predeterminado y en nuestro caso con más de 20 mil alumnos, lo cual
	 * implicará que en algún momento habrá que redimensionar la matriz de
	 * adyacencias o crearla con un tamaño muy grande.
	 */

	public ManageNetworkGraph(String[] students) {
		this.students = new LinkedList<String>();

		// we must initialize each Integer list
		// each index i corresponds to a student, the function
		// getIndex is used to obtain the correspondence
		lst_of_lstAdjacents = new LinkedList<LinkedList<Integer>>();
		
		// Incluimos los vértices del grafo
		for (int k = 0; k < students.length; k++) {
			this.addStudent(students[k]);
		}
	}

	// searches the student and returns its index
	public int getIndex(String student) {
		int index = -1;
		if (student != null && !"".equals(student)) {
			for (int i = 0; i < this.students.size() && index == -1; i++) {
				String studentAux = this.students.get(i);
				if (student.equals(studentAux)) {
					index = i;
				}
			}
		}
		return index;
	}

	// checks if index is right and returns its associated email
	public String checkVertex(int index) {
		String email = null;
		
		// Si la posición que ocupa vértice "i" está en el rango
		if (index >= 0 && index < this.students.size()) {
			email = this.students.get(index);
		}
		return email;
	}

	/**
	 * It takes taking two students (emails) as input and creates a friendship
	 * relation between them. Keep in mind that friendship relation is a symmetric
	 * relationship.
	 */
	public void addStudent(String student) {// añade un nuevo miembro a la red social
		if (student != null && !"".equals(student)) {
			this.students.add(student);
			lst_of_lstAdjacents.addLast(new LinkedList<Integer>()); // Crear su lista de adyacencias vacia
		}
	}

	/**
	 * It takes two students (emails) as input and creates a friendship relation
	 * between them. Keep in mind that friendship relation is a symmetric
	 * relationship.
	 * 
	 * @param studentA
	 * @param studentB
	 */
	public void areFriends(String studentA, String studentB) {
		if (studentA != null && studentB != null) {
			int posA = this.getIndex(studentA);
			if (posA == -1) {
				// Si no está el vértice en el grafo lo incluimos para studentA
				this.addStudent(studentA);
				posA = this.students.size() - 1; // Guardamos su posición para establecer la relación de amistad
			}

			int posB = this.getIndex(studentB);
			if (posB == -1) {
				// Si no está el vértice en el grafo lo incluimos para studentB
				this.addStudent(studentB);
				posB = this.students.size() - 1;
			}

			
			// Establecer la relación de amistada de forma simétrica
			
			// Hacemos B amigo de A
			LinkedList<Integer> studentsAdjacentsA = this.lst_of_lstAdjacents.get(posA);
			if (!studentsAdjacentsA.contains(posB)) {
				studentsAdjacentsA.add(posB);
			}

			// Hacemos A amigo de B
			LinkedList<Integer> studentsAdjacentsB = this.lst_of_lstAdjacents.get(posB);
			if (!studentsAdjacentsB.contains(posA)) {
				studentsAdjacentsB.add(posA);
			}
		}
	}

	/**
	 * This takes a student (email), and returns an object of LinkedList<String>,
	 * which contains the emails of his/her direct friends.
	 * 
	 * @param studentA
	 * @return
	 */
	public LinkedList<String> getDirectFriends(String studentA) {
		LinkedList<String> lDirectFriends = new LinkedList<String>();
		if (studentA != null) {
			// Buscamos si el estudiante está en la red social
			int posA = this.getIndex(studentA);
			if (posA != -1) {
				// Obtenemos sus amigos (lista de posiciones respecto a "this.students")
				List<Integer> lstAdjacents = this.lst_of_lstAdjacents.get(posA);
				// Recuperar sus emails de los amigos
				for (Integer index : lstAdjacents) {
					lDirectFriends.add(this.students.get(index));
				}
			}
		}
		return lDirectFriends;
	}

	public int[] getAdjacents(int i) {
		List<Integer> lstAdj = this.lst_of_lstAdjacents.get(i);
		int[] arrayAdj = new int[lstAdj.size()];
		int j = 0;
		for (Integer index : lstAdj) {
			arrayAdj[j] = index; // Generamos el array con las posiciones de los adyacentes al vertice cuya posición se pasa por parámetro
			j++;
		}
		return arrayAdj;
	}

	public LinkedList<String> suggestedFriends(String studentA) {
		LinkedList<String> lSuggestedFriends = new LinkedList<String>();
		int posA = this.getIndex(studentA);
		if (studentA != null && posA != -1) {
			boolean[] visited = new boolean[this.students.size()];
			for (int j = 0; j < visited.length; j++) {
				visited[j] = false;
			}
			visited[posA] = true; // No puedo ser amigo de mi mismo
			
			// Lista de amigos directos
			List<Integer> lsAdjA = this.lst_of_lstAdjacents.get(posA);
			
			// Se crea la lista con los amigos potenciales incluyendo los que ya son amigos (estos serán filtrados más adelante)
			List<Integer> lstFriends = this.depth(posA, visited);
			for (Integer posFriends : lstFriends) {
				// Se incluye la sugerencia de amistad si no es un amigo directo
				if (!lsAdjA.contains(posFriends)) {
					lSuggestedFriends.add(this.students.get(posFriends));
				}
			}
		}
		return lSuggestedFriends;
	}

	public LinkedList<Integer> depth(int i, boolean[] visited) {
		LinkedList<Integer> path = new LinkedList<Integer>();
		return depth(i, visited, path); // Genera una lista con los vértices del árbol recubridor
	}

	protected LinkedList<Integer> depth(int i, boolean[] visited, LinkedList<Integer> path) {
		List<Integer> lstAdj = this.lst_of_lstAdjacents.get(i);
		for (Integer index : lstAdj) {
			if (!visited[index]) { // Si el vértice aún no ha sido visitado
				path.add(index); // Se incluye a la lista de amigos potenciales
				visited[index] = true;
				depth(index, visited, path); // Continuamos la búsqueda en los amigos de sus amigos
			}
		}

		return path;
	}

	/**
	 * Representación del grafo como un String
	 */
	public void show() {
		String str = "";
		int index = 0;
		for (String student : students) {
			str += student + " [";
			LinkedList<Integer> lstAdjacents = lst_of_lstAdjacents.get(index);
			for (Integer posAdj : lstAdjacents) {
				String name = students.get(posAdj);
				str += name + ", ";
			}
			index++;
			str += "]\n";
		}

		System.out.println(str);
	}

}
