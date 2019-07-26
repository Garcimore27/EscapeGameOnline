package Jeu;

public enum ChoixJeux {
	
	//Objets construits
	Challenger("Challenger", "A vous de trouver le code détenu par la Machine"),
	Defenseur("Défenseur", "La Machine cherche votre code"),
	Duel("Duel", "Le premier qui trouve le code de l'autre a gagné !");

	private String nom = "";
	private String details = "";
	
	//Constructeur
	ChoixJeux(String nom, String details){
		
		this.nom = nom;
		this.details = details;
	}
	
	public String getNom() {
		return nom;
	}
	
	public String getDetails() {
		return details;
	}
	
	public String toString() {
		
		return nom + " : " + details;
	}
	
	
	
	
}
