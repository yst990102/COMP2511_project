package unsw.loopmania.model.goal;

import org.json.JSONObject;

import unsw.loopmania.model.Character;
import unsw.loopmania.model.LoopManiaWorld;

public class GoalComposite extends GoalComponent {

    GoalComponent leftComponent;
    GoalComponent rightComponent;

    public GoalComposite(JSONObject goal, LoopManiaWorld world) {
        this.goal = goal.getString("goal");
        this.world = world;

        if (goal.has("quantity")) {
            leftComponent = new GoalLeaf(goal, world);
            rightComponent = null;
        } else {
            leftComponent = new GoalComposite(goal.getJSONArray("subgoals").getJSONObject(0), world);
            rightComponent = new GoalComposite(goal.getJSONArray("subgoals").getJSONObject(1), world);
        }
    }

    public String getContent() {
        String leftContent;
        String rightContent;

        if (leftComponent == null) {
            leftContent = "";
        } else {
            leftContent = leftComponent.getContent();
        }

        if (rightComponent == null) {
            rightContent = "";
        } else {
            rightContent = rightComponent.getContent();
        }

        if (leftContent.equals("")) {
            return "";
        } else if (rightContent.equals("")) {
            return leftContent;
        } else {
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

    @Override
    public boolean getLogicResult() {
        boolean leftResult = (leftComponent != null) ? leftComponent.getLogicResult() : true;
        boolean rightResult = (rightComponent != null) ? rightComponent.getLogicResult() : true;

        if (this.goal.equals("AND")) {
            return leftResult & rightResult;
        } else if (this.goal.equals("OR")) {
            return leftResult | rightResult;
        } else {
            return leftResult;
        }
    }

}