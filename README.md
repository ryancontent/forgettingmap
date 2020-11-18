# Objective
The objective of this task is design, implement and test a thread-safe 'forgetting map'.
A 'forgetting' map should hold associations between a ‘key’ and some ‘content’. It should implement
at least two methods:
1. add (add an association)
2. find (find content using the specified key).

It should hold as many associations as it can, but no more than x associations at any time, with x being
a parameter passed to the constructor. Associations that are least used, in the sense of the frequency
of 'finds' performed against an association, are removed from the map as needed.

# Design & Implement
The idea I had was to create a new class using composition. I created two versions of this:
1. `ForgettingMap`
2. `ForgettingList`
...which use `Map` and `List` respectively to store the data.

Since the objective stated `key` and `content` should be used, I created the `Associations` class to contain these values.
~Note that `Associations` is not necessary to achieve the objective while using `ForgettingMap`, so this could be removed and only hold `content`.

# Test
Run all tests using `./gradlew test`
