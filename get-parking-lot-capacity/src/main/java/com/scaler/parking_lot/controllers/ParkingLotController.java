package com.scaler.parking_lot.controllers;

import com.scaler.parking_lot.dtos.GetParkingLotCapacityRequestDto;
import com.scaler.parking_lot.dtos.GetParkingLotCapacityResponseDto;
import com.scaler.parking_lot.dtos.Response;
import com.scaler.parking_lot.dtos.ResponseStatus;
import com.scaler.parking_lot.exceptions.GetParkingLotRequestValidationException;
import com.scaler.parking_lot.models.ParkingFloor;
import com.scaler.parking_lot.models.VehicleType;
import com.scaler.parking_lot.services.ParkingLotService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    public ParkingLotController(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    public GetParkingLotCapacityResponseDto getParkingLotCapacity(
            GetParkingLotCapacityRequestDto getParkingLotCapacityRequestDto) {


        GetParkingLotCapacityResponseDto responseDto = new GetParkingLotCapacityResponseDto();

        try {
            System.out.println("ParkingLotController: parkingLotId " + getParkingLotCapacityRequestDto.getParkingLotId()
                    + " floors: " + getParkingLotCapacityRequestDto.getParkingFloorIds()
                    + " vehicleTypes: " + getParkingLotCapacityRequestDto.getVehicleTypes());
            List<VehicleType> vehicleTypeList = new ArrayList<>();
            for(String vehicleType : getParkingLotCapacityRequestDto.getVehicleTypes()){
                vehicleTypeList.add(VehicleType.valueOf(vehicleType));
            }

            Map<ParkingFloor, Map<String, Integer>> map =
                    parkingLotService.getParkingLotCapacity(getParkingLotCapacityRequestDto.getParkingLotId(),
                            getParkingLotCapacityRequestDto.getParkingFloorIds(),
                            vehicleTypeList);

            responseDto.setCapacityMap(map);
            responseDto.setResponse(new Response(ResponseStatus.SUCCESS,""));

        }catch (Exception e){
            responseDto.setResponse(new Response(ResponseStatus.FAILURE,e.getMessage()));
        }

        System.out.println("ParkingLotController: status " + responseDto.getResponse().getResponseStatus().toString());
        return responseDto;
    }

}
