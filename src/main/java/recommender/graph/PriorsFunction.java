package recommender.graph;



import com.google.common.base.Function;

import java.util.ArrayList;
import java.util.Set;

public class PriorsFunction {

    public static Function getPriorsFunction(GraphSettings graphSettings){
        ArrayList<String> contesto = new ArrayList<>();
        contesto.add("P_user");

        for (int i = 0; i < graphSettings.getContesto().length ; i++) {
            contesto.add("C_"+graphSettings.getContesto()[i]);
        }

        double pesi[] = graphSettings.getPriors_weights();
        Set pref_c = graphSettings.getHistory().keySet();

        Function f = ((Object i) -> {
            if (contesto.contains(i)) return pesi[0];
            else if (i.toString().startsWith("C_")) return pesi[2];
            else if (pref_c.contains(i.toString().substring(2))) return pesi[3];
            else return pesi[1];
        });
        return f;
    }

}
