package Jeu;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author agarnij
 * 
 * La classe Config permet de lire un fichier qui contient les param�tres du jeu
 * Si la lecture du fichier de config �choue, on pr�voit qd m�me des valeurs par d�faut
 * 
 */
public class Config {
	protected static final Logger logger = LogManager.getLogger();
	private boolean modeDeveloppeur = false; // indique si la combinaison � trouver est affich�e (mode d�veloppeur) ou non
	private int nbEssais = 6; // nb de tentatives par d�faut
	private int nbChiffres = 5; //nb de chiffres � deviner par d�faut

	/** 
	 * Constructeur de la classe Config 
	 */
	public Config() {
		// On essaie de lire un fichier contenant les valeurs n�cessaires au jeu
		try {
			FileInputStream fichierInput = new FileInputStream(new File("./resources/config.properties")); // On instancie un Stream du fichier config.properties, de nom fichierInput 
			//DataInputStream dis = new DataInputStream(fichierInput);
			BufferedInputStream bis = new BufferedInputStream(fichierInput);
			Properties proprietes = new Properties(); // 
			proprietes.load(bis);
			fichierInput.close();
			bis.close();
			
			// On attribue maintenant les propri�t�s de l'objet en cours gr�ce � la lecture du fichier de config
			this.modeDeveloppeur = Boolean.parseBoolean(proprietes.getProperty("modeDeveloppeur"));
			this.nbEssais = Integer.parseInt(proprietes.getProperty("nbEssais"));
			this.nbChiffres = Integer.parseInt(proprietes.getProperty("nbChiffres"));
			
			logger.info("Les param�tres du fichier de configuration config.properties ont �t� charg�s !");		
			
		} catch(FileNotFoundException e) {
			//System.out.println(e.getMessage());
			logger.error("Le fichier de configuration config.properties n'a pu �tre trouv� !");
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	* Retourne la valeur de nbEssais
	*/
	public int getNbEssais() {
		return nbEssais;
	}
	
	/**
	* Retourne la valeur de nbChiffres
	*/
	public int getNbChiffres() {
		return nbChiffres;
	}
	
	/**
	* Retourne la valeur de nbChiffres
	*/
	public boolean getModeDeveloppeur() {
		return modeDeveloppeur;
	}
	
	/**
	 * M�thode afin de v�rifier que les attributs de l'instance ont bien �t� renseign�s
	 */
	public void verifData() {
		System.out.println("Mode D�veloppeur : " + this.getModeDeveloppeur() + " - Nb Essais : " + this.getNbEssais() + " - Nb Chiffres : " + this.getNbChiffres());
	}
	
	
}