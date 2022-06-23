package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	private PremierLeagueDAO dao;
	private Graph<Match, DefaultWeightedEdge> grafo;
	private Map<Integer, Match> idMap;
	
	private List<Match> best;
	private int pesoMax;
	
	public Model() {
		super();
		this.dao = new PremierLeagueDAO();
	}
	
	public void creaGrafo(int mese, int min) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.idMap = new HashMap<>();
		
		this.dao.getVertici(mese, idMap);
		
		//Aggiungo i vertici
		Graphs.addAllVertices(this.grafo, idMap.values());
		
		//Aggiungo gli archi
		for(Adiacenza a: this.dao.getAdicenze(mese, min, idMap)) {
			if(this.grafo.containsVertex(a.getM1()) && this.grafo.containsVertex(a.getM2())) {
				Graphs.addEdgeWithVertices(this.grafo, a.getM1(), a.getM2(), a.getPeso());
			}
		}
		System.out.println("Grafo creato!");
	  	System.out.println("#VERTICI: "+ this.grafo.vertexSet().size());
	  	System.out.println("#ARCHI: "+ this.grafo.edgeSet().size());
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}

	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public boolean grafoCreato() {
		if(this.grafo == null) 
			return false;
			else
				return true;
	}
	
	public List<Adiacenza> getConnessioniMax(){
		int pesoMax = 0;
		List<Adiacenza> connessioniMax = new ArrayList<>();
		
		for(DefaultWeightedEdge e: this.grafo.edgeSet()) {
			if(this.grafo.getEdgeWeight(e) > pesoMax) {
				pesoMax = (int) this.grafo.getEdgeWeight(e);
			}
		}
		
		for(DefaultWeightedEdge e: this.grafo.edgeSet()) {
			if(this.grafo.getEdgeWeight(e) == pesoMax) {
				connessioniMax.add(new Adiacenza(this.grafo.getEdgeSource(e), this.grafo.getEdgeTarget(e), (int) this.grafo.getEdgeWeight(e)));
			}
		}
		return connessioniMax;
	}
	
	public List<Match> getVertici(){
		List<Match> vertici = new ArrayList<>(this.grafo.vertexSet());
		return vertici;
	}
	
	public List<Match> trovaCammino(Match partenza, Match arrivo){
		this.best = new ArrayList<>();
		
		this.pesoMax = 0;
		
		List<Match> parziale = new ArrayList<>();
		
		parziale.add(partenza);
		
		cerca(parziale, arrivo, pesoMax);
		
		return best;
		
		
	}

	private void cerca(List<Match> parziale, Match arrivo, int peso) {

		if(peso > this.pesoMax && parziale.get(parziale.size()-1).equals(arrivo)) {
			this.best = new ArrayList<>(parziale);
			pesoMax = peso;
		}
		
		Match ultimo = parziale.get(parziale.size()-1);
		for(Match m: Graphs.neighborListOf(this.grafo, ultimo)) {
			DefaultWeightedEdge e = this.grafo.getEdge(m, ultimo);
			if(e!= null && !parziale.contains(m)) {
				if((m.getTeamHomeID() != ultimo.getTeamHomeID() && m.getTeamAwayID() != ultimo.getTeamAwayID()) || (m.getTeamHomeID() != ultimo.getTeamAwayID() && m.getTeamAwayID() != ultimo.getTeamHomeID())) {
					parziale.add(m);
					peso += this.grafo.getEdgeWeight(e);
					cerca(parziale, arrivo, peso);
					parziale.remove(m);
					peso -= this.grafo.getEdgeWeight(e);
					
				}
			}
		}
		
		
		
	}
}
