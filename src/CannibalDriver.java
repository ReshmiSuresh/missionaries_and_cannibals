/**
 * Created by reshmi on 9/15/16.
 */

import java.util.ArrayList;
import java.util.List;

public class CannibalDriver {
    public static final int MAXDEPTH = 5000;

    private static void printSuccessors(CannibalProblem problem)
    {
        List<UUSearchProblem.UUSearchNode> successors = problem.startNode.getSuccessors();
        System.out.println("successors of startNode = " + successors);

        for(int i=0;i<successors.size();i++)
            System.out.println("successors of " + successors.get(i) + " = " + successors.get(i).getSuccessors());

    }


    public static void main(String args[]) {

        // interesting starting state:
        //  8, 5, 1  (IDS slow, but uses least memory.)


        // set up the "standard" 331 problem:
        CannibalProblem mcProblem = new CannibalProblem(3, 3, 1, 0, 0, 0);

//        printSuccessors(mcProblem);
//        System.out.println(successors.size());
//        System.out.println("successors.get(1) = " + successors.get(1));
        List<UUSearchProblem.UUSearchNode> path;


        path = mcProblem.breadthFirstSearch();
        System.out.println("bfs path length:  " + path.size() + " " + path);
        mcProblem.printStats();
        System.out.println("--------");
//
//
        path = mcProblem.depthFirstMemoizingSearch(MAXDEPTH);
        System.out.println("dfs memoizing path length:" + path.size() + " " + path);
        mcProblem.printStats();
        System.out.println("--------");
////
        path = mcProblem.depthFirstPathCheckingSearch(MAXDEPTH);
        System.out.println("dfs path checking path length:" + path.size() + " " + path);
        mcProblem.printStats();
//
////
        System.out.println("--------");
        path = mcProblem.IDSearch(MAXDEPTH);
        System.out.println("Iterative deepening (path checking) path length:" + path.size() + " " + path);
        mcProblem.printStats();

    }
}