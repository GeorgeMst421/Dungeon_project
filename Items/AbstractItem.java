package Items;

import Interfaces.Item;

public abstract class AbstractItem implements Item {
    protected String name;
    protected String description;

    public AbstractItem(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String toString(){
        return getName() + " " + getDescription();
    }
}
