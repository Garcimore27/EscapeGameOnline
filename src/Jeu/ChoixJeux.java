package Jeu;

public enum ChoixJeux {
	
	//Objets construits
	Challenger("Challenger : A vous de trouver le code d�tenu par la Machine"),
	Defenseur("D�fenseur : La Machine cherche votre code"),
	Duel("Duel : le premier qui trouve le code de l'autre a gagn� !");

	private String details = "";
	
	//Constructeur
	ChoixJeux(String details){
		
		this.details = details;
	}
	
	public String toString() {
		
		return details;
	}
	
	
	
	
}
