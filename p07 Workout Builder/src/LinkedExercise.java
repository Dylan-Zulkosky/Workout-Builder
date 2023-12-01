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
// Online Sources: I used the LinkedExercise javadocs to help create this class
//////////////// (https://cs300-www.cs.wisc.edu/wp/wp-content/uploads/2023/fall/p07/documentation
//////////////// /LinkedExercise.html)
//
///////////////////////////////////////////////////////////////////////////////

/**
 * This class models a node for use in a singly-linked list. This node can ONLY contain elements of
 * type Exercise.
 */
public class LinkedExercise {

  // Data fields
  private Exercise exercise; // The Exercise contained in this linked node, which cannot be replaced
                             // after this node is created
  private LinkedExercise next; // The next linked node in this list

  /**
   * Creates a new node containing the provided exercise data and next node
   * 
   * @param data - the exercise to store in this node
   * @param next - the next node in this list, which MAY be null
   */
  public LinkedExercise(Exercise data, LinkedExercise next) {
    this.exercise = data;
    this.next = next;
  }

  /**
   * Creates a new node containing the provided exercise data with no following node
   * 
   * @param data - the exercise to store in this node
   */
  public LinkedExercise(Exercise data) {
    this(data, null);
  }

  /**
   * Accesses the next linked node in the list, which may be null
   * 
   * @return the reference to the node which follows this one in the list
   */
  public LinkedExercise getNext() {
    return this.next;
  }

  /**
   * Changes the node which follows this one to be the provided value, which may be null
   * 
   * @param node - the reference to set as the next node in the list
   */
  public void setNext(LinkedExercise node) {
    this.next = node;
  }

  /**
   * Accesses the exercise stored in this linked node
   * 
   * @return the Exercise stored in this linked node
   */
  public Exercise getExercise() {
    return this.exercise;
  }

  /**
   * Returns a String representation of this linked exercise.
   */
  @Override
  public String toString() {
    if (next != null) {
      return exercise.toString() + " -> ";
    } else {
      return exercise.toString() + " -> END";
    }
  }
}
