package com.scaler.parking_lot.services;

import com.scaler.parking_lot.exceptions.InvalidParkingLotException;
import com.scaler.parking_lot.models.*;
import com.scaler.parking_lot.respositories.ParkingFloorsRepository;
import com.scaler.parking_lot.respositories.ParkingLotRepository;

import java.util.*;

public class ParkingLotServiceImpl implements ParkingLotService{

    private ParkingLotRepository parkingLotRepository;

    public ParkingLotServiceImpl(ParkingLotRepository repo){
        parkingLotRepository = repo;
    }

    @Override
    public Map<ParkingFloor, Map<String, Integer>> getParkingLotCapacity(
            long parkingLotId, List<Long> parkingFloors, List<VehicleType> vehicleTypes) throws InvalidParkingLotException, InvalidParkingLotException {

        // Map<ParkingFloor, Map<VehicleType, Integer>>
        Map<ParkingFloor, Map<String, Integer>> parkingFloorMap = new HashMap<>();

        Optional<ParkingLot> optionalParkingLot = parkingLotRepository.getParkingLotById(parkingLotId);

        if(optionalParkingLot.isEmpty())
            throw new InvalidParkingLotException("Not a Valid parikingLotId");

        ParkingLot parkingLot = optionalParkingLot.get();
        HashSet<Long> allowedParkingFloors = new HashSet<>();

        if(parkingFloors.isEmpty()){
            for(ParkingFloor parkingFloor: parkingLot.getParkingFloors()){
                allowedParkingFloors.add(parkingFloor.getId());
            }
        }
        else{
            allowedParkingFloors = new HashSet<>(parkingFloors);
        }


        HashSet<VehicleType> allowedVehicleType ;
        if(vehicleTypes.isEmpty())
            allowedVehicleType = new HashSet<>(EnumSet.allOf(VehicleType.class));
        else
            allowedVehicleType = new HashSet<>(vehicleTypes);

        for(ParkingFloor parkingFloor: parkingLot.getParkingFloors()){
            if(!allowedParkingFloors.contains(parkingFloor.getId()))
                continue;;

            Map<String, Integer> vehicleTypeMap = new HashMap<>();
            for(ParkingSpot parkingSpot : parkingFloor.getSpots()){
                if(allowedVehicleType.contains(parkingSpot.getSupportedVehicleType()) &&
                    parkingSpot.getStatus().equals(ParkingSpotStatus.AVAILABLE)
                ){
                    String vehicleType = parkingSpot.getSupportedVehicleType().toString();
                    vehicleTypeMap.put(vehicleType,
                            vehicleTypeMap.getOrDefault(vehicleType,0)+1);
                }
            }

            parkingFloorMap.put(parkingFloor, vehicleTypeMap);
        }

        return parkingFloorMap;
    }
}
