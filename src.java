import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

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

		// Test avec le dossier git placé un dossier en arrière
		jls("..\\ckjm-master\\ckjm-master\\src\\gr");
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
	 * jls fait appel à jlsRec qui trouve les fichier '.java' en cherchant
	 * récursivement tous les sous dossiers.
	 */
	public static void jls(String path){

		File file = new File(path);
		String toWrite = jlsRec(file,file.getName());

		System.out.println(toWrite); // Affiche sur la ligne de commande

		try{
			// Création nouveau fichier ou remplacement de l'ancien
			File output = new File("output.csv");
			output.createNewFile();

			// Ecriture sur le nouveau fichier
			FileWriter writer = new FileWriter("output.csv");
			writer.write(toWrite);
			writer.close();

		} catch(IOException e){
			System.out.println(" - Error - ");
		}
	}

	/*
	 * Description :
	 * ----------
	 * Avec le chemin donné en entré, retourne les données recherchées sur 
	 * chaque fichier '.java' grâce au module et des appels récursifs.
	 * 
	 * Paramètres :
	 * ----------
	 * @param path File le chemin du dossier à regarder.
	 * @param module String le nom du module ou se trouverait un fichier '.java'
	 * @return temp String les valeurs demandée au format csv
	 * 
	 * Informations complémentaires :
	 * ----------
	 * Appel récursif depuis jls, cette méthode n'est pas utilisé autre part.
	 */
	public static String jlsRec(File path, String module){
		String temp = "";

		// On regarde tous les fichiers :
		for (final File file : path.listFiles()){

			// Si on regarde un dossier on fait un appel récursif
			if (file.isDirectory())
				temp += jlsRec(file,module + "." + file.getName());
			
			// Si c'est un fichier '.java' on prend les informations nécessaires
			else if (file.isFile()){
				int len = file.getName().length();
				if (len > 5 && file.getName().substring(len-5).equals(".java")){
					temp += file.getPath() + "," 
						+ module + "," 
						+ file.getName().substring(0,len-5) + "\n";
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