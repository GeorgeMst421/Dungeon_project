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

public class Ghost extends AbstractEnemy{
    public Ghost(){
        super(40,"Ghost",new EnemyWeapon(
                                "GhostWeapon",
                                "Ghost claws",
                                List.of(new Damage(DamageType.MAGICAL,new Dice(1,6),2),
                                        new Damage(DamageType.SLASHING,new Dice(1,6),2))
                            )
        );
    }
    @Override
    protected int calculateDamageByType(DamageType dmgType, int dmg) {
        if( dmgType == DamageType.BLUNT) return 0;
        return dmg;
    }
    @Override
    public int giveEXP() {
        return 100;
    }
    @Override
    public Image getSprite() throws IOException {
        return ImageIO.read(new File("images/ghost.png"));
    }
}
