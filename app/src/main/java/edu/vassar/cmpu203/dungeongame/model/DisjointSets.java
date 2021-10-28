package edu.vassar.cmpu203.dungeongame.model;
import java.util.ArrayList;

public class DisjointSets{
    ArrayList<ArrayList<Integer>> disjointSets;
    public DisjointSets(){
        disjointSets = new ArrayList<>();
        ArrayList<Integer> disjointSubSet = new ArrayList<>();
        //in order for this to work, the disjoint begins with a single empty list within it
        disjointSets.add(disjointSubSet); }
    public boolean addPair(Pair n){
        /* In this method the input pair is taken and the method sees if either, both, or neither of the mazeArray in the pair
         *  Are already a part of one of the sets in the disjoint */
        boolean validAdd = true;
        if (n == null) { return false;}
        //this boolean is used to check if a given pair will be added to the adjacency table later
        int node1 = n.node1;
        int node2 = n.node2;
        int node1Pos = -1;
        int node2Pos = -1;
        ArrayList<Integer> newSet = new ArrayList<>();
            for (int i = 0; i < disjointSets.size(); i++) {
                for (int j = 0; j < disjointSets.get(i).size(); j++) {
                    /* This iterates through all of the elements of each list and if the node id is present within
                     *  a given list, the index of that list (not its position within the sublist) is recorded
                     *  if it is totally absent, its position will be a -1 */
                    if (disjointSets.get(i).get(j) == node1) { node1Pos = i; }
                    if (disjointSets.get(i).get(j) == node2) { node2Pos = i; } } }
            //Case 1: Neither node is already present in a sublist. A new sublist is created and both are put into it
            if (node1Pos == -1 && node2Pos == -1) {
                newSet.add(node1);
                newSet.add(node2);
                disjointSets.add(newSet);}
            //Case 2: Both are already present and are present in the same sublist
            else if (node1Pos == node2Pos) { validAdd = false; }
            //Case 3: node1 is not in a sublist, but node2 is. node1 is added to the list with node2
            else if (node1Pos == -1 && node2Pos > -1) {
                disjointSets.get(node2Pos).add(node1);}
            //Case4: the same as Case3 but with node2 not being in a list instead of 1
            else if (node2Pos == -1 && node1Pos > -1) {
                disjointSets.get(node1Pos).add(node2);}
            //Case5: both mazeArray are present, but in different sublists. Their respective sublists are merged
            else if (node1Pos != node2Pos) {
                disjointSets.get(node1Pos).addAll(disjointSets.get(node2Pos));
                disjointSets.remove(node2Pos);}
            disjointSets.trimToSize();
            return validAdd;
        }

}