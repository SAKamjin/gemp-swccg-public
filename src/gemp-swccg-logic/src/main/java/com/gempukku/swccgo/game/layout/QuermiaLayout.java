package com.gempukku.swccgo.game.layout;

import com.gempukku.swccgo.filters.Filters;

/**
 * Represents the location layout for Quermia.
 */
public class QuermiaLayout extends AbstractSystemLayout {

    // Layout order for Quermia:
    //  1) Sites (in forward or reverse order)
    //      A) Senate Council Chambers
    //      B) Interior sites
    //      C) Interior/Exterior sites
    //      D) Senate Courtyard
    //      E) Exterior sites
    //      F) Forest sites
    //  2) Clouds
    //  3) Planet
    //  4) Asteroids
    //  5) Big One
    //  6) Big One: Asteroid Cave

    /**
     * Needed to generate snapshot.
     */
    public QuermiaLayout() {
    }

    /**
     * Creates the location layout for Quermia.
     * @param systemName the system name
     * @param parsec the parsec number for the system
     */
    public QuermiaLayout(String systemName, int parsec) {
        super(systemName, parsec);

        //  1) Sites (in forward or reverse order)
        _groupOrders.add(
                new LocationReversibleGroupOrder(
                        //  A) Senate Council Chambers
                        new LocationGroup("Senate Council Chambers", Filters.Senate_Council_Chambers),
                        //  B) Interior sites
                        new LocationGroup("Interior sites", Filters.and(Filters.interior_site, Filters.not(Filters.or(Filters.exterior_site,
                                Filters.forest_site, Filters.Senate_Council_Chambers)), Filters.partOfSystem(systemName))),
                        //  C) Interior/Exterior sites
                        new LocationGroup("Interior/Exterior sites", Filters.and(Filters.interior_site, Filters.exterior_site, Filters.partOfSystem(systemName))),
                        //  D) Senate Courtyard
                        new LocationGroup("Senate Courtyard", Filters.Senate_Courtyard),
                        //  E) Exterior sites
                        new LocationGroup("Exterior sites", Filters.and(Filters.exterior_site, Filters.not(Filters.or(Filters.interior_site,
                                Filters.forest_site, Filters.Senate_Courtyard)), Filters.partOfSystem(systemName))),
                        //  F) Forest sites
                        new LocationGroup("Forest sites", Filters.and(Filters.forest_site, Filters.partOfSystem(systemName)))));

        //  2) Clouds
        //  3) Planet
        //  4) Asteroids
        //  5) Big One
        //  6) Big One: Asteroid Cave
        _groupOrders.add(
                new LocationFixedGroupOrder(getPlanetSystemAndSectorsLocationGroups(systemName)));
    }
}
