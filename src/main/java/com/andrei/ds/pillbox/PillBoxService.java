package com.andrei.ds.pillbox;

import com.andrei.ds.MedicationTakenRequest;
import com.andrei.ds.MedicationTakenResponse;
import com.andrei.ds.PillBoxServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class PillBoxService extends PillBoxServiceGrpc.PillBoxServiceImplBase {
    @Override
    public void takeMeds(
            MedicationTakenRequest request,
            StreamObserver<MedicationTakenResponse> responseObserver
    ) {
        MedicationTakenResponse response = MedicationTakenResponse.newBuilder()
                .setConfirmation("Patient with id " + request.getPatientId() + " has taken " + request.getDrugName())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
