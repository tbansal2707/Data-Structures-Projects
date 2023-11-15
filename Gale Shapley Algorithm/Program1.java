/*
 * Name: Tushar Bansal
 * EID: tb34826
 */

import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
//import java.util.List;
//import java.util.PriorityQueue;

/**
 * Your solution goes in this class.
 *
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 *
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution.
 */
public class Program1 extends AbstractProgram1 {


    /**
     * Determines whether a candidate Matching represents a solution to the stable matching problem.
     * Study the description of a Matching in the project documentation to help you with this.
     */
    @Override
    public boolean isStableMatching(Matching problem) {

        ArrayList<ArrayList<Integer>> highschoolPreference = problem.getHighSchoolPreference();
        ArrayList<ArrayList<Integer>> studentPreference = problem.getStudentPreference();
        ArrayList<Integer> studentMatching = problem.getStudentMatching();
        int studentCount = problem.getStudentCount();
        int highschoolCount = problem.getHighSchoolCount();
        int totalHighSchoolSpots = problem.totalHighSchoolSpots();

        HashMap<Integer, HashMap<Integer, Integer>> schoolPreferenceIndices = new HashMap<>();

        // Iterate over the highschoolPreference list
        for (int school = 0; school < highschoolPreference.size(); school++) {
            ArrayList<Integer> preferences = highschoolPreference.get(school);

            // Create a HashMap to store the indices of students for the current school
            HashMap<Integer, Integer> studentIndices = new HashMap<>();

            // Iterate over the schools's preferences and store the indices in the inner HashMap
            for (int index = 0; index < preferences.size(); index++) {
                int student = preferences.get(index);
                studentIndices.put(student, index);
            }

            // Add the inner HashMap to the schoolPreferenceIndices HashMap
            schoolPreferenceIndices.put(school, studentIndices);
        }
        
        
        // ArrayList<Integer> currentAllocatedStudents = new ArrayList<Integer>();
        // for (int highSchoolIndex =0; highSchoolIndex < highschoolCount; highSchoolIndex++){
        //     for (int studentnum = 0; studentnum < studentCount; studentnum++){
        //         if (studentMatching.get(studentnum) == highSchoolIndex) {
        //             currentAllocatedStudents.add(studentnum);
        //         }
        //     }
        // }

        // int sum = 0;
        // for (int element : studentMatching) {
        //     sum += element;
        // }

        // if (sum < totalHighSchoolSpots) {
        //     return false;
        // }

        int count = 0;
        for (int i = 0; i < studentMatching.size(); i++) {
            if (studentMatching.get(i) != -1) {
                count+=1;
                }
            }
        
        if (count != totalHighSchoolSpots) {
            return false;
        }


        // Iterate over all students
        for (int i=0; i < studentCount ; i++)
        {
            // Current highschool matching for student i
            int curMatch = studentMatching.get(i);

            if (curMatch == -1) {
                for (int highSchoolIndex =0; highSchoolIndex < highschoolCount; highSchoolIndex++) {
                    for (int studentIndex = 0; studentIndex < studentCount; studentIndex++) {
                        ArrayList<Integer> currentAllocatedStudents = new ArrayList<Integer>();
                        if (studentMatching.get(studentIndex) == highSchoolIndex) {
                            currentAllocatedStudents.add(studentIndex);
                        }
                        
                        //ArrayList<Integer> pref = highschoolPreference.get(j);
                        int preferenceOfHighschoolForStudent = schoolPreferenceIndices.get(highSchoolIndex).get(i);

                        for (int currentMatches = 0; currentMatches < currentAllocatedStudents.size(); currentMatches++) {
                            int currentMatchStudentIndex = currentAllocatedStudents.get(currentMatches);
                            //int r_current_match_in_list = highschool_k_preflist.indexOf(current_match_index);
                            int preferenceOfHighschoolForCurrentStudent = schoolPreferenceIndices.get(highSchoolIndex).get(currentMatchStudentIndex);
                            if (preferenceOfHighschoolForStudent < preferenceOfHighschoolForCurrentStudent) {
                                return false;
                            }
                        }
                    }


                }
            }

            else {// Assigned highschool preference
                ArrayList<Integer> pref = highschoolPreference.get(curMatch);
                
                // Figure out student index in highschool preference
                int studentIndex = 0;
                for (; studentIndex < studentCount; studentIndex++)
                {
                    if (pref.get(studentIndex) == i)
                    {
                        break;
                    }
                }

                // For all students having higher priority than current student as per high school preference
                // If they also have higher priority for this highschool then their current assignment, it isn't a stable matching
                for (int j = 0; j< studentIndex; j++)
                {
                    int currentStudent = pref.get(j);
                    ArrayList<Integer> studentPref = studentPreference.get(currentStudent);
                    int currentMatch = studentMatching.get(currentStudent);
                    if (currentMatch == -1)
                        return false;
                    for (int m = 0; m < highschoolCount; m++)
                    {
                        if (studentPref.get(m) == currentMatch)
                        {
                            break;
                        }
                        else if (studentPref.get(m) == curMatch)
                        {
                            return false;
                        }
                    }
                }
            }    
        }

        return true;
    }


            
    @Override
    public Matching stableMatchingGaleShapley_studentoptimal(Matching problem) {

        //get all the data from input
        int studentCount = problem.getStudentCount();
        int highschoolCount = problem.getHighSchoolCount();
        ArrayList<ArrayList<Integer>> studentPreference = problem.getStudentPreference();
        ArrayList<ArrayList<Integer>> highschoolPreference = problem.getHighSchoolPreference();
        ArrayList<Integer> highschoolSpots = problem.getHighSchoolSpots();
        int totalSpots = problem.totalHighSchoolSpots();

        // Create arrays to keep track of spots remaining, current student indices, and student matching
        int[] spotsRemaining = new int[highschoolCount];
        int[] arrayOfZeros = new int[highschoolCount];
        int[] highSchoolPreferencePosition = new int[highschoolCount];
        int[] studentMatching = new int[studentCount];
        int[] highSchoolsPending = new int[studentCount];
        Arrays.fill(spotsRemaining, 0);
        Arrays.fill(arrayOfZeros, 0);
        Arrays.fill(highSchoolPreferencePosition, 0);
        Arrays.fill(studentMatching, -1);
        Arrays.fill(highSchoolsPending, highschoolCount);

        HashMap<Integer, HashMap<Integer, Integer>> schoolPreferenceIndices = new HashMap<>();

        // Iterate over the highschoolPreference list
        for (int school = 0; school < highschoolPreference.size(); school++) {
            ArrayList<Integer> preferences = highschoolPreference.get(school);

            // Create a HashMap to store the indices of students for the current school
            HashMap<Integer, Integer> studentIndices = new HashMap<>();

            // Iterate over the schools's preferences and store the indices in the inner HashMap
            for (int index = 0; index < preferences.size(); index++) {
                int student = preferences.get(index);
                studentIndices.put(student, index);
            }

            // Add the inner HashMap to the schoolPreferenceIndices HashMap
            schoolPreferenceIndices.put(school, studentIndices);
        }

        for (int i = 0; i < highschoolCount; i++) {
            spotsRemaining[i] = highschoolSpots.get(i);
        }
        boolean studentsWithOptions = true;

        // Iterate over all students to be allocated
        //for (int j = 0; j < studentCount; j++) 
        while (studentsWithOptions)
        {
            for (int studentIndex = 0; studentIndex < studentCount; studentIndex++) 
            {
                if (highSchoolsPending[studentIndex] > 0) 
                {
                    // If student is unmatched
                    if (studentMatching[studentIndex] == -1) 
                    {
                        outerloop:
                        // Go over student preferences one by one and see if it can be allocated
                        for (int highSchool = highschoolCount - highSchoolsPending[studentIndex] ; highSchool < highschoolCount; highSchool++)
                        {
                            highSchoolsPending[studentIndex] -=1;
                            int highSchoolIndex = studentPreference.get(studentIndex).get(highSchool);
                            if (spotsRemaining[highSchoolIndex] > 0)
                            {
                                // reduce spot count
                                spotsRemaining[highSchoolIndex] -= 1;
                                // allocate college
                                studentMatching[studentIndex] = highSchoolIndex;
                                //totalSpots -= 1;
                                //System.out.println(studentIndex);
                                break outerloop;
                                
                            }
                            else
                            {
                                //int[] currentAllocatedStudents = new int[highschoolSpots.get(highSchoolIndex)-spotsRemaining[highSchoolIndex]];
                                ArrayList<Integer> currentAllocatedStudents = new ArrayList<Integer>();
                                for (int studentnum = 0; studentnum < studentCount; studentnum++) 
                                {
                                    if (studentMatching[studentnum] == highSchoolIndex) {
                                        currentAllocatedStudents.add(studentnum);
                                    }
                                }

                                // Rewrite this with hashMap similar to highschool optimal
                                //ArrayList<Integer> highSchoolPreflist = highschoolPreference.get(highSchoolIndex);

                                int preferenceOfHighschoolForStudent = schoolPreferenceIndices.get(highSchoolIndex).get(studentIndex);
                                int maxpreferenceOfHighschoolForCurrentStudent = 0;
                                int maxcurrentMatchStudentIndex = 0;

                                for (int currentMatches = 0; currentMatches < currentAllocatedStudents.size(); currentMatches++) {
                                    int currentMatchStudentIndex = currentAllocatedStudents.get(currentMatches);
                                    //int r_current_match_in_list = highschool_k_preflist.indexOf(current_match_index);
                                    int newpreferenceOfHighschoolForCurrentStudent = schoolPreferenceIndices.get(highSchoolIndex).get(currentMatchStudentIndex);
                                    if (newpreferenceOfHighschoolForCurrentStudent > maxpreferenceOfHighschoolForCurrentStudent) {
                                        maxpreferenceOfHighschoolForCurrentStudent = newpreferenceOfHighschoolForCurrentStudent;
                                        maxcurrentMatchStudentIndex = currentMatchStudentIndex;
                                    }
                                }
                                if (preferenceOfHighschoolForStudent < maxpreferenceOfHighschoolForCurrentStudent) {
                                    studentMatching[studentIndex] = highSchoolIndex;
                                    studentMatching[maxcurrentMatchStudentIndex] = -1;
                                    //System.out.println(maxcurrentMatchStudentIndex);

                                    break outerloop;
                                }
                                

                            }
                        }
                    }
                }
            }

            int counter1 = 0;
            int counter2 = 0;
            for (int i = 0; i < studentMatching.length; i++) {
                if (studentMatching[i] == -1 && highSchoolsPending[i] == 0) {
                counter1+=1;
                }
                if (studentMatching[i] == -1) {
                counter2+=1;
                }
            }


            if (studentCount!=totalSpots) {
                if (counter1 == studentCount-totalSpots) {
                studentsWithOptions = false;
                }
            }

            if (studentCount==totalSpots) {
                if (counter2 == 0) {
                studentsWithOptions = false;
                }
            }


            //System.out.println(count);
        }
        ArrayList<Integer> studentMatchingList = new ArrayList<>();
        for (int i = 0; i < studentCount; i++) 
        {
            studentMatchingList.add(studentMatching[i]);
        }
        problem.setStudentMatching(studentMatchingList);




        return problem;
    
        
    }
            
            


    @Override
        public Matching stableMatchingGaleShapley_highschooloptimal(Matching problem) {
            
            //get all the data from input
            int studentCount = problem.getStudentCount();
            int highschoolCount = problem.getHighSchoolCount();
            ArrayList<ArrayList<Integer>> studentPreference = problem.getStudentPreference();
            ArrayList<ArrayList<Integer>> highschoolPreference = problem.getHighSchoolPreference();
            ArrayList<Integer> highschoolSpots = problem.getHighSchoolSpots();

            // Create arrays to keep track of spots remaining, current student indices, and student matching
            int[] spotsRemaining = new int[highschoolCount];
            int[] arrayOfZeros = new int[highschoolCount];
            int[] highSchoolPreferencePosition = new int[highschoolCount];
            int[] studentMatching = new int[studentCount];
            Arrays.fill(spotsRemaining, 0);
            Arrays.fill(arrayOfZeros, 0);
            Arrays.fill(highSchoolPreferencePosition, 0);
            Arrays.fill(studentMatching, -1);

            // Create a HashMap to store the indices of schools for all students
            HashMap<Integer, HashMap<Integer, Integer>> studentPreferenceIndices = new HashMap<>();

            // Iterate over the STUDENTPREFERENCE list
            for (int student = 0; student < studentPreference.size(); student++) {
                ArrayList<Integer> preferences = studentPreference.get(student);

                // Create a HashMap to store the indices of schools for the current student
                HashMap<Integer, Integer> schoolIndices = new HashMap<>();

                // Iterate over the student's preferences and store the indices in the inner HashMap
                for (int index = 0; index < preferences.size(); index++) {
                    int school = preferences.get(index);
                    schoolIndices.put(school, index);
                }

                // Add the inner HashMap to the studentPreferenceIndices HashMap
                studentPreferenceIndices.put(student, schoolIndices);
            }


            for (int i = 0; i < highschoolCount; i++) {
                spotsRemaining[i] = highschoolSpots.get(i);
            }

            LinkedList<Integer> highschoolsWithAvailableSpots = new LinkedList<>();
            for (int i = 0; i < highschoolCount; i++) {
                highschoolsWithAvailableSpots.add(i);
            }
            
            //iterate over all highschools to allocate slots to students
            while (!highschoolsWithAvailableSpots.isEmpty()) {
                int highschool = highschoolsWithAvailableSpots.get(0);
                int initialHighschoolPreferencePosition = highSchoolPreferencePosition[highschool];
            
                //run the logic to allocate students to highschool only if slots are remaining and highschool's preference list is not exhausted
                while (spotsRemaining[highschool] != 0 && highSchoolPreferencePosition[highschool] >= initialHighschoolPreferencePosition) {
                    //1. get student at current highschool's preference list
                    int student = highschoolPreference.get(highschool).get(highSchoolPreferencePosition[highschool]);
                    //2. check student's allocation status

                    //if student is unallocated, then allocate the current high school to student
                    if (studentMatching[student] == -1) {
                        //allocate student to highschool
                        studentMatching[student] = highschool;
                        //reduce spots remaining for highschool
                        spotsRemaining[highschool] -= 1;
                        //increment highschool's preference list position
                        highSchoolPreferencePosition[highschool] += 1;

                    }
                    //if student is allocated, then check if student prefers current highschool over current allocation
                    else {
                        //check if student prefers current highschool over current allocation
                        int currentHighschool = studentMatching[student];
                        int currentHighschoolIndex = studentPreferenceIndices.get(student).get(currentHighschool);
                        int newHighschoolIndex = studentPreferenceIndices.get(student).get(highschool);
                        //int currentHighschoolIndex = studentPreference.get(student).indexOf(currentHighschool);
                        //int newHighschoolIndex = studentPreference.get(student).indexOf(highschool);

                        //if student prefers current highschool over current allocation, then allocate current highschool to student
                        if (newHighschoolIndex < currentHighschoolIndex) {
                            //allocate student to highschool
                            studentMatching[student] = highschool;
                            //reduce spots remaining for highschool
                            spotsRemaining[highschool] -= 1;
                            //increment highschool's preference list position
                            spotsRemaining[currentHighschool] += 1;
                            //increment highschool's preference list position
                            highSchoolPreferencePosition[highschool] += 1;

                            //add to highschoolsWithAvailableSpots if spots remaining is 1 
                            //because it won't be part of highschoolsWithAvailableSpots if slots were 0 and now we are incrementing it by 1
                            if (spotsRemaining[currentHighschool] == 1) {
                                highschoolsWithAvailableSpots.add(currentHighschool);                       
                            }
                        }
                        //if student prefers current allocation over current highschool, then increment highschool's preference list position
                        else {
                            //increment highschool's preference list position
                            highSchoolPreferencePosition[highschool] += 1;
                        }
                    }
                    
                }

                if (spotsRemaining[highschool] == 0){
                    highschoolsWithAvailableSpots.remove(0);
                }           
            }     

            ArrayList<Integer> studentMatchingList = new ArrayList<>();
            for (int i = 0; i < studentCount; i++) {
                studentMatchingList.add(studentMatching[i]);
            }

            problem.setStudentMatching(studentMatchingList);

            return problem;
        }
}