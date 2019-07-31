package Jeu;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JouerMode implements Jouable{
	static final Logger logger = LogManager.getLogger();
	private int nbCoups = 0; // Contient le nombre de propositions effectu�es
	protected Scanner sc = new Scanner(System.in); // lecture des entr�es au clavier
	
	public JouerMode(){
		
	}
	
	public void ModeChallenger(Config listeParametres) {
		String indices = ""; // contient les +, - ou = ... indices donn�s par la machine
		String prop = ""; // Contient la proposition d'une combinaison 
		boolean perdu = false;
		String aTrouver = "";
		
		Joueur machine = new Joueur(listeParametres.getNbChiffres());
		
		
		for (int i = 0; i < machine.creeCombi.getGenereCombi().size(); i++) {
			aTrouver += machine.creeCombi.getGenereCombi().get(i).toString();
		}
				
		if(listeParametres.getModeDeveloppeur()) {
			
			System.out.println("Mode d�veloppeur activ� - La combinaison � trouver est : " + aTrouver);
			}
		System.out.println("Essayez de deviner ma combinaison, veuillez entrer une premi�re proposition de " + machine.creeCombi.getGenereCombi().size() + " chiffres");
		prop = sc.nextLine();
		
		while (!machine.gagne && !perdu) {
			nbCoups ++;
			indices = machine.Compare(prop);
			
			if (nbCoups == 1) {
				System.out.print(nbCoups + "�re Proposition : ");
			}else {
				System.out.print(nbCoups + "�me Proposition : ");
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
			System.out.println("Bravo, vous avez trouv� la bonne combinaison en " + nbCoups + " essai(s) !");
		}
		
	}
	
	public void ModeDefenseur(Config listeParametres) {
		String indices = ""; // contient les +, - ou = ... indices donn�s par le joueur
		String prop = ""; // Contient la proposition d'une combinaison calcul�e par le programme 
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
					System.out.println("Recommencez, la proposition est erron�e !");
				}
				if (nbCoups ==1) {
					System.out.println("Merci d'indiquer des +, des - ou des = lorsque chacun des chiffres � deviner est plus grand, moins grand ou �gal � ceux qui sont propos�s !");
					System.out.print(nbCoups + "�re Proposition : ");
				}else {
					System.out.print(nbCoups + "�me Proposition : ");
				}
			
				System.out.println(homme.creeCombi.getPropositionCombi());
				
				indices = sc.nextLine();
				
			} while (!vErreurs.estCeQueSignesOK(indices, prop));
	
		} while (!(homme.nouvelleCombinaison(indices, homme.creeCombi.getPropositionCombi())) && (nbCoups < listeParametres.getNbEssais()));
		
		if(homme.nouvelleCombinaison(indices, homme.creeCombi.getPropositionCombi())) {
			System.out.println("Le programme a trouv� la combinaison en " + nbCoups + " essai(s) !");
		}else {
			System.out.println("Bravo, le programme n'a pas trouv� votre combinaison et a atteint le nb de coups max : " + nbCoups + " essai(s) !");
		}
	}
	
	public void ModeDuel(Config listeParametres) {
		
		// Le mode duel consiste � alterner le mode challenger et le mode Defenseur
		String indices = ""; // contient les +, - ou = ... indices donn�s par la machine
		String prop = ""; // Contient la proposition d'une combinaison calcul�e par le programme 
		boolean perdu = false;
		String aTrouver = "";
		int nbSize = 0;
		
		String indicesHomme = ""; // contient les +, - ou = ... indices donn�s par le joueur
		String propMachine = ""; // Contient la proposition d'une combinaison calcul�e par le programme 
				
		System.out.println("Mode Duel activ� - Chacun d'entre nous, alternativement, doit tenter de trouver la combinaison de l'autre !");
		System.out.println("Celui qui la trouve le plus vite a gagn� !");
		
		Joueur homme = new Joueur(listeParametres.getNbChiffres()); //Idem mode Defenseur
		Joueur machine = new Joueur(listeParametres.getNbChiffres()); // Idem mode Challenger
		
		for (int i = 0; i < machine.creeCombi.getGenereCombi().size(); i++) {
			aTrouver += machine.creeCombi.getGenereCombi().get(i).toString(); // contient la fameuse combinaison d�finie par le programme que le joueur doit trouver
		}
		
		// Idem mode Challenger
		if(listeParametres.getModeDeveloppeur()) {
			System.out.println("Mode d�veloppeur activ� - La combinaison � trouver est : " + aTrouver);
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
				System.out.print(nbCoups + "�re Proposition : ");
			}else {
				System.out.print(nbCoups + "�me Proposition : ");
			}
		
			System.out.println(prop + " : " + indices);
			
			if (nbCoups == listeParametres.getNbEssais() && !machine.gagne) {
				perdu = true;
				System.out.println("Vous avez perdu, le nombre maximum d'essais est atteint, il fallait trouver la combinaison : " + aTrouver);
				System.out.println("Voyons si la machine fait mieux !");
			}
			if(machine.gagne){
				System.out.println("Bravo, vous avez trouv� la bonne combinaison en " + nbCoups + " essai(s) !");
				System.out.println("Voyons si la machine fait aussi bien pour son dernier essai !");
			}
			
			//Partie mode Defenseur
			propMachine = "";
			for (int i=0; i < homme.creeCombi.getPropositionCombi().size(); i++) {
				propMachine += homme.creeCombi.getPropositionCombi().get(i).toString();
			}
			if (nbCoups ==1) {
				System.out.println("Merci d'indiquer des +, des - ou des = lorsque chacun des chiffres � deviner est plus grand, moins grand ou �gal � ceux qui sont propos�s !");
				System.out.print("Mode D�fenseur - " + nbCoups + "�re Proposition : ");
			}else {
				System.out.print("Mode D�fenseur - " + nbCoups + "�me Proposition : ");
			}
			System.out.println(propMachine);//homme.creeCombi.propositionCombi
			
			indicesHomme = sc.nextLine();
			
			if (nbCoups == listeParametres.getNbEssais() && !homme.gagne) {
				perdu = true;
				System.out.println("La machine a perdu, le nombre maximum d'essais est atteint !");
			}
			
			if(homme.nouvelleCombinaison(indicesHomme, homme.creeCombi.getPropositionCombi())) {
				System.out.println("La machine a trouv� la bonne combinaison en " + nbCoups + " essai(s) !");
			}
		} 
		
		// Si l'homme gagne et que la machine a perdu :
		if (nbCoups != listeParametres.getNbEssais() && !homme.gagne) {
			perdu = true;
			System.out.println("La machine a perdu !");
		}
	}
	
}
