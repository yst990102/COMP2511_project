package unsw.loopmania.model.buildings;

import java.io.File;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import unsw.loopmania.model.StaticEntity;

public class HeroCastle extends StaticEntity {

  public static Image image = new Image((new File("src/assets/hero_castle.png")).toURI().toString());

  public HeroCastle(SimpleIntegerProperty x, SimpleIntegerProperty y) {
    super(x, y);
  }
}
