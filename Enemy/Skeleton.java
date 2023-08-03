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

public class Skeleton extends AbstractEnemy{
    public Skeleton() {
        super(45, "Skeleton",new EnemyWeapon(
                        "SkeletonWeapon",
                        "Femur Bone",
                        List.of(new Damage(DamageType.BLUNT, new Dice(2, 6), 4))
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
        return 120;
    }

    @Override
    public Image getSprite() throws IOException {
        return ImageIO.read(new File("images/skeleton.png"));
    }
}
