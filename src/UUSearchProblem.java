/**
 * Created by reshmi on 9/15/16.
 */
// CLEARLY INDICATE THE AUTHOR OF THE FILE HERE (YOU),
//  AND ATTRIBUTE ANY SOURCES USED (INCLUDING THIS STUB, BY
//  DEVIN BALKCOM).

import java.util.*;

public abstract class UUSearchProblem {

    // used to store performance information about search runs.
    //  these should be updated during the process of searches

    // see methods later in this class to update these values
    protected int nodesExplored;
    protected int maxMemory;

    protected UUSearchNode startNode;


    protected interface UUSearchNode {
        public ArrayList<UUSearchNode> getSuccessors();
        public boolean goalTest();
        public int getDepth();
    }

    // breadthFirstSearch:  return a list of connecting Nodes, or null
    // no parameters, since start and goal descriptions are problem-dependent.
    //  therefore, constructor of specific problems should set up start
    //  and goal conditions, etc.

    public List<UUSearchNode> breadthFirstSearch()
    {
        resetStats();
        List<UUSearchNode> path;
        Queue<UUSearchNode> queue = new LinkedList<UUSearchNode>();
        HashMap<UUSearchNode, UUSearchNode> visited = new HashMap<>();
        ArrayList<UUSearchNode> successors;

        queue.offer(startNode);
        incrementNodeCount();
        while(!queue.isEmpty())
        {
            UUSearchNode temp = queue.poll();


            if(temp.goalTest())
            {
//                System.out.println("reached");
                path = backchain(temp, visited);
//                System.out.println("temp = " + temp);
                updateMemory(visited.size() + 1);
                nodesExplored = visited.size() + 1;
                return path;
            }

            successors = temp.getSuccessors();
            for(int i=0;i<successors.size();i++)
            {
                if(!visited.containsValue(successors.get(i)))
                {
                    queue.offer(successors.get(i));
                    visited.put(successors.get(i),temp);
                    incrementNodeCount();
                }
            }
        }
        return null;
    }

    // backchain should only be used by bfs, not the recursive dfs
    private List<UUSearchNode> backchain(UUSearchNode node,
                                         HashMap<UUSearchNode, UUSearchNode> visited)
    {

        List<UUSearchNode> extracted_path = new ArrayList<UUSearchNode>();  //list is in reverse order

//        ArrayList<UUSearchNode> keys = new ArrayList<UUSearchNode>(visited.keySet());
//        for(int i=keys.size()-1;i>=0;i++)
//        {
//            extracted_path.add(visited.get(keys.get(i)));
//        }
//        for (UUSearchNode name: visited.keySet()){
//
//            String key =name.toString();
//            String value = visited.get(name).toString();
////            System.out.println(key + " " + value);
//
//
//        }
//        extracted_path.add(node);

        extracted_path.add(node);
//        System.out.println("node = " + node);
        while(node != startNode)
        {
            for(Map.Entry<UUSearchNode,UUSearchNode> entry : visited.entrySet())
            {
                if(entry.getKey().equals(node))
                {
//                    System.out.println("node = " + node);
                    node = entry.getValue();
                    extracted_path.add(node);
                    break;
                }
            }
        }
//        extracted_path.add(startNode);
        return extracted_path;
    }

    public List<UUSearchNode> depthFirstMemoizingSearch(int maxDepth) {
        resetStats();

        HashMap<UUSearchNode, Integer> visited = new HashMap<>();
        return dfsrm(startNode, visited, 0, maxDepth);


    }

    // recursive memoizing dfs. Private, because it has the extra
    // parameters needed for recursion.
    private List<UUSearchNode> dfsrm(UUSearchNode currentNode, HashMap<UUSearchNode, Integer> visited,
                                     int depth, int maxDepth) {

        // keep track of stats; these calls charge for the current node
        updateMemory(visited.size());
        incrementNodeCount();

        List<UUSearchNode> path_memoized = new ArrayList<>();
        path_memoized.add(currentNode);
        visited.put(currentNode, depth);
        if(depth > maxDepth)                                    // BASE CASE
            return null;
        if(currentNode.goalTest())                              // BASE CASE
        {
//            System.out.println("reach - dfs");
            return path_memoized;
        }


        ArrayList<UUSearchNode> successors;

        // Comments *must* clearly show the "base case" and "recursive case" that any recursive function has.

//        path_memoized.add(currentNode);
        successors = currentNode.getSuccessors();
        for(int i=0;i<successors.size();i++)
        {
            if(!visited.containsKey(successors.get(i)))         // RECURSIVE CASE
            {
                List<UUSearchNode> temppath = dfsrm(successors.get(i), visited, depth+1, maxDepth);
                if(temppath != null)
                {
                    path_memoized.addAll(temppath);
                    return path_memoized;
                }
            }
        }
        return null;
    }


    // set up the iterative deepening search, and make use of dfsrpc
    public List<UUSearchNode> IDSearch(int maxDepth)
    {
        resetStats();

        HashSet<UUSearchNode> currentPath = new HashSet<UUSearchNode>();
        for(int i=0;i<=maxDepth;i++)
        {
            List<UUSearchNode> path = dfsrpc(startNode, currentPath, 0, i);
            currentPath.clear();
            if(path != null)
                return path;
//            path.clear();
        }
        return null;
    }

    // set up the depth-first-search (path-checking version),
    //  but call dfspc to do the real work
    public List<UUSearchNode> depthFirstPathCheckingSearch(int maxDepth) {
        resetStats();

        // I wrote this method for you.  Nothing to do.

        HashSet<UUSearchNode> currentPath = new HashSet<UUSearchNode>();

        return dfsrpc(startNode, currentPath, 0, maxDepth);

    }

    // recursive path-checking dfs. Private, because it has the extra
    // parameters needed for recursion.
    private List<UUSearchNode> dfsrpc(UUSearchNode currentNode, HashSet<UUSearchNode> currentPath,
                                      int depth, int maxDepth) {


        updateMemory(currentPath.size());
        incrementNodeCount();


       List<UUSearchNode> path_checking = new ArrayList<>();
        path_checking.add(currentNode);

        if(depth > maxDepth)                    // BASE CASE
            return null;

        if(currentNode.goalTest())              // BASE CASE
        {
            return path_checking;
        }


        ArrayList<UUSearchNode> successors;

        // Comments *must* clearly show the "base case" and "recursive case" that any recursive function has.

//        path_checking.clear();
        currentPath.add(currentNode);
        successors = currentNode.getSuccessors();
        for(int i=0;i<successors.size();i++)
        {
            if(!currentPath.contains(successors.get(i)))    // RECURSIVE CASE
            {
                List<UUSearchNode> temppath = dfsrpc(successors.get(i), currentPath, depth+1, maxDepth);
                if(temppath != null)
                {
                    path_checking.addAll(temppath);
                    return path_checking;
                }
            }
        }
        currentPath.remove(currentNode);
        return null;
    }

    protected void resetStats() {
        nodesExplored = 0;
        maxMemory = 0;
    }

    protected void printStats() {
        System.out.println("Nodes explored during last search:  " + nodesExplored);
        System.out.println("Maximum memory usage during last search " + maxMemory);
    }

    protected void updateMemory(int currentMemory) {
        maxMemory = Math.max(currentMemory, maxMemory);
    }

    protected void incrementNodeCount() {
        nodesExplored++;
    }

}