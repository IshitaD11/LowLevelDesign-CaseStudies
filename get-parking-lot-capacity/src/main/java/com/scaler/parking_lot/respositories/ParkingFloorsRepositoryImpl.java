package com.scaler.parking_lot.respositories;

import com.scaler.parking_lot.models.ParkingFloor;

import java.util.HashMap;
import java.util.Optional;

public class ParkingFloorsRepositoryImpl implements ParkingFloorsRepository{

    private HashMap<Long,ParkingFloor> parkingFloors = new HashMap<>();

    @Override
    public Optional<ParkingFloor> getParkingFloorsById(Long parkingFloorId) {
        if(!parkingFloors.containsKey(parkingFloorId))
            Optional.empty();
        return Optional.of(parkingFloors.get(parkingFloorId));
    }

    @Override
    public ParkingFloor save(ParkingFloor parkingFloor) {
        parkingFloors.put(parkingFloor.getId(),parkingFloor);
        return parkingFloor;
    }
}
