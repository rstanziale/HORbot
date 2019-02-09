package recommender.contentBased.services;

import settings.HORmessages;
import common.Utils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import recommender.contentBased.beans.Item;
import survey.context.beans.Location;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    /**
     * Constructor of Recommender
     * @param CSVPath path of file
     */
    public Recommender(String CSVPath) {
        List<Item> pois = this.readCSV(CSVPath);

        this.analyzer = new StandardAnalyzer();
        this.index = new RAMDirectory();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        IndexWriter w;
        try {
            w  = new IndexWriter(index, config);
            for (Item p : pois) {
                addDoc(w, p.getWebsite(), p.getName(), p.getAddress(), p.getPhone(), p.getTags(), p.getLat(), p.getLng());
            }
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lucene method for search into Index relevant document to recommend
     * @param query is string to querying the index
     * @param location is user location
     * @return a list of relevant item to recommend
     * @throws IOException for Input/Output exception
     * @throws ParseException for search document exception
     */
    public List<Item> searchItems(String query, Location location) throws IOException, ParseException {
        Query q = new QueryParser("tags", analyzer).parse(this.queryTransform(query));

        int hitsPerPage = 20;
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);

        TopDocs docs = searcher.search(q, hitsPerPage);
        ScoreDoc[] hits = docs.scoreDocs;

        Set<Item> items = new TreeSet<Item>();
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
                        Float.valueOf(d.get("lat")),
                        Float.valueOf(d.get("lng")));
                i.setScore(hit.score);
                items.add(i);
            }
        }
        reader.close();

        return new ArrayList<Item> (items);
    }

    /**
     * Read items to recommend to user from a CSV file
     * @param csvFile representing the file path of CSV file
     * @return an item list
     */
    private List<Item> readCSV(String csvFile) {
        List<Item> pois = new ArrayList<Item>();
        InputStream in = getClass().getResourceAsStream(csvFile);
        BufferedReader br = null;
        String line;

        try {
            br = new BufferedReader(new InputStreamReader(in));

            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] items = line.split(HORmessages.CSV_SPLIT);
                pois.add(new Item(items[0], items[1], items[2], items[3], items[4],
                                items[5], Double.valueOf(items[6]), Integer.valueOf(items[7]),
                                Float.valueOf(items[8]), Float.valueOf(items[9])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return pois;
    }

    /**
     * Lucene method for add a document to index representing the item to indexing
     * @param w is the IndexWriter of Lucene
     * @param website representing the website of item
     * @param name representing the name of item
     * @param address representing the address of item
     * @param phone representing the phone of item
     * @param tags representing the TAGS of item
     * @param lat representing the latitude of item
     * @param lng representing the longitude of item
     * @throws IOException for Input/Output exception
     */
    private static void addDoc(IndexWriter w,
                               String website, String name, String address, String phone, String tags,
                               float lat, float lng) throws IOException {
        Document doc = new Document();
        doc.add(new StringField("name", name, Field.Store.YES));
        doc.add(new StringField("website", website, Field.Store.YES));
        doc.add(new StringField("address", address, Field.Store.YES));
        doc.add(new StringField("phone", phone, Field.Store.YES));
        doc.add(new TextField("tags", tags, Field.Store.YES));
        doc.add(new StringField("lat", String.valueOf(lat), Field.Store.YES));
        doc.add(new StringField("lng", String.valueOf(lng), Field.Store.YES));
        w.addDocument(doc);
    }

    /**
     * Count term by term of the input query
     * @param query representing the query to trasnform
     * @return transformed Query
     */
    private String queryTransform(String query) {
        Map<String, Integer> fields = new HashMap<String, Integer>();

        for (String s : query.split(" ")) {
            if (fields.containsKey(s)) {
                fields.put(s, fields.get(s) + 1);
            } else {
                fields.put(s, 1);
            }
        }

        return this.getTransformedQuery(fields);
    }

    /**
     * Transform mapped query in a Lucene string
     * @param fields representing a map with the term as key and value as number of occurrences of term
     * @return Transformed query
     */
    private String getTransformedQuery(Map<String, Integer> fields) {
        String s = "";

        for (String key : fields.keySet()) {
            s += key + "^" + fields.get(key) + " ";
        }
        return s;
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
        return Utils.distance(lat1, lat2, lon1, lon2, 0.0, 0.0) < HORmessages.THRESHOLD;
    }
}