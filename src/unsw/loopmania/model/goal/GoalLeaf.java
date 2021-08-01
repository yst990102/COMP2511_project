package unsw.loopmania.model.goal;

import org.json.JSONObject;

import unsw.loopmania.model.LoopManiaWorld;

public class GoalLeaf extends GoalComponent {

    int quantity;

    public GoalLeaf(JSONObject goal, LoopManiaWorld world) {
        this.world = world;
        this.goal = goal.getString("goal");
        if (goal.has("quantity")) {
            this.quantity = goal.getInt("quantity");
        }
    }

    public String getContent() {
        String string_goal;
        switch (this.goal) {
        case "cycles":
            string_goal = "cycles";
            return (quantity + " " + string_goal);
        case "experience":
            string_goal = "xp";
            return (quantity + " " + string_goal);
        case "gold":
            string_goal = "$";
            return (string_goal + quantity);
        case "bosses":
            string_goal = "BOSS";
            return (string_goal);
        default:
            break;
        }
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
        boolean isAllBossKilled = this.world.hasDoggieKilled & this.world.hasMuskeKilled;

        if (this.goal.equals("cycles")) {
            return current_cycles >= this.quantity;
        } else if (this.goal.equals("experience")) {
            return current_exp >= this.quantity;
        } else if (this.goal.equals("gold")) {
            return current_gold >= this.quantity;
        } else if (this.goal.equals("bosses")) {
            return isAllBossKilled;
        }

        return false;
    }
}