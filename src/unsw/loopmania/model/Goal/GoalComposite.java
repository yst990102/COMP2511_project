package unsw.loopmania.model.Goal;

import org.json.JSONObject;

public class GoalComposite extends GoalComponent {

    GoalComponent leftComponent;
    GoalComponent rightComponent;

    public GoalComposite(JSONObject goal){
        this.goal = goal.getString("goal");
        
        if (goal.has("quantity")){
            leftComponent = new GoalLeaf(goal);
            rightComponent = null;
        } else {
            leftComponent = new GoalComposite(goal.getJSONArray("subgoals").getJSONObject(0));
            rightComponent = new GoalComposite(goal.getJSONArray("subgoals").getJSONObject(1));
        }
    }


    public String getContent() {
        String leftContent;
        String rightContent;

        if (leftComponent == null){
            leftContent = "";
        }else{
            leftContent = leftComponent.getContent();
        }

        if (rightComponent == null){
            rightContent = "";
        }else{
            rightContent = rightComponent.getContent();
        }

        if (leftContent.equals("")){
            return "";
        }else if (rightContent.equals("")){
            return leftContent;
        }else{
            return (leftContent + " " + goal + " " + rightContent);
        }
        
    }


    @Override
    public void addLeft(GoalComponent leftcomponent) {
        this.leftComponent = leftcomponent;
    }


    @Override
    public void addRight(GoalComponent rightcomponent) {
        this.rightComponent = rightcomponent;        
    }


    @Override
    public void removeLeft(GoalComponent leftcomponent) {
        this.leftComponent = null;
    }


    @Override
    public void removeRight(GoalComponent rightcomponet) {
        this.rightComponent = null;
    }
    
}