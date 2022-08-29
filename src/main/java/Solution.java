import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;

class Solution {
    public static void main(String[] args) {
        final Solution solution = new Solution();
        int[][] item1 = new int[][] {{15,5},{2,6},{5,3},{14,8},{12,4},{19,6},{25,4},{13,4},{9,1}};
        int[][] item2 = new int[][] {{15,9},{2,4},{5,2},{14,4},{12,3},{19,1},{25,7},{13,6},{9,9}};
        solution.mergeSimilarItems(item1, item2);
    }
    public List<List<Integer>> mergeSimilarItems(int[][] items1, int[][] items2) {
        Map<Integer, Integer> value_weight = new HashMap();
        for (int[] temp : items1)
            value_weight.put(temp[0], value_weight.getOrDefault(temp[0], 0) + temp[1]);
        for (int[] temp : items2)
            value_weight.put(temp[0], value_weight.getOrDefault(temp[0], 0) + temp[1]);
        Set<Integer> keys = value_weight.keySet();
        Iterator<Integer> iterator = keys.iterator();
        Deque<List<Integer>> res = new LinkedList();
        while (iterator.hasNext()) {
            int key = iterator.next();
            List<Integer> temp_list = new LinkedList();
            temp_list.add(key);
            temp_list.add(value_weight.get(key));
            if (res.size() == 0)
                res.add(temp_list);
            else {
                int pre_key = res.getFirst().get(0);
                if (key > pre_key)
                    res.addLast(temp_list);
                else
                    res.addFirst(temp_list);
            }
            System.out.println(res);
        }
        return (List<List<Integer>>)res;
    }
}