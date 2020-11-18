package com.ryancontent.forgettingmap;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ForgettingMap {

    private final int maxAssociations;
    // private List<Association> associationsMap = new ArrayList<>();
    private Map<Integer, Association> associationsMap = new ConcurrentHashMap<Integer, Association>();

    /**
     * Constructs ForgettingMap class containing 0 to n Associations
     * 
     * @param maxAssociations max number of Associations allowed
     */
    public ForgettingMap(int maxAssociations) {
        this.maxAssociations = maxAssociations;
    }

    /**
     * Adds a Association and corresponding key to Map.
     * 
     * @param association
     */
    public void add(Association association) {
        int key = association.getKey();

        if (this.associationsMap.containsKey(key)) {
            this.associationsMap.replace(key, association);
            return;
        }

        if (size() + 1 > this.maxAssociations) {
            this.associationsMap.remove(findLeastAccessedKey());
        }
        this.associationsMap.put(key, association);
    }

    /**
     * Finds Association in map by key. If key is found, Assocaition.TimesAccessed
     * is incremented.
     * 
     * @param key
     * @return association if found or null
     */
    public Association find(int key) {
        Association association = this.associationsMap.get(key);
        if (!Objects.isNull(association)) {
            association.incrementTimesAccessed();
        }
        return association;
    }

    public int size() {
        return this.associationsMap.size();
    }

    /**
     * Helper to find which key is least used based on Association.timesAccessed
     * int. This is the least efficient part of this class. To improve further, an
     * ordered list of index can be managed so that the least used index is always
     * at the back. This would be similar to how `ForgettingList` does this for
     * `content`.
     * 
     * @return key
     */
    private int findLeastAccessedKey() {
        Association leastAccessed = null;
        int leastAccessedIndex = -1;
        for (int i = 0; i < this.associationsMap.size(); i++) {
            Association association = this.associationsMap.get(i);
            if (Objects.isNull(leastAccessed) || association.getTimesAccessed() < leastAccessed.getTimesAccessed()) {
                leastAccessed = association;
                leastAccessedIndex = i;
            }
        }
        return leastAccessedIndex;
    }

}
