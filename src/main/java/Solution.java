import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class Solution {
    public static void main(String[] args) {
        final Solution solution = new Solution();
        String[] words = {"c","b","a","c","a","a","a","b","c"};
        solution.findSubstring(
                "bcabbcaabbccacacbabccacaababcbb", words);
    }
    public List<Integer> findSubstring(String s, String[] words) {
        Map<String, Integer> res = new HashMap();
        List<Integer> result = new LinkedList();
        for (String word : words) {
            res.put(word, res.getOrDefault(word, 0) + 1);
        }
        int valid = res.size();
        int count = 0;
        int w = words[0].length();
        int l = s.length();
        int m = words.length;
        for (int i = 0; i < w; i++) {
            Map<String, Integer> win = new HashMap();
            count = 0;
            int left = i;
            int right = i;
            while (right + w <= l) {
                String put_word = s.substring(right, right + w);
                right += w;
                if (res.containsKey(put_word)) {
                    win.put(put_word, win.getOrDefault(put_word, 0) + 1);
                    if (win.get(put_word) == res.get(put_word)) {
                        count++;
                        System.out.println(count + "right" + right);
                    } else if (win.get(put_word) > res.get(put_word)) {
                        while (left + w < right) {
                            String out = s.substring(left, left + w);
                            left += w;
                            win.put(out, win.get(out) - 1);
                            if (out.equals(put_word))
                                break;
                            else {
                                count--;
                            }
                        }
                    }
                    if (count == valid) {
                        result.add(left);
                        left += w;
                        win.put(s.substring(left - w, left), win.get(s.substring(left - w, left)) - 1);
                        count--;
                    }
                } else {
                    left = right;
                    count = 0;
                    win = new HashMap<String, Integer>();
                }
            }
        }
        return result;
    }
}
