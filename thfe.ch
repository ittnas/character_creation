#Version 1.0
#Writer version 1.02

#Personal information {
	  Nimi
	  Vaatteet
	  Ulkonäkö
	  Luonne
	  Elämäntarina
}

#Global variables {
	priceMultiplier = 0.6
	skillDep = 0.5
	minValue = 3.0
	attrLog = 1.06
	pointLimit = 400
	attrPointLimit = 300
	inheritance = 0.25
	logStr = 1.15
}

#Basic attributes {
       Fyysisyys
       Koordinaatio
       Reaktiot
       Terävyys
       Oppivaisuus
       Määrätietoisuus
}

#Skills {
	Fyysiset ominaisuudet: {
		 Ketteryys: {
		 	    Juokseminen: 6,0.8,Ketteryys (Koordinaatio, Koordinaatio, Fyysisyys, Fyysisyys, Koordinaatio);
			    Hyppiminen: 5,0.8,Ketteryys (Koordinaatio, Koordinaatio, Fyysisyys, Koordinaatio, Fyysisyys);
			    Uiminen: 3.0,0.4,Ketteryys (Koordinaatio, Koordinaatio, Koordinaatio, Fyysisyys, Fyysisyys);
			    Kiipeäminen: 6,0.5,Ketteryys (Koordinaatio, Koordinaatio, Fyysisyys, Fyysisyys, Fyysisyys);
			    }
		Taistelu: {
			  Lähitaistelu: {
			  	Aseet: {
			  	  Miekat: 8.0,0.5,Taistelu (Koordinaatio, Koordinaatio, Reaktiot, Fyysisyys, Reaktiot);
				  Kirveet: 8.0,0.5,Taistelu (Koordinaatio, Fyysisyys, Fyysisyys, Reaktiot, Koordinaatio);
				  Nuijat: 8.0, 0.5,Taistelu (Koordinaatio, Reaktiot, Fyysisyys, Fyysisyys, Fyysisyys);
				  Salkoaseet: 7.0, 0.5,Taistelu (Koordinaatio, Koordinaatio, Reaktiot, Fyysisyys, Fyysisyys);
				  }
				Puolustus: {
				  Väistäminen: 6.0, 0.6,Taistelu (Reaktiot, Reaktiot, Reaktiot, Koordinaatio, Reaktiot);
	  			  Haarniskassa liikkuminen: 6.0, 0.8, Taistelu (Fyysisyys, Fyysisyys, Fyysisyys, Fyysisyys, Koordinaatio);
				  Kilven käyttö: 8.0,0.6,Taistelu (Reaktiot, Reaktiot, Reaktiot,Koordinaatio,Koordinaatio);
				  }
			}
		Pitkän matkan taistelu: {
			Ampuminen: {
				  Jouset: 11,0.5,Taistelu (Koordinaatio, Reaktiot, Reaktiot, Fyysisyys, Reaktiot);0.0
	  			  Varsijouset: 8.0,0.5,Taistelu  (Koordinaatio, Reaktiot, Reaktiot, Reaktiot, Koordinaatio);0.0
	  			  }
	  		Heittäminen: {
				  Tikarin heittäminen: 6.0,0.5,Taistelu (Koordinaatio, Reaktiot, Fyysisyys, Reaktiot, Fyysisyys) ;
				  Kirveen heittäminen: 6.0, 0.5,Taistelu (Fyysisyys, Fyysisyys, Fyysisyys, Koordinaatio, Fyysisyys) ; 0.0
				  Keihään heittäminen: 6.0, 0.5, Taistelu (Fyysisyys, Fyysisyys, Fyysisyys, Koordinaatio, Koordinaatio) ; 0.0
				  }
			}
		}
		Kunto: {
		       Kestävyys: 6.0,0.5,Ketteryys (Fyysisyys,Fyysisyys,Fyysisyys,Fyysisyys,Fyysisyys);0.0
		       Voima: 7.0,0.5, Ketteryys (Fyysisyys, Fyysisyys,Fyysisyys,Fyysisyys,Fyysisyys);0.0
		       }
	}

	Sosiaaliset taidot: {
		Olemus: {
			Uhkailu: 6.0, 0.5,Puhetaidot (Fyysisyys, Fyysisyys, Terävyys, Terävyys, Terävyys) ; 0.0
			Seurustelu: 6.0, 0.5,Puhetaidot (Fyysisyys, Fyysisyys, Terävyys, Terävyys, Terävyys) ; 0.0
		}
		Puhetaito: {
			Neuvottelu: 8.0, 0.5,Puhetaidot (Terävyys, Terävyys, Terävyys, Määrätietoisuus, Määrätietoisuus) ; 0.0
			Valehtelu: 8.0, 0.5,Puhetaidot (Terävyys, Terävyys, Terävyys, Terävyys, Oppivaisuus) ; 0.0			
			Joukoille puhuminen: 6.0, 0.5,Puhetaidot (Fyysisyys, Terävyys, Terävyys, Oppivaisuus, Oppivaisuus) ; 0.0			
			Valehtelun havaitseminen: 6.0, 0.5,Puhetaidot (Terävyys, Terävyys, Terävyys, Oppivaisuus, Oppivaisuus) ; 0.0
		}
	}
Käytännön taidot: {
		Eläinten hoito:  {
			Ratsastaminen: 7.0, 0.5,Taistelu (Koordinaatio, Koordinaatio, Terävyys, Fyysisyys, Koordinaatio);
			Eläinten käsitteleminen: 3.0, 0.5,Erätaidot (Oppivaisuus, Terävyys, Terävyys, Oppivaisuus, Terävyys); 0.0
			Ajotaito: 3.0, 0.5,Erätaidot (Terävyys, Reaktiot, Reaktiot, Koordinaatio, Koordinaatio);
		}
		Ammattitaidot: {
			Käsityöt: 5.0, 0.5,Erätaidot(Fyysisyys, Koordinaatio, Koordinaatio, Terävyys, Oppivaisuus); 0.0
			Sepän taidot: 4.0, 0.5, Erätaidto(Fyysisyys, Fyysisyys, Koordinaatio, Oppivaisuus, Terävyys); 0.0
		}
		Lääkintä: {
			Myrkkyjen tuntemus: 7.0, 0.5,Erätaidot (Oppivaisuus, Oppivaisuus, Terävyys, Terävyys, Oppivaisuus) ; 0.0
			Rohtojen tuntemus: 5.0, 0.5,Erätaidot (Oppivaisuus, Oppivaisuus, Terävyys, Terävyys, Oppivaisuus) ; 0.0
			Tautien parantaminen: 4.0, 0.5,Erätaidot (Oppivaisuus, Terävyys, Terävyys, Terävyys, Oppivaisuus) ; 0.0
			Haavojen hoito: 6.0, 0.5,Erätaidot (Koordinaatio, Koordinaatio, Terävyys, Oppivaisuus, Oppivaisuus) ; 0.0
		}
		Erätaidot: {
			Jäljittäminen: 5.0,0.5,Erätaidot (Terävyys,Määrätietoisuus,Määrätietoisuus,Terävyys,Oppivaisuus);0.0
			Suunnistaminen: 5.0,0.7,Erätaidot (Terävyys,Terävyys,Terävyys,Oppivaisuus,Määrätietoisuus);0.0
			Luonnossa selviytyminen: 5,0.3,Erätaidot (Terävyys, Terävyys,Oppivaisuus,Oppivaisuus,Oppivaisuus);0.0
		}
	}
	Salavihkaisuus:  {
		Äänettömyys: {
			Hiipiminen: 8.0, 0.5,Salavihkaisuus (Koordinaatio, Koordinaatio, Koordinaatio, Terävyys, Koordinaatio) ; 0.0
			Piiloutuminen: 5.0, 0.5,Salavihkaisuus (Koordinaatio, Koordinaatio, Terävyys, Terävyys, Terävyys) ; 0.0
		}
		Näppäryys:  {
			Taskuvarkaus: 7.0, 0.5 ,Salavihkaisuus(Koordinaatio, Reaktiot, Reaktiot, Reaktiot, Reaktiot) ; 0.0
			Tiirikointi: 7.0, 0.5,Salavihkaisuus (Koordinaatio, Koordinaatio, Koordinaatio, Terävyys, Terävyys) ; 0.0
		}
		Tarkkaavaisuus: {
			Havainnointi: 8.0, 0.5,Salavihkaisuus (Määrätietoisuus, Määrätietoisuus, Fyysisyys, Terävyys, Terävyys) ; 0.0
			Aloitekyky: 9.0, 0.5,Salavihkaisuus (Reaktiot, Reaktiot, Reaktiot, Reaktiot, Reaktiot) ; 0.0
		}
	}

	Tiedot: {
		Hovitieto:  {
			Heraldiikka: 4.0, 0.5,Tiedot(Oppivaisuus, Oppivaisuus, Oppivaisuus, Oppivaisuus, Terävyys) ; 0
			Etiketti: 4.0, 0.5,Tiedot (Terävyys, Terävyys, Terävyys, Oppivaisuus, Oppivaisuus) ; 0
			Politiikka: 4.0, 0.5,Tiedot (Oppivaisuus, Oppivaisuus, Oppivaisuus, Terävyys, Terävyys) ; 0
		}
		Sotatiede: {
			  Strategia: 4.0, 0.5, Tiedot(Oppivaisuus, Oppivaisuus, Terävyys, Terävyys, Terävyys) ; 0
			  Taktiikka: 4.0, 0.5, Tiedot(Oppivaisuus, Oppivaisuus, Terävyys,Terävyys,Terävyys) ; 0
		}
		Tieteet: {
			Insinööritaito: 4.0, 0.5 ,Tiedot(Oppivaisuus, Oppivaisuus, Oppivaisuus, Oppivaisuus, Terävyys) ; 0
			Alkemia: 4.0, 0.5 ,Tiedot(Oppivaisuus, Oppivaisuus, Terävyys, Terävyys, Oppivaisuus) ; 0
			Äidinkieli: 5.0,0.8,Tiedot(Oppivaisuus,Oppivaisuus,Oppivaisuus,Oppivaisuus,Terävyys); 0
		}
		Matkailu: {
			Kielitaito: 5.0, 0.5 ,Tiedot(Oppivaisuus, Oppivaisuus, Terävyys, Terävyys, Oppivaisuus) ; 0
			Merenkulku: 4.0, 0.5 ,Tiedot(Oppivaisuus, Terävyys, Terävyys, Koordinaatio, Terävyys) ; 0
			Maantiede: 5.0, 0.5, Tiedot(Oppivaisuus, Oppivaisuus, Terävyys, Oppivaisuus, Terävyys) ; 0
			
		}
	}

	Luonne: 0,0.5,1.0{
		Paheellisuus: 10,0.8,1.0,Luonne(Määrätietoisuus,Määrätietoisuus,Määrätietoisuus,Määrätietoisuus,Määrätietoisuus);
		Raivokkuus: 10,0.8,1.0,Luonne(Määrätietoisuus,Määrätietoisuus,Määrätietoisuus,Määrätietoisuus,Määrätietoisuus);
		Päättäväisyys: 6.0,0.8,1.0,Luonne(Määrätietoisuus,Määrätietoisuus,Määrätietoisuus,Määrätietoisuus,Terävyys);
		Rohkeus: 10,0.8,1.0,Luonne (Fyysisyys, Määrätietoisuus, Määrätietoisuus, Fyysisyys, Määrätietoisuus);
	}
		
}
