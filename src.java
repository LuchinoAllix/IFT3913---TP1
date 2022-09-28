public class src{
	/*
	 * La classe src contient toutes les méthodes demandées dans l'énoncé.
	 * 
	 * 
	 */
	

	// PARTIE 0 :
		
	public static void jls(String path)
	/*
	 * Description :
	 * ----------
	 * La méthode jls répond à la partie 0 de l'énnoncé. A savoir, trouver tout 
	 * les fichiers '.java' dans le dossier (et ses sous dossiers) donné en 
	 * paramètre pour mettre dans un fichier csv les valeurs suivantes : 
	 *  - Chemin du ficheir
	 *  - Nom du paquet
	 *  - nom de la classe
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
	{
		String toWrite = "";
		jlsRec(path,toWrite);
		// TODO : créer/écrire dans le fichier csv
		// Que faire si chemin invalide ?

	}

	public static void jlsRec(String path, String ans){
		// TODO : fonction récursive
		// TODO : documentation 
			
	}

}