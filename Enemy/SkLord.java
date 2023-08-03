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

public class SkLord extends AbstractEnemy {

    public SkLord() {
        super(55, "SkLord", new EnemyWeapon(
                        "SkLordWeapon",
                        "Unholy Mace",
                        List.of(new Damage(DamageType.BLUNT, new Dice(2, 6), 2),
                                new Damage(DamageType.SLASHING, new Dice(1,6),2)
                        )
                )
        );
    }

    @Override
    protected int calculateDamageByType(DamageType dmgType, int dmg) {
        if( dmgType == DamageType.SLASHING) return dmg/2;
        return dmg;
    }

    @Override
    public int giveEXP() {
        return 400;
    }
    @Override
    public Image getSprite() throws IOException {
        return ImageIO.read(new File("images/skeleton-lord0.png"));
    }
}
