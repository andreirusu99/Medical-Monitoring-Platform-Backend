package com.andrei.ds.services;

import com.andrei.ds.*;
import com.andrei.ds.DTOs.TreatmentDTO;
import com.andrei.ds.DTOs.builders.TreatmentBuilder;
import com.andrei.ds.entities.Treatment;
import com.andrei.ds.services.TreatmentService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@GrpcService
public class PillBoxService extends PillBoxServiceGrpc.PillBoxServiceImplBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(PillBoxService.class);

    private final TreatmentService treatmentService;

    @Autowired
    public PillBoxService(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @Override
    public void registerMedsTaken(
            MedicationTakenRequest request,
            StreamObserver<MedicationTakenResponse> responseObserver
    ) {
        LOGGER.info("Patient {} has taken {}", request.getPatientId(), request.getDrugName());
        MedicationTakenResponse response = MedicationTakenResponse.newBuilder()
                .setPatientId(request.getPatientId())
                .setDrugName(request.getDrugName())
                .setAcknowledged(true)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void registerMedsNotTaken(
            MedicationNotTakenRequest request,
            StreamObserver<MedicationNotTakenResponse> responseObserver) {
        LOGGER.info("Patient {} has NOT taken {}", request.getPatientId(), request.getDrugName());
        MedicationNotTakenResponse response = MedicationNotTakenResponse.newBuilder()
                .setPatientId(request.getPatientId())
                .setDrugName(request.getDrugName())
                .setAcknowledged(true)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void downloadMedicationPlan(MedicationPlanRequest request, StreamObserver<MedicationPlanResponse> responseObserver) {
        LOGGER.info("Medication plan for patient {} requested", request.getPatientId());
        List<TreatmentDTO> treatments = treatmentService.retrievePatientTreatments(UUID.fromString(request.getPatientId()), UUID.fromString(request.getPatientId()), "");
        ArrayList<Treatment> activeTreatments = new ArrayList<>();

        treatments.forEach(treatment -> {
            String start = treatment.getStartDate();
            String end = treatment.getEndDate();
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            try {
                long startDate = format.parse(start).getTime();
                long endDate = format.parse(end).getTime();
                long current = Calendar.getInstance().getTimeInMillis();

                if (startDate < current && current < endDate) {
                    activeTreatments.add(TreatmentBuilder.toEntity(treatment));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        LOGGER.info("Patient {} has {} active treatment/s", request.getPatientId(), activeTreatments.size());

        MedicationPlanResponse.Builder responseBuilder = MedicationPlanResponse.newBuilder()
                .setPatientId(request.getPatientId());

        activeTreatments.forEach(treatment ->
                treatment.getDosage().forEach((drug, dosage)
                        -> responseBuilder.addItems(
                        MedicationItem.newBuilder()
                                .setDrugName(drug)
                                .setDosage(dosage)
                )));
        MedicationPlanResponse response = responseBuilder.build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
