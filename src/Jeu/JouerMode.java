package Jeu;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JouerMode implements Jouable{
	static final Logger logger = LogManager.getLogger();
	private int nbCoups = 0; // Contient le nombre de propositions effectuées
	protected Scanner sc = new Scanner(System.in); // lecture des entrées au clavier
	
	public JouerMode(){
		
	}
	
	public void ModeChallenger(Config listeParametres) {
		String indices = ""; // contient les +, - ou = ... indices donnés par la machine
		String prop = ""; // Contient la proposition d'une combinaison 
		boolean perdu = false;
		String aTrouver = "";
		
		Joueur machine = new Joueur(listeParametres.getNbChiffres());
		
		
		for (int i = 0; i < machine.creeCombi.getGenereCombi().size(); i++) {
			aTrouver += machine.creeCombi.getGenereCombi().get(i).toString();
		}
				
		if(listeParametres.getModeDeveloppeur()) {
			
			System.out.println("Mode développeur activé - La combinaison à trouver est : " + aTrouver);
			}
		System.out.println("Essayez de deviner ma combinaison, veuillez entrer une première proposition de " + machine.creeCombi.getGenereCombi().size() + " chiffres");
		prop = sc.nextLine();
		
		while (!machine.gagne && !perdu) {
			nbCoups ++;
			indices = machine.Compare(prop);
			
			if (nbCoups == 1) {
				System.out.print(nbCoups + "ère Proposition : ");
			}else {
				System.out.print(nbCoups + "ème Proposition : ");
			}
		
			System.out.println(prop + " : " + indices);
			
			if (nbCoups == listeParametres.getNbEssais() && !machine.gagne) {
				perdu = true;
			} else if(!machine.gagne){
				System.out.println("Quelle est votre nouvelle proposition ?");
				prop = sc.nextLine();
			}
		} 
		
		if(perdu) {
			System.out.println("Vous avez perdu, il fallait trouver la combinaison : " + aTrouver);
		}else {
			System.out.println("Bravo, vous avez trouvé la bonne combinaison en " + nbCoups + " essai(s) !");
		}
		
	}
	
	public void ModeDefenseur(Config listeParametres) {
		String indices = ""; // contient les +, - ou = ... indices donnés par le joueur
		String prop = ""; // Contient la proposition d'une combinaison calculée par le programme 
		VerificationErreurs vErreurs = new VerificationErreurs();
		Joueur homme = new Joueur(listeParametres.getNbChiffres());
		int cpteMauvaiseProposition;
		
		do {
			nbCoups ++;
			prop = "";
			for (int i=0; i < homme.creeCombi.getPropositionCombi().size(); i++) {
				prop += homme.creeCombi.getPropositionCombi().get(i).toString();
			}
			cpteMauvaiseProposition = -1;
			do {
				cpteMauvaiseProposition +=1;
				if (cpteMauvaiseProposition>0) {
					System.out.println("Recommencez, la proposition est erronée !");
				}
				if (nbCoups ==1) {
					System.out.println("Merci d'indiquer des +, des - ou des = lorsque chacun des chiffres à deviner est plus grand, moins grand ou égal à ceux qui sont proposés !");
					System.out.print(nbCoups + "ère Proposition : ");
				}else {
					System.out.print(nbCoups + "ème Proposition : ");
				}
			
				System.out.println(homme.creeCombi.getPropositionCombi());
				
				indices = sc.nextLine();
				
			} while (!vErreurs.estCeQueSignesOK(indices, prop));
	
		} while (!(homme.nouvelleCombinaison(indices, homme.creeCombi.getPropositionCombi())) && (nbCoups < listeParametres.getNbEssais()));
		
		if(homme.nouvelleCombinaison(indices, homme.creeCombi.getPropositionCombi())) {
			System.out.println("Le programme a trouvé la combinaison en " + nbCoups + " essai(s) !");
		}else {
			System.out.println("Bravo, le programme n'a pas trouvé votre combinaison et a atteint le nb de coups max : " + nbCoups + " essai(s) !");
		}
	}
	
	public void ModeDuel(Config listeParametres) {
		
		// Le mode duel consiste à alterner le mode challenger et le mode Defenseur
		String indices = ""; // contient les +, - ou = ... indices donnés par la machine
		String prop = ""; // Contient la proposition d'une combinaison calculée par le programme 
		boolean perdu = false;
		String aTrouver = "";
		int nbSize = 0;
		
		String indicesHomme = ""; // contient les +, - ou = ... indices donnés par le joueur
		String propMachine = ""; // Contient la proposition d'une combinaison calculée par le programme 
				
		System.out.println("Mode Duel activé - Chacun d'entre nous, alternativement, doit tenter de trouver la combinaison de l'autre !");
		System.out.println("Celui qui la trouve le plus vite a gagné !");
		
		Joueur homme = new Joueur(listeParametres.getNbChiffres()); //Idem mode Defenseur
		Joueur machine = new Joueur(listeParametres.getNbChiffres()); // Idem mode Challenger
		
		for (int i = 0; i < machine.creeCombi.getGenereCombi().size(); i++) {
			aTrouver += machine.creeCombi.getGenereCombi().get(i).toString(); // contient la fameuse combinaison définie par le programme que le joueur doit trouver
		}
		
		// Idem mode Challenger
		if(listeParametres.getModeDeveloppeur()) {
			System.out.println("Mode développeur activé - La combinaison à trouver est : " + aTrouver);
			}
		
		nbSize = machine.creeCombi.getGenereCombi().size();
		
		
		// Idem mode Challenger
		while (!machine.gagne && !perdu && !homme.gagne) {
			//propMachine = "";
			// Idem mode Challenger
			System.out.println("Proposition de " + nbSize + " chiffres : ");
			prop = sc.nextLine();
			
			nbCoups ++;
			indices = machine.Compare(prop);
			
			if (nbCoups == 1) {
				System.out.print(nbCoups + "ère Proposition : ");
			}else {
				System.out.print(nbCoups + "ème Proposition : ");
			}
		
			System.out.println(prop + " : " + indices);
			
			if (nbCoups == listeParametres.getNbEssais() && !machine.gagne) {
				perdu = true;
				System.out.println("Vous avez perdu, le nombre maximum d'essais est atteint, il fallait trouver la combinaison : " + aTrouver);
				System.out.println("Voyons si la machine fait mieux !");
			}
			if(machine.gagne){
				System.out.println("Bravo, vous avez trouvé la bonne combinaison en " + nbCoups + " essai(s) !");
				System.out.println("Voyons si la machine fait aussi bien pour son dernier essai !");
			}
			
			//Partie mode Defenseur
			propMachine = "";
			for (int i=0; i < homme.creeCombi.getPropositionCombi().size(); i++) {
				propMachine += homme.creeCombi.getPropositionCombi().get(i).toString();
			}
			if (nbCoups ==1) {
				System.out.println("Merci d'indiquer des +, des - ou des = lorsque chacun des chiffres à deviner est plus grand, moins grand ou égal à ceux qui sont proposés !");
				System.out.print("Mode Défenseur - " + nbCoups + "ère Proposition : ");
			}else {
				System.out.print("Mode Défenseur - " + nbCoups + "ème Proposition : ");
			}
			System.out.println(propMachine);//homme.creeCombi.propositionCombi
			
			indicesHomme = sc.nextLine();
			
			if (nbCoups == listeParametres.getNbEssais() && !homme.gagne) {
				perdu = true;
				System.out.println("La machine a perdu, le nombre maximum d'essais est atteint !");
			}
			
			if(homme.nouvelleCombinaison(indicesHomme, homme.creeCombi.getPropositionCombi())) {
				System.out.println("La machine a trouvé la bonne combinaison en " + nbCoups + " essai(s) !");
			}
		} 
		
		// Si l'homme gagne et que la machine a perdu :
		if (nbCoups != listeParametres.getNbEssais() && !homme.gagne) {
			perdu = true;
			System.out.println("La machine a perdu !");
		}
	}
	
}
