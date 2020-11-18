package com.ryancontent.forgettingmap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ForgettingMapTests {

    private Integer MAX_ASSOCIATIONS = 20;
    private int testAssociationInc = 0;

    private void addUniqueTestAssociations(ForgettingMap forgettingMap, int numOfAssociations, int startingIdx) {
        for (int i = startingIdx; i < numOfAssociations; i++) {
            forgettingMap.add(generateTestAssociation());
        }
    }

    private Association generateTestAssociation() {
        String content = String.format("TEST INPUT %s", testAssociationInc);
        Association testAssociation = new Association(testAssociationInc, content);
        testAssociationInc++;
        return testAssociation;
    }

    @Test
    public void constructForgettingMapClass() {
        ForgettingMap forgettingMap = new ForgettingMap(MAX_ASSOCIATIONS);
        assertEquals(false, Objects.isNull(forgettingMap), "Class must not be null");
    }

    @Test
    public void addMoreThanMaxAndExpectSameSize() {
        ForgettingMap forgettingMap = new ForgettingMap(MAX_ASSOCIATIONS);
        addUniqueTestAssociations(forgettingMap, MAX_ASSOCIATIONS + 5, 0);
        assertEquals(MAX_ASSOCIATIONS, forgettingMap.size(), "Size must not exceed the max");
    }

    @Test
    public void addMoreThanMaxAndExpectLeastAccessedRemoved() {
        ForgettingMap forgettingMap = new ForgettingMap(2);

        Association associationToKeep = generateTestAssociation();
        Association associationToDelete = generateTestAssociation();

        forgettingMap.add(associationToKeep);
        forgettingMap.add(associationToDelete);
        forgettingMap.find(associationToKeep.getKey()); // find record to keep. On next add, least accessed should be
                                                        // removed
        forgettingMap.add(generateTestAssociation());

        assertEquals(null, forgettingMap.find(associationToDelete.getKey()),
                "Least accessed Association must be removed");
    }

    @Test
    public void addDuplicateAssociationsAndExpectSameSize() {
        ForgettingMap forgettingMap = new ForgettingMap(MAX_ASSOCIATIONS);
        Association testAssociation = generateTestAssociation();
        forgettingMap.add(testAssociation);
        forgettingMap.add(testAssociation);
        forgettingMap.add(testAssociation);
        assertEquals(1, forgettingMap.size(), "Size must not change when adding duplicates");
    }

    @Test
    public void findAssociationByKey() {
        ForgettingMap forgettingMap = new ForgettingMap(MAX_ASSOCIATIONS);
        Association testAssociation = generateTestAssociation();
        forgettingMap.add(testAssociation);
        addUniqueTestAssociations(forgettingMap, MAX_ASSOCIATIONS, 1);
        Association foundAssociation = forgettingMap.find(testAssociation.getKey());
        assertEquals(testAssociation, foundAssociation, "Found association must match");
    }

    @Test
    public void testIncrementTimesAccessed() {
        Association association = generateTestAssociation();
        int TIMES_TO_RUN = 1000;
        for (int i = 0; i < TIMES_TO_RUN; i++) {
            association.incrementTimesAccessed();
        }
        assertEquals(TIMES_TO_RUN, association.getTimesAccessed(),
                "Incremented timesAccessed must match the actual number of times it ran");
    }

    @Test
    public void testAddingManyUniqueAssociations() {
        int TIMES_TO_RUN = 1000;
        ForgettingMap forgettingMap = new ForgettingMap(TIMES_TO_RUN);

        addUniqueTestAssociations(forgettingMap, TIMES_TO_RUN, 0);
        assertEquals(TIMES_TO_RUN, forgettingMap.size(),
                "Size of ForgettingMap must match the actual number of times it ran");
    }

}
