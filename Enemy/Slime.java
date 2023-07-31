package Enemy;

import Enums.DamageType;
import Game.Dice;
import Items.Weapons.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Slime extends AbstractEnemy{
    public Slime() {
        super(20, "Slime",new EnemyWeapon(
                                    "SlimeWeapon",
                                    "Blob Of Slime",
                                    List.of(new Damage(DamageType.BLUNT, new Dice(1, 6), 2))
                                )
        );
    }

    @Override
    protected int calculateDamageTaken(DamageType dmgType, int dmg) {
        if( dmgType == DamageType.SLASHING) return 0; //immune to slashing
        return dmg;
    }
    @Override
    public int giveEXP() {
        return 30;
    }
    @Override
    public Image getSprite() throws IOException {
        return ImageIO.read(new File("images/slime.png"));
    }
}
