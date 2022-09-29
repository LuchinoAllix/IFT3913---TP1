import java.io.*;
import java.util.Arrays;

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

		// Test partie 0 (avec le dossier git placé un dossier en arrière)
		//jls("..\\ckjm-master\\ckjm-master\\src\\gr");

		// Test partie 1
		File file = new File("src.java");
		System.out.println(nvloc(file));

//		// Test partie 0
//		jls("/Users/anthony/Desktop/ckjm-master/src");
//
//		// Test partie 1
//		File file = new File("/Users/anthony/Desktop/ckjm-master/src/gr/spinellis/ckjm/MethodVisitor.java");
//		System.out.println(nvloc(file));
//
//		// Test partie 2
//		File csv = new File("/Users/anthony/Documents/Workspace/ift3913tp1/output.csv");
//		//File file2 = new File("/Users/anthony/Desktop/ckjm-master/src/gr/spinellis/ckjm/MethodVisitor.java");
//		lcsec("/Users/anthony/Desktop/ckjm-master/src/gr/spinellis/ckjm/MethodVisitor.java", csv);

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

	/*
	 * Description :
	 * ----------
	 * Etant donné un fichier source d'une classe java, retourne le nombre de 
	 * lignes de codes non vides.
	 * 
	 * Paramètres :
	 * ----------
	 * @param file File un fichier java
	 * @param int le nombre de lignes de codes non vides
	 * 
	 * Information complémentaires :
	 * ----------
	 * Une ligne avec seulement un espace ou une tabulation n'est pas 
	 * considérée comme vide.
	 */
	public static int nvloc(File file){
		int cnt = 0;
		
		try {
			FileReader reader = new FileReader(file);
			BufferedReader br = new BufferedReader(reader);
			String line;
			while((line = br.readLine())!=null){
				if(line.length() > 0){
					cnt ++;
				}
			}
			reader.close();

		} catch (IOException e) {
			System.out.println(" - Error - ");
		}

		return cnt;
	}

	// PARTIE 2 :
	// TODO CSEC(c) si c est mentionné dans un autre fichier mais pas dans c

	/*
	 * Description :
	 * ----------
	 * 
	 * 
	 * Paramètres :
	 * ----------
	 * 
	 * 
	 * Informations complémentaires :
	 * ----------
	 * 
	 */
	public static void lcsec(String path,File csv){
		String toWrite = lcsecRec(csv);
		try{
			// Création nouveau fichier ou remplacement de l'ancien
			File output = new File("output2.csv");
			output.createNewFile();

			// Ecriture sur le nouveau fichier
			FileWriter writer = new FileWriter("output2.csv");
			writer.write(toWrite);
			writer.close();

		} catch(IOException e){
			System.out.println(" - Error - ");
			e.printStackTrace();
		}
	}

	public static String lcsecRec(File csv) {
		String result = "";
		String csvString = csvToString(csv);
		String[] values = csvString.split("\n");
		String[] deps = new String[values.length];
		for (int i = 0; i < deps.length; i++) {
			deps[i] = values[i].split(",")[2];
		}

		for (String value : values) {
			int csec = 0;
			String alreadyAdded = "";
			try {
				File file = new File(value.split(",")[0]);
				FileReader reader = new FileReader(file);
				BufferedReader br = new BufferedReader(reader);
				String line;
				while((line = br.readLine())!=null) {
					for (String dep : deps) {
						if (line.contains(dep) && (!dep.equals(value.split(",")[2])) && !alreadyAdded.contains(dep)) {
							alreadyAdded += dep + " ";
							csec++;
						}
					}
				}
				result += value + ", " + csec + "\n";
				reader.close();
			} catch (IOException e) {
				System.out.println(" - Error - ");
				e.printStackTrace();
			}
		}

		return result;
 	}

	public static String csvToString(File csv) {
		String csvString = "";
		try {
			FileReader reader = new FileReader(csv);
			BufferedReader br = new BufferedReader(reader);
			String line;
			while((line = br.readLine())!=null) {
				csvString += line + "\n";
			}
			reader.close();
		} catch (IOException e) {
			System.out.println(" - Error - ");
			e.printStackTrace();
		}
		return csvString;
	}

	// PARTIE 3 :

	/*
	 * Description :
	 * ----------
	 * 
	 * 
	 * Paramètres :
	 * ----------
	 * 
	 * 
	 * Informations complémentaires :
	 * ----------
	 * 
	 */
	public static void egon(String path, float seuil){
		jls(path); // Pour être sur que 'output' existe.
		File csv = new File("output");
		String csec = lcsecRec(csv);
		String[] lines = csec.split("\n");
		String[][] colonnes = new String[lines.length][5];

		// On crée des tableau pour stocker les métriques
		int[] csecTab = new int[lines.length];
		int[] nvlocTab = new int[lines.length];
		
		for(int i = 0 ; i< lines.length ; i++){
			// On rajoute la métrique nvloc
			colonnes[i] = lines[i].split(",");
			File file = new File(colonnes[i][0]);
			int nvlocVal = nvloc(file);
			colonnes[i][4] = "" + nvlocVal;

			// On complète les tableaux avec toutes les valeurs des métriques
			nvlocTab[i] = nvlocVal;
			csecTab[i] = Integer.parseInt(colonnes[i][3]);

		}

		// On calcule le nombre de classe maximum à afficher
		int nbClasses = (int) Math.floor(lines.length * seuil * 0.01);

		// Il se peut que le seuil soit trop bas pour avoir la moindre classe
		if(nbClasses > 0){

			// On trie les métriques
			Arrays.sort(nvlocTab);
			Arrays.sort(csecTab);

			// On calcul les valeurs métriques minimum pour être suspect
			int nvlocCrit = nvlocTab[lines.length - nbClasses];
			int csecCrit = csecTab[lines.length - nbClasses];
			
			// Pour chacune des classes on regarde si les deux métriques sont critiques
			for(int i = 0 ; i< lines.length ; i++){
				if(Integer.parseInt(colonnes[i][3]) >= csecCrit){
					if(Integer.parseInt(colonnes[i][4]) >= nvlocCrit){
						String res = colonnes[i].toString();
						System.out.println(res.substring(1,res.length()-1));
					}
				} 
			}
		}
		

	}

	// PARTIE 4 :

}





