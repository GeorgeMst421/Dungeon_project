package Enemy;

import Enums.DamageType;
import Game.Dice;
import Items.Weapons.Damage;
import Items.Weapons.EnemyWeapon;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Goblin extends AbstractEnemy{
    public Goblin(){
        super(30,"Goblin",new EnemyWeapon(
                                    "GoblinWeapon",
                                    "Crude Sword",
                                    List.of(new Damage(DamageType.SLASHING, new Dice(1,6),1))
                                    )
        );
    }
    @Override
    protected int calculateDamageTaken(DamageType dmgType, int dmg) {
        if( dmgType == DamageType.SLASHING) return dmg/2;
        return dmg;
    }
    @Override
    public int giveEXP() {
        return 60;
    }
    @Override
    public Image getSprite() throws IOException {
        return ImageIO.read(new File("images/goblin.png"));
    }
}
