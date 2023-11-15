/*
 * Name: Tushar Bansal
 * EID:tb34826
 */

// Implement your algorithms here
// Methods may be added to this file, but don't remove anything
// Include this file in your final submission

import java.util.ArrayList;

public class Program2 {
    private ArrayList<Student> students;    // this is a list of all Students, populated by Driver class
    private Heap minHeap;

    // additional constructors may be added, but don't delete or modify anything already here
    public Program2(int numStudents) {
        minHeap = new Heap();
        students = new ArrayList<Student>();
    }

    /**
     * findMinimumStudentCost(Student start, Student dest)
     *
     * @param start - the starting Student.
     * @param dest  - the end (destination) Student.
     * @return the minimum cost possible to get from start to dest.
     * Assume the given graph is always connected.
     */
    public int findMinimumStudentCost(Student start, Student dest) {


        for (int i = 0; i < students.size(); i++) {
            students.get(i).resetminCost();
        }
        //System.out.println("hi1");

        //System.out.println(minHeap.size());
        start.setminCost(0);
        //System.out.println(students.size());
        minHeap.buildHeap(students); 
        //System.out.println("hi3");
        //System.out.println(students);
        //System.out.println(minHeap);
        while (minHeap.size() != 0) {
            //System.out.println(minHeap.findMin());
            Student current = minHeap.extractMin();
            //System.out.println(minHeap);
            //System.out.println("hi3");
            //System.out.println(current);
            // System.out.println(minHeap);
            if (current == dest) {
                return dest.getminCost();
            }
    
            ArrayList<Student> neighbors = current.getNeighbors();
            ArrayList<Integer> prices = current.getPrices();
    
            for (int i = 0; i < neighbors.size(); i++) {
                Student neighbor = neighbors.get(i);
                int price = prices.get(i);
                // System.out.println("hi1");
                // System.out.println(i);
                // System.out.println(price);
                // System.out.println("hi2");
                // System.out.println(current.getminCost());
                int newCost = current.getminCost() + price;
    
                if (newCost < neighbor.getminCost()) {
                    // System.out.println("hi3");

                    //System.out.println(i);
                    //System.out.println(newCost);
                    // System.out.println(neighbor.getminCost());
                    // System.out.println("hi4");
                    
                    
                    //neighbor.setminCost(newCost);
    
                    minHeap.changeKey(neighbor, newCost);
                    neighbor.setminCost(newCost); 
                    
                }
                
            }
            //System.out.println(minHeap);
        }
        return -1;
    }

    /**
     * findMinimumClassCost()
     *
     * @return the minimum total cost required to connect (span) each student in the class.
     * Assume the given graph is always connected.
     */
    public int findMinimumClassCost() {


        int totalCost = 0;

        for (int i = 0; i < students.size(); i++) {
            students.get(i).resetminCost();
        }
    
        Student start = students.get(0);
        start.setminCost(0); 
    
        minHeap.buildHeap(students); 
    
        while (minHeap.size() != 0) {
            Student currentStudent = minHeap.extractMin();
    
            totalCost += currentStudent.getminCost(); 
    
            ArrayList<Student> neighbors = currentStudent.getNeighbors();
            ArrayList<Integer> prices = currentStudent.getPrices();
    
            for (int i = 0; i < neighbors.size(); i++) {
                Student neighbor = neighbors.get(i);
                int cost = prices.get(i);
    
                if (minHeap.contains(neighbor) && cost < neighbor.getminCost()) {
                    
                    minHeap.changeKey(neighbor, cost);
                    neighbor.setminCost(cost);
                }
            }
        }
    
        return totalCost;
    }

    //returns edges and prices in a string.
    public String toString() {
        String o = "";
        for (Student v : students) {
            boolean first = true;
            o += "Student ";
            o += v.getName();
            o += " has neighbors ";
            ArrayList<Student> ngbr = v.getNeighbors();
            for (Student n : ngbr) {
                o += first ? n.getName() : ", " + n.getName();
                first = false;
            }
            first = true;
            o += " with prices ";
            ArrayList<Integer> wght = v.getPrices();
            for (Integer i : wght) {
                o += first ? i : ", " + i;
                first = false;
            }
            o += System.getProperty("line.separator");

        }

        return o;
    }

///////////////////////////////////////////////////////////////////////////////
//                           DANGER ZONE                                     //
//                everything below is used for grading                       //
//                      please do not change :)                              //
///////////////////////////////////////////////////////////////////////////////

    public Heap getHeap() {
        return minHeap;
    }

    public ArrayList<Student> getAllstudents() {
        return students;
    }

    // used by Driver class to populate each Student with correct neighbors and corresponding prices
    public void setEdge(Student curr, Student neighbor, Integer price) {
        curr.setNeighborAndPrice(neighbor, price);
    }

    // used by Driver.java and sets students to reference an ArrayList of all Students
    public void setAllNodesArray(ArrayList<Student> x) {
        students = x;
    }
}
