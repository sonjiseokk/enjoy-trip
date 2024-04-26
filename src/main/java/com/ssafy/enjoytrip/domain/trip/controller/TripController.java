package com.ssafy.enjoytrip.domain.trip.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.enjoytrip.domain.trip.controller.request.TripSearchCondition;
import com.ssafy.enjoytrip.domain.trip.model.TripDescriptionDto;
import com.ssafy.enjoytrip.domain.trip.model.TripDto;
import com.ssafy.enjoytrip.domain.trip.service.TripService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/trip")
public class TripController {
	private final TripService tripService;
	
	@PostMapping(value = "/search")
	public ResponseEntity<?> tripList(@RequestBody TripSearchCondition con) {
		try {
			List<TripDto> list = tripService.searchTrip(con);
			if(list != null && !list.isEmpty()) {
				return new ResponseEntity<List<TripDto>>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@PostMapping(value = "/view")
	public ResponseEntity<?> tripDescription(@RequestParam(value = "contentId") String contentId) {
		try {
			TripDescriptionDto tripDescriptionDto = tripService.searchTripDescription(contentId);
			if(tripDescriptionDto != null) {
				return new ResponseEntity<TripDescriptionDto>(tripDescriptionDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return null;
		}
		
	}
}
