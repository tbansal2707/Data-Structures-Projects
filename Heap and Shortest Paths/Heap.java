/*
 * Name: Tushar Bansal
 * EID: tb34826
 */

// Implement your heap here
// Methods may be added to this file, but don't remove anything
// Include this file in your final submission

import java.util.ArrayList;
import java.util.HashMap;

public class Heap {
    private ArrayList<Student> minHeap;
    private HashMap<Student, Integer> indexMap;

    public Heap() {
        minHeap = new ArrayList<Student>();
        indexMap = new HashMap<>();
    }

    /**
     * buildHeap(ArrayList<Student> students)
     * Given an ArrayList of Students, build a min-heap keyed on each Student's minCost
     * Time Complexity - O(nlog(n)) or O(n)
     *
     * @param students
     */
    public void buildHeap(ArrayList<Student> students) {
        //System.out.println("hi1");
        //minHeap = new ArrayList<Student>();
        //minHeap = students;
        minHeap.clear();
        indexMap.clear();
        //System.out.println("hi2");
        //int lastNonLeafNodeIndex = (minHeap.size() / 2) - 1;

        // for (int i = 0; i < minHeap.size(); i++) {
        //     indexMap.put(minHeap.get(i), i);
        // }

        for (int i = 0; i < students.size(); i++) {
            //minHeap.add(students.get(i));
            //System.out.println(i);
            insertNode(students.get(i));
            //System.out.println();
            //System.out.println(i);
        }
        //System.out.println("hi");
    }

    

    /**
     * insertNode(Student in)
     * Insert a Student into the heap.
     * Time Complexity - O(log(n))
     *
     * @param in - the Student to insert.
     */
    public void insertNode(Student in) {
        //System.out.println("hi4");
        minHeap.add(in);
        int index = minHeap.size() - 1;
        indexMap.put(in, index);
        //System.out.println("hi7");
        heapifyUp(index);
        //System.out.println("hi5");
    }

    /**
     * findMin()
     * Time Complexity - O(1)
     *
     * @return the minimum element of the heap.
     */
    public Student findMin() {
        if (minHeap.isEmpty()) {
            return null;
        }
        return minHeap.get(0);
    }

    /**
     * extractMin()
     * Time Complexity - O(log(n))
     *
     * @return the minimum element of the heap, AND removes the element from said heap.
     */
    public Student extractMin() {
        if (minHeap.isEmpty()) {
            return null;
        }



        Student min = minHeap.get(0);
        //Student lastElement = minHeap.remove(minHeap.size() - 1);
        // int lastElementIndex = minHeap.size() - 1;

        // indexMap.remove(min);
        // if (!minHeap.isEmpty()) {
        //     minHeap.set(0, minHeap.get(lastElementIndex));
        //     indexMap.put(lastElement, 0);
        //     heapifyDown(0);
        // }

        delete(0);
        return min;
    }

    /**
     * delete(int index)
     * Deletes an element in the min-heap given an index to delete at.
     * Time Complexity - O(log(n))
     *
     * @param index - the index of the item to be deleted in the min-heap.
     */
    public void delete(int index) {
        if (index < 0 || index >= minHeap.size()) {
            return;
        }

        
        Student deleted = minHeap.get(index);
        int lastElementIndex = minHeap.size() - 1;
        indexMap.remove(deleted);
        indexMap.put(minHeap.get(lastElementIndex), index);
        //Student temp = minHeap.get(index);
        minHeap.set(index, minHeap.get(lastElementIndex));
        //minHeap.set(lastElementIndex, temp);
        // swap(index, lastElementIndex);
        minHeap.remove(lastElementIndex);
        heapify(index);
    }

    /**
     * changeKey(Student r, int newCost)
     * Changes minCost of Student s to newCost and updates the heap.
     * Time Complexity - O(log(n))
     *
     * @param r       - the Student in the heap that needs to be updated.
     * @param newCost - the new cost of Student r in the heap (note that the heap is keyed on the values of minCost)
     */
    public void changeKey(Student r, int newCost) {
        Integer index = indexMap.get(r);
        if (index != null) {
       
            int currentCost = r.getminCost();
            r.setminCost(newCost);
            //System.out.println(currentCost);
            //System.out.println(newCost);

            if (newCost < currentCost) {
                heapifyUp(index);
            } else if (newCost > currentCost) {
                heapifyDown(index);
            }

        }
    }

    private void heapify(int index) {

        int heapSize = minHeap.size();
        int smallest = index;
        int left = 2 * index + 1;
        int right = 2 * index + 2;

        if (left < heapSize) {
            if (right < heapSize && (minHeap.get(left).getminCost() <= minHeap.get(smallest).getminCost() || minHeap.get(right).getminCost() <= minHeap.get(smallest).getminCost())) {

                heapifyDown(index);
            }

            else if (minHeap.get(left).getminCost() <= minHeap.get(smallest).getminCost()) {
                heapifyDown(index);
            }
        }

        else {
            heapifyUp(index);
        }
    }
    
    private void heapifyDown(int index) {
        //System.out.println("hi9");
        int heapSize = minHeap.size();
        int smallest = index;
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        

        if (left < heapSize && minHeap.get(left).getminCost() <= minHeap.get(smallest).getminCost()) {

            if (minHeap.get(left).getminCost() < minHeap.get(smallest).getminCost() || (minHeap.get(left).getminCost() == minHeap.get(smallest).getminCost() && minHeap.get(left).getName() < minHeap.get(smallest).getName())) {
                smallest = left;
            }
        }
        if (right < heapSize && minHeap.get(right).getminCost() <= minHeap.get(smallest).getminCost()) {
            if (minHeap.get(right).getminCost() < minHeap.get(smallest).getminCost() || (minHeap.get(right).getminCost() == minHeap.get(smallest).getminCost() && minHeap.get(right).getName() < minHeap.get(smallest).getName())) {
                smallest = right;
            }
        }

        if (smallest != index) {
            swap(index, smallest);
            heapifyDown(smallest);
        }
    }

    private void heapifyUp(int index) {
        int parent = (index - 1) / 2;
        //System.out.println("hi8");
        //System.out.println(parent);

        while (index > 0 && minHeap.get(index).getminCost() <= minHeap.get(parent).getminCost()) {
            //System.out.println("hi9");

            if (minHeap.get(index).getminCost() < minHeap.get(parent).getminCost()|| (minHeap.get(index).getminCost() == minHeap.get(parent).getminCost() && minHeap.get(index).getName() < minHeap.get(parent).getName())) {
                swap(index, parent);
                index = parent;
                parent = (index - 1) / 2;
            }

            else {
                break;
            }
        
        }

        
    }

    private void swap(int i, int j) {
        Student temp = minHeap.get(i);
        minHeap.set(i, minHeap.get(j));
        minHeap.set(j, temp);
        indexMap.put(minHeap.get(i), i);
        indexMap.put(minHeap.get(j), j);
    }

    
    public int size() {
        return minHeap.size();
    }

    public String toString() {
        String output = "";
        for (int i = 0; i < minHeap.size(); i++) {
            output += minHeap.get(i).getName() + " ";
        }
        return output;
    }

    public boolean contains(Student student) {
        return indexMap.containsKey(student);
    }

    

///////////////////////////////////////////////////////////////////////////////
//                           DANGER ZONE                                     //
//                everything below is used for grading                       //
//                      please do not change :)                              //
///////////////////////////////////////////////////////////////////////////////

    public ArrayList<Student> toArrayList() {
        return minHeap;
    }
}
