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
		//File file = new File("src.java");
		//System.out.println(nvloc(file));

		egon("..\\jfreechart-master\\jfreechart-master\\src\\main\\java",10);
		//File csv = new File("output.csv");
		//lcsec("..\\ckjm-master\\ckjm-master\\src\\gr", csv);
		


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
	 * lignes de codes non vides. Cette méthode répond à la partie 1.
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
			System.out.println(toWrite);
			writer.close();

		} catch(IOException e){
			System.out.println(" - Error - ");
		}
	}

	public static String lcsecRec(File csv) {
		String result = "";
		String[] csvEntries = csvToString(csv).split("\n");
		// Creation tableau csec qui va prendre les classes dans l'ordre du csv et de leurs couplages
		String[] csec = new String[csvEntries.length];
		// Creation tableau deps qui va prendre les classes dans l'ordre du csv
		String[] deps = new String[csvEntries.length];
		// Initialisation des valeurs de scec et deps avec les noms des classes du fichier csv
		for (int i = 0; i < csvEntries.length; i++) {
			csec[i] = csvEntries[i].split(",")[2];
			deps[i] = csvEntries[i].split(",")[2];
		}

		// Pour chaque entrée du fichier csv, lecture du fichier concerné
		// et regarde si une autre class présente dans le tableau deps est mentionné
		// Si oui la class est ajouté au couplage du fichier concerné et inversement
		// dans csec sauf si ils ont déjà été ajouté
		for (int x = 0; x < csvEntries.length; x++) {
			try {
				File file = new File(csvEntries[x].split(",")[0]);
				FileReader reader = new FileReader(file);
				BufferedReader br = new BufferedReader(reader);
				String line;
				while((line = br.readLine()) != null) {
					for (int y = 0; y < deps.length; y++) {
						if ((x != y) && line.contains(deps[y])) {
							if (!csec[x].contains(deps[y])) {
								csec[x] = csec[x] + "/" + deps[y];
							}
							if(!csec[y].contains(deps[x])) {
								csec[y] = csec[y] + "/" + deps[x];
							}
						}
					}
				}
				reader.close();
			} catch (IOException e) {
				System.out.println(" - Error - ");
			}
		}
		for (int z = 0; z < csvEntries.length; z++) {
			result += csvEntries[z] + "," + (csec[z].split("/").length - 1) + "\n";
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
	 * Cette méthode répond à la partie 3 de l'enoncé. Egon affiche les classes 
	 * qui sont suspectes d'être divines parmis celles dans le dossier mentioné 
	 * en paramètre. Le nombre de classes affichées est déterminé par le seuil.
	 *
	 * Paramètres :
	 * ----------
	 * @param path String : Le chemin du dossier à regarder
	 * @param seuil float : Le seuil de classes qu'on souhaite regarder
	 *
	 * Informations complémentaires :
	 * ----------
	 * Nous avons décidez de minorer le seuil, c'est à dire que si on regarde 
	 * 10 classes mais que l'on a un seuil de 1% on ne regarde aucune classe. 
	 * En effet, on estime que si on veut 1% des classes suspectes alors il 
	 * faudrait au moins 100 classes.
	 * Cette méthode ne retourne rien mais possède un effet de bord car elle 
	 * affiche les classes suspectes et fait appel à jls (qui possède un effet 
	 * de bord) et lcsecRec.
	 */
	public static void egon(String path, float seuil){
		jls(path); // Pour être sur que 'output' existe.
		File csv = new File("output.csv");
		String csec = lcsecRec(csv);
		String[] lines = csec.split("\n");
		String[][] tab = new String[lines.length][5];

		// On crée des tableau pour stocker les métriques
		int[] csecTab = new int[lines.length];
		int[] nvlocTab = new int[lines.length];

		for(int i = 0 ; i< lines.length ; i++){

			// On met les valeurs dans un tableau
			String[] tabTemp = new String[4];
			tabTemp = lines[i].split(",");

			for(int j= 0; j < 4;j++){
				tab[i][j] = tabTemp[j];
			}

			// On rajoute la métrique nvloc
			File file = new File(tab[i][0]);
			int nvlocVal = nvloc(file);
			tab[i][4] = "" + nvlocVal;

			// On complète les tableaux avec toutes les valeurs des métriques
			nvlocTab[i] = nvlocVal;
			csecTab[i] = Integer.parseInt(tab[i][3]);

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
			String res = "";

			for(int i = 0 ; i< lines.length ; i++){
				if(Integer.parseInt(tab[i][3]) >= csecCrit){
					if(Integer.parseInt(tab[i][4]) >= nvlocCrit){
						res += tab[i][0] + ","
							+ tab[i][1] + ","
							+ tab[i][2] + ","
							+ tab[i][3] + ","
							+ tab[i][4] + "\n";
					}
				}
			}

			// Affichage du résultat
			System.out.println(res);

			// Création des fichiers pour la partie 4
			try{
				// Création nouveau fichier ou remplacement de l'ancien
				File output = new File("output_Seuil_10.csv");
				output.createNewFile();
	
				// Ecriture sur le nouveau fichier
				FileWriter writer = new FileWriter("output_Seuil_10.csv");
				writer.write(res);
				writer.close();
	
			} catch(IOException e){
				System.out.println(" - Error - ");
			}
		}
	}
}





