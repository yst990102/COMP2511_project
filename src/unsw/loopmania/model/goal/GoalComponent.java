package unsw.loopmania.model.goal;

import unsw.loopmania.model.LoopManiaWorld;

public abstract class GoalComponent {
    String goal;

    LoopManiaWorld world;

    public abstract String getContent();

    public abstract boolean getLogicResult();

}