package com.ssafy.enjoytrip.global.util;

import com.ssafy.enjoytrip.domain.board.entity.Board;

import java.util.ArrayList;
import java.util.List;

public class Kmp {
    static int[] makeTable(String pattern) {
        int n = pattern.length();
        int[] table = new int[n];

        int idx=0;
        for(int i=1; i<n; i++) {
            // 일치하는 문자가 발생했을 때(idx>0), 연속적으로 더 일치하지 않으면 idx = table[idx-1]로 돌려준다.
            while(idx>0 && pattern.charAt(i) != pattern.charAt(idx)) {
                idx = table[idx-1];
            }

            if(pattern.charAt(i) == pattern.charAt(idx)) {
                idx += 1;
                table[i] = idx;
            }
        }
        return table;
    }

    static boolean kmp(String parent, String pattern, int[] table) {
        int n1 = parent.length();
        int n2 = pattern.length();

        int idx = 0; // 현재 대응되는 글자 수
        for(int i=0; i< n1; i++) {
            // idx번 글자와 짚더미의 해당 글자가 불일치할 경우,
            // 현재 대응된 글자의 수를 table[idx-1]번으로 줄인다.
            while(idx>0 && parent.charAt(i) != pattern.charAt(idx)) {
                idx = table[idx-1];
            }
            // 글자가 대응될 경우
            if(parent.charAt(i) == pattern.charAt(idx)) {
                if(idx == n2-1) {
                    return true;
                }else {
                    idx += 1;
                }
            }
        }
        return false;
    }

    public static List<Board> multiKmp(List<Board> parents, String[] keywords){
        List<int[]> tableList = new ArrayList<>();
        List<Board> result = new ArrayList<>();
        for (String keyword: keywords) {
            tableList.add(makeTable(keyword));
        }

        for (int i = 0; i < parents.size(); i++) {
            boolean isCorrect = true;
            for (int j = 0; j < keywords.length; j++) {
                if(!kmp(parents.get(i).getSubject(), keywords[j], tableList.get(j))) {
                    isCorrect = false;
                    break;
                }
            }
            if (isCorrect) {
                result.add(parents.get(i));
            }
        }
        return result;
    }
}