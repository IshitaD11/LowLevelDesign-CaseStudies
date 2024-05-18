package com.scaler.parking_lot.respositories;

import com.scaler.parking_lot.models.Gate;
import com.scaler.parking_lot.models.ParkingLot;

import java.util.HashMap;
import java.util.Optional;

public class ParkingLotRepositoryImpl implements ParkingLotRepository{
    HashMap<Long,ParkingLot> parkingLots = new HashMap<>();
    HashMap<Long,ParkingLot> parkingLotGateMap = new HashMap<>();

    @Override
    public Optional<ParkingLot> getParkingLotByGateId(long gateId) {
        if(!parkingLotGateMap.containsKey(gateId))
            return Optional.empty();
        return Optional.of(parkingLotGateMap.get(gateId));
    }

    @Override
    public Optional<ParkingLot> getParkingLotById(long id) {
        if(!parkingLots.containsKey(id))
            return Optional.empty();
        return Optional.of(parkingLots.get(id));
    }

    @Override
    public ParkingLot save(ParkingLot parkingLot) {
        parkingLots.put(parkingLot.getId(),parkingLot);
        for(Gate gate : parkingLot.getGates()){
            parkingLotGateMap.put(gate.getId(), parkingLot);
        }
        return parkingLot;
    }
}
