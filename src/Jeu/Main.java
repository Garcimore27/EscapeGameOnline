package Jeu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

	static final Logger logger = LogManager.getLogger();
	
	public static void main(String[] args) {
		//boolean rejouePartie = false; // prend la valeur "vrai" si on veut rejouer une partie
		int rejoueMemePartie = 0;
		
		Config param = new Config(); //charge la config du fichier contenant les propriétés du jeu (nb Essais, nb Chiffres ...)
		param.verifData(); // Juste pour voir les paramètres chargés ou par défaut
		
		do {
		//Jeux jeu = new Jeux(param); // lance le choix des menus et lance le jeu sélectionné
		Jeux jeu = new Jeux(param, rejoueMemePartie); // lance le choix des menus et lance le jeu sélectionné
		rejoueMemePartie = jeu.choixFinPartie();
		
		//rejouePartie = jeu.rejouer(); // demande si on rejoue
		} while(rejoueMemePartie != 3); // relance le même jeu ou les menus de choix de jeu tant que l'on souhaite jouer
		
		System.out.println("Merci d'avoir joué ! - Au plaisir de vous revoir !");
	}
}
