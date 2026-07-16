package com.gempukku.swccgo.packagedProduct;

import com.gempukku.swccgo.game.CardCollection;
import com.gempukku.swccgo.game.SwccgCardBlueprintLibrary;

import java.util.*;

/**
 * Defines a DB:CCG Alternate Image booster pack.
 */
public class DBCCGAlternateImageBoosterPack extends BasePackagedCardProduct {
    private Random _random = new Random();
    private int _series;
    /**
     * Creates a DB:CCG Alternate Image booster pack.
     * @param library the blueprint library
     */
    public DBCCGAlternateImageBoosterPack(SwccgCardBlueprintLibrary library, int series) {
        super(library);
        _series = series;
    }

    /**
     * Gets the name of the product.
     * @return the name of the product.
     */
    @Override
    public String getProductName() {
        switch(_series) {
            case 1:
                return ProductName.DBCCG_ALTERNATE_IMAGE_BOOSTER_PACK_SERIES_1;
            case 2:
                return ProductName.DBCCG_ALTERNATE_IMAGE_BOOSTER_PACK_SERIES_2;
            case 3:
                return ProductName.DBCCG_ALTERNATE_IMAGE_BOOSTER_PACK_SERIES_3;
            case 4:
                return ProductName.DBCCG_ALTERNATE_IMAGE_BOOSTER_PACK_SERIES_4;
            /**
            case 5:
                return ProductName.TBD;
            case 6:
                return ProductName.TBD;
            case 7:
                return ProductName.TBD;
            case 8:
                return ProductName.TBD;
            */
        }

        return ProductName.DBCCG_ALTERNATE_IMAGE_BOOSTER_PACK_SERIES_1;
    }

    /**
     * Gets the price of the product.
     * @return the price of the product.
     */
    @Override
    public float getProductPrice() {
        return ProductPrice.DBCCG_ALTERNATE_IMAGE_BOOSTER_PACK;
    }

    /**
     * Opens the packaged card product.
     * @return the card collection items contained in the packaged card product.
     */
    @Override
    public List<CardCollection.Item> openPackage() {
        List<CardCollection.Item> result = new LinkedList<CardCollection.Item>();
        addRandomCard(result, 3);
        return result;
    }

    /**
     * Adds random cards to the list.
     * @param result the list of cards in the pack
     * @param count the number cards to add
     */
    private void addRandomCard(List<CardCollection.Item> result, int count) {
        List<String> possibleCards = new ArrayList<String>();
        switch(_series) {
            case 1:
                possibleCards.add("302_12^"); //Howlader Taldrya
                possibleCards.add("302_25^"); //Dacien Victae, Deputy Grand Master
                possibleCards.add("302_29^"); //Lord Idris Adenn, Voice
                break;
            case 2:
                possibleCards.add("303_15^"); //Shadow Academy: Dueling Platform
                possibleCards.add("303_21^"); //Master Kamjin Lap'lamiz, Justicar
                possibleCards.add("303_27^"); //Kai Lap'lamiz With Lightsaber
                break;
            case 3:
                possibleCards.add("304_8^"); //Komilia Lap'lamiz
                possibleCards.add("304_9^"); //Komilia Lap'lamiz, Exile
                possibleCards.add("304_13^"); //Sykes Jade
                possibleCards.add("304_25^"); //Komilia Lap'lamiz, Emperor's Guard
                possibleCards.add("304_27^"); //Rohan Lap'lamiz, Stormtrooper
                possibleCards.add("304_28^"); //Master Kamjin 'Maverick' Lap'lamiz
                possibleCards.add("304_30^"); //Kamjin Lap'lamiz, Proconsul
                possibleCards.add("304_33^"); //Pete From Sales
                possibleCards.add("304_34^"); //Kamjin Lap'lamiz, Sith Battlemaster
                possibleCards.add("304_71^"); //Where is Jenni III
                possibleCards.add("304_122^"); //Hostile Takeover
                possibleCards.add("304_145^"); //Kai Lap'lamiz, Jedi Knight
                break;
            case 4:
                possibleCards.add("305_5^"); //Sunrider
                possibleCards.add("305_19^"); //Declan Roark With Blaster Rifle
                possibleCards.add("305_20^"); //Declan Roark
                possibleCards.add("305_22^"); //Darth Sarin, Grand Master
                possibleCards.add("305_24^"); //Declan Roark's Blaster Rifle
                possibleCards.add("305_66^"); //Ken Iode, Sapphire Leader
                possibleCards.add("305_103^"); //Selika Roh di Plagia, Dread Lord
                possibleCards.add("305_104^"); //Selika Roh di Plagia's Lightsaber
                possibleCards.add("305_130^"); //I Have Neglected Your Training
                possibleCards.add("305_130_BACK^"); //Your Training Is Now Complete
                possibleCards.add("305_189^"); //Velira Morvane
                break;
            /**
            case 5:
                possibleCards.add("217_27^"); //Ajan Kloss: Training Course (Borderless)

                break;
            case 6:
                possibleCards.add("204_1^"); //BB-8 (Border Breaker)

                break;
            case 7:
                possibleCards.add("224_9^"); //Balanced Attack & Darklighter Spin (Animated AI)    

                break;
            case 8:
                possibleCards.add("201_8^"); //A New Secret Base (V)

                break;
             */
        }

        filterNonExistingCards(possibleCards);
        Collections.shuffle(possibleCards, _random);
        addCards(result, possibleCards.subList(0, Math.min(possibleCards.size(), count)), false);
    }
}
