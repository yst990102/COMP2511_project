package unsw.loopmania.model.goal;

public abstract class GoalComponent {
    String goal;

    public abstract void addLeft(GoalComponent leftcomponent);
    public abstract void addRight(GoalComponent rightcomponent);

    public abstract void removeLeft(GoalComponent leftcomponent);
    public abstract void removeRight(GoalComponent rightcomponet);
    
    public abstract String getContent();

}