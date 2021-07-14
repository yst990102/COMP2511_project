package unsw.loopmania.model.goal;

import org.json.JSONObject;

public class GoalLeaf extends GoalComponent {

    int quantity;

    public GoalLeaf (JSONObject goal){
        this.goal = goal.getString("goal");
        this.quantity = goal.getInt("quantity");
    }

    public String getContent() {
        return (quantity + goal);
    }

    @Override
    public void addLeft(GoalComponent leftcomponent) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addRight(GoalComponent rightcomponent) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeLeft(GoalComponent leftcomponent) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeRight(GoalComponent rightcomponet) {
        // TODO Auto-generated method stub
        
    }
}  