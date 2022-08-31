import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;

class Solution {
    public static void main(String[] args) {
        System.out.println(1 << 10);
        var s = 1;
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