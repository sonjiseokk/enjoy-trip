package com.ssafy.enjoytrip.domain.trip.service;

import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.domain.trip.mapper.TripMapper;
import com.ssafy.enjoytrip.domain.trip.model.GugunDto;
import com.ssafy.enjoytrip.domain.trip.model.SidoDto;
import com.ssafy.enjoytrip.domain.trip.model.TripDto;

@Service
public class TripServiceImpl implements TripService {
	
	private final TripMapper tripMapper;
	
	@Autowired
	private TripServiceImpl(TripMapper tripMapper) {	
		this.tripMapper = tripMapper;
	}
	
	private static Double minDistance;
	private static int[] shortestPath;
	
	// 기본 info list
	@Override
	public JSONObject searchTrip(String contentTypeId, int sidoCode, int gugunCode) throws Exception {
		
		List<TripDto> lists = tripMapper.searchTrip(contentTypeId, sidoCode, gugunCode);
		
		JSONObject json = new JSONObject();
		
		JSONArray jsonArray = new JSONArray();
		for(TripDto list : lists) {
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("content_id", list.getContentId());
			jsonObj.put("content_type_id", list.getContentTypeId());
			jsonObj.put("title", list.getTitle());
			jsonObj.put("addr1", list.getAddr1());
			jsonObj.put("addr2", list.getAddr2());
			jsonObj.put("zipcode", list.getZipcode());
			jsonObj.put("tel", list.getTel());
			jsonObj.put("first_image", list.getFirstImage());
			jsonObj.put("first_image2", list.getFirstImage2());
			jsonObj.put("readcount", list.getReadcount());
			jsonObj.put("latitude", list.getLatitude());
			jsonObj.put("longitude", list.getLongitude());
			jsonObj.put("mlevel", list.getMlevel());
			
			jsonArray.put(jsonObj);
		}
		
		json.put("body", jsonArray);
		
		return json;
	}

	// 기본 info + overView
	@Override
	public TripDto searchTripDetail(String contentTypeId) throws Exception {
		return tripMapper.searchTripDetail(contentTypeId);
	}
	
	@Override
	public JSONObject searchTripJSON(String contentTypeId) throws Exception {
		TripDto tripDto =  tripMapper.searchTripDetail(contentTypeId);
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("content_id", tripDto.getContentId());
		jsonObj.put("content_type_id", tripDto.getContentTypeId());
		jsonObj.put("title", tripDto.getTitle());
		jsonObj.put("addr1", tripDto.getAddr1());
		jsonObj.put("addr2", tripDto.getAddr2());
		jsonObj.put("zipcode", tripDto.getZipcode());
		jsonObj.put("tel", tripDto.getTel());
		jsonObj.put("first_image", tripDto.getFirstImage());
		jsonObj.put("first_image2", tripDto.getFirstImage2());
		jsonObj.put("readcount", tripDto.getReadcount());
		jsonObj.put("latitude", tripDto.getLatitude());
		jsonObj.put("longitude", tripDto.getLongitude());
		jsonObj.put("mlevel", tripDto.getMlevel());
		
		return jsonObj;
	}

	@Override
	public JSONObject getSido() throws Exception {
		List<SidoDto> sidoList = tripMapper.getSido();
		
		JSONObject json = new JSONObject();
		
		JSONArray jsonArray = new JSONArray();
		for(SidoDto sido : sidoList) {
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("sido_code", sido.getSidoCode());
			jsonObj.put("sido_name", sido.getSidoName());
			
			jsonArray.put(jsonObj);
		}
		
		json.put("body", jsonArray);
		
		return json;
	}

	@Override
	public JSONObject getGugun(int sidoCode) throws Exception {
		
		List<GugunDto> gugunList = tripMapper.getGugun(sidoCode);
		
		JSONObject json = new JSONObject();
		
		JSONArray jsonArray = new JSONArray();
		for(GugunDto gugun : gugunList) {
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("gugun_code", gugun.getGugunCode());
			jsonObj.put("gugun_name", gugun.getGugunName());
			
			jsonArray.put(jsonObj);
		}
		
		json.put("body", jsonArray);
		
		return json;
	}

	@Override
	public JSONObject detailView(String contentId) throws Exception {
		
		TripDto trip = tripMapper.searchTripDetail(contentId);
		String[] detail = tripMapper.searchOverview(contentId);
		
		trip.setOverView(detail[0]);
		trip.setHomepage(detail[1]);
		JSONObject json = new JSONObject();
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("content_id", trip.getContentId());
		jsonObj.put("content_type_id", trip.getContentTypeId());
		jsonObj.put("title", trip.getTitle());
		jsonObj.put("addr1", trip.getAddr1());
		jsonObj.put("addr2", trip.getAddr2());
		jsonObj.put("zipcode", trip.getZipcode());
		jsonObj.put("tel", trip.getTel());
		jsonObj.put("first_image", trip.getFirstImage());
		jsonObj.put("first_image2", trip.getFirstImage2());
		jsonObj.put("readcount", trip.getReadcount());
		jsonObj.put("latitude", trip.getLatitude());
		jsonObj.put("longitude", trip.getLongitude());
		jsonObj.put("mlevel", trip.getMlevel());
		jsonObj.put("overview", trip.getOverView());
		jsonObj.put("hompage", trip.getHomepage());
		
		json.put("body", jsonObj);
		
		return json;
	}
	@Override
	public JSONObject optimalDist(String contentId, List<TripDto> tripList) throws Exception {
		int n = tripList.size();
		minDistance = Double.MAX_VALUE;
		shortestPath = new int[n];
		
		// 시작점 찾기
		int startIndex = -1;
		for(int i = 0; i < n; i++) {
			if(tripList.get(i).getContentId() == Integer.parseInt(contentId)) {
				startIndex = i;
				break;
			}
		}
		
		// 거리 입력하기
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
        
        JSONObject jsonArray = new JSONObject();
        
        for(int i = 0; i < n; i++) {
        	JSONObject jsonObj = new JSONObject();
        	jsonObj.put("latitude", tripList.get(shortestPath[i]).getLatitude());
        	jsonObj.put("longitude", tripList.get(shortestPath[i]).getLongitude());
        	jsonArray.put(Integer.toString(i), jsonObj);
        }
		
		return jsonArray;
	}
	
	public static double getDistance(double lat1, double lon1, double lat2, double lon2) {
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

	public static void dfs(double[][] graph, boolean[] visited, int cur, double distance, int cnt, int n, int[] path) {
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