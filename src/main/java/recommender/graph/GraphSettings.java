package recommender.graph;

import java.util.ArrayList;
import java.util.Map;

public class GraphSettings {

    //variabili con valori di default
    private String[] contesto;
    private Map<String, String[]> history;
    private String user;
    private int top_rank=3;
    private String city="bari";
    private boolean full_connected=false;
    private boolean diretto=false;
    private Double alpha=0.3;
    private int max_iterations=100;
    private String alg="PageRankPriors";
    private boolean inverso=true;
    private double priors_weights[] = new double[] {1d, 0d, 0d, 0d};
    private ArrayList to_avoid = new ArrayList();
    private boolean avoid_visited = true;

    //costruttore
    public GraphSettings(){

    }

    //get e set vari, il mapping viene effettuato in automatica dal server
    public boolean isInverso() {
        return inverso;
    }

    public ArrayList getTo_avoid() {
        return to_avoid;
    }

    public void setTo_avoid(ArrayList to_avoid) {
        this.to_avoid = to_avoid;
    }


    public int getMax_iterations() {
        return max_iterations;
    }

    public void setMax_iterations(int max_iterations) {
        this.max_iterations = max_iterations;
    }

    public double[] getPriors_weights() {
        return priors_weights;
    }

    public boolean isAvoid_visited() {
        return avoid_visited;
    }

    public void setAvoid_visited(boolean avoid_visited) {
        this.avoid_visited = avoid_visited;
    }

    public void setPriors_weights(double[] priors_weights) {
        this.priors_weights = priors_weights;
    }

    public void setInverso(boolean inverso) {
        this.inverso = inverso;
    }

    public String getAlg() {
        return alg;
    }

    public void setAlg(String alg) {
        this.alg = alg;
    }

    public Double getAlpha() {
        return alpha;
    }

    public void setAlpha(Double alpha) {
        this.alpha = alpha;
    }

    public String[] getContesto() {
        return contesto;
    }

    public void setContesto(String[] contesto) {
        this.contesto = contesto;
    }

    public boolean isDiretto() {
        return diretto;
    }

    public void setDiretto(boolean diretto) {
        this.diretto = diretto;
    }

    public Map<String, String[]> getHistory() {
        return history;
    }

    public void setHistory(Map<String, String[]> history) {
        this.history = history;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getTop_rank() {
        return top_rank;
    }

    public void setTop_rank(int top_rank) {
        this.top_rank = top_rank;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isFull_connected() {
        return full_connected;
    }

    public void setFull_connected(boolean full_connected) {
        this.full_connected = full_connected;
    }

}
