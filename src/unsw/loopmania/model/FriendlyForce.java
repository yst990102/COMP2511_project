package unsw.loopmania.model;

import org.json.JSONObject;

public class FriendlyForce {

    /**
     * Constructor for FriendlyForce
     */
    public int hp;
    public int attack;

    /**
     * get the FriendlyForce Hp
     * @return int
     */
    public int getHp() {
        return this.hp;
    }

    /**
     * set the FriendlyForce Hp
     * @param hp
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

    /**
     * get the FriendlyForce attack
     * @return int
     */
    public int getAttack() {
        return this.attack;
    }

    /**
     * set the FriendlyForce attack
     * @param attack
     */
    public void setAttack(int attack) {
        this.attack = attack;
    }

    /**
     * save the data to Json
     * @return JSONObject
     */
    public JSONObject toJson() {
        JSONObject Info = new JSONObject();
        Info.put("hp", this.hp);
        Info.put("attack", this.attack);

        return Info;
    }

}
