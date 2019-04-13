package recommender.contentBased.services;

import common.UserPreferences;
import common.Utils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import recommender.RecommendUtils;
import recommender.contentBased.beans.Item;
import recommender.contextAware.beans.UserContext;
import recommender.graphBased.GraphRecommender;
import recommender.graphBased.GraphSettings;
import settings.HORMessages;
import survey.context.beans.Activity;
import survey.context.beans.Context;
import survey.context.beans.Location;

import java.io.IOException;
import java.util.*;

/**
 * Define Recommender class
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class Recommender {

    private StandardAnalyzer analyzer;
    private Directory index;
    private String city;

    /**
     * Constructor of Recommender
     * @param pois list of Items
     * @param city representing city of recommendation
     */
    public Recommender(List<Item> pois, String city) {
        this.city = city;
        this.analyzer = new StandardAnalyzer();
        this.index = new RAMDirectory();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        IndexWriter w;
        try {
            w  = new IndexWriter(index, config);
            for (Item p : pois) {
                addDoc(w,
                        p.getWebsite(),
                        p.getName(),
                        p.getAddress(),
                        p.getPhone(),
                        p.getTags(),
                        p.getRatingAverage(),
                        p.getLat(),
                        p.getLng());
            }
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lucene method for search into Index relevant document to recommend
     * @param userPreferences representing user preferences
     * @param userContext representing user context
     * @param location is user location
     * @param recommendType representing recommend type
     * @return a list of relevant item to recommend
     * @throws IOException for Input/Output exception
     * @throws ParseException for search document exception
     */
    public List<Item> recommend(UserPreferences userPreferences,
                                UserContext userContext,
                                Location location,
                                int recommendType) throws IOException, ParseException {
        if (recommendType >=0 && recommendType < 4) {
            String query = RecommendUtils.generateQueryAccordingRecommendType(
                    userPreferences,
                    userContext,
                    recommendType);
            Query q = new QueryParser("tags", analyzer).parse(query);

            int hitsPerPage = 50;
            IndexReader reader = DirectoryReader.open(index);
            IndexSearcher searcher = new IndexSearcher(reader);

            TopDocs docs = searcher.search(q, hitsPerPage);
            ScoreDoc[] hits = docs.scoreDocs;

            Set<Item> items = new TreeSet<>();
            for (ScoreDoc hit : hits) {
                int docId = hit.doc;
                Document d = searcher.doc(docId);

                if (this.isNearbyItem(location.getLatitude(), Float.valueOf(d.get("lat")),
                        location.getLongitude(), Float.valueOf(d.get("lng")))) {
                    Item i = new Item(d.get("website"),
                            d.get("name"),
                            d.get("address"),
                            d.get("phone"),
                            d.get("tags"),
                            Double.valueOf(d.get("ratingAverage")),
                            Float.valueOf(d.get("lat")),
                            Float.valueOf(d.get("lng")));
                    i.setScore(hit.score);
                    i.setRecommenderType(recommendType);
                    i.setQuery(query);
                    items.add(i);
                }
            }
            reader.close();

            return new ArrayList<>(items);
        } else {
            GraphSettings gs = new GraphSettings();
            gs.setCity(this.city);

            Map<String, String[]> contextForGraph = this.setContextForGraphRecommend(userPreferences);
            gs.setContesto(contextForGraph.keySet().toArray(new String[0]));
            gs.setHistory(contextForGraph);

            GraphRecommender graph = new GraphRecommender(gs);
            HashMap<String, Double> map = graph.Pagerank();

            Set<Item> items = new TreeSet<>();
            for (String contextQuery : map.keySet())  {
                Term term = new Term("name", contextQuery);
                Query q = new TermQuery(term);

                int hitsPerPage = 1;
                IndexReader reader = DirectoryReader.open(index);
                IndexSearcher searcher = new IndexSearcher(reader);

                TopDocs docs = searcher.search(q, hitsPerPage);
                ScoreDoc[] hits = docs.scoreDocs;
                for (ScoreDoc hit : hits) {
                    int docId = hit.doc;
                    Document d = searcher.doc(docId);
                    Item i = new Item(d.get("website"),
                            d.get("name"),
                            d.get("address"),
                            d.get("phone"),
                            d.get("tags"),
                            Double.valueOf(d.get("ratingAverage")),
                            Float.valueOf(d.get("lat")),
                            Float.valueOf(d.get("lng")));
                    i.setScore(hit.score);
                    i.setRecommenderType(recommendType);
                    i.setQuery(contextQuery);
                    items.add(i);
                }
                reader.close();
            }

            return new ArrayList<>(items);
        }
    }

    /**
     * Lucene method for add a document to index representing the item to indexing
     * @param w is the IndexWriter of Lucene
     * @param website representing the website of item
     * @param name representing the name of item
     * @param address representing the address of item
     * @param phone representing the phone of item
     * @param tags representing the TAGS of item
     * @param ratingAverage representing rating average
     * @param lat representing the latitude of item
     * @param lng representing the longitude of item
     * @throws IOException for Input/Output exception
     */
    private static void addDoc(IndexWriter w,
                               String website, String name, String address, String phone, String tags,
                               double ratingAverage, float lat, float lng) throws IOException {
        Document doc = new Document();
        doc.add(new StringField("name", name, Field.Store.YES));
        doc.add(new StringField("website", website, Field.Store.YES));
        doc.add(new StringField("address", address, Field.Store.YES));
        doc.add(new StringField("phone", phone, Field.Store.YES));
        doc.add(new TextField("tags", tags, Field.Store.YES));
        doc.add(new StringField("ratingAverage", String.valueOf(ratingAverage), Field.Store.YES));
        doc.add(new StringField("lat", String.valueOf(lat), Field.Store.YES));
        doc.add(new StringField("lng", String.valueOf(lng), Field.Store.YES));
        w.addDocument(doc);
    }

    /**
     * Check if two Items are close
     * @param lat1 representing Item 1 latitude
     * @param lat2 representing Item 2 latitude
     * @param lon1 representing Item 1 longitude
     * @param lon2 representing Item 2 longitude
     * @return boolean flag
     */
    private boolean isNearbyItem(double lat1, double lat2, double lon1, double lon2) {
        return Utils.distance(lat1, lat2, lon1, lon2, 0.0, 0.0) < HORMessages.THRESHOLD;
    }

    /**
     * Set map for graphBased-based recommend
     * @param userPreferences representing user preferences
     * @return a map of context, activities
     */
    private Map<String, String[]> setContextForGraphRecommend(UserPreferences userPreferences) {
        Map<String, String[]> contextForGraph = new HashMap<>();
        for (Context c : userPreferences.getSurveyContext().getSurveyValues()) {
            List<String> activities = new ArrayList<>();
            for (Activity a : c.getActivities()) {
                if (a.isChecked()) {
                    Collections.addAll(activities, HORMessages.TAGS.get(a.getActivityName()).split(" "));
                }
            }
            contextForGraph.put(c.getContextName(), activities.toArray(new String[0]));
        }
        return contextForGraph;
    }
}