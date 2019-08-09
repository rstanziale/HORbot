package recommender.graphBased;

import com.google.common.base.Function;

import java.util.ArrayList;
import java.util.Set;

public class PriorsFunction {

    static Function getPriorsFunction(GraphSettings graphSettings){
        ArrayList<String> context = new ArrayList<>();
        context.add("P_user");

        for (int i = 0; i < graphSettings.getContesto().length ; i++) {
            context.add("C_"+graphSettings.getContesto()[i]);
        }

        double[] weights = graphSettings.getPriors_weights();
        Set pref_c = graphSettings.getHistory().keySet();

        Function f = ((Object i) -> {
            if (context.contains(i)) return weights[0];
            else if (i.toString().startsWith("C_")) return weights[2];
            else if (pref_c.contains(i.toString().substring(2))) return weights[3];
            else return weights[1];
        });
        return f;
    }
}
