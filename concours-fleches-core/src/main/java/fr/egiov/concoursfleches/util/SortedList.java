package fr.egiov.concoursfleches.util;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Sorted list.<br />
 * Don't handle equals objects.
 * 
 * @author giovarej
 * 
 * @param <E>
 *           type of the elements of the list
 */
public class SortedList<E extends Comparable<E>> extends LinkedList<E>
{
   /** Serial version UID */
   private static final long serialVersionUID = 1L;

   /**
    * {@inheritDoc}
    * 
    * @see java.util.LinkedList#add(Object)
    */
   @Override
   public boolean add(E p_Element)
   {
      boolean elementAdded = false;

      // Finds the index where to add the element
      int index = Collections.binarySearch(this, p_Element);

      // Adds the element at the position found above
      if (index < 0)
      {
         index = -index - 1;
         super.add(index, p_Element);
         elementAdded = true;
      }

      return elementAdded;
   }
}
