package unsw.loopmania.model.goal;

import org.json.JSONObject;

import unsw.loopmania.model.LoopManiaWorld;

public class GoalLeaf extends GoalComponent {

    int quantity;

    public GoalLeaf(JSONObject goal, LoopManiaWorld world) {
        this.world = world;
        this.goal = goal.getString("goal");
        this.quantity = goal.getInt("quantity");
    }

    public String getContent() {
        return (quantity + goal);
    }

    @Override
    public boolean getLogicResult() {
        int current_cycles = this.world.getNthCycle();
        // System.out.println("current_cycles == " + current_cycles);
        int current_exp = (this.world.getCharacter() != null) ? this.world.getCharacter().getXP() : 0;
        // System.out.println("current_exp == " + current_exp);
        int current_gold = (this.world.getCharacter() != null) ? this.world.getCharacter().getGold() : 0;
        // System.out.println("current_gold == " + current_gold);

        if (this.goal.equals("cycles")) {
            return current_cycles >= this.quantity;
        } else if (this.goal.equals("experience")) {
            return current_exp >= this.quantity;
        } else if (this.goal.equals("gold")) {
            return current_gold >= this.quantity;
        }

        return false;
    }
}