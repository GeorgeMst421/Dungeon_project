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

public class Leoric extends AbstractEnemy{

    public Leoric() {
        super(120, "Leoric", new EnemyWeapon(
                        "LeoricWeapon",
                        "Mace of the Fallen Champion",
                        List.of(new Damage(DamageType.BLUNT, new Dice(2, 6), 5),
                                new Damage(DamageType.SLASHING, new Dice(1,4),2),
                                new Damage(DamageType.MAGICAL, new Dice(1,4),2)
                        )
                )
        );
    }

    @Override
    protected int calculateDamageByType(DamageType dmgType, int dmg) {
        return dmg/2;
    }
    @Override
    public int giveEXP() {
        return 30000;
    }

    @Override
    public Image getSprite() throws IOException {
        return ImageIO.read(new File("images/leoric.png"));
    }
}
