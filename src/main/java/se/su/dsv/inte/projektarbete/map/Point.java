package se.su.dsv.inte.projektarbete.map;

import se.su.dsv.inte.projektarbete.map.Tiles.TileType;

public class Point {
    private final TileType type;
    //TODO: private INSERT MONSTER / PLAYER TYPE HERE //Can only hold 1 at a time
    private InteractableObject interactableObject;

    /**
     * @param type type value.
     */
    public Point(TileType type) {
        this.type = type;
    }

    /**
     * @param type type value.
     * @param interactableObject interactableObject value.
     */
    public Point(TileType type, InteractableObject interactableObject) {
        this.interactableObject = interactableObject;
        this.type = type;
    }

    /**
     * @return TileType, type
     */
    public TileType getType() {
        return type;
    }

    /**
     * @return InteractableObject, the point's object.
     * @throws NullPointerException, if no object is present.
     */
    public InteractableObject getInteractableObject() {
        if (interactableObject == null) {
            throw new NullPointerException("No InteractableObject here");
        }
        return interactableObject;
    }

    /**
     * Sets the object to null.
     */
    public void removeIntractableObject() {
        interactableObject = null;
    }
}
