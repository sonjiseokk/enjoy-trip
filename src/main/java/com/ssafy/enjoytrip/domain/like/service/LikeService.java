package com.ssafy.enjoytrip.domain.like.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ssafy.enjoytrip.domain.trip.service.AttractionInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.domain.like.mapper.LikeMapper;
import com.ssafy.enjoytrip.domain.like.model.LikeDto;
import com.ssafy.enjoytrip.domain.trip.mapper.AttractionInfoMapper;
import com.ssafy.enjoytrip.domain.trip.model.AttractionInfoDto;

@Service
@RequiredArgsConstructor
public class LikeService {
	private final AttractionInfoService attractionInfoService;
	private final LikeMapper likeMapper;
	private final AttractionInfoMapper attractionInfoMapper;
	
	Double minDistance;
	int[] shortestPath;
	public boolean likeCheck(int contentId, final String userId) throws Exception {
		List<LikeDto> likeDtos = likeMapper.listLike(userId);
		for (LikeDto likeDto : likeDtos) {
			if (likeDto.getContentId() == contentId) {
				return true;
			}
		}
		return false;
	}

	public void registLike(String userId, int contentId) throws Exception {
		LikeDto likeDto = new LikeDto(userId, contentId);
		likeMapper.registLike(likeDto);
	}


	public List<AttractionInfoDto> listLike(String userId) throws Exception {
		try {

			List<LikeDto> myLikes = likeMapper.listLike(userId);
			List<AttractionInfoDto> likeAttractions = new ArrayList<>();
			for (LikeDto myLike : myLikes) {
				AttractionInfoDto infoDto = attractionInfoService.getTrip(myLike.getContentId());
				likeAttractions.add(infoDto);
			}
			return likeAttractions;
		} catch (Exception e) {
			e.fillInStackTrace();
			throw new Exception("관심 목록을 가져오는 과정에서 에러 발생");
		}
	}


	public void deleteLike(int contentId, String userId) throws Exception {
		LikeDto likeDto = new LikeDto(userId, contentId);
		likeMapper.deleteLike(likeDto);
	}


	public List<AttractionInfoDto> optimalPath(String userId, int contentId) throws Exception {
		List<LikeDto> likeList = likeMapper.listLike(userId);
		List<AttractionInfoDto> tripList = new ArrayList<>();
		
		for(LikeDto likeDto : likeList) {
			tripList.add(attractionInfoMapper.getTrip(likeDto.getContentId()));
		}
		
		int n = tripList.size();
		minDistance = Double.MAX_VALUE;
		shortestPath = new int[n];
		// 시작점 찾기
		int startIndex = -1;
		for(int i = 0; i < n; i++) {
			if(tripList.get(i).getContentId() == contentId) {
				startIndex = i;
				break;
			}
		}
		// 비어있으면 null
		if(likeList.isEmpty()) {
			return null;
		}
		
		double[][] distance = new double[n][n];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++){
				if(i != j) {
					distance[i][j] = getDistance(tripList.get(i).getLatitude(), tripList.get(i).getLongitude(), tripList.get(j).getLatitude(), tripList.get(j).getLongitude());
				}
			}
		}
		// 백트래킹으로 최적 경로 탐색
		boolean[] visited = new boolean[n];
		visited[startIndex] = true; // 시작 노드 방문 처리
		int[] path = new int[n]; // 경로 저장 배열
		path[0] = startIndex; // 시작 노드는 0번째 인덱스에 저장
		
		dfs(distance, visited, startIndex, 0, 1, n, path);
		
		List<AttractionInfoDto> sortedTripList = new ArrayList<>();
		for(int i : shortestPath){
			sortedTripList.add(tripList.get(i));
		}
		
		return sortedTripList;
	}
	
	public double getDistance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371e3; // 지구의 반지름 (m)
        double r1 = Math.toRadians(lat1);
        double r2 = Math.toRadians(lat2);
        double latgap = Math.toRadians(lat2 - lat1);
        double longap = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latgap / 2) * Math.sin(latgap / 2) +
                   Math.cos(r1) * Math.cos(r2) *
                   Math.sin(longap / 2) * Math.sin(longap / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // 결과는 미터 단위
    }

	public void dfs(double[][] graph, boolean[] visited, int cur, double distance, int cnt, int n, int[] path) {
		 if (cnt == n) { // 모든 노드를 방문했을 때
            if (distance < minDistance) { // 현재 경로가 최단 경로보다 짧은 경우
                minDistance = distance;
                shortestPath = Arrays.copyOf(path, path.length); // 최단 경로 저장
            }
            return;
        }

        for (int next = 0; next < n; next++) {
            if (!visited[next] && graph[cur][next] != 0) { // 방문하지 않은 노드이고, 갈 수 있는 경우
                visited[next] = true; // 노드 방문 처리
                path[cnt] = next; // 경로에 노드 추가
                dfs(graph, visited, next, distance + graph[cur][next], cnt + 1, n, path); // 다음 노드로 이동
                visited[next] = false; // 백트래킹
            }
        }
    }


}
