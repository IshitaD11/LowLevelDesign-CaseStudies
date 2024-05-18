package com.scaler.parking_lot.respositories;

import com.scaler.parking_lot.models.ParkingFloor;

import java.util.Optional;

public interface ParkingFloorsRepository {

    Optional<ParkingFloor> getParkingFloorsById(Long parkingFloorId);

    public ParkingFloor save(ParkingFloor parkingFloor);
}
