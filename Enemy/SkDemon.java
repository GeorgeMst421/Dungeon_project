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

public class SkDemon extends AbstractEnemy{
    public SkDemon() {
        super(50, "SkDemon", new EnemyWeapon(
                        "SkDemonWeapon",
                        "Flaming Skull",
                        List.of(new Damage(DamageType.BLUNT, new Dice(2, 6), 2),
                                new Damage(DamageType.MAGICAL, new Dice(1,6),1)
                        )
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
        return 200;
    }
    @Override
    public Image getSprite() throws IOException {
        return ImageIO.read(new File("images/skeleton-demon.png"));
    }
}

