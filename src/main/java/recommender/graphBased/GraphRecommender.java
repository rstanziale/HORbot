package recommender.graphBased;

import edu.uci.ics.jung.algorithms.scoring.PageRank;
import edu.uci.ics.jung.algorithms.scoring.PageRankWithPriors;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.OrderedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class GraphRecommender {

    private Graph graph;
    private GraphSettings graphSettings;
    private HashMap<String, String[]> map;
    private int archi_PC = 0, archi_CD = 0, archi_DL = 0, archi_CL=0, archi_LD=0;
    private boolean debug=false;

    public GraphRecommender(GraphSettings graphSettings) throws IOException {
        //debug level, si può attivare in caso ci sia bisogno di risolvere qualche errore
        //debug=true;
        this.graphSettings=graphSettings;
        FromFile.SetData(graphSettings.getCity());

        //Inizializzo il grafo
        graph = new OrderedSparseMultigraph<>();

        //Creazione nodo utente
        String utente="P_user";
        graph.addVertex(utente); //Livello 0
        if(debug) System.out.println("Ho collegato aggiunto "+utente);

        if(debug) System.out.println("Livello 0-1");
        //NB: è stato realizzato solo il caso con un utente, il caso multi utente è riservato a sviluppi futuri
        if (graphSettings.isFull_connected()) {
            graphSettings.getHistory().keySet().forEach((i) -> graph.addEdge("PC:" + (++archi_PC), new Pair<>(utente, "C_" + i), EdgeType.DIRECTED));
            if(debug) graphSettings.getHistory().keySet().forEach((i) -> System.out.println("Ho collegato "+utente+" con "+ "C_"+i));
        }
        else{
            for (String contesto: graphSettings.getContesto()) {
                graph.addEdge("PC:" + (++archi_PC), new Pair<>(utente, "C_"+contesto), EdgeType.DIRECTED);
                if(debug) System.out.println("Ho collegato "+utente+" con " + contesto);
            }
        }//Livello 0-1


        //Leggo da file
        map = FromFile.getPlacesNew();

        if(graphSettings.isInverso()){
            //architettura U->C->D->L
            if(debug) System.out.println("Livello 1-2");
            graphSettings.getHistory().keySet().forEach((i) -> {
                for (String descrizione: graphSettings.getHistory().get(i)) {
                    graph.addEdge("CD:" + (++archi_CD), new Pair<>("C_"+i, "D_"+descrizione), EdgeType.DIRECTED);
                    if(debug) System.out.println("Ho collegato "+i+" con "+ descrizione);
                }
            });//Livello 1-2

            if(debug) System.out.println("Livello 2-3");
            map.keySet().forEach((i) -> {
                for (int j = 0; j < map.get(i).length; j++) {
                    if(graphSettings.isDiretto()) graph.addEdge("DL:" + (++archi_DL), new Pair<>("D_"+map.get(i)[j], "L_"+i), EdgeType.DIRECTED);
                    else graph.addEdge("DL:" + (++archi_DL), new Pair<>("D_"+map.get(i)[j], "L_"+i), EdgeType.UNDIRECTED);
                    if(debug) System.out.println("Ho collegato "+map.get(i)[j]+" con "+ i);
                }
            });//Livello 2-3
        }
        else{
            //architettura U->C->L->D
            if(debug) System.out.println("Livello 1-2");
            graphSettings.getHistory().keySet().forEach((i) -> {
                for (String luogo: graphSettings.getHistory().get(i)) {
                    graph.addEdge("CL:" + (++archi_CL), new Pair<>("C_"+i, "L_"+luogo), EdgeType.DIRECTED);
                    if(debug) System.out.println("Ho collegato "+i+" con "+ luogo);
                }
            });//Livello 1-2

            if(debug) System.out.println("Livello 2-3");
            map.keySet().forEach((i) -> {
                for (int j = 0; j < map.get(i).length; j++) {
                    if(graphSettings.isDiretto()) graph.addEdge("LD:" + (++archi_LD), new Pair<>("L_"+i, "D_"+map.get(i)[j]), EdgeType.DIRECTED);
                    else graph.addEdge("LD:" + (++archi_LD), new Pair<>("L_"+i, "D_"+map.get(i)[j]), EdgeType.UNDIRECTED);
                    if(debug) System.out.println("Ho collegato "+ i +" con "+ map.get(i)[j]);
                }
            });//Livello 2-3

        }
    }

    public HashMap<String, Double> Pagerank() {

        HashMap<String, Double> score_map  = new HashMap<>();
        PageRankWithPriors ranker;

        if(debug) System.out.println("before PageRank");
        //Scelgo l'algoritmo opportuno

        if(graphSettings.getAlg().equals("PageRankPriors")) ranker = new PageRankWithPriors(graph, PriorsFunction.getPriorsFunction(graphSettings), graphSettings.getAlpha());
        else ranker = new PageRank(graph, graphSettings.getAlpha());

        //calcola PageRank
        ranker.setMaxIterations(graphSettings.getMax_iterations());
        ranker.evaluate();

        ArrayList<String> visitati = new ArrayList<>();
        if(graphSettings.isAvoid_visited()){
            for(String s : graphSettings.getHistory().keySet()){
                visitati.addAll(Arrays.asList(graphSettings.getHistory().get(s)));
            }
        }

        if(debug) System.out.println("After PageRank");
        //salvo risultato solo per i Luoghi
        for (Object v : graph.getVertices()) {
            if (v.toString().contains("L_") && !ranker.getVertexScore(v).toString().equals("0.0")) {
                if(!graphSettings.getTo_avoid().contains(v.toString().substring(2))) {
                    if(graphSettings.isAvoid_visited() && visitati.contains(v.toString().substring(2))) continue;
                    score_map.put(v.toString(), (Double) ranker.getVertexScore(v));
                }
            }
        }

        if(debug) System.out.println("Unordered Recommendations");
        if(debug) System.out.println(map);

        //ordinamento mappa in base allo score
        Object[] ordinati = score_map.entrySet().stream()
                .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue())).toArray();


        //svuoto la vecchia mappa
        score_map = new HashMap<>();

        //prendo i primi top_rank. NB: qua li mette bene però nel JSON li mette comunque al contrario.
        for (int i =0 ; i< graphSettings.getTop_rank(); i++) {
            String nome = ordinati[i].toString();
            //String score = nome.substring(nome.indexOf("=") + 1);
            nome = nome.substring(2, nome.indexOf("="));
            score_map.put(nome, i+1.0);
        }

        if(debug) System.out.println("Ordered Recommendations");
        if(debug) System.out.println(score_map);

        return score_map;
    }
}
