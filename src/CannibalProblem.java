/**
 * Created by reshmi on 9/15/16.
 */

import java.util.ArrayList;
import java.util.Arrays;


// for the first part of the assignment, you might not extend UUSearchProblem,
//  since UUSearchProblem is incomplete until you finish it.

public class CannibalProblem extends UUSearchProblem
{

    // the following are the only instance variables you should need.
    //  (some others might be inherited from UUSearchProblem, but worry
    //  about that later.)

    private int goalm, goalc, goalb;
    private int totalMissionaries, totalCannibals;

    public CannibalProblem(int sm, int sc, int sb, int gm, int gc, int gb) {
        // I (djb) wrote the constructor; nothing for you to do here.

        startNode = new CannibalNode(sm, sc, sb, 0);
        goalm = gm;
        goalc = gc;
        goalb = gb;
        totalMissionaries = sm;
        totalCannibals = sc;

    }

//    public static void main(String[] args)
//    {
//
//        CannibalNode testNode = new CannibalNode(3, 3, 1, 0);
//        ArrayList<UUSearchNode> successors = testNode.getSuccessors();
//        System.out.println(successors.size());
//        for(int i=0;i<successors.size();i++)
//        {
//            System.out.println(successors.get(i));
//
//        }
//
//    }

    // node class used by searches.  Searches themselves are implemented
    //  in UUSearchProblem.
    private class CannibalNode implements UUSearchNode
    {

        // do not change BOAT_SIZE without considering how it affect
        // getSuccessors.

        private final static int BOAT_SIZE = 2;

        // how many missionaries, cannibals, and boats
        // are on the starting shore
        private int[] state;

        // how far the current node is from the start.  Not strictly required
        //  for search, but useful information for debugging, and for comparing paths
        private int depth;

        public CannibalNode(int m, int c, int b, int d) {
            state = new int[3];
            this.state[0] = m;
            this.state[1] = c;
            this.state[2] = b;

            depth = d;

        }

        private boolean isSafeState(int missionaries, int cannibals)
        {
            int no_of_missionaries_other_side = totalMissionaries - missionaries;
            int no_of_cannibals_other_side = totalCannibals - cannibals;
            if((missionaries > 0 && missionaries < cannibals) ||
                    (no_of_missionaries_other_side > 0 && no_of_cannibals_other_side > no_of_missionaries_other_side))
                return false;
            else
                return true;
        }

        public ArrayList<UUSearchNode> getSuccessors() {

            // add actions (denoted by how many missionaries and cannibals to put
            // in the boat) to current state.

            // You write this method.  Factoring is usually worthwhile.  In my
            //  implementation, I wrote an additional private method 'isSafeState',
            //  that I made use of in getSuccessors.  You may write any method
            //  you like in support of getSuccessors.
            ArrayList<UUSearchNode> legal_states = new ArrayList<>();
            
//            if(this.state[2]==1){
//                int max_mis = this.state[0]>BOAT_SIZE ? BOAT_SIZE : this.state[0];
//                for (int i=max_mis)
//            }

            if(state[2] == 1)
            {
                for (int i = 0; i <= state[0]; i++)
                {
                    for (int j = 0; j <= state[1]; j++)
                    {
                        int number_of_people_crossing = i + j;
//                    System.out.println("number_of_people_crossing = " + number_of_people_crossing);
                        if (number_of_people_crossing <= BOAT_SIZE && number_of_people_crossing > 0)
                        {
                            if (isSafeState(state[0] - i, state[1] - j))
                            {
                                CannibalNode node = new CannibalNode(state[0] - i, state[1] - j, 0, 0);
                                legal_states.add(node);
//                                print("" + node.state[0] + "," + node.state[1] + "," + node.state[2]);
//                                System.out.println("node = " + node);
                            }
                        }
                    }
                }
            }
            else
            {
                for (int i = 0; i <= totalMissionaries - state[0]; i++)
                {
                    for (int j = 0; j <= totalCannibals - state[1]; j++)
                    {
                        int number_of_people_crossing = i + j;
//                    System.out.println("number_of_people_crossing = " + number_of_people_crossing);
                        if (number_of_people_crossing <= BOAT_SIZE && number_of_people_crossing > 0)
                        {
                            if (isSafeState(i + state[0], j + state[1]))
                            {
                                CannibalNode node = new CannibalNode(i + state[0], j + state[1], 1, 0);
                                legal_states.add(node);
//                                System.out.println("" + node.state[0] + "," + node.state[1] + "," + node.state[2]);
                            }
                        }
                    }
                }
            }

//            System.out.println("legal_states = " + legal_states);
            return legal_states;
        }

        void print(String str){
            System.out.println(str);
        }
        @Override
        public boolean goalTest() {
            if(goalm == state[0] && goalc == state[1] && goalb == state[2])
                return true;
            return false;

        }



        // an equality test is required so that visited lists in searches
        // can check for containment of states
        @Override
        public boolean equals(Object other) {
            return Arrays.equals(state, ((CannibalNode) other).state);
        }

        @Override
        public int hashCode() {
            return state[0] * 100 + state[1] * 10 + state[2];
        }

        @Override
        public String toString() {

            // you write this method
            String str = ""+hashCode();
            return str;
        }

//        You might need this method when you start writing
//        (and debugging) UUSearchProblem.
        
		@Override
		public int getDepth() {
			return depth;
		}

    }


}