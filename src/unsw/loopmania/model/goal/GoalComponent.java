package unsw.loopmania.model.goal;

import unsw.loopmania.model.LoopManiaWorld;

public abstract class GoalComponent {
    String goal;

    LoopManiaWorld world;

    public abstract void addLeft(GoalComponent leftcomponent);

    public abstract void addRight(GoalComponent rightcomponent);

    public abstract void removeLeft(GoalComponent leftcomponent);

    public abstract void removeRight(GoalComponent rightcomponet);

    public abstract String getContent();

    public abstract boolean getLogicResult();

}