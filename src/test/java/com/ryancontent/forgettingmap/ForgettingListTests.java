package com.ryancontent.forgettingmap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ForgettingListTests {

    private Integer MAX_ASSOCIATIONS = 20;
    private int testAssociationInc = 0;

    private void addUniqueTestAssociations(ForgettingList forgettingList, int numOfAssociations, int startingIdx) {
        for (int i = startingIdx; i < numOfAssociations; i++) {
            forgettingList.add(generateTestAssociation());
        }
    }

    private Association generateTestAssociation() {
        String content = String.format("TEST INPUT %s", testAssociationInc);
        Association testAssociation = new Association(testAssociationInc, content);
        testAssociationInc++;
        return testAssociation;
    }

    @Test
    public void constructForgettingListClass() {
        ForgettingList forgettingList = new ForgettingList(MAX_ASSOCIATIONS);
        assertEquals(false, Objects.isNull(forgettingList), "Class must not be null");
    }

    @Test
    public void addMoreThanMaxAndExpectSameSize() {
        ForgettingList forgettingList = new ForgettingList(MAX_ASSOCIATIONS);
        addUniqueTestAssociations(forgettingList, MAX_ASSOCIATIONS + 5, 0);
        assertEquals(MAX_ASSOCIATIONS, forgettingList.size(), "Size must not exceed the max");
    }

    @Test
    public void addMoreThanMaxAndExpectLeastAccessedRemoved() {
        ForgettingList forgettingList = new ForgettingList(2);

        Association associationToKeep = generateTestAssociation();
        Association associationToDelete = generateTestAssociation();

        forgettingList.add(associationToKeep);
        forgettingList.add(associationToDelete);
        forgettingList.find(associationToKeep.getKey()); // find record to keep. On next add, least accessed should be
                                                         // removed
        forgettingList.add(generateTestAssociation());

        assertEquals(null, forgettingList.find(associationToDelete.getKey()),
                "Least accessed Association must be removed");
    }

    @Test
    public void addDuplicateAssociationsAndExpectSameSize() {
        ForgettingList forgettingList = new ForgettingList(MAX_ASSOCIATIONS);
        Association testAssociation = generateTestAssociation();
        forgettingList.add(testAssociation);
        forgettingList.add(testAssociation);
        forgettingList.add(testAssociation);
        assertEquals(1, forgettingList.size(), "Size must not change when adding duplicates");
    }

    @Test
    public void findAssociationByKey() {
        ForgettingList forgettingList = new ForgettingList(MAX_ASSOCIATIONS);
        Association testAssociation = generateTestAssociation();
        forgettingList.add(testAssociation);
        addUniqueTestAssociations(forgettingList, MAX_ASSOCIATIONS, 1);
        Association foundAssociation = forgettingList.find(testAssociation.getKey());
        assertEquals(testAssociation, foundAssociation, "Found association must match");
    }

    @Test
    public void testAddingManyUniqueAssociations() {
        int TIMES_TO_RUN = 1000;
        ForgettingList forgettingList = new ForgettingList(TIMES_TO_RUN);

        addUniqueTestAssociations(forgettingList, TIMES_TO_RUN, 0);
        assertEquals(TIMES_TO_RUN, forgettingList.size(),
                "Size of ForgettingList must match the actual number of times it ran");
    }

}
