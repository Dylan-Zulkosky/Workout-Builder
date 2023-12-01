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
// Online Sources: I used the WorkoutBuilder javadocs to help create this class
//////////////// (https://cs300-www.cs.wisc.edu/wp/wp-content/uploads/2023/fall/p07/documentation
//////////////// /WorkoutBuilder.html)
// I watched a video to help create linked lists and the methods
//////////////// (https://www.youtube.com/watch?v=_Bi_C3QTv-g). This video helped me create all of
//////////////// the methods to help me create this project
//
///////////////////////////////////////////////////////////////////////////////

import java.util.NoSuchElementException;

/**
 * this class creates the work outs by having them go by different stages of warmup, primary, and
 * cooldown
 */
public class WorkoutBuilder implements ListADT<Exercise> {

  // Data Fields
  private int cooldownCount; // The number of exercises with WorkoutType equal to COOLDOWN in this
                             // WorkoutBuilder list
  private LinkedExercise head; // The node containing the element at index 0 of this singly-linked
                               // list
  private int primaryCount; // The number of exercises with WorkoutType equal to PRIMARY in this
                            // WorkoutBuilder list
  private int size; // The total number of exercises contained in this WorkoutBuilder list
  private LinkedExercise tail; // The node containing the last element of this singly-linked list
  private int warmupCount; // The number of exercises with WorkoutType equal to WARMUP in this
                           // WorkoutBuilder list

  /**
   * Accesses the total number of elements in this WorkoutBuilder list
   * 
   * @return the size of this list
   */
  @Override
  public int size() {
    return this.size;
  }

  /**
   * Accesses the number of warm-up exercises stored in this WorkoutBuilder list
   * 
   * @return the count of exercises with WorkoutType equal to WARMUP
   */
  public int getWarmupCount() {
    return this.warmupCount;
  }

  /**
   * Accesses the number of primary exercises stored in this WorkoutBuilder list
   * 
   * @return the count of exercises with WorkoutType equal to PRIMARY
   */
  public int getPrimaryCount() {
    return this.primaryCount;
  }

  /**
   * Accesses the number of cool-down exercises stored in this WorkoutBuilder list
   * 
   * @return the count of exercises with WorkoutType equal to COOLDOWN
   */
  public int getCooldownCount() {
    return this.cooldownCount;
  }

  /**
   * Checks whether this WorkoutBuilder list is empty
   * 
   * @return true if this list contains no elements and neither its head nor tail refer to
   *         LinkedExercise objects
   */
  @Override
  public boolean isEmpty() {
    return size == 0 && head == null && tail == null;
  }

  /**
   * Removes all elements from this list. The list will be empty after this call returns.
   */
  public void clear() {
    this.head = null;
    this.tail = null;
    this.size = 0;
    this.warmupCount = 0;
    this.primaryCount = 0;
    this.cooldownCount = 0;
  }

  /**
   * Finds the index of a given exercise in this WorkoutBuilder list, if it is present. Note that
   * Exercise contains an overridden equals() method for use here.
   * 
   * @return the index of this object in the list if it is present; -1 if it is not
   * @param findObject - the exercise to search for in this list
   */
  @Override
  public int indexOf(Exercise findObject) {
    // create index and node
    int index = 0;
    LinkedExercise item = head;

    // Go through the list
    while (item != null) {
      if (item.getExercise().equals(findObject)) {
        return index;
      }
      item = item.getNext();
      // Increment index
      index++;
    }

    // Return -1 if it is not present in list
    return -1;
  }

  /**
   * Returns the exercise stored at the given index of this list without removing it.
   * 
   * @param index - position within this list
   * @return the exercise stored at the given index of this list
   * @throws IndexOutOfBoundsException - with a descriptive error message if the index is not valid
   *                                   for the current size of this list
   */
  @Override
  public Exercise get(int index) {
    // check if index is less than zero or bigger than the size of list
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException(
          index + " is not valid for the current size of the list.");
    }

    // create the node
    LinkedExercise current = head;
    for (int i = 0; i < index; i++) {
      if (current == null) {
        throw new IndexOutOfBoundsException("Unexpected null node at index " + i);
      }
      current = current.getNext();
    }

    // return exercise
    return current.getExercise();
  }

  /**
   * Adds the provided Exercise to the appropriate position in the list for its WorkoutType, and
   * increments the corresponding counter fields: WARMUP: adds to the FRONT (head) of the list
   * PRIMARY: adds after all warm-ups and before any cool-downs; if there are any existing primary
   * exercises, adds before all of those as well COOLDOWN: adds to the END (tail) of the list We
   * recommend implementing private helper methods for each of these cases, but this is not
   * required. Remember to consider the case where you are adding the very first exercise to the
   * list!
   * 
   * @param newObject - the exercise to add to the WorkoutBuilder list
   */
  @Override
  public void add(Exercise newObject) {
    // create LinkedExercise
    LinkedExercise node = new LinkedExercise(newObject);

    // see if head is null
    if (head == null) {
      head = node;
      tail = node;
    } else {
      if (newObject.getType() == WorkoutType.WARMUP) {
        // Add to front of head
        node.setNext(head);
        head = node;
      } else if (newObject.getType() == WorkoutType.PRIMARY) {
        // add node to tail
        if (primaryCount == 0) {
          tail.setNext(node);
          tail = node;
        } else {
          LinkedExercise current = head;
          // insert warmup at the end of warmups
          while (current.getNext() != null
              && current.getNext().getExercise().getType() == WorkoutType.WARMUP) {
            current = current.getNext();
          }
          node.setNext(current.getNext());
          current.setNext(node);
        }
      } else if (newObject.getType() == WorkoutType.COOLDOWN) {
        // add to tail
        tail.setNext(node);
        tail = node;
      }
    }

    // update size and field
    updateCounterFields(newObject.getType(), 1);
    size++;
  }

  /**
   * Removes an exercise of the provided type from the list, if one exists, and decrements the
   * corresponding counter fields: WARMUP: removes the FIRST (head) element from the list PRIMARY:
   * removes the FIRST exercise of type PRIMARY from the list COOLDOWN: removes the LAST (tail)
   * element from the list You are encouraged to implement private helper methods for each of these
   * cases as well, but this is not required. Be sure to check that there are any exercises with the
   * given type present in the list, and remember to consider the case where you are removing the
   * very last exercise from the entire list!
   * 
   * @param type - the type of exercise to remove from the list
   * @return the exercise object that has been removed from the list
   * @throws NoSuchElementException - if there are no exercises in the list with the given
   *                                WorkoutType
   */
  public Exercise removeExercise(WorkoutType type) throws NoSuchElementException {
    // Check if list is empty or no exercieses
    if (isEmpty()) {
      throw new NoSuchElementException("No exercises of type " + type + " in the list.");
    }

    // create thing to remove to null
    LinkedExercise remove = null;

    // find type to remove
    if (type == WorkoutType.WARMUP) {
      remove = head;
      // update head after removing original
      head = head.getNext();
    } else if (type == WorkoutType.PRIMARY) {
      // remove a primary
      LinkedExercise current = head;
      while (current.getNext() != null
          && current.getNext().getExercise().getType() == WorkoutType.WARMUP) {
        // find last warmup to remove primary after
        current = current.getNext();
      }
      remove = current.getNext();
      current.setNext(current.getNext().getNext());
      // Check to see if removed is tail
      if (current.getNext() == null) {
        tail = current;
      }
    } else if (type == WorkoutType.COOLDOWN) {
      // remove a cooldown
      LinkedExercise current = head;
      while (current.getNext() != tail) {
        current = current.getNext();
      }
      // update tail and node
      remove = tail;
      tail = current;
      current.setNext(null);
    }

    // update field and size
    updateCounterFields(type, -1);
    size--;
    // return node that got removed
    return remove.getExercise();
  }

  /**
   * Removes the exercise with the provided ID number from the list, if it is present, and adjusts
   * any corresponding counter fields as necessary. This method uses a linear search algorithm.
   * 
   * @param exerciseID - the unique identifier of the exercise to be removed
   * @return the exercise object that has been removed from the list
   * @throws NoSuchElementException - if no exercises in the list match the provided exerciseID
   *                                number
   */
  public Exercise removeExercise(int exerciseID) {
    try {
      LinkedExercise current = head;
      LinkedExercise previous = null;

      // search for exerciseID
      while (current != null && current.getExercise().getExerciseID() != exerciseID) {
        previous = current;
        current = current.getNext();
      }

      // check if id was found
      if (current == null) {
        throw new NoSuchElementException("No exercise with ID " + exerciseID + " in the list.");
      }

      // check if head is removed
      if (previous == null) {
        // remove head
        head = head.getNext();
        if (head == null) {
          // list is empty
          tail = null;
        }
      } else {
        previous.setNext(current.getNext());

        // check if tail is removed
        if (current == tail) {
          tail = previous;
        }
      }

      // update fields
      updateCounterFields(current.getExercise().getType(), -1);
      size--;
      return current.getExercise();

    } catch (NoSuchElementException e) {
      // catch exception
      System.out.println("Error: " + e.getMessage());
      return null;
    }
  }

  /**
   * Returns a String representation of the contents of this list, as the concatenated String
   * representations of all LinkedExercise nodes in this list. See the sample output at the end of
   * the writeup for examples.
   */
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    LinkedExercise current = head;

    while (current != null) {
      result.append(current.toString());
      current = current.getNext();
    }

    // Return string
    return result.toString();
  }

  /**
   * Updates the counter fields based on the provided work outs
   * 
   * @param type      - work out to be updated
   * @param increaset - amount to increase work out by
   */
  private void updateCounterFields(WorkoutType type, int increase) {
    if (type == WorkoutType.WARMUP) {
      warmupCount += increase;
    } else if (type == WorkoutType.PRIMARY) {
      primaryCount += increase;
    } else if (type == WorkoutType.COOLDOWN) {
      cooldownCount += increase;
    }
  }
}
