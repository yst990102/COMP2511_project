package unsw.loopmania.model;

import org.json.JSONObject;

public class FriendlyForce {

    public int hp;
    public int attack;

    public int getHp() {
        return this.hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return this.attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public JSONObject toJson() {
        JSONObject Info = new JSONObject();
        Info.put("hp", this.hp);
        Info.put("attack", this.attack);

        return Info;
    }

}
