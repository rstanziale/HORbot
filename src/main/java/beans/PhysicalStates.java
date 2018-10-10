package beans;

import beans.values.PhysicalHeart;
import beans.values.PhysicalsBody;
import beans.values.PhysicalsFood;
import beans.values.PhysicalsSleep;

import java.util.List;

public class PhysicalStates {
    private List<PhysicalHeart> heart;
    private List<PhysicalsSleep> sleep;
    private List<PhysicalsFood> food;
    private List<PhysicalsBody> body;

    public List<PhysicalHeart> getHeart() {
        return heart;
    }

    public void setHeart(List<PhysicalHeart> heart) {
        this.heart = heart;
    }

    public List<PhysicalsSleep> getSleep() {
        return sleep;
    }

    public void setSleep(List<PhysicalsSleep> sleep) {
        this.sleep = sleep;
    }

    public List<PhysicalsFood> getFood() {
        return food;
    }

    public void setFood(List<PhysicalsFood> food) {
        this.food = food;
    }

    public List<PhysicalsBody> getBody() {
        return body;
    }

    public void setBody(List<PhysicalsBody> body) {
        this.body = body;
    }
}
