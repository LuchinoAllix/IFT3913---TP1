import java.io.File;


/*
* La classe src contient toutes les méthodes demandées dans l'énoncé.
* 
* 
*/
public class src{

	/*
	 * TODO
	 */
	public static void main(String[] args) {
		//jls("..\\ckjm-master\\ckjm-master\\src\\gr");
	}
	
	

	// PARTIE 0 :

	/*
	 * Description :
	 * ----------
	 * La méthode jls répond à la partie 0 de l'énnoncé. A savoir, trouver tout 
	 * les fichiers '.java' dans le dossier (et ses sous dossiers) donné en 
	 * paramètre pour mettre dans un fichier csv les valeurs suivantes : 
	 *  - Chemin du ficheir
	 *  - Nom du paquet
	 *  - Nom de la classe
	 * 
	 * Paramètres et valeurs de retour :
	 * ----------
	 * @param path String : Le chemin d'accès du dossier
	 * @return void : La méthode ne retourne rien mais possède un effet de bord
	 * car elle crée un fichier csv.
	 * 
	 * Information complémentaire :
	 * ---------
	 * Il est a noter que si le fichier csv correspondant au chemin fourni en 
	 * paramètre existe déjà, il sera remplacé par le nouveau fichier csv.
	 * jls fait appel à jlsRec qui trouve tout les fichier '.java' et cherche
	 * récursivement tout les sous dossiers.
	 */
	public static void jls(String path){

		File file = new File(path);
		String toWrite = jlsRec(file,file.getName());

		System.out.println("Début test :\n");
		System.out.println(toWrite); // test
		System.out.println("Fin test.");

		// TODO : créer/écrire dans le fichier csv
	}

	/*
	 * TODO
	 */
	public static String jlsRec(File path, String module){
		String temp = "";
		for (final File file : path.listFiles()){
			if (file.isDirectory())
				temp += jlsRec(file,module + "." + file.getName());
			
			else if (file.isFile()){
				int len = file.getName().length();
				if (len > 5 && file.getName().substring(len-5).equals(".java")){
					temp += file.getPath() + "," 
						+ module + "," 
						+ file.getName().substring(0,len-5) + "\n\n";
						System.out.println(module);
				}
			}
		}
		return temp;
	}

	// PARTIE 1 :

	// PARTIE 2 :

	// PARTIE 3 :

	// PARTIE 4 :

}