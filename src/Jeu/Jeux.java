package Jeu;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * La classe Jeux définit les 3 modes de jeux (Challenger, Défenseur et Duel) et une méthode pour demander à rejouer 
 * @author agarnij
 *
 */

public class Jeux {
	static final Logger logger = LogManager.getLogger();
	private String jeuChoisi = "non"; // contient "non" si pas encore de choix ou Challenger ou Défenseur ou Duel
	private boolean choix = false; //si on a choisi un jeu, cette variable est vraie sinon elle est fausse
	protected Scanner sc = new Scanner(System.in); // lecture des entrées au clavier
	//private int nbCoups = 0; // Contient le nombre de propositions effectuées ... déplacé dans la classe JouerMode
	static int choixMode = -1; // Contient le n° du jeu choisi et conserve ce choix pour la prochaine instance si le joueur souhaite rejouer au même jeu
	
	/**
	 * Affiche les menus et intercepte les choix de l'utilisateur
	 * @param parametres
	 */
	public Jeux(Config parametres, int numMemeJeu) {
		
		if (!choix && (numMemeJeu == 0 || numMemeJeu == 2)) {
            System.out.println("A quel jeu souhaitez-vous jouer ?");
            int i = 0;
            //Liste des énumérations de ChoixJeux
            for (ChoixJeux detail: ChoixJeux.values()) {
            	i++;
            	System.out.println(i + " - " + detail.toString());
            }
            /*
            System.out.println("1 - Challenger : A vous de trouver le code détenu par la Machine");
            System.out.println("2 - Défenseur : La Machine cherche votre code");
            System.out.println("3 - Duel : le premier qui trouve le code de l'autre a gagné !");
            */
            while (!choix) {
            	boolean typeReponseOK;
                do {
                    try {
                    	choixMode = sc.nextInt();
                    	if (choixMode > 0 && choixMode <= ChoixJeux.values().length) {
                    		typeReponseOK = true;
                    	} else {
                            System.out.println("Le nombre saisi doit être compris entre 1 et "+ ChoixJeux.values().length + ". Recommencez !");
                            logger.error("Réponse inattendue dans le choix des jeux ... l'entier saisi ne correspond à aucun jeu !");
                    		typeReponseOK = false;
                    	}
                   	
                    } catch (InputMismatchException e) {
                        sc.next();
                        System.out.println("Vous devez saisir un nombre entre 1 et "+ ChoixJeux.values().length + " pour déclencher le jeu correspondant !");
                        logger.error("Réponse inattendue dans le choix des jeux ... ce n'est pas un entier !");
                        typeReponseOK = false;
                    }
                } while (!typeReponseOK);

                sc.nextLine();

                switch (choixMode) {
                    case 1:
                        this.jeuChoisi = ChoixJeux.Challenger.getNom();
                        choix = true;
                        break;

                    case 2:
                        this.jeuChoisi = ChoixJeux.Defenseur.getNom();
                        choix = true;
                        break;

                    case 3:
                        this.jeuChoisi = ChoixJeux.Duel.getNom();
                        choix = true;
                        break;

                    default:
                        System.out.println("Vous avez fait une erreur, ce choix n'existe pas !");

                }
                System.out.println("Mode " + this.jeuChoisi + " sélectionné !");
                logger.info("Mode "+ this.jeuChoisi + " sélectionné !");	
            }
            
          
        }
		JouerMode quelMode = new JouerMode();
		lancementJeu(parametres, choixMode, quelMode);
         	
 	}
	
	public void lancementJeu(Config para, int jeuChoix, JouerMode quelMode) {
		switch(jeuChoix) {
		case 1:
			quelMode.ModeChallenger(para);
			break;
		case 2:
			quelMode.ModeDefenseur(para);
			break;
		case 3:
			quelMode.ModeDuel(para);
			break;
		}
		
	}
	
	/*
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
				
		Joueur homme = new Joueur(listeParametres.getNbChiffres());
		
		do {
			nbCoups ++;
			prop = "";
			for (int i=0; i < homme.creeCombi.getPropositionCombi().size(); i++) {
				prop += homme.creeCombi.getPropositionCombi().get(i).toString();
			}
			if (nbCoups ==1) {
				System.out.println("Merci d'indiquer des +, des - ou des = lorsque chacun des chiffres à deviner est plus grand, moins grand ou égal à ceux qui sont proposés !");
				System.out.print(nbCoups + "ère Proposition : ");
			}else {
				System.out.print(nbCoups + "ème Proposition : ");
			}
		
			System.out.println(homme.creeCombi.getPropositionCombi());
			
			indices = sc.nextLine();
			
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

// Ancienne méthode pour rejouer mais ne prend pas en compte le fait de rejouer le même jeu.
public boolean rejouer(){
    while (true){
        System.out.println("Souhaitez-vous rejouer ? - O / N ");
        String choixRejouer = sc.nextLine();
        choixRejouer = choixRejouer.toUpperCase();
        switch(choixRejouer){
            case "O" :
                return true;
            case "N" :
                return false;
            default :
                System.out.println("Je n'ai pas compris votre réponse !");
                continue;
        	}
    	}
	}*/

public Integer choixFinPartie() {
	boolean choixOK = false;
	int retourChoix = 0;
	do {
        try {
        	System.out.println("Que souhaitez-vous faire maintenant ? \n" +
    	            "1 - Relancer le même jeu ? \n" +
    	            "2 - Revoir le choix de tous les jeux ? \n" +
    	            "3 - Quitter l'application ? \n");
    	    System.out.print("Votre choix ? 1 - 2 ou 3 : ");
        	retourChoix = sc.nextInt();
        	if (retourChoix > 0 && retourChoix < 4) {
        		choixOK = true;
        	} else {
                System.out.println("Le nombre saisi doit être compris entre 1 et 3. Recommencez !");
                logger.error("Réponse inattendue dans le choix des menus ... l'entier saisi ne correspond à aucun jeu !");
        		choixOK = false;
        		retourChoix = 0;
        	}
       	
        } catch (InputMismatchException e) {
            sc.next();
            System.out.println("Vous devez saisir un nombre entre 1 et 3 pour déclencher l'action correspondante !");
            logger.error("Réponse inattendue dans le choix des menus (1 à 3) ... ce n'était pas un entier !");
            choixOK = false;
            retourChoix = 0;
        }
    } while (!choixOK);

	return retourChoix;
	
	}
}

