package com.example.HospitalInfoSystem.Dto;

public class PrescriptionDto {
    private String name;
    private String dosage;
    private String duration;

    public PrescriptionDto(String name, String dosage, String duration) {
        this.name = name;
        this.dosage = dosage;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public String getDosage() {
        return dosage;
    }

    public String getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return name + " (" + dosage + ", " + duration + ")";
    }
}
