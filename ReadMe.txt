Μούστος Γεώργιος 
ΜΠΣ Ηλεκτρονικού Αυτοματισμού

Documentation:

ITEMS & WEAPONS
Υλοποίησα το interface Item με 4 μεθόδους getName(), getDescription(), getItemType(), List<ItemEffect> getItemEffects().
Έπειτα το interface Equippable extends Item με 2 μεθόδους getSlotType() και μια boolean isWeapon() καθώς και το Consumable με τις getUsesLeft() και use().

Eπειδή έχουμε μικτά damage types στο game έφτιαξα μια κλάσση Damage η οποία έχει 3 πεδία: DamageType, Dice, (int)diceBonus και 2 μεθόδους. Η  getDamageType() επιστρέφει το DamageType και η roll που "ρίχνει¨το ζάρι και επιστρέφει το αποτέλεσμα.

Στη συνέχεια, μια abstract κλάσση AbstractItem implements Item και ακολουθώντας την μια abstract κλάσση AbstractWeapon extends AbstractItem.
Η AbstractWeapon έχει μια λιστα απο Damages  και η μέθοδος hit() επιστρέφει ενα Map<DamageType, Integer> αντιστοιχίζοντας τους διαφορετικούς τύπους με το αντίστοιχο damage πχ SLASHING 16, BLUNT 20, MAGICAL 10.

Έπειτα έφτιαξα μια κλασση EnemyWeapon extends AbstractWeapon όπου κάνει implement της μεθόδους του Interface Item με την getItemType() να επιστρέφει ItemType.ENEMY_WEAPON και η getItemEffects να επιστρέφει ΠΑΝΤΑ null γνωρίζοντας ότι είναι ατέλεια της υλοποίησης.


~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Κλάσση PlayerControler: Περιέχει τα πεδία AbstractPlayer, Room και Direction. Χρησιμοποιείται για τον έλεγχο του παίκτη με τις μεθόδους turnLeft(), turnRight(), moveForward(), attack(), spell(), rest(), useHealthPotion(), useManaPotion().

Κλάσση LevelManager: Περιέχει τα πεδία List<AbstractEnemy>, AbstractPlayer, έναν integer για το μέγιστο αριθμό επιπέδων,
