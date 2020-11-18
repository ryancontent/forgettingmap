package com.ryancontent.forgettingmap;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ForgettingList {

    private final int maxAssociations;
    private List<Association> associationsList = new ArrayList<>();

    /**
     * Constructs ForgettingList class containing 0 to n Associations. Order of List
     * is maintained so that most recently accessed Association is at the front of
     * queue.
     * 
     * @param maxAssociations max number of Associations allowed
     */
    public ForgettingList(int maxAssociations) {
        this.maxAssociations = maxAssociations;
    }

    /**
     * Adds a new Association to the front of queue. If Association already exists,
     * it gets replaced and moves to the front of queue. If maxAssociations is
     * reached, the "least found" Association gets removed.
     * 
     * @param association
     */
    public void add(Association association) {

        int duplicateIdx = findDuplicateKey(association.getKey());
        if (duplicateIdx > -1) {
            this.associationsList.remove(duplicateIdx); // remove duplicate entry before adding
        }

        else if (size() + 1 > this.maxAssociations) {
            this.associationsList.remove(findLeastAccessedIndex()); // remove least used Association
        }

        this.associationsList.add(0, association); // add to index 0 to preserve access order

    }

    /**
     * Finds Association in queue by key. If key is found, Assocaition is moved to
     * the front of the queue.
     * 
     * @param key
     * @return association if found or null
     */
    public Association find(int key) {
        for (int i = 0; i < this.associationsList.size(); i++) {
            Association association = this.associationsList.get(i);
            if (association.getKey() == key) {
                if (i > 0) {
                    // if found association is not first in the list, reorder
                    this.associationsList.remove(i);
                    this.associationsList.add(0, association);
                }
                association.incrementTimesAccessed();
                return association;
            }
        }
        return null;
    }

    public int size() {
        return this.associationsList.size();
    }

    /**
     * Helper to find if a duplicate key exists in any Associations in the queue.
     * 
     * @param key
     * @return index in array for the location of key
     */
    private int findDuplicateKey(int key) {
        for (int i = 0; i < this.associationsList.size(); i++) {
            if (this.associationsList.get(i).getKey() == key) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Helper to find which index is least used based on Association.timesAccessed
     * int.
     * 
     * @return index
     */
    private int findLeastAccessedIndex() {
        Association leastAccessed = null;
        int leastAccessedIndex = -1;
        for (int i = 0; i < this.associationsList.size(); i++) {
            Association association = this.associationsList.get(i);
            if (Objects.isNull(leastAccessed) || association.getTimesAccessed() < leastAccessed.getTimesAccessed()) {
                leastAccessed = association;
                leastAccessedIndex = i;
            }
        }
        return leastAccessedIndex;
    }

}
