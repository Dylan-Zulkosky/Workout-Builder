//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: Finding a Workout Builder
// Course: CS 300 Fall 2023
//
// Author: Dylan Zulkosky
// Email: dzulkosky@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name: None
// Partner Email: None
// Partner Lecturer's Name: None
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
// ___ Write-up states that pair programming is allowed for this assignment.
// ___ We have both read and understand the course Pair Programming Policy.
// ___ We have registered our team prior to the team registration deadline.
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons: None
// Online Sources: I used the examples in the end of the p07 rubric to help see what the output
//////////////// should look like to help create all of my testers for this project
// I watched a video to help create linked lists and the methods
//////////////// (https://www.youtube.com/watch?v=_Bi_C3QTv-g). This video helped me create all of
//////////////// the methods to help me create this project
//
///////////////////////////////////////////////////////////////////////////////

import java.util.NoSuchElementException;

/**
 * this class is a tester class for methods made to help make work out builders
 */
public class WorkoutBuilderTester {

  /**
   * checks for the correctness of the WorkoutBuilder.clear() method
   * 
   * @return true if tests pass
   */
  public static boolean testClear() {
    // reset all id numbers
    Exercise.resetIDNumbers();
    System.out.println("Testing clear method:");

    // create workout with exercises
    WorkoutBuilder workoutBuilder = new WorkoutBuilder();
    workoutBuilder.add(new Exercise(WorkoutType.WARMUP, "Warm-up Exercise 1"));
    workoutBuilder.add(new Exercise(WorkoutType.PRIMARY, "Primary Exercise 1"));
    workoutBuilder.add(new Exercise(WorkoutType.COOLDOWN, "Cooldown Exercise 1"));

    // see list before clear
    System.out.println("Before clear: ");
    System.out.println(workoutBuilder);

    // clear the workoutBuilder
    workoutBuilder.clear();

    // see list after clear
    System.out.println("After clear: ");
    System.out.println(workoutBuilder);

    // check if the counts and size are reset to zero
    return workoutBuilder.size() == 0 && workoutBuilder.getWarmupCount() == 0
        && workoutBuilder.getPrimaryCount() == 0 && workoutBuilder.getCooldownCount() == 0
        && workoutBuilder.isEmpty();
  }

  /**
   * Checks for the correctness of the WorkoutBuilder.add() method
   * 
   * @return true if all tests pass
   */
  public static boolean testAddExercises() {
    // reset id numbers
    Exercise.resetIDNumbers();

    // test to add primary work out to workoutBuilder
    WorkoutBuilder workout = new WorkoutBuilder();
    workout.add(new Exercise(WorkoutType.PRIMARY, "boxing"));
    String expected = "PRIMARY: boxing (1) -> END";
    String actual = workout.toString();

    if (!expected.equals(actual) || workout.size() != 1 || workout.getPrimaryCount() != 1) {
      // print out what is wrong
      System.out.print(actual);
      return false;
    }

    // test to add primaries being added before others
    // checking for broken implementation on gradescope
    // reset id numbers
    Exercise.resetIDNumbers();

    WorkoutBuilder workout3 = new WorkoutBuilder();
    workout3.add(new Exercise(WorkoutType.WARMUP, "yoga"));
    workout3.add(new Exercise(WorkoutType.PRIMARY, "run"));
    workout3.add(new Exercise(WorkoutType.PRIMARY, "sprint"));
    String expected3 = "WARMUP: yoga (1) -> PRIMARY: sprint (3) -> PRIMARY: run (2) -> END";
    String actual3 = workout3.toString();
    if (!expected3.equals(actual3) || workout3.size() != 3 || workout3.getPrimaryCount() != 2) {
      System.out.print(actual3);
      return false;
    }

    // true if all pass
    return true;
  }

  /**
   * checks for the correctness of BOTH of the WorkoutBuilder.removeExercise() methods
   * 
   * @return true if all tests pass
   */
  public static boolean testRemoveExercises() {
    Exercise.resetIDNumbers();

    // test removing a cool down from workoutBuilder
    WorkoutBuilder workoutBuilder = new WorkoutBuilder();
    Exercise expectedRemove = new Exercise(WorkoutType.COOLDOWN, "stretch");
    workoutBuilder.add(new Exercise(WorkoutType.WARMUP, "jumpingjacks"));
    workoutBuilder.add(new Exercise(WorkoutType.COOLDOWN, "rest"));
    workoutBuilder.add(expectedRemove);

    Exercise actualRemoved = workoutBuilder.removeExercise(WorkoutType.COOLDOWN);

    // check to see if expected is removed
    if (!expectedRemove.equals(actualRemoved) || workoutBuilder.size() != 2) {
      return false;
    }

    // check to see if cool down was the last one removed
    String expected = "WARMUP: jumpingjacks (2) -> COOLDOWN: rest (3) -> END";
    String actual = workoutBuilder.toString();

    if (!expectedRemove.equals(actualRemoved)
        || (!expected.equals(actual) || workoutBuilder.size() != 2)) {
      System.out.print(actual);
      return false;
    }

    // create new work out builder
    // reset id numbers before each test
    Exercise.resetIDNumbers();
    WorkoutBuilder workoutBuilder2 = new WorkoutBuilder();

    // add exercises
    Exercise exercise1 = new Exercise(WorkoutType.WARMUP, "Stretching");
    Exercise exercise2 = new Exercise(WorkoutType.PRIMARY, "Running");
    workoutBuilder2.add(exercise1);
    workoutBuilder2.add(exercise2);

    // remove exercise with ID
    int exerciseIDToRemove = exercise2.getExerciseID();
    Exercise removedExercise = workoutBuilder2.removeExercise(exerciseIDToRemove);

    // check to make sure at least something was removed
    if (removedExercise != null) {
      System.out.println(workoutBuilder2);
    } else {
      System.out.println("Exercise with ID " + exerciseIDToRemove + " not found.");
    }
    return true;
  }

  /**
   * checks for the correctness of the WorkoutBuilder.get() method
   * 
   * @return true if all tests pass
   */
  public static boolean testGetExercises() {
    Exercise.resetIDNumbers();
    // test method by getting exercise in middle of index
    WorkoutBuilder builder1 = new WorkoutBuilder();
    Exercise expected1 = new Exercise(WorkoutType.WARMUP, "stretch");
    builder1.add(new Exercise(WorkoutType.PRIMARY, "sprint"));
    builder1.add(expected1);
    builder1.add(new Exercise(WorkoutType.WARMUP, "walk"));
    Exercise actual1 = builder1.get(1);

    if (!expected1.equals(actual1)) {
      return false;
    }

    Exercise.resetIDNumbers();
    // test method by getting exercise at first index
    WorkoutBuilder builder2 = new WorkoutBuilder();
    Exercise expected2 = new Exercise(WorkoutType.WARMUP, "walk");
    builder2.add(expected2);
    // 0 is first index
    Exercise actual2 = builder2.get(0);

    if (!expected2.equals(actual2) || builder2.size() != 1) {
      return false;
    }

    Exercise.resetIDNumbers();
    // test for outside index
    try {
      WorkoutBuilder builder3 = new WorkoutBuilder();
      builder3.add(new Exercise(WorkoutType.PRIMARY, "sprint"));
      builder3.get(2);
    } catch (Exception e) {
      System.out.print("Not in index.");
    }
    return true;
  }

  /**
   * a test suite method to run all your test methods
   * 
   * @return true if all tests pass
   */
  public static boolean runAllTests() {
    boolean clear = testClear(), add = testAddExercises(), remove = testRemoveExercises(),
        get = testGetExercises();

    System.out.println("test clear: " + (clear ? "pass" : "FAIL"));
    System.out.println("test add: " + (add ? "pass" : "FAIL"));
    System.out.println("test remove: " + (remove ? "pass" : "FAIL"));
    System.out.println("test get: " + (get ? "pass" : "FAIL"));

    return true;
  }

  public static void main(String[] args) {
    runAllTests();
    demo();
  }

  /**
   * Helper method to display the size and the count of different boxes stored in a list of boxes
   * 
   * @param list a reference to an InventoryList object
   * @throws NullPointerException if list is null
   */
  private static void displaySizeCounts(WorkoutBuilder list) {
    System.out.println(
        "  Size: " + list.size() + ", warmupCount: " + list.getWarmupCount() + ", primaryCount: "
            + list.getPrimaryCount() + ", cooldownCount: " + list.getCooldownCount());
  }

  /**
   * Demo method showing how to use the implemented classes in P07 Inventory Storage System
   * 
   * @param args input arguments
   */
  public static void demo() {
    // Create a new empty WorkoutBuilder object
    WorkoutBuilder list = new WorkoutBuilder();
    System.out.println(list); // prints list's content
    displaySizeCounts(list); // displays list's size and counts
    // Add a primary exercise to an empty list
    list.add(new Exercise(WorkoutType.PRIMARY, "5k run")); // adds PRIMARY: 5k run (1)
    System.out.println(list); // prints list's content
    list.add(new Exercise(WorkoutType.WARMUP, "stretch")); // adds WARMUP: stretch (2) at the head
                                                           // of the list
    System.out.println(list); // prints list's content
    list.add(new Exercise(WorkoutType.PRIMARY, "bench press")); // adds PRIMARY: bench press (3)
    System.out.println(list); // prints list's content
    list.add(new Exercise(WorkoutType.WARMUP, "upright row")); // adds WARMUP: upright row (4) at
                                                               // the head of the list
    System.out.println(list); // prints list's content
    list.add(new Exercise(WorkoutType.WARMUP, "db bench")); // adds WARMUP: db bench (5) at the head
                                                            // of the list
    System.out.println(list); // prints list's content
    displaySizeCounts(list); // displays list's size and counts
    // Add more exercises to list and display its contents
    list.add(new Exercise(WorkoutType.COOLDOWN, "stretch")); // adds COOLDOWN: stretch (6) at the
                                                             // end of the list
    System.out.println(list); // prints list's content
    displaySizeCounts(list); // displays list's size and counts
    list.add(new Exercise(WorkoutType.COOLDOWN, "sit-ups")); // adds COOLDOWN: sit-ups (7) at the
                                                             // end of the list
    System.out.println(list); // prints list's content
    displaySizeCounts(list); // displays list's size and counts
    list.removeExercise(WorkoutType.COOLDOWN); // removes COOLDOWN: sit-ups (7) from the list
    System.out.println(list); // prints list's content
    displaySizeCounts(list); // displays list's size and counts
    list.add(new Exercise(WorkoutType.PRIMARY, "deadlift")); // adds PRIMARY: deadlift (8)
    System.out.println(list); // prints list's content
    displaySizeCounts(list); // displays list's size and counts
    list.removeExercise(WorkoutType.COOLDOWN); // removes COOLDOWN: stretch (6) from the list
    System.out.println(list); // prints list's content
    displaySizeCounts(list); // displays list's size and counts
    list.removeExercise(WorkoutType.WARMUP); // removes WARMUP: db bench (5)
    System.out.println(list); // prints list's content
    list.removeExercise(3); // removes PRIMARY: bench press (3) from the list
    System.out.println(list); // prints list's content
    displaySizeCounts(list); // displays list's size and counts
    try {
      list.removeExercise(25); // tries to remove box #25
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    System.out.println(list); // prints list's content
    displaySizeCounts(list); // displays list's size and counts
    // remove all warm-ups
    while (list.getWarmupCount() != 0) {
      list.removeExercise(WorkoutType.WARMUP);
    }
    System.out.println(list); // prints list's content
    displaySizeCounts(list); // displays list's size and counts
    list.removeExercise(1); // removes PRIMARY: 5k run (1) from the list -> empty list
    System.out.println(list); // prints list's content
    displaySizeCounts(list); // displays list's size and counts
    list.add(new Exercise(WorkoutType.COOLDOWN, "walk")); // adds COOLDOWN: walk (9) to the list
    System.out.println(list); // prints list's content
    displaySizeCounts(list); // displays list's size and counts
    list.removeExercise(8); // removes PRIMARY: deadlift (8) from the list
    System.out.println(list); // prints list's content
    displaySizeCounts(list); // displays list's size and counts
    list.removeExercise(WorkoutType.COOLDOWN); // removes COOLDOWN: walk (9) from the list
    System.out.println(list); // prints list's content
    displaySizeCounts(list); // displays list's size and counts
    list.add(new Exercise(WorkoutType.WARMUP, "pull-up")); // adds WARMUP: pull-up (10) to the list
    System.out.println(list); // prints list's content
    displaySizeCounts(list); // displays list's size and counts
    list.removeExercise(10); // removes WARMUP: pull-up (10) from the list
    System.out.println(list); // prints list's content
    displaySizeCounts(list); // displays list's size and counts
  }

}
